package neatlogic.framework.process.notify.constvalue;

import neatlogic.framework.notify.core.INotifyTriggerType;
import neatlogic.framework.process.constvalue.ProcessTaskGroupSearch;
import neatlogic.framework.process.constvalue.ProcessUserType;
import neatlogic.framework.util.$;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ProcessTaskStepTaskNotifyTriggerType implements INotifyTriggerType {
    CREATETASK("createtask", "nfpnc.processtasksteptasknotifytriggertype.text.createtask", "nfpnc.processtasksteptasknotifytriggertype.description.createtask",
            Arrays.asList(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.WORKER.getValue())),
    EDITTASK("edittask", "nfpnc.processtasksteptasknotifytriggertype.text.edittask", "nfpnc.processtasksteptasknotifytriggertype.description.edittask",
            Arrays.asList(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.WORKER.getValue())),
    DELETETASK("deletetask", "nfpnc.processtasksteptasknotifytriggertype.text.deletetask", "nfpnc.processtasksteptasknotifytriggertype.description.deletetask",
            Arrays.asList(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.MINOR.getValue(),
                    ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.WORKER.getValue())),
    COMPLETETASK("completetask", "nfpnc.processtasksteptasknotifytriggertype.text.completetask", "nfpnc.processtasksteptasknotifytriggertype.description.completetask",
            Arrays.asList(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.WORKER.getValue())),
    COMPLETEALLTASK("completealltask", "nfpnc.processtasksteptasknotifytriggertype.text.completealltask", "nfpnc.processtasksteptasknotifytriggertype.description.completealltask",
            Arrays.asList(ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue() + "#" + ProcessUserType.WORKER.getValue()));

    private String trigger;
    private String text;
    private String description;
    private List<String> excludeList;

    ProcessTaskStepTaskNotifyTriggerType(String _trigger, String _text, String _description) {
        this.trigger = _trigger;
        this.text = _text;
        this.description = _description;
        this.excludeList = new ArrayList<>();
    }

    ProcessTaskStepTaskNotifyTriggerType(String _trigger, String _text, String _description, List<String> _excludeList) {
        this.trigger = _trigger;
        this.text = _text;
        this.description = _description;
        this.excludeList = _excludeList;
    }

    @Override
    public String getTrigger() {
        return trigger;
    }

    @Override
    public String getText() {
        return $.t(text);
    }

    @Override
    public String getDescription() {
        return $.t(description);
    }

    public static String getText(String trigger) {
        for (ProcessTaskStepTaskNotifyTriggerType n : values()) {
            if (n.getTrigger().equals(trigger)) {
                return n.getText();
            }
        }
        return "";
    }

    @Override
    public List<String> getExcludeList() {
        return excludeList;
    }
}
