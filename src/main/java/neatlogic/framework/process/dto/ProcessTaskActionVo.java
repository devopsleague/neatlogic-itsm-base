package neatlogic.framework.process.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import neatlogic.framework.util.SnowflakeUtil;

import java.util.Date;

public class ProcessTaskActionVo {

	private Long id;
	private Long processTaskId;
	private Long processTaskStepId;
	private String processTaskStepName;
	private String trigger;
	private String triggerText;
	private Date triggerTime;
	private String integrationUuid;
	private String integrationName;
	private boolean isSucceed;
	private String status;
	private String statusText;
	private String error;
	private JSONObject config;
	@JSONField(serialize = false)
	private String configStr;

	public Long getId() {
		if (id == null) {
			id = SnowflakeUtil.uniqueLong();
		}
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
	public String getTrigger() {
		return trigger;
	}
	public void setTrigger(String trigger) {
		this.trigger = trigger;
	}
	public String getTriggerText() {
		return triggerText;
	}
	public void setTriggerText(String triggerText) {
		this.triggerText = triggerText;
	}

	public Date getTriggerTime() {
		return triggerTime;
	}

	public void setTriggerTime(Date triggerTime) {
		this.triggerTime = triggerTime;
	}

	public String getIntegrationUuid() {
		return integrationUuid;
	}
	public void setIntegrationUuid(String integrationUuid) {
		this.integrationUuid = integrationUuid;
	}
	public String getIntegrationName() {
		return integrationName;
	}
	public void setIntegrationName(String integrationName) {
		this.integrationName = integrationName;
	}
	public boolean isSucceed() {
		return isSucceed;
	}
	public void setSucceed(boolean isSucceed) {
		this.isSucceed = isSucceed;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusText() {
		return statusText;
	}
	public void setStatusText(String statusText) {
		this.statusText = statusText;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public JSONObject getConfig() {
		if (config == null && configStr != null) {
			config = JSONObject.parseObject(configStr);
		}
		return config;
	}

	public void setConfig(JSONObject config) {
		this.config = config;
	}

	public String getConfigStr() {
		if (configStr == null && config != null) {
			configStr = config.toJSONString();
		}
		return configStr;
	}

	public void setConfigStr(String configStr) {
		this.configStr = configStr;
	}
}
