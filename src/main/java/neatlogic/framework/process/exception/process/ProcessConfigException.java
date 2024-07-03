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

package neatlogic.framework.process.exception.process;

import neatlogic.framework.exception.core.ApiRuntimeException;
import neatlogic.framework.util.$;

public class ProcessConfigException extends ApiRuntimeException {

    public enum Type {
        SLA, CONDITION, COPY, PRE_STEP_ASSIGN, PRE_STEP_ASSIGN_CONDITION_STEP
    }

    public ProcessConfigException(Type type, String name) {
        super(getMessage(type, name));
    }

    private static String getMessage(Type type, String name) {
        if (type == Type.SLA) {
            return $.t("nfpep.processconfigexception.sla", name);
        } else if (type == Type.CONDITION) {
            return $.t("nfpep.processconfigexception.condition", name);
        } else if (type == Type.COPY) {
            return $.t("nfpep.processconfigexception.copy", name);
        } else if (type == Type.PRE_STEP_ASSIGN) {
            return $.t("nfpep.processconfigexception.prestepassign", name);
        } else if (type == Type.PRE_STEP_ASSIGN_CONDITION_STEP) {
            return $.t("nfpep.processconfigexception.prestepassignconditionstep", name);
        }
        return "";
    }
}
