/*
 * Copyright (c)  2021 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.dao.mapper.task;

import codedriver.framework.process.dto.TaskConfigVo;

import java.util.List;

/**
 * @author lvzk
 * @since 2021/9/1 14:18
 **/
public interface TaskMapper {

    int searchTaskConfigCount(TaskConfigVo taskConfigVo);

    List<TaskConfigVo> searchTaskConfig(TaskConfigVo taskConfigVo);

    int checkTaskConfigNameIsRepeat(TaskConfigVo taskConfigVo);

    TaskConfigVo getTaskConfigById(Long taskId);

    int updateTaskConfig(TaskConfigVo taskConfigVo);

    int insertTaskConfig(TaskConfigVo taskConfigVo);

    int deleteTaskConfigById(Long taskId);
}
