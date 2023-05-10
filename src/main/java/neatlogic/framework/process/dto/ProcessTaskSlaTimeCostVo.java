/*
Copyright(c) 2023 NeatLogic Co., Ltd. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package neatlogic.framework.process.dto;

/**
 * @author linbq
 * @since 2021/11/22 18:30
 **/
public class ProcessTaskSlaTimeCostVo {
    /**
     * 直接耗时
     */
    private long realTimeCost;
    /**
     * 工作时间耗时
     */
    private long timeCost;

    public long getRealTimeCost() {
        return realTimeCost;
    }

    public void setRealTimeCost(long realTimeCost) {
        this.realTimeCost = realTimeCost;
    }

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }
}
