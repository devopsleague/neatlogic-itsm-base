package codedriver.framework.process.notify.schedule.plugin;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

import codedriver.framework.asynchronization.threadlocal.TenantContext;
import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.common.constvalue.GroupSearch;
import codedriver.framework.common.constvalue.SystemUser;
import codedriver.framework.dao.mapper.RoleMapper;
import codedriver.framework.dao.mapper.TeamMapper;
import codedriver.framework.dao.mapper.UserMapper;
import codedriver.framework.exception.role.RoleNotFoundException;
import codedriver.framework.exception.team.TeamNotFoundException;
import codedriver.framework.exception.user.UserNotFoundException;
import codedriver.framework.process.constvalue.ProcessTaskStatus;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dto.ProcessTaskSlaTimeVo;
import codedriver.framework.process.dto.ProcessTaskSlaTransferVo;
import codedriver.framework.process.dto.ProcessTaskSlaVo;
import codedriver.framework.process.dto.ProcessTaskStepVo;
import codedriver.framework.process.dto.ProcessTaskStepWorkerVo;
import codedriver.framework.process.stephandler.core.IProcessStepHandler;
import codedriver.framework.process.stephandler.core.ProcessStepHandlerFactory;
import codedriver.framework.scheduler.core.JobBase;
import codedriver.framework.scheduler.dto.JobObject;

@Component
public class ProcessTaskSlaTransferJob extends JobBase {
	static Logger logger = LoggerFactory.getLogger(ProcessTaskSlaTransferJob.class);
	
	private final static Integer INTERVAL_IN_SECONDS = 60 * 60;
	
	@Autowired
	private ProcessTaskMapper processTaskMapper;

	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	private TeamMapper teamMapper;
	
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public Boolean checkCronIsExpired(JobObject jobObject) {
		Long slaTransferId = (Long) jobObject.getData("slaTransferId");
		ProcessTaskSlaTransferVo processTaskSlaTransferVo = processTaskMapper.getProcessTaskSlaTransferById(slaTransferId);
		if (processTaskSlaTransferVo == null) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void reloadJob(JobObject jobObject) {
		System.out.println(this.getClassName() + "::reloadJob");
		String tenantUuid = jobObject.getTenantUuid();
		TenantContext.get().switchTenant(tenantUuid);
		Long slaTransferId = (Long) jobObject.getData("slaTransferId");
		ProcessTaskSlaTransferVo processTaskSlaTransferVo = processTaskMapper.getProcessTaskSlaTransferById(slaTransferId);
		boolean isJobLoaded = false;
		if (processTaskSlaTransferVo != null) {
			ProcessTaskSlaTimeVo slaTimeVo = processTaskMapper.getProcessTaskSlaTimeBySlaId(processTaskSlaTransferVo.getSlaId());
			if (slaTimeVo != null) {
				if (processTaskSlaTransferVo != null && processTaskSlaTransferVo.getConfigObj() != null) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					JSONObject policyObj = processTaskSlaTransferVo.getConfigObj();
					String expression = policyObj.getString("expression");
					int time = policyObj.getIntValue("time");
					String unit = policyObj.getString("unit");
					String transferTo = policyObj.getString("transferTo");
					if (StringUtils.isNotBlank(expression) && StringUtils.isNotBlank(transferTo)) {
						Calendar transferDate = Calendar.getInstance();
						transferDate.setTime(slaTimeVo.getExpireTime());
						if (expression.equalsIgnoreCase("before")) {
							time = -time;
						}
						if (StringUtils.isNotBlank(unit) && time != 0) {
							if (unit.equalsIgnoreCase("day")) {
								transferDate.add(Calendar.DAY_OF_MONTH, time);
							} else if (unit.equalsIgnoreCase("hour")) {
								transferDate.add(Calendar.HOUR, time);
							} else {
								transferDate.add(Calendar.MINUTE, time);
							}
						}
						System.out.println("now: " + sdf.format(new Date()));
						System.out.println("transferDate: " + sdf.format(transferDate.getTime()));
						System.out.println(time + unit);
						JobObject.Builder newJobObjectBuilder = new JobObject.Builder(processTaskSlaTransferVo.getId().toString(), this.getGroupName(), this.getClassName(), TenantContext.get().getTenantUuid())
								.withBeginTime(transferDate.getTime())
								.withIntervalInSeconds(INTERVAL_IN_SECONDS)
								.addData("slaTransferId", processTaskSlaTransferVo.getId());
						JobObject newJobObject = newJobObjectBuilder.build();
						Date triggerDate = schedulerManager.loadJob(newJobObject);
						System.out.println("triggerDate：" + triggerDate);
						if (triggerDate != null) {
							// 更新通知记录时间
							processTaskSlaTransferVo.setTriggerTime(triggerDate);
							processTaskMapper.updateProcessTaskSlaTransfer(processTaskSlaTransferVo);
							isJobLoaded = true;
						}
					}
				}
			}
		}
		if (!isJobLoaded) {
			// 没有加载到作业，则删除通知记录
			System.out.println("删除转交数据：" + slaTransferId);
			processTaskMapper.deleteProcessTaskSlaTransferById(slaTransferId);
		}
	}

	@Override
	public void initJob(String tenantUuid) {
		List<ProcessTaskSlaTransferVo> slaTransferList = processTaskMapper.getAllProcessTaskSlaTransfer();
		for (ProcessTaskSlaTransferVo processTaskSlaTransferVo : slaTransferList) {
			JobObject.Builder jobObjectBuilder = new JobObject.Builder(processTaskSlaTransferVo.getSlaId().toString(), this.getGroupName(), this.getClassName(), TenantContext.get().getTenantUuid()).addData("slaTransferId", processTaskSlaTransferVo.getId());
			JobObject jobObject = jobObjectBuilder.build();
			this.reloadJob(jobObject);
		}
	}

	@Override
	public void executeInternal(JobExecutionContext context, JobObject jobObject) throws JobExecutionException {
		System.out.println(this.getClassName()+"::executeInternal");
		Long slaTransferId = (Long) jobObject.getData("slaTransferId");
		ProcessTaskSlaTransferVo processTaskSlaTransferVo = processTaskMapper.getProcessTaskSlaTransferById(slaTransferId);
		try {
			if (processTaskSlaTransferVo != null) {
				Long slaId = processTaskSlaTransferVo.getSlaId();
				ProcessTaskSlaTimeVo processTaskSlaTimeVo = processTaskMapper.getProcessTaskSlaTimeBySlaId(slaId);
				List<ProcessTaskStepVo> processTaskStepList = processTaskMapper.getProcessTaskStepBaseInfoBySlaId(slaId);
				ProcessTaskSlaVo processTaskSlaVo = processTaskMapper.getProcessTaskSlaById(slaId);
				if (processTaskSlaVo != null && processTaskSlaTimeVo != null && processTaskSlaTransferVo.getConfigObj() != null) {
					JSONObject policyObj = processTaskSlaTransferVo.getConfigObj();
					String transferTo = policyObj.getString("transferTo");
					if (StringUtils.isNotBlank(transferTo) && transferTo.contains("#")) {
						boolean transferToIsExists = true;//转交对象是否存在、合法
						String[] split = transferTo.split("#");
						if(GroupSearch.USER.getValue().equals(split[0])) {
							if(userMapper.checkUserIsExists(split[1]) == 0) {
								throw new UserNotFoundException(split[1]);
							}
						}else if(GroupSearch.TEAM.getValue().equals(split[0])) {
							if(teamMapper.checkTeamIsExists(split[1]) == 0) {
								throw new TeamNotFoundException(split[1]);
							}
						}else if(GroupSearch.ROLE.getValue().equals(split[0])) {
							if(roleMapper.checkRoleIsExists(split[1]) == 0) {
								throw new RoleNotFoundException(split[1]);
							}
						}else {
							transferToIsExists = false;
						}
						if (transferToIsExists) {
							ProcessTaskStepWorkerVo workerVo = new ProcessTaskStepWorkerVo();
							workerVo.setProcessTaskId(processTaskSlaVo.getProcessTaskId());
							workerVo.setType(split[0]);
							workerVo.setUuid(split[1]);
							for (ProcessTaskStepVo processTaskStepVo : processTaskStepList) {
								// 未处理、处理中和挂起的步骤才需要转交
								if (processTaskStepVo.getStatus().equals(ProcessTaskStatus.PENDING.getValue()) || processTaskStepVo.getStatus().equals(ProcessTaskStatus.RUNNING.getValue()) || processTaskStepVo.getStatus().equals(ProcessTaskStatus.HANG.getValue())) {
									List<ProcessTaskStepWorkerVo> workerList = new ArrayList<>();
									workerVo.setProcessTaskStepId(processTaskStepVo.getId());
									workerList.add(workerVo);
									IProcessStepHandler stepHandler = ProcessStepHandlerFactory.getHandler(processTaskStepVo.getHandler());
									if (stepHandler != null) {
										UserContext.init(SystemUser.SYSTEM.getConfig(), null, SystemUser.SYSTEM.getTimezone(), null, null);
										stepHandler.transfer(processTaskStepVo, workerList);
										System.out.println("转交成功：" + processTaskStepVo.getId());
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		} finally {
			schedulerManager.unloadJob(jobObject);
			if (processTaskSlaTransferVo != null) {
				// 删除转交记录
				processTaskMapper.deleteProcessTaskSlaTransferById(processTaskSlaTransferVo.getId());
			}
		}
	}

	@Override
	public String getGroupName() {
		return TenantContext.get().getTenantUuid() + "-PROCESSTASK-SLA-TRANSFER";
	}

}
