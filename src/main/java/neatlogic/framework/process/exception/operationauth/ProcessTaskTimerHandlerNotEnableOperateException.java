/*Copyright (C) $today.year  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.exception.operationauth;

import neatlogic.framework.process.operationauth.core.IOperationType;

/**
 * @author linbq
 * @since 2022/3/1 11:27
 **/
public class ProcessTaskTimerHandlerNotEnableOperateException extends ProcessTaskPermissionDeniedException {
    private static final long serialVersionUID = 9216337410118158663L;

    public ProcessTaskTimerHandlerNotEnableOperateException(IOperationType operationType) {
        super("定时节点不支持“{0}”操作", operationType.getText());
    }
}
