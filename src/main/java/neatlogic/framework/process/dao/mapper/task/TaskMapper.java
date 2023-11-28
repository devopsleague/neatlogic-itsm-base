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

package neatlogic.framework.process.dao.mapper.task;

import neatlogic.framework.common.dto.BasePageVo;
import neatlogic.framework.common.dto.ValueTextVo;
import neatlogic.framework.process.dto.TaskConfigVo;
import com.alibaba.fastjson.JSONArray;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author lvzk
 * @since 2021/9/1 14:18
 **/
public interface TaskMapper {

    int searchTaskConfigCount(TaskConfigVo taskConfigVo);

    List<TaskConfigVo> searchTaskConfig(TaskConfigVo taskConfigVo);

    int checkTaskConfigNameIsRepeat(TaskConfigVo taskConfigVo);

    TaskConfigVo getTaskConfigById(Long taskId);

    TaskConfigVo getTaskConfigByName(String name);

    List<TaskConfigVo> getTaskConfigByIdList(JSONArray stepTaskIdList);

    List<Map<String,Long>> getTaskConfigReferenceCountMap(List<Long> idList);

    List<ValueTextVo> getTaskConfigReferenceProcessList(@Param("taskConfigId") Long taskConfigId,@Param("basePageVo") BasePageVo basePageVo);

    int getTaskConfigReferenceProcessCount(Long taskConfigId);

    int updateTaskConfig(TaskConfigVo taskConfigVo);

    int insertTaskConfig(TaskConfigVo taskConfigVo);

    int deleteTaskConfigById(Long taskId);

}
