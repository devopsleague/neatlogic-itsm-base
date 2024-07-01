/*
 * Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neatlogic.framework.process.stephandler.core;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.process.constvalue.ProcessFlowDirection;
import neatlogic.framework.process.constvalue.ProcessStepHandlerType;
import neatlogic.framework.process.dto.ProcessStepRelVo;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProcessMessageManager {
    private static final ThreadLocal<ProcessMessageContext> context = new ThreadLocal<>();

    public static void setConfig(JSONObject config) {
        context.set(new ProcessMessageContext(config));
    }

    public static void setStepName(String stepName) {
        ProcessMessageContext processMessageContext = context.get();
        if (processMessageContext != null) {
            processMessageContext.setStepName(stepName);
        }
    }

    public static String getStepName() {
        ProcessMessageContext processMessageContext = context.get();
        if (processMessageContext != null) {
            return processMessageContext.getStepName();
        }
        return StringUtils.EMPTY;
    }

    public static void release() {
        context.remove();
    }

    public static List<String> getEffectiveStepUuidList() {
        ProcessMessageContext processMessageContext = context.get();
        if (processMessageContext == null) {
            return new ArrayList<>();
        }
        List<String> effectiveStepUuidList = processMessageContext.getEffectiveStepUuidList();
        if (effectiveStepUuidList == null) {
            List<ProcessStepRelVo> allProcessStepRelList = getProcessStepRelList();
            String startStepUuid = null;
            String endStepUuid = null;
            JSONObject process = processMessageContext.getConfig();
            JSONArray stepList = process.getJSONArray("stepList");
            for (int i = 0; i < stepList.size(); i++) {
                JSONObject step = stepList.getJSONObject(i);
                if (MapUtils.isEmpty(step)) {
                    continue;
                }
                String handler = step.getString("handler");
                if (Objects.equals(handler, ProcessStepHandlerType.START.getHandler())) {
                    startStepUuid = step.getString("uuid");
                } else if (Objects.equals(handler, ProcessStepHandlerType.END.getHandler())) {
                    endStepUuid = step.getString("uuid");
                }
            }
            List<List<String>> routeList = new ArrayList<>();
            List<String> routeStepList = new ArrayList<>();
            routeList.add(routeStepList);
            getAllRouteList(startStepUuid, routeList, routeStepList, endStepUuid, allProcessStepRelList);
            // 有效的步骤UUID列表
            effectiveStepUuidList = new ArrayList<>();
            for (List<String> routeStepUuidList : routeList) {
                for (String routeStepUuid : routeStepUuidList) {
                    if (!effectiveStepUuidList.contains(routeStepUuid)) {
                        effectiveStepUuidList.add(routeStepUuid);
                    }
                }
            }
            List<ProcessStepRelVo> connectionList = new ArrayList<>();
            for (ProcessStepRelVo processStepRelVo : allProcessStepRelList) {
                if (effectiveStepUuidList.contains(processStepRelVo.getFromStepUuid()) && effectiveStepUuidList.contains(processStepRelVo.getToStepUuid())) {
                    connectionList.add(processStepRelVo);
                }
            }
            processMessageContext.setConnectionList(connectionList);
            processMessageContext.setEffectiveStepUuidList(effectiveStepUuidList);
        }
        return effectiveStepUuidList;
    }

    public static List<ProcessStepRelVo> getProcessStepRelList() {
        ProcessMessageContext processMessageContext = context.get();
        if (processMessageContext == null) {
            return new ArrayList<>();
        }
        List<ProcessStepRelVo> processStepRelList = processMessageContext.getConnectionList();
        if (processStepRelList == null) {
            JSONObject process = processMessageContext.getConfig();
            JSONArray connectionList = process.getJSONArray("connectionList");
            if (connectionList == null) {
                connectionList = new JSONArray();
            }
            for (int i = connectionList.size() - 1; i >= 0; i--) {
                JSONObject connectionObj = connectionList.getJSONObject(i);
                if (MapUtils.isEmpty(connectionObj)) {
                    connectionList.remove(i);
                }
                if (StringUtils.isBlank(connectionObj.getString("uuid"))) {
                    connectionList.remove(i);
                }
                if (StringUtils.isBlank(connectionObj.getString("fromStepUuid"))) {
                    connectionList.remove(i);
                }
                if (StringUtils.isBlank(connectionObj.getString("toStepUuid"))) {
                    connectionList.remove(i);
                }
                if (StringUtils.isBlank(connectionObj.getString("type"))) {
                    connectionList.remove(i);
                }
            }
            processStepRelList = connectionList.toJavaList(ProcessStepRelVo.class);
            processMessageContext.setConnectionList(processStepRelList);
        }
        return processStepRelList;
    }

    /**
     *
     * @param stepUuid 当前步骤uuid
     * @param routeList 收集所有后置路经列表
     * @param routeStepList 遍历过的步骤列表
     * @param endStepUuid 结束步骤uuid
     * @param allProcessStepRelList 所有连线列表
     */
    private static void getAllRouteList(String stepUuid, List<List<String>> routeList, List<String> routeStepList, String endStepUuid, List<ProcessStepRelVo> allProcessStepRelList) {
        if (!routeStepList.contains(stepUuid)) {
            routeStepList.add(stepUuid);
            if (!stepUuid.equals(endStepUuid)) {
                List<String> toStepUuidList = new ArrayList<>();
                for (ProcessStepRelVo processStepRelVo : allProcessStepRelList) {
                    if (Objects.equals(processStepRelVo.getFromStepUuid(), stepUuid) && Objects.equals(processStepRelVo.getType(), ProcessFlowDirection.FORWARD.getValue())) {
                        toStepUuidList.add(processStepRelVo.getToStepUuid());
                    }
                }
                List<String> tmpRouteStepList = new ArrayList<>(routeStepList);
                for (int i = 0; i < toStepUuidList.size(); i++) {
                    String toStepUuid = toStepUuidList.get(i);
                    if (i == 0) {
                        getAllRouteList(toStepUuid, routeList, routeStepList, endStepUuid, allProcessStepRelList);
                    } else {
                        List<String> newRouteStepList = new ArrayList<>(tmpRouteStepList);
                        routeList.add(newRouteStepList);
                        getAllRouteList(toStepUuid, routeList, newRouteStepList, endStepUuid, allProcessStepRelList);
                    }
                }
            }
        }
    }
}
