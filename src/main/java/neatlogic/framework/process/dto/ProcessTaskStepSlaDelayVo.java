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

package neatlogic.framework.process.dto;

import neatlogic.framework.common.dto.BaseEditorVo;
import neatlogic.framework.util.SnowflakeUtil;

public class ProcessTaskStepSlaDelayVo extends BaseEditorVo {

    private Long id;
    private Long processTaskId;
    private String processTaskTitle;
    private Long targetProcessTaskId;
    private Long targetProcessTaskStepId;
    private Long slaId;
    private Long time;

    public Long getId() {
        if (id == null) {
            id = SnowflakeUtil.uniqueLong();
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(Long processTaskId) {
        this.processTaskId = processTaskId;
    }

    public String getProcessTaskTitle() {
        return processTaskTitle;
    }

    public void setProcessTaskTitle(String processTaskTitle) {
        this.processTaskTitle = processTaskTitle;
    }

    public Long getTargetProcessTaskId() {
        return targetProcessTaskId;
    }

    public void setTargetProcessTaskId(Long targetProcessTaskId) {
        this.targetProcessTaskId = targetProcessTaskId;
    }

    public Long getTargetProcessTaskStepId() {
        return targetProcessTaskStepId;
    }

    public void setTargetProcessTaskStepId(Long targetProcessTaskStepId) {
        this.targetProcessTaskStepId = targetProcessTaskStepId;
    }

    public Long getSlaId() {
        return slaId;
    }

    public void setSlaId(Long slaId) {
        this.slaId = slaId;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
