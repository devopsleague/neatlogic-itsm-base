package codedriver.framework.process.dto;

import codedriver.framework.attribute.dto.AttributeDataVo;

public class ProcessTaskFormAttributeDataVo extends AttributeDataVo {
	private Long processTaskId;
	private Long processTaskStepId;
	private String type;
	private String formUuid;

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

	public String getFormUuid() {
		return formUuid;
	}

	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
