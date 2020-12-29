package codedriver.framework.process.score.schedule.plugin;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections4.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;

import codedriver.framework.asynchronization.threadlocal.TenantContext;
import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.common.constvalue.SystemUser;
import codedriver.framework.process.constvalue.ProcessTaskStatus;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dao.mapper.WorktimeMapper;
import codedriver.framework.process.dao.mapper.score.ProcessTaskScoreMapper;
import codedriver.framework.process.dao.mapper.score.ScoreTemplateMapper;
import codedriver.framework.process.dto.ProcessTaskVo;
import codedriver.framework.process.dto.score.ProcessTaskAutoScoreVo;
import codedriver.framework.process.dto.score.ProcessTaskScoreVo;
import codedriver.framework.process.dto.score.ScoreTemplateDimensionVo;
import codedriver.framework.process.dto.score.ScoreTemplateVo;
import codedriver.framework.process.stephandler.core.ProcessStepHandlerFactory;
import codedriver.framework.process.util.WorkTimeUtil;
import codedriver.framework.scheduler.core.JobBase;
import codedriver.framework.scheduler.dto.JobObject;

/**
 * 工单自动评分定时类
 */
@Component
public class ProcessTaskAutoScoreJob extends JobBase {
	static Logger logger = LoggerFactory.getLogger(ProcessTaskAutoScoreJob.class);

	@Autowired
	private ProcessTaskMapper processTaskMapper;

	@Autowired
	private ScoreTemplateMapper scoreTemplateMapper;

	@Autowired
	private ProcessTaskScoreMapper processTaskScoreMapper;

	@Autowired
	private WorktimeMapper worktimeMapper;

	@Override
	public Boolean checkCronIsExpired(JobObject jobObject) {
		Long processTaskId = (Long) jobObject.getData("processTaskId");
		List<ProcessTaskScoreVo> processtaskScoreVos = processTaskScoreMapper.searchProcessTaskScoreByProcesstaskId(processTaskId);
		if (CollectionUtils.isEmpty(processtaskScoreVos)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void reloadJob(JobObject jobObject) {
		String tenantUuid = jobObject.getTenantUuid();
		TenantContext.get().switchTenant(tenantUuid);
		Long processTaskId = (Long) jobObject.getData("processTaskId");
		List<ProcessTaskScoreVo> processtaskScoreVos = processTaskScoreMapper.searchProcessTaskScoreByProcesstaskId(processTaskId);
		if(CollectionUtils.isEmpty(processtaskScoreVos)){
		    /** 如果没有评分记录，那么读取评分配置 */
		    ProcessTaskVo task = processTaskMapper.getProcessTaskById(processTaskId);
	        if(task != null && task.getStatus().equals(ProcessTaskStatus.SUCCEED.getValue())) {
	            String config = processTaskScoreMapper.getProcessTaskAutoScoreConfigByProcessTaskId(processTaskId);
	            Integer autoTime = (Integer)JSONPath.read(config, "config.autoTime");
	            if(autoTime != null) {
	                String autoTimeType = (String)JSONPath.read(config, "config.autoTimeType");
	                /**
            	            * 如果没有设置评分时限类型是自然日还是工作日，默认按自然日顺延
            	            * 如果设置为工作日，那么获取当前时间以后的工作日历，按工作日历顺延
	                 */
	                Date autoScoreDate = null;
	                if("workDay".equals(autoTimeType) && worktimeMapper.checkWorktimeIsExists(task.getWorktimeUuid()) > 0) {
	                    long expireTime = WorkTimeUtil.calculateExpireTime(task.getEndTime().getTime(), TimeUnit.DAYS.toMillis(autoTime), task.getWorktimeUuid());
	                    autoScoreDate = new Date(expireTime);
	                }else {
	                    autoScoreDate = new Date(task.getEndTime().getTime() + TimeUnit.DAYS.toMillis(autoTime));
	                }
	                ProcessTaskAutoScoreVo processTaskAutoScoreVo = new ProcessTaskAutoScoreVo();
	                processTaskAutoScoreVo.setProcessTaskId(processTaskId);
	                processTaskAutoScoreVo.setTriggerTime(autoScoreDate);
	                processTaskScoreMapper.updateProcessTaskAutoScoreByProcessTaskId(processTaskAutoScoreVo);
	                JobObject.Builder newJobObjectBuilder = new JobObject.Builder(processTaskId.toString(), this.getGroupName(), this.getClassName(), TenantContext.get().getTenantUuid())
	                    .withBeginTime(autoScoreDate)
	                    .withIntervalInSeconds(60 * 60)
	                    .withRepeatCount(0)
	                    .addData("processTaskId", processTaskId);
	                JobObject newJobObject = newJobObjectBuilder.build();
	                schedulerManager.loadJob(newJobObject);
	            }
	        }
		}
	}

	@Override
	public void initJob(String tenantUuid) {
	    List<Long> processTaskIdList = processTaskScoreMapper.getAllProcessTaskAutoScoreProcessTaskIdList();
	    for(Long processTaskId : processTaskIdList) {
	        JobObject.Builder jobObjectBuilder = new JobObject.Builder(processTaskId.toString(), this.getGroupName(), this.getClassName(), TenantContext.get().getTenantUuid()).addData("processTaskId", processTaskId);
            JobObject jobObject = jobObjectBuilder.build();
            this.reloadJob(jobObject);
	    }
	}

	@Override
	public void executeInternal(JobExecutionContext context, JobObject jobObject) throws JobExecutionException {	        	    
        Long processTaskId = (Long) jobObject.getData("processTaskId");
	    List<ProcessTaskScoreVo> processTaskScoreVos = processTaskScoreMapper.searchProcessTaskScoreByProcesstaskId(processTaskId);
	    if(CollectionUtils.isEmpty(processTaskScoreVos)) {
	        ProcessTaskVo task = processTaskMapper.getProcessTaskById(processTaskId);
	        if(task != null) {
	            String config = processTaskScoreMapper.getProcessTaskAutoScoreConfigByProcessTaskId(processTaskId);
	            Long scoreTemplateId = (Long)JSONPath.read(config, "scoreTemplateId");
	            ScoreTemplateVo template = scoreTemplateMapper.getScoreTemplateById(scoreTemplateId);
	            if(template != null) {
	                List<ScoreTemplateDimensionVo> dimensionList = template.getDimensionList();
	                if(CollectionUtils.isNotEmpty(dimensionList)){
	                    for(ScoreTemplateDimensionVo vo : dimensionList){
	                        vo.setScore(5);
	                    }
	                    JSONObject paramObj = new JSONObject();
	                    paramObj.put("scoreTemplateId", scoreTemplateId);
	                    paramObj.put("scoreDimensionList", dimensionList);
	                    //paramObj.put("content", "系统自动评价");
	                    task.setParamObj(paramObj);
                        /** 执行转交前，设置当前用户为system,用于权限校验 **/
                        UserContext.init(SystemUser.SYSTEM.getConfig(), null, SystemUser.SYSTEM.getTimezone(), null, null);
                        ProcessStepHandlerFactory.getHandler().scoreProcessTask(task);
	                }
	            }
	        }
	    }	    
	}

	@Override
	public String getGroupName() {
		return TenantContext.get().getTenantUuid() + "-PROCESSTASK-AUTOSCORE";
	}

}
