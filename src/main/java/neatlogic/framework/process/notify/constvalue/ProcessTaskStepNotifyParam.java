/*Copyright (C) $today.year  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.notify.constvalue;

import neatlogic.framework.common.constvalue.ParamType;
import neatlogic.framework.notify.core.INotifyParam;
import neatlogic.framework.util.I18n;

/**
 * @author linbq
 * @since 2021/10/16 13:49
 **/
public enum ProcessTaskStepNotifyParam implements INotifyParam {

    STEPID("stepId", new I18n("步骤id"), ParamType.NUMBER),
    STEPNAME("stepName", new I18n("步骤名"), ParamType.STRING),
    PROCESS_TASK_STEP_ACTIVE_TIME("processTaskStepActiveTime", new I18n("步骤激活时间"), ParamType.STRING),
    PROCESS_TASK_STEP_START_TIME("processTaskStepStartTime", new I18n("步骤开始时间"), ParamType.STRING),
    PROCESS_TASK_STEP_TRANSFER_TIME("processTaskStepTransferTime", new I18n("步骤转交时间"), ParamType.STRING),
    PROCESS_TASK_STEP_TRANSFER_CONTENT("processTaskStepTransferContent", new I18n("步骤转交原因"), ParamType.STRING),
    PROCESS_TASK_STEP_TRANSFER_WORKER("processTaskStepTransferWorker", new I18n("步骤转交对象"), ParamType.STRING),
    PROCESS_TASK_CURRENT_STEP_COMPLETE_CONTENT("processTaskCurrentStepCompleteContent", new I18n("当前步骤处理内容"), ParamType.STRING),
    PROCESS_TASK_CURRENT_STEP_BACK_CONTENT("processTaskCurrentStepBackContent", new I18n("步骤回退原因"), ParamType.STRING),
    PROCESS_TASK_STEP_SLA("processTaskStepSla", new I18n("步骤时效"), ParamType.ARRAY, "<#if DATA.processTaskStepSla?? && (DATA.processTaskStepSla?size > 0)>\n" +
            "\t\t\t\t<#list DATA.processTaskStepSla as item>\n" +
            "\t\t\t\t\t${item_index}-${item.id}-${item.name}-${item.status}-${item.timeLeft}-${item.timeLeftFormat}-${item.timeCostFormat}-${item.expireTimeFormat}\n" +
            "\t\t\t\t\t<#if item_has_next>,</#if>\n" +
            "\t\t\t\t</#list>\n" +
            "\t\t\t</#if>"),
    STEPWORKER("stepWorker", new I18n("步骤处理人"), ParamType.STRING),
    STEPSTAYTIME("stepStayTime", new I18n("步骤停留时间"), ParamType.STRING),
    REASON("reason", new I18n("原因"), ParamType.STRING),
    PROCESS_TASK_STEP_PAUSE_CONTENT("processTaskStepPauseContent", new I18n("步骤暂停原因"), ParamType.STRING),
    PROCESS_TASK_STEP_RECOVER_CONTENT("processTaskStepRecoverContent", new I18n("步骤恢复原因"), ParamType.STRING),
    PROCESS_TASK_STEP_RETREAT_CONTENT("processTaskStepRetreatContent", new I18n("步骤撤回原因"), ParamType.STRING),
    STEP_COMMENT("stepComment", new I18n("步骤回复"), ParamType.STRING),
    STEP_COMMENT_USER("stepCommentUser", new I18n("步骤回复用户"), ParamType.STRING),
    STEP_COMMENT_LIST("stepCommentList", new I18n("步骤回复列表"), ParamType.ARRAY),
    ;

    private final String value;
    private final I18n text;
    private final ParamType paramType;
    private String freemarkerTemplate;

    ProcessTaskStepNotifyParam(String value, I18n text, ParamType paramType) {
        this(value, text, paramType, null);
    }

    ProcessTaskStepNotifyParam(String value, I18n text, ParamType paramType, String freemarkerTemplate) {
        this.value = value;
        this.text = text;
        this.paramType = paramType;
        this.freemarkerTemplate = freemarkerTemplate;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String getText() {
        return text.toString();
    }

    @Override
    public ParamType getParamType() {
        return paramType;
    }

    @Override
    public String getFreemarkerTemplate() {
        if (freemarkerTemplate == null && paramType != null) {
            freemarkerTemplate = paramType.getFreemarkerTemplate(value);
        }
        return freemarkerTemplate;
    }
}
