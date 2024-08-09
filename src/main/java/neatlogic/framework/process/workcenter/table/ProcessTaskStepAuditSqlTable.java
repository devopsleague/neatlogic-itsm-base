/*
 * Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neatlogic.framework.process.workcenter.table;

import neatlogic.framework.util.I18n;
import org.springframework.stereotype.Component;

@Component
public class ProcessTaskStepAuditSqlTable implements ISqlTable {

    @Override
    public String getName() {
        return "processtask_step_audit";
    }

    @Override
    public String getShortName() {
        return "psa";
    }

    public enum FieldEnum {
        PROCESSTASK_ID("processtask_id", new I18n("工单ID")),
        PROCESSTASK_STEP_ID("processtask_step_id", new I18n("工单步骤ID")),
        USER_UUID("user_uuid", new I18n("用户UUID")),
        ACTION("action", new I18n("上报人"));

        private final String name;
        private final I18n text;

        FieldEnum(String _value, I18n _text) {
            this.name = _value;
            this.text = _text;
        }

        public String getValue() {
            return name;
        }

        public String getText() {
            return text.toString();
        }
    }
}
