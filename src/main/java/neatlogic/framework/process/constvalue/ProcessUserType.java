/*
 * Copyright(c) 2023 NeatLogic Co., Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package neatlogic.framework.process.constvalue;

import neatlogic.framework.common.constvalue.IUserType;
import neatlogic.framework.dto.UserTypeVo;

import java.util.HashMap;
import java.util.Map;

public enum ProcessUserType implements IUserType {
    MAJOR("major", "处理人", true),
    MINOR("minor", "协助处理人", true),
    //	AGENT("agent","代办人",true),
    OWNER("owner", "上报人", true),
    REPORTER("reporter", "代报人", true),
    WORKER("worker", "待处理人", true),
    DEFAULT_WORKER("defaultworker", "异常处理人", false),
    FOCUS_USER("focususer", "关注人", false);

    private final String status;
    private final String text;
    private final boolean isShow;

    private ProcessUserType(String _status, String _text, boolean _isShow) {
        this.status = _status;
        this.text = _text;
        this.isShow = _isShow;
    }

    public String getValue() {
        return status;
    }

    public String getText() {
        return text;
    }

    public boolean getIsShow() {
        return isShow;
    }

    public static String getValue(String _status) {
        for (ProcessUserType s : ProcessUserType.values()) {
            if (s.getValue().equals(_status)) {
                return s.getValue();
            }
        }
        return null;
    }

    public static String getText(String _status) {
        for (ProcessUserType s : ProcessUserType.values()) {
            if (s.getValue().equals(_status)) {
                return s.getText();
            }
        }
        return "";
    }


    @Override
    public UserTypeVo getUserType() {
        UserTypeVo vo = new UserTypeVo();
        vo.setModuleId(getModuleId());
        Map<String,String> map = new HashMap<>();
        for(ProcessUserType type : ProcessUserType.values()){
            map.put(type.getValue(),type.getText());
        }
        vo.setValues(map);
        return vo;
    }

    @Override
    public String getModuleId() {
        return "process";
    }
}
