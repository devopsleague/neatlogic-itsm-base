package neatlogic.framework.process.constvalue;

import neatlogic.framework.util.$;

public enum ConditionProcessTaskOptions {
    TASKID("id", "工单ID"),
    STEPID("stepid", "步骤ID"),
    TITLE("title", "标题"),
    CHANNELTYPE("channeltype", "服务类型"),
    CONTENT("content", "上报内容"),
    STARTTIME("starttime", "开始时间"),
    OWNER("owner", "上报人"),
    OWNERUSERID("owneruserid", "上报人用户ID"),
    PRIORITY("priority", "优先级"),
    OWNERGROUP("ownergroup", "上报人集团"),
    OWNERCOMPANY("ownercompany", "上报人公司"),
    OWNERCENTER("ownercenter", "上报人中心"),
    OWNERDEPARTMENT("ownerdepartment", "上报人部门"),
    OWNERTEAM("ownerteam", "上报人组"),
    OWNERROLE("ownerrole", "上报人角色"),
    OWNERLEVEL("ownerlevel", "上报人级别"),
    REGION("region", "地域");

    private final String value;
    private final String text;

    ConditionProcessTaskOptions(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return $.t(text);
    }
}
