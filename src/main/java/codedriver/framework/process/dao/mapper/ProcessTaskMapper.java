/*
 * Copyright(c) 2021 TechSureCo.,Ltd.AllRightsReserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.dao.mapper;

import codedriver.framework.dto.AuthenticationInfoVo;
import codedriver.framework.process.dto.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ProcessTaskMapper {
    ProcessTaskSlaVo getProcessTaskSlaById(Long slaId);

    List<ProcessTaskVo> getProcessTaskByStatusList(@Param("statusList") List<String> statusList, @Param("count") Integer count);

    List<ProcessTaskSlaNotifyVo> getAllProcessTaskSlaNotify();

    List<ProcessTaskSlaTransferVo> getAllProcessTaskSlaTransfer();

    List<ProcessTaskStepVo> getProcessTaskStepBaseInfoBySlaId(Long slaId);

    ProcessTaskSlaTimeVo getProcessTaskSlaTimeBySlaId(Long slaId);

    ProcessTaskSlaNotifyVo getProcessTaskSlaNotifyById(Long id);

    ProcessTaskSlaTransferVo getProcessTaskSlaTransferById(Long id);

    List<ProcessTaskSlaVo> getProcessTaskSlaByProcessTaskStepId(Long processTaskStepId);

    List<ProcessTaskSlaVo> getProcessTaskSlaByProcessTaskId(Long processTaskId);

    List<ProcessTaskSlaTimeVo> getProcessTaskSlaTimeByProcessTaskStepIdList(List<Long> processTaskStepIdList);

    ProcessTaskVo getProcessTaskBaseInfoById(Long processTaskId);


    List<ProcessTaskVo> getTaskListByIdList(List<Long> idList);

    /**
     * 查询待处理的工单id
     * @param map 工单查询条件
     * @return 工单ID列表
     */
    List<Long> getProcessingTaskIdListByCondition(@Param("conditionMap") Map<String, Object> map);

    /**
     * 查询待处理的工单数量
     * @param map 工单查询条件
     * @return 工单数量
     */
    int getProcessingTaskCountByCondition(@Param("conditionMap") Map<String, Object> map);

    List<ProcessTaskStepVo> getProcessTaskStepBaseInfoByProcessTaskId(Long processTaskId);

//    List<Long> getProcessTaskStepIdByConvergeId(Long convergeId);

    ProcessTaskFormVo getProcessTaskFormByProcessTaskId(Long processTaskId);

    List<ProcessTaskFormVo> getProcessTaskFormListByProcessTaskIdList(List<Long> existsProcessTaskIdList);

    List<ProcessTaskFormAttributeDataVo> getProcessTaskStepFormAttributeDataByProcessTaskId(Long processTaskId);

    List<ProcessTaskFormAttributeDataVo> getProcessTaskStepFormAttributeDataByProcessTaskIdList(List<Long> existsFormProcessTaskIdList);

    List<ProcessTaskStepContentVo> getProcessTaskStepContentByProcessTaskStepId(Long processTaskStepId);

    List<ProcessTaskStepContentVo> getProcessTaskStepContentByProcessTaskStepIdList(List<Long> processTaskStepIdList);

    List<ProcessTaskStepUserVo> getProcessTaskStepUserByStepId(
            @Param("processTaskStepId") Long processTaskStepId, @Param("userType") String userType);

    List<ProcessTaskStepWorkerPolicyVo>
    getProcessTaskStepWorkerPolicy(ProcessTaskStepWorkerPolicyVo processTaskStepWorkerPolicyVo);

    List<ProcessTaskStepWorkerVo> getProcessTaskStepWorkerByProcessTaskIdAndProcessTaskStepId(
            @Param("processTaskId") Long processTaskId, @Param("processTaskStepId") Long processTaskStepId);

    List<ProcessTaskStepWorkerVo> getProcessTaskStepWorkerListByProcessTaskIdList(List<Long> processTaskIdList);

    Long getProcessTaskLockById(Long processTaskId);

    int checkProcessTaskConvergeIsExists(ProcessTaskConvergeVo processTaskStepConvergeVo);

    List<ProcessTaskConvergeVo> getProcessTaskConvergeListByStepId(Long processTaskStepId);

    List<ProcessTaskStepVo> getFromProcessTaskStepByToId(Long toProcessTaskStepId);

    List<ProcessTaskStepVo> getToProcessTaskStepByFromIdAndType(
            @Param("fromProcessTaskStepId") Long fromProcessTaskStepId, @Param("type") String type);

    List<ProcessTaskStepVo> getProcessTaskStepByConvergeId(Long convergeId);

    List<ProcessTaskStepRelVo> getProcessTaskStepRelByFromId(Long fromProcessTaskStepId);

    List<ProcessTaskStepRelVo> getProcessTaskStepRelListByFromIdList(List<Long> fromProcessTaskStepIdList);

    List<ProcessTaskStepRelVo> getProcessTaskStepRelByToId(Long toProcessTaskStepId);

    List<ProcessTaskStepRelVo> getProcessTaskStepRelByProcessTaskId(Long processTaskId);

    List<ProcessTaskStepRelVo> getProcessTaskStepRelListByProcessTaskIdList(List<Long> processTaskIdList);

    ProcessTaskStepVo getStartProcessTaskStepByProcessTaskId(Long processTaskId);

    ProcessTaskStepVo getEndProcessTaskStepByProcessTaskId(Long processTaskId);

    List<ProcessTaskStepVo> getProcessTaskStepByProcessTaskIdAndType(@Param("processTaskId") Long processTaskId,
                                                                            @Param("type") String type);

    List<ProcessTaskStepVo> getProcessTaskActiveStepByProcessTaskIdAndProcessStepType(
            @Param("processTaskId") Long processTaskId, @Param("processStepTypeList") List<String> processStepTypeList,
            @Param("isActive") Integer isActive);

    ProcessTaskStepVo getProcessTaskStepBaseInfoById(Long processTaskStepId);

    ProcessTaskVo getProcessTaskById(Long id);

    List<ProcessTaskStepAuditVo> getProcessTaskStepAuditList(ProcessTaskStepAuditVo processTaskStepAuditVo);

    List<ProcessTaskStepVo> getProcessTaskStepListByProcessTaskId(Long processTaskId);

    List<ProcessTaskStepVo> getProcessTaskStepListByProcessTaskIdList(List<Long> processTaskIdList);

    Set<Long> getProcessTaskStepIdSetByChannelUuidListAndAuthenticationInfo(
            @Param("keyword") String keyword,
            @Param("channelUuidList") List<String> channelUuidList,
            @Param("authenticationInfoVo") AuthenticationInfoVo authenticationInfoVo
    );

    ProcessTaskFormAttributeDataVo getProcessTaskFormAttributeDataByProcessTaskIdAndAttributeUuid(
            ProcessTaskFormAttributeDataVo processTaskFormAttributeDataVo);

    int checkIsWorker(@Param("processTaskId") Long processTaskId,
                             @Param("processTaskStepId") Long processTaskStepId, @Param("userType") String userType,
                             @Param("authenticationInfoVo") AuthenticationInfoVo authenticationInfoVo);

    int checkIsProcessTaskStepUser(ProcessTaskStepUserVo processTaskStepUserVo);

    List<ProcessTaskAssignWorkerVo> getProcessTaskAssignWorker(ProcessTaskAssignWorkerVo processTaskAssignWorkerVo);

    ProcessTaskStepVo getProcessTaskStepBaseInfoByProcessTaskIdAndProcessStepUuid(
            @Param("processTaskId") Long processTaskId, @Param("processStepUuid") String processStepUuid);

    List<ProcessTaskStepAuditVo> getProcessTaskAuditList(ProcessTaskStepAuditVo processTaskStepAuditVo);

    List<ProcessTaskVo> getProcessTaskListByIdList(List<Long> processTaskIdList);

    List<ProcessTaskStepVo> getProcessTaskStepListByIdList(List<Long> processTaskStepIdList);

//    ProcessTaskStepNotifyPolicyVo getProcessTaskStepNotifyPolicy(ProcessTaskStepNotifyPolicyVo processTaskStepNotifyPolicyVo);

    Map<String, String> getProcessTaskOldFormAndPropByTaskId(Long processTaskId);

    List<Map<String, Object>> getWorkloadByTeamUuid(String teamUuid);

    List<Long> getFileIdListByContentId(Long contentId);

    ProcessTaskStepContentVo getProcessTaskStepContentById(Long id);

    List<ProcessTaskStepUserVo> getProcessTaskStepUserList(ProcessTaskStepUserVo processTaskStepUserVo);

    List<ProcessTaskStepUserVo> getProcessTaskStepUserListByProcessTaskIdList(List<Long> processTaskIdList);

    List<ProcessTaskStepUserVo> getProcessTaskStepUserListByProcessTaskIdListAndStatusList(@Param("processTaskIdList") List<Long> processTaskIdList, @Param("statusList") List<String> statusList);

    String getProcessTaskScoreInfoById(Long processtaskId);

    ProcessTaskVo getProcessTaskAndStepById(Long processtaskId);

    Long getFromProcessTaskIdByToProcessTaskId(Long toProcessTaskId);

    List<Long> getToProcessTaskIdListByFromProcessTaskId(Long processTaskId);

    int getProcessTaskRelationCountByProcessTaskId(Long processTaskId);

    List<ProcessTaskRelationVo> getProcessTaskRelationList(ProcessTaskRelationVo processTaskRelationVo);

    List<Long> getRelatedProcessTaskIdListByProcessTaskId(Long processTaskId);

    List<Long> checkProcessTaskIdListIsExists(List<Long> processTaskIdList);

    int getProcessTaskCountByKeywordAndChannelUuidList(ProcessTaskSearchVo processTaskSearchVo);

    List<ProcessTaskVo> getProcessTaskListByKeywordAndChannelUuidList(ProcessTaskSearchVo processTaskSearchVo);

    ProcessTaskTranferReportVo getProcessTaskTransferReportByToProcessTaskId(Long toProcessTaskId);

    ProcessTaskRelationVo getProcessTaskRelationById(Long id);

    List<ProcessTaskStepRemindVo> getProcessTaskStepRemindListByProcessTaskStepId(Long processTaskStepId);

    int searchProcessTaskImportAuditCount(ProcessTaskImportAuditVo processTaskImportAuditVo);

    List<ProcessTaskImportAuditVo> searchProcessTaskImportAudit(ProcessTaskImportAuditVo processTaskImportAuditVo);

    ProcessTaskScoreTemplateVo getProcessTaskScoreTemplateByProcessTaskId(Long processTaskId);

    List<Long> getSlaIdListByProcessTaskStepId(Long processTaskStepId);

    List<Long> getSlaIdListByProcessTaskId(Long processTaskId);

    String getProcessTaskSlaConfigById(Long id);

    List<Long> getProcessTaskStepIdListBySlaId(Long slaId);

    List<ProcessTaskSlaNotifyVo> getProcessTaskSlaNotifyBySlaId(Long slaId);

    List<ProcessTaskSlaTransferVo> getProcessTaskSlaTransferBySlaId(Long slaId);

    Long getProcessTaskSlaLockById(Long slaId);

    ProcessTaskStepAgentVo getProcessTaskStepAgentByProcessTaskStepId(Long processTaskStepId);

    int checkProcessTaskFocusExists(@Param("processTaskId") Long processTaskId,
                                           @Param("userUuid") String userUuid);

    List<String> getFocusUsersOfProcessTask(Long processTaskId);

    List<String> getFocusUserListByTaskId(Long processTaskId);

    List<ProcessTagVo> getProcessTaskTagListByProcessTaskId(@Param("processTaskId") Long processTaskId);

    int getProcessTaskStepInOperationCountByProcessTaskId(Long processTaskId);

    int getProcessTaskCountByChannelTypeUuidAndStartTime(ProcessTaskVo processTaskVo);

    List<ProcessTaskVo> getProcessTaskListByChannelTypeUuidAndStartTime(ProcessTaskVo processTaskVo);

    String getProcessTaskStepNameById(Long id);

    Integer getProcessTaskCountBySql(String searchSql);

    List<Map<String, Object>> getWorkcenterProcessTaskMapBySql(String searchSql);

    List<ProcessTaskVo> getProcessTaskBySql(String searchSql);

    Long getProcessTaskIdByChannelUuidLimitOne(String channelUuid);

    Long getProcessTaskIdByPriorityUuidLimitOne(String prioriryUuid);

    List<ChannelVo> getChannelReferencedCountList();

    List<ProcessTaskStepFileVo> getProcessTaskStepFileListByTaskId(Long taskId);

    Long getRepeatGroupIdByProcessTaskId(Long processTaskId);

    List<Long> getProcessTaskIdListByRepeatGroupId(Long repeatGroupId);

    Integer getProcessTaskStepReapprovalRestoreBackupMaxSortByBackupStepId(Long processTaskStepId);

    List<ProcessTaskStepReapprovalRestoreBackupVo> getProcessTaskStepReapprovalRestoreBackupListByBackupStepId(Long processTaskStepId);

    List<ProcessTaskVo> getProcessTaskByIndexKeyword(@Param("keywordList") List<String> keywordList, @Param("limit") int limit, @Param("targetType") String targetType,@Param("columnPro") String columnPro);

    List<Long> getProcessTaskStepIdListByProcessTaskIdAndTagId(ProcessTaskStepTagVo processTaskStepTagVo);

    int insertIgnoreProcessTaskConfig(ProcessTaskConfigVo processTaskConfigVo);

    int replaceProcessTaskOldFormProp(@Param("processTaskId") Long processTaskId, @Param("form") String form,
                                             @Param("prop") String prop);

    int insertProcessTaskForm(ProcessTaskFormVo processTaskFormVo);


    int insertIgnoreProcessTaskFormContent(ProcessTaskFormVo processTaskFormVo);


    int insertProcessTask(ProcessTaskVo processTaskVo);


    int replaceProcessTask(ProcessTaskVo processTaskVo);

    int insertIgnoreProcessTaskContent(ProcessTaskContentVo processTaskContentVo);

    int insertProcessTaskStep(ProcessTaskStepVo processTaskStepVo);

    int replaceProcessTaskStep(ProcessTaskStepVo processTaskStepVo);

    int insertProcessTaskSlaNotify(ProcessTaskSlaNotifyVo processTaskSlaNotifyVo);

    int insertProcessTaskSlaTransfer(ProcessTaskSlaTransferVo processTaskSlaTransferVo);


    int insertProcessTaskStepUser(ProcessTaskStepUserVo processTaskStepUserVo);

    int insertProcessTaskStepWorkerPolicy(ProcessTaskStepWorkerPolicyVo processTaskStepWorkerPolicyVo);

    int insertProcessTaskStepRel(ProcessTaskStepRelVo processTaskStepRelVo);

    int insertProcessTaskStepContent(ProcessTaskStepContentVo processTaskStepContentVo);

    int insertProcessTaskStepAudit(ProcessTaskStepAuditVo processTaskStepAuditVo);

    int insertProcessTaskStepAuditDetail(ProcessTaskStepAuditDetailVo processTaskStepAuditDetailVo);

    int insertIgnoreProcessTaskStepWorker(ProcessTaskStepWorkerVo processTaskStepWorkerVo);

    int insertIgnoreProcessTaskStepUser(ProcessTaskStepUserVo processTaskStepUserVo);

    int insertIgnoreProcessTaskConverge(ProcessTaskConvergeVo processTaskConvergeVo);

    int insertIgnoreProcessTaskStepConfig(ProcessTaskStepConfigVo processTaskStepConfigVo);


    int insertProcessTaskStepFormAttribute(
            ProcessTaskStepFormAttributeVo processTaskStepFormAttributeVo);

    int insertProcessTaskSla(ProcessTaskSlaVo processTaskSlaVo);


    int insertProcessTaskSlaTime(ProcessTaskSlaTimeVo processTaskSlaTimeVo);

    int insertProcessTaskStepSla(@Param("processTaskStepId") Long processTaskStepId, @Param("slaId") Long slaId);


    int replaceProcessTaskFormAttributeData(
            ProcessTaskFormAttributeDataVo processTaskFromAttributeDataVo);

    int insertProcessTaskStepFile(ProcessTaskStepFileVo processTaskStepFileVo);

    int insertProcessTaskAssignWorker(ProcessTaskAssignWorkerVo processTaskAssignWorkerVo);

    int insertIgnoreProcessTaskStepNotifyPolicyConfig(ProcessTaskStepNotifyPolicyVo processTaskStepNotifyPolicyVo);

    int insertProcessTaskStepNotifyPolicy(ProcessTaskStepNotifyPolicyVo processTaskStepNotifyPolicyVo);

    int insertProcessTaskTransferReport(ProcessTaskTranferReportVo processTaskTranferReportVo);

    int replaceProcessTaskRelation(ProcessTaskRelationVo processTaskRelationVo);

    int insertProcessTaskStepRemind(ProcessTaskStepRemindVo processTaskStepRemindVo);

    int batchInsertProcessTaskImportAudit(@Param("list") List<ProcessTaskImportAuditVo> processTaskImportAuditVos);

    int insertProcessTaskScoreTemplate(ProcessTaskScoreTemplateVo processTaskScoreTemplateVo);

    int insertProcessTaskScoreTemplateConfig(ProcessTaskScoreTemplateConfigVo processTaskScoreTemplateConfigVo);


    int insertProcessTaskFocus(@Param("processTaskId") Long processTaskId,
                                      @Param("userUuid") String userUuid);

    int insertProcessTaskTag(@Param("processTaskTagList") List<ProcessTaskTagVo> processTaskTagList);

    int replaceProcessTaskStepAgent(ProcessTaskStepAgentVo processTaskStepAgentVo);

    int insertProcessTaskStepInOperation(ProcessTaskStepInOperationVo processTaskStepInOperationVo);

    int insertProcessTaskStepTag(ProcessTaskStepTagVo processTaskStepTagVo);

    int replaceProcessTaskRepeatList(List<ProcessTaskRepeatVo> processTaskRepeatList);

    int replaceProcessTaskRepeat(ProcessTaskRepeatVo processTaskRepeatVo);

    int insertProcessTaskStepReapprovalRestoreBackup(ProcessTaskStepReapprovalRestoreBackupVo processTaskStepReapprovalRestoreBackupVo);

    int updateProcessTaskStepStatus(ProcessTaskStepVo processTaskStepVo);

    int updateProcessTaskStatus(ProcessTaskVo processTaskVo);

    int updateProcessTaskSlaNotify(ProcessTaskSlaNotifyVo processTaskNotifyVo);


    int updateProcessTaskSlaTime(ProcessTaskSlaTimeVo processTaskSlaTimeVo);

    int updateProcessTaskSlaTransfer(ProcessTaskSlaTransferVo processTaskSlaTransferVo);

//    int updateProcessTaskStepRelIsHit(@Param("fromProcessTaskStepId") Long fromProcessTaskStepId, @Param("toProcessTaskStepId") Long toProcessTaskStepId, @Param("isHit") Integer isHit);
    int updateProcessTaskStepRelIsHit(ProcessTaskStepRelVo processTaskStepRelVo);

//    int updateProcessTaskStepConvergeIsCheck(@Param("isCheck") Integer isCheck,
//                                                    @Param("convergeId") Long convergeId, @Param("processTaskStepId") Long processTaskStepId);


    int updateProcessTaskStepUserStatus(ProcessTaskStepUserVo processTaskStepUserVo);


    int updateProcessTaskIsShow(ProcessTaskVo processTaskVo);


    int updateProcessTaskTitleOwnerPriorityUuid(ProcessTaskVo processTaskVo);

    int updateProcessTaskStepContentById(ProcessTaskStepContentVo processTaskStepContentVo);

    int updateProcessTaskStepWorkerUuid(ProcessTaskStepWorkerVo processTaskStepWorkerVo);

    int updateProcessTaskStepUserUserUuid(ProcessTaskStepUserVo processTaskStepUserVo);

    int updateProcessTaskPriorityUuidById(@Param("id") Long processTaskId,
                                                 @Param("priorityUuid") String priorityUuid);


    int updateProcessTaskSerialNumberById(@Param("id") Long processTaskId,
                                                 @Param("serialNumber") String serialNumber);

    int deleteProcessTaskFormAttributeDataByProcessTaskId(Long processTaskId);

    int deleteProcessTaskStepWorker(ProcessTaskStepWorkerVo processTaskStepWorkerVo);

    int deleteProcessTaskStepUser(ProcessTaskStepUserVo processTaskStepUserVo);

    int deleteProcessTaskConvergeByStepId(Long processTaskStepId);

    int deleteProcessTaskSlaNotifyById(Long slaNotifyId);

    int deleteProcessTaskSlaTransferById(Long slaTransferId);

    int deleteProcessTaskAssignWorker(ProcessTaskAssignWorkerVo processTaskAssignWorkerVo);

    int deleteProcessTaskStepFileByProcessTaskStepId(@Param("processTaskId") Long processTaskId, @Param("processTaskStepId") Long processTaskStepId);

    int deleteProcessTaskStepContentByProcessTaskStepId(Long processTaskStepId);

    int deleteProcessTaskStepFileByContentId(Long contentId);

    int deleteProcessTaskStepContentById(Long contentId);

    int deleteProcessTaskRelationById(Long processTaskRelationId);

    int deleteProcessTaskStepRemind(ProcessTaskStepRemindVo processTaskStepRemindVo);


    int deleteProcessTaskFocus(@Param("processTaskId") Long processTaskId,
                                      @Param("userUuid") String userUuid);

    int deleteProcessTaskStepAgentByProcessTaskStepId(Long processTaskStepId);

    int deleteProcessTaskAssignWorkerByProcessTaskId(Long processTaskId);

    int deleteProcessTaskConvergeByProcessTaskId(Long processTaskId);

    int deleteProcessTaskSlaTransferBySlaId(Long slaId);

    int deleteProcessTaskSlaNotifyBySlaId(Long slaId);

    int deleteProcessTaskStepFileByProcessTaskId(Long processTaskId);

    int deleteProcessTaskFormByProcessTaskId(Long processTaskId);

    int deleteProcessTaskSlaTimeBySlaId(Long slaId);

    int deleteProcessTaskStepByProcessTaskId(Long processTaskId);

    int deleteProcessTaskByProcessTaskId(Long processTaskId);

    int deleteProcessTaskFocusByProcessTaskId(Long processTaskId);

    int deleteProcessTaskTagByProcessTaskId(Long processTaskId);

    int deleteProcessTaskStepInOperationByProcessTaskStepIdAndOperationType(
            ProcessTaskStepInOperationVo processTaskStepInOperationVo);

    int deleteProcessTaskStepWorkerMinorByProcessTaskStepId(Long processTaskStepId);

    int deleteProcessTaskStepUserMinorByProcessTaskStepId(Long processTaskStepId);

    int deleteProcessTaskRepeatByProcessTaskId(Long processTaskId);

    int deleteProcessTaskStepReapprovalRestoreBackupByBackupStepId(Long processTaskStepId);
}
