package codedriver.framework.process.workcenter.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import codedriver.framework.apiparam.core.ApiParamType;
import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.process.constvalue.ProcessFieldType;
import codedriver.framework.process.workcenter.column.core.IWorkcenterColumn;
import codedriver.framework.restful.annotation.EntityField;

public class WorkcenterTheadVo {

	@JSONField(serialize = false)
	@EntityField(name = "工单中心分类唯一标识段", type = ApiParamType.STRING)
	private String workcenterUuid;
	@EntityField(name = "字段名（表单属性则存属性uuid）", type = ApiParamType.STRING)
	private String name ;
	@EntityField(name = "字段中文名", type = ApiParamType.STRING)
	private String displayName ;
	@EntityField(name = "字段排序", type = ApiParamType.INTEGER)
	private Integer sort = 100;
	@EntityField(name = "字段宽度", type = ApiParamType.INTEGER)
	private Integer width = 1;
	@EntityField(name = "字段是否展示", type = ApiParamType.INTEGER)
	private Integer isShow = 1;
	@JSONField(serialize = false)
	@EntityField(name = "所属用户", type = ApiParamType.STRING)
	private String userUuid;
	@EntityField(name = "字段类型", type = ApiParamType.STRING)
	private String type ;
	@EntityField(name = "字段样式", type = ApiParamType.STRING)
	private String className ;
	
	
	public WorkcenterTheadVo(JSONObject obj) {
		this.name = obj.getString("name");
		this.sort = obj.getInteger("sort");
		this.width = obj.getInteger("width");
		this.isShow = obj.getInteger("isShow");
		this.userUuid = UserContext.get().getUserUuid();
		this.type = obj.getString("type");
		this.className = obj.getString("className");
	}
	
	public WorkcenterTheadVo(IWorkcenterColumn column) {
		this.name = column.getName();
		this.userUuid = UserContext.get().getUserUuid();
		this.displayName = column.getDisplayName();
		this.type = ProcessFieldType.COMMON.getValue();
		this.className = column.getClassName();
		this.sort = column.getSort();
	}
	
	public WorkcenterTheadVo(String _workcenterUuid,String _userUuid) {
		this.workcenterUuid = _workcenterUuid;
		this.userUuid = _userUuid;
	}
		
	public WorkcenterTheadVo() {
		
	}
	public String getWorkcenterUuid() {
		return workcenterUuid;
	}
	public void setWorkcenterUuid(String workcenterUuid) {
		this.workcenterUuid = workcenterUuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	
	
}
