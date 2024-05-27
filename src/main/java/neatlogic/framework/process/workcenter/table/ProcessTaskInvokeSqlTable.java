package neatlogic.framework.process.workcenter.table;

import neatlogic.framework.process.workcenter.table.constvalue.ProcessSqlTypeEnum;
import neatlogic.framework.util.I18n;
import org.springframework.stereotype.Component;

/**
 * @Title: ProcessTaskStepTable
 * @Package: neatlogic.module.process.workcenter.core.table
 * @Description: TODO
 * @Author: 89770
 * @Date: 2021/1/15 16:37
 **/

@Component
public class ProcessTaskInvokeSqlTable implements ISqlTable {
    @Override
    public String getName() {
        return "processtask_invoke";
    }

    @Override
    public String getShortName() {
        return "pti";
    }

    public enum FieldEnum {
        PROCESSTASK_ID("processtask_id", new I18n("term.itsm.processtaskid"), "processTaskId"),
        SOURCE("source", new I18n("common.source"), "processTaskInvokeSource"),
        TYPE("type", new I18n("nfpwtp.fieldenum.type"), "processTaskInvokeType"),
        INVOKE_ID("invoke_id", new I18n("nmaaja.createautoexecjobfromcombopapi.input.param.desc.invokeid"), "processTaskInvokeId");
        private final String name;
        private final I18n text;
        private final String proName;

        private FieldEnum(String _value, I18n _text, String _proName) {
            this.name = _value;
            this.text = _text;
            this.proName = _proName;
        }

        private FieldEnum(String _value, I18n _text) {
            this.name = _value;
            this.text = _text;
            this.proName = _value;
        }

        public String getValue() {
            return name;
        }

        public String getText() {
            return text.toString();
        }

        public String getProName() {
            return proName;
        }

        public static String getText(String value) {
            for (ProcessSqlTypeEnum f : ProcessSqlTypeEnum.values()) {
                if (f.getValue().equals(value)) {
                    return f.getText();
                }
            }
            return "";
        }
    }
}
