package neatlogic.framework.process.constvalue;

import neatlogic.framework.util.I18n;

public enum UserProfileOperate {
    KEEP_ON_CREATE_TASK("keeponcreatetask", new I18n("enum.process.userprofileoperate.keep_on_create_task")),
    VIEW_PROCESSTASK_DETAIL("viewprocesstaskdetail", new I18n("enum.process.userprofileoperate.view_processtask_detail")),
    BACK_CATALOG_LIST("backcataloglist", new I18n("enum.process.userprofileoperate.back_catalog_list"));

    private String value;
    private I18n text;

    private UserProfileOperate(String _value, I18n _text) {
        this.value = _value;
        this.text = _text;
    }

    public String getValue() {
        return value;
    }

    public String getText() {
        return text.toString();
    }

}
