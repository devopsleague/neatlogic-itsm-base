package neatlogic.framework.process.dto;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

public class ProcessStepRelVo implements Serializable {
	private static final long serialVersionUID = 1970685757497902601L;
	private String processUuid;
	private String uuid;
	private String fromStepUuid;
	private String toStepUuid;
	@JSONField(serialize = false)
	private String condition;

	private JSONObject conditionConfig;

	private String name;
	private String type;
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null)
			return false;
		if (!(other instanceof ProcessStepRelVo))
			return false;

		final ProcessStepRelVo rel = (ProcessStepRelVo) other;
		try {
			if (getUuid().equals(rel.getUuid())) {
				return true;
			}
		} catch (Exception ex) {
			return false;
		}
		return false;
	}

	@Override
	public int hashCode() {
		int result = getUuid().hashCode() * 29;
		return result;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getFromStepUuid() {
		return fromStepUuid;
	}

	public void setFromStepUuid(String fromStepUuid) {
		this.fromStepUuid = fromStepUuid;
	}

	public String getToStepUuid() {
		return toStepUuid;
	}

	public void setToStepUuid(String toStepUuid) {
		this.toStepUuid = toStepUuid;
	}

	public String getCondition() {
		if (condition == null && conditionConfig != null) {
			condition = conditionConfig.toJSONString();
		}
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public JSONObject getConditionConfig() {
		if (conditionConfig == null && condition != null) {
			conditionConfig = JSONObject.parseObject(condition);
		}
		return conditionConfig;
	}

	public void setConditionConfig(JSONObject conditionConfig) {
		this.conditionConfig = conditionConfig;
	}

	public String getProcessUuid() {
		return processUuid;
	}

	public void setProcessUuid(String processUuid) {
		this.processUuid = processUuid;
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
