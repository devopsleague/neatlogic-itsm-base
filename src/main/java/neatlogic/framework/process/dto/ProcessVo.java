/*Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/

package neatlogic.framework.process.dto;

import neatlogic.framework.common.constvalue.ApiParamType;
import neatlogic.framework.common.dto.BaseEditorVo;
import neatlogic.framework.process.constvalue.ProcessFlowDirection;
import neatlogic.framework.process.constvalue.ProcessStepHandlerType;
import neatlogic.framework.process.constvalue.ProcessStepType;
import neatlogic.framework.notify.dto.InvokeNotifyPolicyConfigVo;
import neatlogic.framework.process.dto.score.ProcessScoreTemplateVo;
import neatlogic.framework.process.exception.process.ProcessStepHandlerNotFoundException;
import neatlogic.framework.process.stephandler.core.IProcessStepInternalHandler;
import neatlogic.framework.process.stephandler.core.ProcessStepHandlerTypeFactory;
import neatlogic.framework.process.stephandler.core.ProcessStepInternalHandlerFactory;
import neatlogic.framework.restful.annotation.EntityField;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.annotation.JSONField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class ProcessVo extends BaseEditorVo {

    @EntityField(name = "common.uuid", type = ApiParamType.STRING)
    private String uuid;

    @EntityField(name = "common.name", type = ApiParamType.STRING)
    private String name;

    @EntityField(name = "common.typename", type = ApiParamType.STRING)
    private String typeName;

    @EntityField(name = "common.isactive", type = ApiParamType.INTEGER)
    private Integer isActive;

    @EntityField(name = "common.config", type = ApiParamType.JSONOBJECT)
    private JSONObject config;

    @EntityField(name = "common.referencecount", type = ApiParamType.INTEGER)
    private int referenceCount;
    // @EntityField(name = "流程表单uuid", type = ApiParamType.STRING)
    private String formUuid;
    private List<ProcessStepVo> stepList;

    // @EntityField(name = "流程属性列表", type = ApiParamType.JSONARRAY)
    private List<ProcessStepRelVo> stepRelList;

    private List<ProcessSlaVo> slaList;

    private ProcessScoreTemplateVo processScoreTemplateVo;
    @JSONField(serialize = false)
    private InvokeNotifyPolicyConfigVo notifyPolicyConfig;
    @JSONField(serialize = false)
    private List<String> integrationUuidList = new ArrayList<>();

    @JSONField(serialize = false)
    private String configStr;

    public synchronized String getUuid() {
        if (StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(String configStr) {
        this.config = JSONObject.parseObject(configStr);
    }

    public List<ProcessStepVo> getStepList() {
        return stepList;
    }

    public void makeupConfigObj() {
        configStr = getConfigStr();
        JSONObject processObj = (JSONObject) JSONPath.read(configStr, "process");
        if (MapUtils.isEmpty(processObj)) {
            return;
        }
        /** 组装表单属性 **/
//        Map<String, List<ProcessStepFormAttributeVo>> processStepFormAttributeMap = new HashMap<>();
        JSONObject formConfig = processObj.getJSONObject("formConfig");
        if (MapUtils.isNotEmpty(formConfig)) {
            String formUuid = formConfig.getString("uuid");
            if (StringUtils.isNotBlank(formUuid)) {
                this.setFormUuid(formUuid);
//                JSONArray authorityList = formConfig.getJSONArray("authorityList");
//                if (CollectionUtils.isNotEmpty(authorityList)) {
//                    for (int i = 0; i < authorityList.size(); i++) {
//                        JSONObject authorityObj = authorityList.getJSONObject(i);
//                        JSONArray processStepUuidList = authorityObj.getJSONArray("processStepUuidList");
//                        JSONArray attributeUuidList = authorityObj.getJSONArray("attributeUuidList");
//                        String action = authorityObj.getString("action");
//                        if (StringUtils.isBlank(action)) {
//                            continue;
//                        }
//                        String type = authorityObj.getString("type");
//                        if (StringUtils.isBlank(type)) {
//                            continue;
//                        }
//                        if (CollectionUtils.isNotEmpty(processStepUuidList)
//                                && CollectionUtils.isNotEmpty(attributeUuidList) && StringUtils.isNotBlank(action)) {
//                            for (int j = 0; j < processStepUuidList.size(); j++) {
//                                String processStepUuid = processStepUuidList.getString(j);
//                                if (StringUtils.isBlank(processStepUuid)) {
//                                    continue;
//                                }
//                                for (int k = 0; k < attributeUuidList.size(); k++) {
//                                    String attributeUuid = attributeUuidList.getString(k);
//                                    if (StringUtils.isBlank(attributeUuid)) {
//                                        continue;
//                                    }
//                                    ProcessStepFormAttributeVo processStepFormAttributeVo = new ProcessStepFormAttributeVo();
//                                    processStepFormAttributeVo.setProcessUuid(this.getUuid());
//                                    processStepFormAttributeVo.setFormUuid(this.getFormUuid());
//                                    processStepFormAttributeVo.setProcessStepUuid(processStepUuid);
//                                    processStepFormAttributeVo.setAttributeUuid(attributeUuid);
//                                    processStepFormAttributeVo.setAction(action);
//                                    processStepFormAttributeVo.setType(type);
//                                    List<ProcessStepFormAttributeVo> processStepFormAttributeList = processStepFormAttributeMap.computeIfAbsent(processStepUuid, key -> new ArrayList<>());
//                                    if (!processStepFormAttributeList.contains(processStepFormAttributeVo)) {
//                                        processStepFormAttributeList.add(processStepFormAttributeVo);
//                                    }
//                                }
//
//                            }
//                        }
//                    }
//                }
            }
        }
        JSONArray slaList = processObj.getJSONArray("slaList");
        if (CollectionUtils.isNotEmpty(slaList)) {
            this.slaList = new ArrayList<>();
            for (int i = 0; i < slaList.size(); i++) {
                JSONObject slaObj = slaList.getJSONObject(i);
                /** 关联了步骤的sla策略才保存 **/
                JSONArray processStepUuidList = slaObj.getJSONArray("processStepUuidList");
                if (CollectionUtils.isNotEmpty(processStepUuidList)) {
                    ProcessSlaVo processSlaVo = new ProcessSlaVo();
                    processSlaVo.setProcessUuid(this.getUuid());
                    processSlaVo.setUuid(slaObj.getString("uuid"));
                    processSlaVo.setName(slaObj.getString("name"));
                    processSlaVo.setCalculateHandler(slaObj.getString("calculateHandler"));
                    processSlaVo.setConfig(slaObj.toJSONString());
                    this.slaList.add(processSlaVo);
                    for (int p = 0; p < processStepUuidList.size(); p++) {
                        processSlaVo.addProcessStepUuid(processStepUuidList.getString(p));
                    }

                    JSONArray notifyPolicyList = slaObj.getJSONArray("notifyPolicyList");
                    if (CollectionUtils.isNotEmpty(notifyPolicyList)) {
                        List<InvokeNotifyPolicyConfigVo> notifyPolicyConfigList = new ArrayList<>();
                        for (int j = 0; j < notifyPolicyList.size(); j++) {
                            JSONObject notifyPolicy = notifyPolicyList.getJSONObject(j);
                            if (MapUtils.isNotEmpty(notifyPolicy)) {
                                notifyPolicyConfigList.add(notifyPolicy.getObject("notifyPolicyConfig", InvokeNotifyPolicyConfigVo.class));
                            }
                        }
                        processSlaVo.setNotifyPolicyConfigList(notifyPolicyConfigList);
                    }
                }
            }
        }
        String virtualStartStepUuid = "";// 虚拟开始节点uuid
        Map<String, ProcessStepVo> stepMap = new HashMap<>();
        JSONArray stepList = processObj.getJSONArray("stepList");
        if (stepList != null && stepList.size() > 0) {
            this.stepList = new ArrayList<>();
            for (int i = 0; i < stepList.size(); i++) {
                JSONObject stepObj = stepList.getJSONObject(i);
                String handler = stepObj.getString("handler");
                if (ProcessStepHandlerType.START.getHandler().equals(handler)) {// 找到虚拟开始节点uuid,虚拟开始节点不写入process_step表
                    virtualStartStepUuid = stepObj.getString("uuid");
                    continue;
                }
                ProcessStepVo processStepVo = new ProcessStepVo();
                processStepVo.setProcessUuid(this.getUuid());
                processStepVo.setConfig(stepObj.getString("stepConfig"));

                String uuid = stepObj.getString("uuid");
                if (StringUtils.isNotBlank(uuid)) {
                    processStepVo.setUuid(uuid);
//                    processStepVo.setFormAttributeList(processStepFormAttributeMap.get(uuid));
                }
                String name = stepObj.getString("name");
                if (StringUtils.isNotBlank(name)) {
                    processStepVo.setName(name);
                }

                if (StringUtils.isNotBlank(handler)) {
                    processStepVo.setHandler(handler);
                    String type = ProcessStepHandlerTypeFactory.getType(handler);
                    processStepVo.setType(type);
                    IProcessStepInternalHandler procssStepUtilHandler = ProcessStepInternalHandlerFactory.getHandler(handler);
                    if (procssStepUtilHandler != null) {
                        JSONObject stepConfigObj = stepObj.getJSONObject("stepConfig");
                        if (stepConfigObj != null) {
                            procssStepUtilHandler.makeupProcessStep(processStepVo, stepConfigObj);
                        }
                    } else {
                        throw new ProcessStepHandlerNotFoundException(handler);
                    }
                }
                this.stepList.add(processStepVo);
                stepMap.put(processStepVo.getUuid(), processStepVo);
            }
        }

        JSONArray relList = processObj.getJSONArray("connectionList");
        if (relList != null && relList.size() > 0) {
            this.stepRelList = new ArrayList<>();
            for (int i = 0; i < relList.size(); i++) {
                ProcessStepRelVo processStepRelVo = relList.getObject(i, ProcessStepRelVo.class);
//                JSONObject relObj = relList.getJSONObject(i);
                String fromStepUuid = processStepRelVo.getFromStepUuid();
                String toStepUuid = processStepRelVo.getToStepUuid();
                if (virtualStartStepUuid.equals(fromStepUuid)) {// 通过虚拟开始节点连线找到真正的开始步骤
                    ProcessStepVo startStep = stepMap.get(toStepUuid);
                    if (startStep != null) {
                        startStep.setType(ProcessStepType.START.getValue());
                    }
                    continue;
                }
//                ProcessStepRelVo processStepRelVo = new ProcessStepRelVo();
//                processStepRelVo.setFromStepUuid(fromStepUuid);
//                processStepRelVo.setToStepUuid(toStepUuid);
//                processStepRelVo.setUuid(relObj.getString("uuid"));
                processStepRelVo.setProcessUuid(this.getUuid());
//                processStepRelVo.setCondition(relObj.getString("conditionConfig"));
//                processStepRelVo.setName(relObj.getString("name"));
                String type = processStepRelVo.getType();
                if (!ProcessFlowDirection.BACKWARD.getValue().equals(type)) {
                    type = ProcessFlowDirection.FORWARD.getValue();
                }
                processStepRelVo.setType(type);
                stepRelList.add(processStepRelVo);
            }
        }
        /** 组装评分设置 */
        JSONObject scoreConfig = processObj.getJSONObject("scoreConfig");
        if (MapUtils.isNotEmpty(scoreConfig)) {
            Integer isActive = scoreConfig.getInteger("isActive");
            if (Objects.equals(isActive, 1)) {
                this.processScoreTemplateVo = JSON.toJavaObject(scoreConfig, ProcessScoreTemplateVo.class);
                this.processScoreTemplateVo.setProcessUuid(uuid);
            }
        }
        /** 组装通知策略 **/
        JSONObject processConfig = processObj.getJSONObject("processConfig");
        if (MapUtils.isNotEmpty(processConfig)) {
            notifyPolicyConfig = processConfig.getObject("notifyPolicyConfig", InvokeNotifyPolicyConfigVo.class);
        }

        JSONArray actionList = (JSONArray) JSONPath.read(configStr, "process.processConfig.actionConfig.actionList");
        if (CollectionUtils.isNotEmpty(actionList)) {
            integrationUuidList = new ArrayList<>();
            for (int i = 0; i < actionList.size(); i++) {
                JSONObject ationObj = actionList.getJSONObject(i);
                String integrationUuid = ationObj.getString("integrationUuid");
                if (StringUtils.isNotBlank(integrationUuid)) {
                    integrationUuidList.add(integrationUuid);
                }
            }
        }
    }

    public void setStepList(List<ProcessStepVo> stepList) {
        this.stepList = stepList;
    }

    public List<ProcessStepRelVo> getStepRelList() {
        return stepRelList;
    }

    public void setStepRelList(List<ProcessStepRelVo> stepRelList) {
        this.stepRelList = stepRelList;
    }

    public String getFormUuid() {
        return formUuid;
    }

    public void setFormUuid(String formUuid) {
        this.formUuid = formUuid;
    }


    public int getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(int referenceCount) {
        this.referenceCount = referenceCount;
    }

    public List<ProcessSlaVo> getSlaList() {
        return slaList;
    }

    public void setSlaList(List<ProcessSlaVo> slaList) {
        this.slaList = slaList;
    }

    public ProcessScoreTemplateVo getProcessScoreTemplateVo() {
        return processScoreTemplateVo;
    }

    public void setProcessScoreTemplateVo(ProcessScoreTemplateVo processScoreTemplateVo) {
        this.processScoreTemplateVo = processScoreTemplateVo;
    }

    public InvokeNotifyPolicyConfigVo getNotifyPolicyConfig() {
        return notifyPolicyConfig;
    }

    public void setNotifyPolicyConfig(InvokeNotifyPolicyConfigVo notifyPolicyConfig) {
        this.notifyPolicyConfig = notifyPolicyConfig;
    }

    public List<String> getIntegrationUuidList() {
        return integrationUuidList;
    }

    public void setIntegrationUuidList(List<String> integrationUuidList) {
        this.integrationUuidList = integrationUuidList;
    }

    public String getConfigStr() {
        if (config != null) {
            return config.toJSONString();
        }
        return configStr;
    }
}
