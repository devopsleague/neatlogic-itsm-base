package neatlogic.framework.process.constvalue;

public enum ProcessTaskConditionType {

    WORKCENTER("workcenter", "工单中心"),
    PROCESS("process", "流程");;

    private final String value;
    private final String text;

    ProcessTaskConditionType(String value, String text) {
        this.value = value;
        this.text = text;
    }

    public String getValue() {
        return value;
    }


    public String getText() {
        return text;
    }

}
