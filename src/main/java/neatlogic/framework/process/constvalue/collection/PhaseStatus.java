/*Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.constvalue.collection;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.common.constvalue.IEnum;
import neatlogic.framework.util.$;

import java.util.List;

public enum PhaseStatus implements IEnum {
    PENDING("pending", "common.notstart"),
    DOING("aborted", "common.doing"),
    DONE("succeed", "common.done");

    private final String status;
    private final String text;

    PhaseStatus(String _status, String _text) {
        this.status = _status;
        this.text = _text;
    }

    @Override
    public String getValue() {
        return status;
    }

    public String getText() {
        return $.t(this.text);
    }


    public static String getValue(String _status) {
        for (PhaseStatus s : PhaseStatus.values()) {
            if (s.getValue().equals(_status)) {
                return s.getValue();
            }
        }
        return null;
    }

    public static String getText(String _status) {
        for (PhaseStatus s : PhaseStatus.values()) {
            if (s.getValue().equals(_status)) {
                return s.getText();
            }
        }
        return "";
    }


    @Override
    public List getValueTextList() {
        JSONArray array = new JSONArray();
        for (PhaseStatus type : values()) {
            array.add(new JSONObject() {
                {
                    this.put("value", type.getValue());
                    this.put("text", type.getText());
                }
            });
        }
        return array;
    }

    @Override
    public String getEnumName() {
        return "阶段状态";
    }
}
