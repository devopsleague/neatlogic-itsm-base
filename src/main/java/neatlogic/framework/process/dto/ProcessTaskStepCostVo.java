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

import java.util.Date;

public class ProcessTaskStepCostVo {
    private Long id;
    private Long processTaskId;
    private Long processTaskStepId;

    private String startAction;
    private String startStatus;
    private Date startTime;
    private String startUserUuid;

    private String endAction;
    private String endStatus;
    private Date endTime;
    private String endUserUuid;

    private Long timeCost;
    private Long realTimeCost;

    public Long getId() {
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

    public Long getProcessTaskStepId() {
        return processTaskStepId;
    }

    public void setProcessTaskStepId(Long processTaskStepId) {
        this.processTaskStepId = processTaskStepId;
    }

    public String getStartAction() {
        return startAction;
    }

    public void setStartAction(String startAction) {
        this.startAction = startAction;
    }

    public String getStartStatus() {
        return startStatus;
    }

    public void setStartStatus(String startStatus) {
        this.startStatus = startStatus;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getStartUserUuid() {
        return startUserUuid;
    }

    public void setStartUserUuid(String startUserUuid) {
        this.startUserUuid = startUserUuid;
    }

    public String getEndAction() {
        return endAction;
    }

    public void setEndAction(String endAction) {
        this.endAction = endAction;
    }

    public String getEndStatus() {
        return endStatus;
    }

    public void setEndStatus(String endStatus) {
        this.endStatus = endStatus;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getEndUserUuid() {
        return endUserUuid;
    }

    public void setEndUserUuid(String endUserUuid) {
        this.endUserUuid = endUserUuid;
    }

    public Long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Long timeCost) {
        this.timeCost = timeCost;
    }

    public Long getRealTimeCost() {
        return realTimeCost;
    }

    public void setRealTimeCost(Long realTimeCost) {
        this.realTimeCost = realTimeCost;
    }
}
