package neatlogic.framework.process.notify.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.common.constvalue.Expression;
import neatlogic.framework.common.constvalue.ParamType;
import neatlogic.framework.condition.core.IConditionHandler;
import neatlogic.framework.dto.ConditionParamVo;
import neatlogic.framework.dto.ExpressionVo;
import neatlogic.framework.form.constvalue.FormConditionModel;
import neatlogic.framework.notify.core.NotifyPolicyHandlerBase;
import neatlogic.framework.notify.dto.NotifyTriggerVo;
import neatlogic.framework.process.condition.core.ProcessTaskConditionFactory;
import neatlogic.framework.process.constvalue.ConditionProcessTaskOptions;
import neatlogic.framework.process.constvalue.ProcessTaskGroupSearch;
import neatlogic.framework.process.constvalue.ProcessUserType;
import neatlogic.framework.process.notify.constvalue.ProcessTaskNotifyParam;
import neatlogic.framework.process.notify.constvalue.ProcessTaskStepNotifyParam;
import neatlogic.framework.process.notify.constvalue.ProcessTaskStepNotifyTriggerType;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public abstract class ProcessTaskNotifyHandlerBase extends NotifyPolicyHandlerBase {

    @Override
    protected List<NotifyTriggerVo> myNotifyTriggerList() {
        List<NotifyTriggerVo> returnList = new ArrayList<>();
        for (ProcessTaskStepNotifyTriggerType notifyTriggerType : ProcessTaskStepNotifyTriggerType.values()) {
            returnList.add(new NotifyTriggerVo(notifyTriggerType));
        }
        List<NotifyTriggerVo> customTriggerList = myCustomNotifyTriggerList();
        if(CollectionUtils.isNotEmpty(customTriggerList)){
            returnList.addAll(customTriggerList);
        }
        return returnList;
    }

    /**
     * 自定义触发点
     */
    protected  List<NotifyTriggerVo> myCustomNotifyTriggerList(){
        return Collections.emptyList();
    }

    /**
     * 默认带工单和工单步骤的邮件模版参数
     */
    @Override
    protected List<ConditionParamVo> mySystemParamList() {
        List<ConditionParamVo> notifyPolicyParamList = new ArrayList<>();
        for(ProcessTaskNotifyParam param : ProcessTaskNotifyParam.values()) {
            notifyPolicyParamList.add(createConditionParam(param));
        }
        for(ProcessTaskStepNotifyParam param : ProcessTaskStepNotifyParam.values()) {
            notifyPolicyParamList.add(createConditionParam(param));
        }
        List<ConditionParamVo> customSystemParamList = myCustomSystemParamList();
        if(CollectionUtils.isNotEmpty(customSystemParamList)){
            notifyPolicyParamList.addAll(customSystemParamList);
        }
        return notifyPolicyParamList;
    }

    /**
     * 自定义邮件模版参数
     */
    protected  List<ConditionParamVo> myCustomSystemParamList(){
        return Collections.emptyList();
    }

    @Override
    protected List<ConditionParamVo> mySystemConditionOptionList() {
        List<ConditionParamVo> notifyPolicyParamList = new ArrayList<>();
        for(ConditionProcessTaskOptions option : ConditionProcessTaskOptions.values()) {
            IConditionHandler condition = ProcessTaskConditionFactory.getHandler(option.getValue());
            if(condition != null) {
                ConditionParamVo param = new ConditionParamVo();
                param.setName(condition.getName());
                param.setLabel(condition.getDisplayName());
                param.setController(condition.getHandler(FormConditionModel.CUSTOM));
                if(condition.getConfig() != null) {
                    param.setConfig(condition.getConfig().toJSONString());
                }
                param.setType(condition.getType());
                ParamType paramType = condition.getParamType();
                if(paramType != null) {
                    param.setParamType(paramType.getName());
                    param.setParamTypeName(paramType.getText());
                    param.setDefaultExpression(paramType.getDefaultExpression().getExpression());
                    for(Expression expression : paramType.getExpressionList()) {
                        param.getExpressionList().add(new ExpressionVo(expression.getExpression(), expression.getExpressionName(),expression.getIsShowConditionValue()));
                    }
                }

                param.setIsEditable(0);
                notifyPolicyParamList.add(param);
            }
        }
        return notifyPolicyParamList;
    }

    @Override
    protected void myAuthorityConfig(JSONObject config) {
        List<String> groupList = JSON.parseArray(config.getJSONArray("groupList").toJSONString(), String.class);
        groupList.add(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue());
        config.put("groupList", groupList);
        List<String> includeList = JSON.parseArray(config.getJSONArray("includeList").toJSONString(), String.class);
        includeList.add(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.DEFAULT_WORKER.getValue());
        includeList.add(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.FOCUS_USER.getValue());
        config.put("includeList", includeList);
    }
}
