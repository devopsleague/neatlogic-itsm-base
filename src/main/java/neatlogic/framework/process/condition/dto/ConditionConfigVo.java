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

package neatlogic.framework.process.condition.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.asynchronization.threadlocal.ConditionParamContext;
import neatlogic.framework.common.constvalue.GroupSearch;
import neatlogic.framework.condition.dto.ConditionBaseVo;
import neatlogic.framework.condition.dto.ConditionConfigBaseVo;
import neatlogic.framework.condition.dto.ConditionGroupBaseVo;
import neatlogic.framework.condition.dto.RelVo;
import neatlogic.framework.util.ConditionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class ConditionConfigVo extends ConditionConfigBaseVo<ConditionConfigVo.ConditionGroupVo<ConditionConfigVo.ConditionVo>> {
    private static final Logger logger = LoggerFactory.getLogger(ConditionConfigVo.class);

    public String buildScript() {
        if (CollectionUtils.isNotEmpty(conditionGroupRelList)) {
            StringBuilder script = new StringBuilder();
            script.append("(");
            String toUuid = null;
            for (RelVo conditionGroupRelVo : conditionGroupRelList) {
                script.append(getConditionGroupByUuid(conditionGroupRelVo.getFrom()).buildScript());
                script.append("and".equals(conditionGroupRelVo.getJoinType()) ? " && " : " || ");
                toUuid = conditionGroupRelVo.getTo();
            }
            script.append(getConditionGroupByUuid(toUuid).buildScript());
            script.append(")");
            return script.toString();
        } else {
            ConditionGroupVo<ConditionVo> conditionGroupVo = conditionGroupList.get(0);
            return conditionGroupVo.buildScript();
        }
    }
    public static class ConditionGroupVo<T extends ConditionBaseVo> extends ConditionGroupBaseVo<ConditionVo> {

        public String buildScript() {
            if (!CollectionUtils.isEmpty(conditionRelList)) {
                StringBuilder script = new StringBuilder();
                script.append("(");
                String toUuid = null;
                for (RelVo conditionRelVo : conditionRelList) {
                    script.append(getConditionByUuid(conditionRelVo.getFrom()).predicate());
                    script.append("and".equals(conditionRelVo.getJoinType()) ? " && " : " || ");
                    toUuid = conditionRelVo.getTo();
                }
                script.append(getConditionByUuid(toUuid).predicate());
                script.append(")");
                return script.toString();
            } else {
                ConditionVo conditionVo = conditionList.get(0);
                return String.valueOf(conditionVo.predicate());
            }
        }
    }

    public static class ConditionVo extends ConditionBaseVo {
        private String type;
        private Boolean result;
        private String error;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Boolean getResult() {
            return result;
        }

        public void setResult(Boolean result) {
            this.result = result;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }

        public boolean predicate() {
            result = false;
            ConditionParamContext context = ConditionParamContext.get();
            if (context != null) {
                List<String> currentValueList = new ArrayList<>();
                JSONObject paramData = context.getParamData();
                Object paramValue = paramData.get(this.name);
                if (paramValue != null) {
                    if (paramValue instanceof String) {
                        currentValueList.add(GroupSearch.removePrefix((String) paramValue));
                    } else if (paramValue instanceof List) {
                        List<String> values = JSON.parseArray(JSON.toJSONString(paramValue), String.class);
                        for (String value : values) {
                            currentValueList.add(GroupSearch.removePrefix(value));
                        }
                    } else {
                        currentValueList.add(GroupSearch.removePrefix(paramValue.toString()));
                    }
                }

                List<String> targetValueList = new ArrayList<>();
                if (valueList != null) {
                    if (valueList instanceof String) {
                        targetValueList.add(GroupSearch.removePrefix((String) valueList));
                    } else if (valueList instanceof List) {
                        List<String> values = JSON.parseArray(JSON.toJSONString(valueList), String.class);
                        for (String value : values) {
                            targetValueList.add(GroupSearch.removePrefix(value));
                        }
                    } else {
                        targetValueList.add(GroupSearch.removePrefix(valueList.toString()));
                    }
                }
                try {
                    result = ConditionUtil.predicate(currentValueList, this.expression, targetValueList);
                } catch (Exception e) {
                    error = e.getMessage();
                    logger.warn(e.getMessage(), e);
                }
                /* 将参数名称、表达式、值的value翻译成对应text，目前条件步骤生成活动时用到**/
//                if (context.isTranslate()) {
//                    if ("common".equals(type)) {
//                        IConditionHandler conditionHandler = ConditionHandlerFactory.getHandler(name);
//                        if (conditionHandler != null) {
//                            valueList = conditionHandler.valueConversionText(valueList, null);
//                            name = conditionHandler.getDisplayName();
//                        }
//                    } else if ("form".equals(type)) {
//                        IConditionHandler conditionHandler = ConditionHandlerFactory.getHandler("form");
//                        if (conditionHandler != null) {
//                            JSONObject formConfig = context.getFormConfig();
//                            if (MapUtils.isNotEmpty(formConfig)) {
//                                JSONObject configObj = new JSONObject();
//                                configObj.put("attributeUuid", name);
//                                configObj.put("formConfig", formConfig);
//                                valueList = conditionHandler.valueConversionText(valueList, configObj);
//                                name = configObj.getString("name");
//                            }
//                        }
//                    }
//                    this.expression = Expression.getExpressionName(this.expression);
//                }
            }

            return result;
        }
    }
}
