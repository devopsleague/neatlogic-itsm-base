/*
Copyright(c) 2023 NeatLogic Co., Ltd. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License. 
 */

package neatlogic.framework.process.constvalue;

import neatlogic.framework.util.I18n;

/**
 * @author linbq
 * @since 2021/7/15 11:15
 **/
public enum ReplaceableText {
    CONTENT_DETAILS("replaceableContentDetails", new I18n("内容详情")),
    STEP_LIST("replaceableSteplist", new I18n("步骤日志")),
    TIME_LINE("replaceableTimeLine", new I18n("时间线")),
    RELATION_LIST("replaceableRelationlist", new I18n("关联工单"));
    private final String value;
    private final I18n text;

    ReplaceableText(String value, I18n text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text.toString();
    }
}
