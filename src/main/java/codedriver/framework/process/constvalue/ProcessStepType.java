/*
 * Copyright(c) 2021 TechSure Co., Ltd. All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.constvalue;

public enum ProcessStepType {
    START("start", "开始"),
    PROCESS("process", "处理节点"),
    END("end", "结束");

    private final String type;
    private final String name;

    ProcessStepType(String _type, String _name) {
        this.type = _type;
        this.name = _name;
    }

    public String getValue() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static String getValue(String _type) {
        for (ProcessStepType s : ProcessStepType.values()) {
            if (s.getValue().equals(_type)) {
                return s.getValue();
            }
        }
        return null;
    }

    public static String getName(String _type) {
        for (ProcessStepType s : ProcessStepType.values()) {
            if (s.getValue().equals(_type)) {
                return s.getName();
            }
        }
        return "";
    }

}
