package codedriver.framework.process.dto;

import java.util.List;

public class ProcessTaskStepRelVo {
	private Long processTaskId;
	private Long fromProcessTaskStepId;
	private Long toProcessTaskStepId;
	private String processStepRelUuid;
	private String fromProcessStepUuid;
	private String toProcessStepUuid;
	private String condition;
	private String toProcessStepHandler;
	private Integer isHit = 0;
	private String scriptResult;
	private String error;
	private List<RelExpressionVo> relExpressionList;
	private String name;
	private String type;
	
	public ProcessTaskStepRelVo() {

	}

	public ProcessTaskStepRelVo(ProcessStepRelVo processStepRelVo) {
		this.setFromProcessStepUuid(processStepRelVo.getFromStepUuid());
		this.setToProcessStepUuid(processStepRelVo.getToStepUuid());
		this.setCondition(processStepRelVo.getCondition());
		this.setProcessStepRelUuid(processStepRelVo.getUuid());
		this.setName(processStepRelVo.getName());
		this.setType(processStepRelVo.getType());
	}

	public ProcessTaskStepRelVo(Long fromProcessTaskStepId, Long toProcessTaskStepId, Integer isHit) {
		this.fromProcessTaskStepId = fromProcessTaskStepId;
		this.toProcessTaskStepId = toProcessTaskStepId;
		this.isHit = isHit;
	}

	public Long getProcessTaskId() {
		return processTaskId;
	}

	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public Integer getIsHit() {
		return isHit;
	}

	public void setIsHit(Integer isHit) {
		this.isHit = isHit;
	}

	public String getProcessStepRelUuid() {
		return processStepRelUuid;
	}

	public void setProcessStepRelUuid(String processStepRelUuid) {
		this.processStepRelUuid = processStepRelUuid;
	}

	public Long getFromProcessTaskStepId() {
		return fromProcessTaskStepId;
	}

	public void setFromProcessTaskStepId(Long fromProcessTaskStepId) {
		this.fromProcessTaskStepId = fromProcessTaskStepId;
	}

	public Long getToProcessTaskStepId() {
		return toProcessTaskStepId;
	}

	public void setToProcessTaskStepId(Long toProcessTaskStepId) {
		this.toProcessTaskStepId = toProcessTaskStepId;
	}

	public String getFromProcessStepUuid() {
		return fromProcessStepUuid;
	}

	public void setFromProcessStepUuid(String fromProcessStepUuid) {
		this.fromProcessStepUuid = fromProcessStepUuid;
	}

	public String getToProcessStepUuid() {
		return toProcessStepUuid;
	}

	public void setToProcessStepUuid(String toProcessStepUuid) {
		this.toProcessStepUuid = toProcessStepUuid;
	}

	public String getToProcessStepHandler() {
		return toProcessStepHandler;
	}

	public void setToProcessStepHandler(String toProcessStepHandler) {
		this.toProcessStepHandler = toProcessStepHandler;
	}

	public String getScriptResult() {
		return scriptResult;
	}

	public void setScriptResult(String scriptResult) {
		this.scriptResult = scriptResult;
	}

	public List<RelExpressionVo> getRelExpressionList() {
		return relExpressionList;
	}

	public void setRelExpressionList(List<RelExpressionVo> relExpressionList) {
		this.relExpressionList = relExpressionList;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
