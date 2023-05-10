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

package neatlogic.framework.process.dao.mapper;

import neatlogic.framework.process.dto.agent.ProcessTaskAgentTargetVo;
import neatlogic.framework.process.dto.agent.ProcessTaskAgentVo;

import java.util.List;

/**
 * @author linbq
 * @since 2021/10/9 20:01
 **/
public interface ProcessTaskAgentMapper {

    List<Long> getProcessTaskAgentIdListByFromUserUuid(String fromUserUuid);

    List<ProcessTaskAgentVo> getProcessTaskAgentListByFromUserUuid(String fromUserUuid);

    List<ProcessTaskAgentVo> getProcessTaskAgentListByToUserUuid(String toUserUuid);

    List<ProcessTaskAgentTargetVo> getProcessTaskAgentTargetListByProcessTaskAgentId(Long processTaskAgentId);

    List<ProcessTaskAgentVo> getProcessTaskAgentDetailListByToUserUuid(String toUserUuid);

    List<ProcessTaskAgentVo> getProcessTaskAgentDetailListByFromUserUuidList(List<String> fromUserUuidList);

    int insertProcessTaskAgent(ProcessTaskAgentVo processTaskAgentVo);

    int insertIgnoreProcessTaskAgentTarget(ProcessTaskAgentTargetVo processTaskAgentTargetVo);

    int updateProcessTaskAgentIsActiveByFromUserUuid(String fromUserUuid);

    int deleteProcessTaskAgentByFromUserUuid(String fromUserUuid);

    int deleteProcessTaskAgentTargetByProcessTaskAgentIdList(List<Long> processTaskAgentIdList);
}
