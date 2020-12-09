package codedriver.framework.process.processtaskserialnumberpolicy.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.quartz.CronExpression;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import codedriver.framework.asynchronization.thread.CodeDriverThread;
import codedriver.framework.asynchronization.threadlocal.TenantContext;
import codedriver.framework.asynchronization.threadpool.CachedThreadPool;
import codedriver.framework.common.dto.ValueTextVo;
import codedriver.framework.common.util.PageUtil;
import codedriver.framework.dao.mapper.TenantMapper;
import codedriver.framework.dto.TenantVo;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dao.mapper.ProcessTaskSerialNumberMapper;
import codedriver.framework.process.dto.ProcessTaskSerialNumberPolicyVo;
import codedriver.framework.process.dto.ProcessTaskVo;
import codedriver.framework.process.processtaskserialnumberpolicy.core.IProcessTaskSerialNumberPolicyHandler;
import codedriver.framework.scheduler.core.IJob;
import codedriver.framework.scheduler.core.JobBase;
import codedriver.framework.scheduler.core.SchedulerManager;
import codedriver.framework.scheduler.dto.JobObject;
import codedriver.framework.util.UuidUtil;

@Service
public class DateTimeAndAutoIncrementPolicy implements IProcessTaskSerialNumberPolicyHandler {
    private Logger logger = LoggerFactory.getLogger(DateTimeAndAutoIncrementPolicy.class);
    @Autowired
    private ProcessTaskSerialNumberMapper processTaskSerialNumberMapper;

    @Autowired
    private ProcessTaskMapper processTaskMapper;

    @Autowired
    private TenantMapper tenantMapper;

    @Override
    public String getName() {
        return "年月日 + 自增序列";
    }

    @SuppressWarnings("serial")
    @Override
    public JSONArray makeupFormAttributeList() {
        JSONArray resultArray = new JSONArray();
        {
            /** 起始值 **/
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type", "text");
            jsonObj.put("name", "startValue");
            jsonObj.put("value", "");
            jsonObj.put("defaultValue", 1);
            jsonObj.put("width", 200);
            jsonObj.put("maxlength", 5);
            jsonObj.put("label", "起始位");
            jsonObj.put("validateList", Arrays.asList("required", new JSONObject() {
                {
                    this.put("name", "number");
                    this.put("message", "请输入正整数");
                }
            }));
            jsonObj.put("placeholder", "1-99999");
            resultArray.add(jsonObj);
        }
        {
            /** 位数 **/
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("type", "select");
            jsonObj.put("name", "digits");
            jsonObj.put("value", "");
            jsonObj.put("defaultValue", "");
            jsonObj.put("width", 200);
            jsonObj.put("label", "工单号位数");
            jsonObj.put("maxlength", 5);
            jsonObj.put("validateList", Arrays.asList("required"));
            jsonObj.put("dataList", new ArrayList<ValueTextVo>() {
                {
                    this.add(new ValueTextVo(13, "13"));
                    this.add(new ValueTextVo(14, "14"));
                    this.add(new ValueTextVo(15, "15"));
                    this.add(new ValueTextVo(16, "16"));
                }
            });
            resultArray.add(jsonObj);
        }
        return resultArray;
    }

    @Override
    public JSONObject makeupConfig(JSONObject jsonObj) {
        JSONObject resultObj = new JSONObject();
        Long startValue = jsonObj.getLong("startValue");
        if (startValue == null) {
            startValue = 0L;
        }
        resultObj.put("startValue", startValue);
        resultObj.put("digits", jsonObj.getInteger("digits"));
        return resultObj;
    }

    @Override
    public String genarate(ProcessTaskSerialNumberPolicyVo processTaskSerialNumberPolicyVo) {
        int digits = processTaskSerialNumberPolicyVo.getConfig().getIntValue("digits");
        digits -= 8;
        long max = (long)Math.pow(10, digits) - 1;
        long serialNumberSeed = processTaskSerialNumberPolicyVo.getSerialNumberSeed();
        if (serialNumberSeed > max) {
            serialNumberSeed -= max;
        }
        processTaskSerialNumberMapper.updateProcessTaskSerialNumberPolicySerialNumberSeedByChannelTypeUuid(
            processTaskSerialNumberPolicyVo.getChannelTypeUuid(), serialNumberSeed + 1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date()) + String.format("%0" + digits + "d", serialNumberSeed);
    }

    @Override
    public int batchUpdateHistoryProcessTask(String channelTypeUuid) {
        int rowNum = processTaskMapper.getProcessTaskCountByChannelTypeUuid(channelTypeUuid);
        if (rowNum > 0) {
            /** 加锁 **/
            ProcessTaskSerialNumberPolicyVo processTaskSerialNumberPolicyVo =
                processTaskSerialNumberMapper.getProcessTaskSerialNumberPolicyLockByChannelTypeUuid(channelTypeUuid);
            int digits = processTaskSerialNumberPolicyVo.getConfig().getIntValue("digits");
            digits -= 8;
            long max = (long)Math.pow(10, digits) - 1;
            long startValue = processTaskSerialNumberPolicyVo.getConfig().getLongValue("startValue");
            long serialNumberSeed = startValue;
            String timeFormat = null;
            int pageSize = 1000;
            int pageCount = PageUtil.getPageCount(rowNum, pageSize);
            ProcessTaskVo processTaskVo = new ProcessTaskVo();
            processTaskVo.setChannelTypeUuid(channelTypeUuid);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            for (int currentPage = 1; currentPage <= pageCount; currentPage++) {
                processTaskVo.setCurrentPage(currentPage);
                List<ProcessTaskVo> processTaskList =
                    processTaskMapper.getProcessTaskListByChannelTypeUuid(processTaskVo);
                for (ProcessTaskVo processTask : processTaskList) {
                    String startTimeFormat = sdf.format(processTask.getStartTime());
                    if (!Objects.equals(timeFormat, startTimeFormat)) {
                        serialNumberSeed = startValue;
                        timeFormat = startTimeFormat;
                    }
                    String serialNumber = startTimeFormat + String.format("%0" + digits + "d", serialNumberSeed);
                    processTaskMapper.updateProcessTaskSerialNumberById(processTask.getId(), serialNumber);
                    processTaskSerialNumberMapper.insertProcessTaskSerialNumber(processTask.getId(), serialNumber);
                    serialNumberSeed++;
                    if (serialNumberSeed > max) {
                        serialNumberSeed -= max;
                    }
                }
            }
            processTaskSerialNumberPolicyVo.setSerialNumberSeed(startValue);
            processTaskSerialNumberMapper
                .updateProcessTaskSerialNumberPolicySerialNumberSeedByChannelTypeUuid(channelTypeUuid, startValue);
        }
        return rowNum;
    }

    @PostConstruct
    public void init() {
        List<TenantVo> tenantList = tenantMapper.getAllActiveTenant();
        for (TenantVo tenantVo : tenantList) {
            CachedThreadPool.execute(new ScheduleLoadJobRunner(tenantVo.getUuid()));
        }
    }

    class ScheduleLoadJobRunner extends CodeDriverThread {
        private String tenantUuid;

        public ScheduleLoadJobRunner(String _tenantUuid) {
            tenantUuid = _tenantUuid;
        }

        @Override
        protected void execute() {
            String oldThreadName = Thread.currentThread().getName();
            try {
                Thread.currentThread().setName("PROCESSTASKSERIALNUMBERSEEDRESETJOB-SCHEDULE-JOB-LOADER-" + tenantUuid);
                // 切换租户数据源
                TenantContext.get().switchTenant(tenantUuid).setUseDefaultDatasource(false);
                IJob job = SchedulerManager.getHandler(ProcessTaskSerialNumberSeedResetJob.class.getName());
                JobObject.Builder jobObjectBuilder = new JobObject.Builder(UuidUtil.randomUuid(), job.getGroupName(),
                    job.getClassName(), TenantContext.get().getTenantUuid()).addData("handler",
                        DateTimeAndAutoIncrementPolicy.class.getName());
                JobObject jobObject = jobObjectBuilder.build();
                job.reloadJob(jobObject);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            } finally {
                Thread.currentThread().setName(oldThreadName);
            }
        }
    }

    @Component
    private static class ProcessTaskSerialNumberSeedResetJob extends JobBase {

        private String cron = "0 0 0 * * ?";

        @Autowired
        private ProcessTaskSerialNumberMapper processTaskSerialNumberMapper;

        @Override
        public String getGroupName() {
            return TenantContext.get().getTenantUuid() + "-PROCESSTASK-SERIALNUMBERSEED-RESET";
        }

        @Override
        public Boolean checkCronIsExpired(JobObject jobObject) {
            return true;
        }

        @Override
        public void reloadJob(JobObject jobObject) {
            String tenantUuid = jobObject.getTenantUuid();
            TenantContext.get().switchTenant(tenantUuid);
            String handler = (String)jobObject.getData("handler");
            if (CronExpression.isValidExpression(cron)) {
                JobObject.Builder newJobObjectBuilder =
                    new JobObject.Builder(jobObject.getJobName(), this.getGroupName(), this.getClassName(),
                        TenantContext.get().getTenantUuid()).withCron(cron).addData("handler", handler);
                JobObject newJobObject = newJobObjectBuilder.build();
                schedulerManager.loadJob(newJobObject);
            }
        }

        @Override
        public void initJob(String tenantUuid) {

        }

        @Override
        public void executeInternal(JobExecutionContext context, JobObject jobObject) throws JobExecutionException {
            String handler = (String)jobObject.getData("handler");
            List<ProcessTaskSerialNumberPolicyVo> processTaskSerialNumberPolicyList =
                processTaskSerialNumberMapper.getProcessTaskSerialNumberPolicyListByHandler(handler);
            for (ProcessTaskSerialNumberPolicyVo processTaskSerialNumberPolicyVo : processTaskSerialNumberPolicyList) {
                ProcessTaskSerialNumberPolicyVo processTaskSerialNumberPolicy =
                    processTaskSerialNumberMapper.getProcessTaskSerialNumberPolicyLockByChannelTypeUuid(
                        processTaskSerialNumberPolicyVo.getChannelTypeUuid());
                Long startValue = 1L;
                Long value = processTaskSerialNumberPolicy.getConfig().getLong("startValue");
                if (value != null) {
                    startValue = value;
                }
                processTaskSerialNumberPolicyVo.setSerialNumberSeed(startValue);
                processTaskSerialNumberMapper.updateProcessTaskSerialNumberPolicySerialNumberSeedByChannelTypeUuid(
                    processTaskSerialNumberPolicyVo.getChannelTypeUuid(), startValue);
            }
        }
    }
}
