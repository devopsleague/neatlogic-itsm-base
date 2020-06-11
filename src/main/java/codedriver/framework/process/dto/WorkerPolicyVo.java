package codedriver.framework.process.dto;

import codedriver.framework.common.constvalue.ApiParamType;
import codedriver.framework.restful.annotation.EntityField;

public class WorkerPolicyVo {

	@EntityField(name = "指派策略类型", type = ApiParamType.STRING)
	private String type;
	@EntityField(name = "指派策略名称", type = ApiParamType.STRING)
	private String name;
	@EntityField(name = "模块id", type = ApiParamType.STRING)
	private String moduleId;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModuleId() {
		return moduleId;
	}
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}
}
