package codedriver.framework.process.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.common.dto.BasePageVo;

public class FormVersionVo extends BasePageVo implements Serializable {
	private static final long serialVersionUID = 8345592242508980127L;
	private String uuid;
	private String formName;
	private String formUuid;
	private Integer version;
	private Integer isActive;
	private String formConfig;
	private String editor;
	private Date editTime;
	private transient List<FormAttributeVo> formAttributeList;
	private transient List<ProcessMatrixFormComponentVo> processMatrixFormComponentList;
	public synchronized String getUuid() {
		if (StringUtils.isBlank(uuid)) {
			uuid = UUID.randomUUID().toString().replace("-", "");
		}
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getIsActive() {
		return isActive;
	}

	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}

	public String getFormUuid() {
		return formUuid;
	}

	public void setFormUuid(String formUuid) {
		this.formUuid = formUuid;
	}

	public String getFormConfig() {
		return formConfig;
	}

	public void setFormConfig(String formConfig) {
		this.formConfig = formConfig;
	}

	public String getEditor() {
		if (StringUtils.isBlank(editor)) {
			editor = UserContext.get().getUserId();
		}
		return editor;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public Date getEditTime() {
		return editTime;
	}

	public void setEditTime(Date editTime) {
		this.editTime = editTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public List<FormAttributeVo> getFormAttributeList() {
		if(formAttributeList == null) {
			if(StringUtils.isNotBlank(this.formConfig)) {
				JSONObject formConfigObj = JSONObject.parseObject(this.formConfig);
				if(MapUtils.isNotEmpty(formConfigObj)) {
					JSONArray controllerList = formConfigObj.getJSONArray("controllerList");
					if(CollectionUtils.isNotEmpty(controllerList)) {
						formAttributeList = new ArrayList<>();
						for(int i = 0; i < controllerList.size(); i++) {
							JSONObject controllerObj = controllerList.getJSONObject(i);
							JSONObject config = controllerObj.getJSONObject("config");
							if(MapUtils.isNotEmpty(config)) {
								formAttributeList.add(new FormAttributeVo(this.getFormUuid(), this.getUuid(), controllerObj.getString("uuid"), controllerObj.getString("label"), controllerObj.getString("type"), controllerObj.getString("handler"), config.getBooleanValue("isRequired"), controllerObj.getString("config"), config.getString("defaultValueList")));
							}
						}
					}
				}
			}			
		}		
		return formAttributeList;
	}

	public void setFormAttributeList(List<FormAttributeVo> formAttributeList) {
		this.formAttributeList = formAttributeList;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<ProcessMatrixFormComponentVo> getProcessMatrixFormComponentList() {
		if(processMatrixFormComponentList == null) {
			if(StringUtils.isNotBlank(this.formConfig)) {
				JSONObject formConfigObj = JSONObject.parseObject(this.formConfig);
				if(MapUtils.isNotEmpty(formConfigObj)) {
					JSONArray controllerList = formConfigObj.getJSONArray("controllerList");
					if(CollectionUtils.isNotEmpty(controllerList)) {
						processMatrixFormComponentList = new ArrayList<>();
						for(int i = 0; i < controllerList.size(); i++) {
							JSONObject controllerObj = controllerList.getJSONObject(i);
							JSONObject config = controllerObj.getJSONObject("config");
							if(MapUtils.isNotEmpty(config)) {
								String dataSource = config.getString("dataSource");
								if("matrix".equals(dataSource)) {
									String matrixUuid = config.getString("matrixUuid");
									if(StringUtils.isNotBlank(matrixUuid)) {
										ProcessMatrixFormComponentVo processMatrixFormComponentVo = new ProcessMatrixFormComponentVo();
										processMatrixFormComponentVo.setMatrixUuid(matrixUuid);
										processMatrixFormComponentVo.setFormVersionUuid(getUuid());
										processMatrixFormComponentVo.setFormAttributeLabel(controllerObj.getString("label"));
										processMatrixFormComponentVo.setFormAttributeUuid(controllerObj.getString("uuid"));
										processMatrixFormComponentList.add(processMatrixFormComponentVo);
									}
								}
							}
						}
					}
				}
			}			
		}
		return processMatrixFormComponentList;
	}

	public void setProcessMatrixFormComponentList(List<ProcessMatrixFormComponentVo> processMatrixFormComponentList) {
		this.processMatrixFormComponentList = processMatrixFormComponentList;
	}

	@Override
	public String toString() {
		return "FormVersionVo [uuid=" + uuid + ", formName=" + formName + ", formUuid=" + formUuid + ", version=" + version + ", isActive=" + isActive + ", formConfig=" + formConfig + ", editor=" + editor + ", editTime=" + editTime + "]";
	}

}
