/*
 * Copyright(c) 2022 TechSure Co., Ltd. All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.constvalue;

public enum ProcessWorkcenterType {
    FACTORY("factory", "默认出厂"), SYSTEM("system", "系统分类"), CUSTOM("custom", "自定义");
    private final String value;
    private final String name;

    ProcessWorkcenterType(String _value, String _name) {
        this.value = _value;
        this.name = _name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getValue(String _value) {
        for (ProcessWorkcenterType s : ProcessWorkcenterType.values()) {
            if (s.getValue().equals(_value)) {
                return s.getValue();
            }
        }
        return null;
    }

    public static String getName(String _value) {
        for (ProcessWorkcenterType s : ProcessWorkcenterType.values()) {
            if (s.getValue().equals(_value)) {
                return s.getName();
            }
        }
        return "";
    }

}
