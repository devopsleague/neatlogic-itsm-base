package codedriver.framework.process.dto;

import java.util.Date;
import java.util.List;

import codedriver.framework.apiparam.core.ApiParamType;
import codedriver.framework.restful.annotation.EntityField;

public class ProcessTaskStepAuditVo {
	@EntityField(name = "活动id", type = ApiParamType.LONG)
	private Long id;
	@EntityField(name = "工单id", type = ApiParamType.LONG)
	private Long processTaskId;
	@EntityField(name = "步骤id", type = ApiParamType.LONG)
	private Long processTaskStepId;
	@EntityField(name = "步骤名称", type = ApiParamType.STRING)
	private String processTaskStepName;
	@EntityField(name = "用户userUuid", type = ApiParamType.STRING)
	private String userUuid;
	@EntityField(name = "用户名", type = ApiParamType.STRING)
	private String userName;
	@EntityField(name = "创建时间", type = ApiParamType.LONG)
	private Date actionTime;
	@EntityField(name = "活动类型，startprocess(上报)、complete(完成)、retreat(撤回)、abort(终止)、recover(恢复)、transfer(转交)、updateTitle(更新标题)、updatePriority(更新优先级)、updateContent(更新上报描述内容)、comment(评论)", type = ApiParamType.STRING)
	private String action;
	//@EntityField(name = "活动详情列表", type = ApiParamType.JSONARRAY)
	private List<ProcessTaskStepAuditDetailVo> auditDetailList;
	
	@EntityField(name = "目标步骤id", type = ApiParamType.LONG)
	private Long nextStepId;
	@EntityField(name = "目标步骤名称", type = ApiParamType.STRING)
	private String nextStepName;
	
	public ProcessTaskStepAuditVo() { 
	}
	
	public ProcessTaskStepAuditVo(Long _processTaskId,String _action) { 
		this.processTaskId = _processTaskId;
		this.action = _action;
	}

	public ProcessTaskStepAuditVo(Long processTaskId, Long processTaskStepId, String userUuid, String action) {
		this.processTaskId = processTaskId;
		this.processTaskStepId = processTaskStepId;
		this.userUuid = userUuid;
		this.action = action;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProcessTaskId() {
		return processTaskId;
	}

	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}

	public Long getProcessTaskStepId() {
		return processTaskStepId;
	}

	public void setProcessTaskStepId(Long processTaskStepId) {
		this.processTaskStepId = processTaskStepId;
	}

	public String getProcessTaskStepName() {
		return processTaskStepName;
	}

	public void setProcessTaskStepName(String processTaskStepName) {
		this.processTaskStepName = processTaskStepName;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getActionTime() {
		return actionTime;
	}

	public void setActionTime(Date actionTime) {
		this.actionTime = actionTime;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public List<ProcessTaskStepAuditDetailVo> getAuditDetailList() {
		return auditDetailList;
	}

	public void setAuditDetailList(List<ProcessTaskStepAuditDetailVo> auditDetailList) {
		this.auditDetailList = auditDetailList;
	}

	public Long getNextStepId() {
		return nextStepId;
	}

	public void setNextStepId(Long nextStepId) {
		this.nextStepId = nextStepId;
	}

	public String getNextStepName() {
		return nextStepName;
	}

	public void setNextStepName(String nextStepName) {
		this.nextStepName = nextStepName;
	}

}
