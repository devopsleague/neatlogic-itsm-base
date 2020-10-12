package codedriver.framework.process.dto;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import codedriver.framework.elasticsearch.annotation.ESKey;
import codedriver.framework.elasticsearch.constvalue.ESKeyType;

public class ProcessTaskStepWorkerPolicyVo implements Comparable<ProcessTaskStepWorkerPolicyVo> {
    @ESKey(type = ESKeyType.PKEY, name ="processTaskId")
	private Long processTaskId;
	private Long processTaskStepId;
	private String processStepUuid;
	private String policy;
	private Integer sort;
	private String config;
	private JSONObject configObj;
	private JSONArray configObjList;

	public ProcessTaskStepWorkerPolicyVo() {

	}

	public ProcessTaskStepWorkerPolicyVo(ProcessStepWorkerPolicyVo policyVo) {
		this.setPolicy(policyVo.getPolicy());
		this.setSort(policyVo.getSort());
		this.setConfig(policyVo.getConfig());
		this.setProcessStepUuid(policyVo.getProcessStepUuid());
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getProcessTaskStepId() {
		return processTaskStepId;
	}

	public void setProcessTaskStepId(Long processTaskStepId) {
		this.processTaskStepId = processTaskStepId;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public JSONObject getConfigObj() {
		if (StringUtils.isNotBlank(config) && configObj == null) {
			try {
				configObj = JSONObject.parseObject(config);
			} catch (Exception ex) {

			}
		}
		return configObj;
	}

	public JSONArray getConfigObjList() {
		if (StringUtils.isNotBlank(config) && configObjList == null) {
			try {
				configObjList = JSONArray.parseArray(config);
			} catch (Exception ex) {

			}
		}
		return configObjList;
	}

	public void setConfigObj(JSONObject configObj) {
		this.configObj = configObj;
	}

	@Override
	public int compareTo(ProcessTaskStepWorkerPolicyVo o) {
		if (this.getSort() != null && o.getSort() != null) {
			return this.getSort().compareTo(o.getSort());
		}
		return 0;
	}

	public String getProcessStepUuid() {
		return processStepUuid;
	}

	public void setProcessStepUuid(String processStepUuid) {
		this.processStepUuid = processStepUuid;
	}

	public String getPolicy() {
		return policy;
	}

	public void setPolicy(String policy) {
		this.policy = policy;
	}

	public Long getProcessTaskId() {
		return processTaskId;
	}

	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}
	
}
