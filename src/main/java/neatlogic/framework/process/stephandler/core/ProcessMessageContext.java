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

import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.process.dto.ProcessStepRelVo;
import neatlogic.framework.restful.constvalue.OperationTypeEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class ProcessMessageContext {

    private JSONObject config;

    private String stepName = StringUtils.EMPTY;

    private OperationTypeEnum operationType;

    private List<String> effectiveStepUuidList;

    private List<ProcessStepRelVo> connectionList;

//    public ProcessMessageContext(JSONObject config) {
//        this.config = config;
//    }

    public JSONObject getConfig() {
        return config;
    }

    public void setConfig(JSONObject config) {
        this.config = config;
    }

    public String getStepName() {
        return stepName;
    }

    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    public List<String> getEffectiveStepUuidList() {
        return effectiveStepUuidList;
    }

    public void setEffectiveStepUuidList(List<String> effectiveStepUuidList) {
        this.effectiveStepUuidList = effectiveStepUuidList;
    }

    public List<ProcessStepRelVo> getConnectionList() {
        return connectionList;
    }

    public void setConnectionList(List<ProcessStepRelVo> connectionList) {
        this.connectionList = connectionList;
    }

    public OperationTypeEnum getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationTypeEnum operationType) {
        this.operationType = operationType;
    }
}
