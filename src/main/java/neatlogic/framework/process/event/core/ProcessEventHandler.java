/*
 * Copyright(c) 2021 TechSure Co., Ltd. All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package neatlogic.framework.process.event.core;

import neatlogic.framework.asynchronization.thread.CodeDriverThread;
import neatlogic.framework.asynchronization.threadpool.CachedThreadPool;
import neatlogic.framework.process.constvalue.ProcessTaskEvent;
import neatlogic.framework.process.dao.mapper.ProcessEventMapper;
import neatlogic.framework.process.dao.mapper.ProcessTaskMapper;
import neatlogic.framework.process.dto.ProcessTaskStepVo;
import neatlogic.framework.process.exception.core.ProcessTaskRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProcessEventHandler {
    static Logger logger = LoggerFactory.getLogger(ProcessEventHandler.class);

    private static final ThreadLocal<List<CodeDriverThread>> RUNNABLES = new ThreadLocal<>();

    private static ProcessEventMapper processEventMapper;

    private static ProcessTaskMapper processTaskMapper;

    @Autowired
    public void setProcessEventMapper(ProcessEventMapper _processEventMapper) {
        processEventMapper = _processEventMapper;
    }

    @Autowired
    public void setProcessTaskMapper(ProcessTaskMapper _processTaskMapper) {
        processTaskMapper = _processTaskMapper;
    }

    public synchronized static void doEvent(ProcessTaskEvent event, Long flowJobStepId) {
        ProcessEventHandler.EventRunner runner = new ProcessEventHandler.EventRunner(event, flowJobStepId);
        if (!TransactionSynchronizationManager.isSynchronizationActive()) {
            CachedThreadPool.execute(runner);
            return;
        }
        List<CodeDriverThread> runnableActionList = RUNNABLES.get();
        if (runnableActionList == null) {
            runnableActionList = new ArrayList<>();
            RUNNABLES.set(runnableActionList);
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    List<CodeDriverThread> runnableActionList = RUNNABLES.get();
                    for (CodeDriverThread runnable : runnableActionList) {
                        CachedThreadPool.execute(runnable);
                    }
                }

                @Override
                public void afterCompletion(int status) {
                    RUNNABLES.remove();
                }
            });
        }
        runnableActionList.add(runner);

    }

    static class EventRunner extends CodeDriverThread {
        private final Long processTaskStepId;
        private final ProcessTaskEvent event;

        public EventRunner(ProcessTaskEvent _event, Long _processTaskStepId) {
            super("PROCESSTASK-EVENTHANDLER-" + _processTaskStepId);
            event = _event;
            processTaskStepId = _processTaskStepId;
        }

        @Override
        public void execute() {
            ProcessTaskStepVo processTaskStepVo = processTaskMapper.getProcessTaskStepBaseInfoById(processTaskStepId);
            if (processTaskStepVo == null) {
                throw new ProcessTaskRuntimeException("找不到步骤信息，processTaskStepId：" + processTaskStepId);
            }
        }

    }
}