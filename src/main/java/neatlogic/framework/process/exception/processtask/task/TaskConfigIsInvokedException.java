/*Copyright (C) 2023  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.exception.processtask.task;

import neatlogic.framework.process.exception.core.ProcessTaskRuntimeException;

/**
 * @author lvzk
 * @since 2021/8/31 14:24
 **/
public class TaskConfigIsInvokedException extends ProcessTaskRuntimeException {

    private static final long serialVersionUID = 2402786145792701691L;

    public TaskConfigIsInvokedException(String processStepName) {
        super("子任务: “{0}” 被引用，无法删除", processStepName);
    }
}
