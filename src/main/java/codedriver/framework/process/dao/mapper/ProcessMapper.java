package codedriver.framework.process.dao.mapper;

import codedriver.framework.common.dto.ValueTextVo;
import codedriver.framework.process.dto.*;
import codedriver.framework.process.dto.score.ProcessScoreTemplateVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProcessMapper {
	int checkProcessIsExists(String processUuid);

	List<String> getProcessStepUuidBySlaUuid(String slaUuid);

	ProcessFormVo getProcessFormByProcessUuid(String processUuid);

	List<ProcessStepRelVo> getProcessStepRelByProcessUuid(String processUuid);

	List<ProcessSlaVo> getProcessSlaByProcessUuid(String processUuid);

	List<String> getSlaUuidListByProcessUuid(String processUuid);

	List<ProcessStepVo> getProcessStepDetailByProcessUuid(String processUuid);

	List<String> getProcessStepUuidListByProcessUuid(String processUuid);

	ProcessVo getProcessByUuid(String processUuid);
	
	ProcessVo getProcessByName(String processName);

	ProcessVo getProcessBaseInfoByUuid(String processUuid);

	List<ProcessStepVo> searchProcessStep(ProcessStepVo processStepVo);

	List<ProcessTypeVo> getAllProcessType();

	int checkProcessNameIsRepeat(ProcessVo processVo);

	int searchProcessCount(ProcessVo processVo);

	List<ProcessVo> searchProcessList(ProcessVo processVo);

	List<ValueTextVo> searchProcessListForSelect(ProcessVo processVo);

	int getProcessReferenceCount(String processUuid);

	List<String> getProcessReferenceUuidList(String processUuid);

	int checkProcessDraftIsExists(ProcessDraftVo processDraftVo);

	ProcessDraftVo getProcessDraftByUuid(String uuid);

	List<ProcessDraftVo> getProcessDraftList(ProcessDraftVo processDraftVo);

	String getEarliestProcessDraft(ProcessDraftVo processDraftVo);

	List<ProcessStepWorkerPolicyVo> getProcessStepWorkerPolicyListByProcessUuid(String processUuid);

	ProcessStepVo getProcessStepByUuid(String processStepUuid);

    ProcessScoreTemplateVo getProcessScoreTemplateByProcessUuid(String processUuid);
    
    List<ValueTextVo> getProcessTagForSelect(ProcessTagVo processTagVo);
    
    List<ProcessTagVo> getProcessTagByNameList(List<String> tagNameList);
    
    int getProcessTagCount(ProcessTagVo processTagVo);

	ProcessStepVo getStartProcessStepByProcessUuid(String processUuid);

	int getFormReferenceCount(String formUuid);

	List<ProcessVo> getFormReferenceList(ProcessFormVo processFormVo);

	ProcessSlaVo getProcessSlaByUuid(String caller);

	Long getNotifyPolicyIdByProcessStepUuid(String processStepUuid);

	List<ProcessVo> getProcessListByUuidList(List<String> uuidList);

	List<Long> getProcessStepTagIdListByProcessStepUuid(String processStepUuid);

	int insertProcess(ProcessVo processVo);

	int insertProcessStep(ProcessStepVo processStepVo);

	int insertProcessStepFormAttribute(ProcessStepFormAttributeVo processStepFormAttributeVo);

	int insertProcessStepRel(ProcessStepRelVo processStepRelVo);

//	int insertProcessStepTeam(ProcessStepTeamVo processStepTeamVo);

	int insertProcessStepWorkerPolicy(ProcessStepWorkerPolicyVo processStepWorkerPolicyVo);

	int insertProcessForm(ProcessFormVo processFormVo);

	int insertProcessStepSla(@Param("stepUuid") String stepUuid, @Param("slaUuid") String slaUuid);

	int insertProcessSla(ProcessSlaVo processSlaVo);

	int insertProcessDraft(ProcessDraftVo processDraftVo);

	int insertProcessTag(ProcessTagVo processTagVo);

	int insertProcessStepTag(ProcessStepTagVo processStepTagVo);

	int insertProcessStepCommentTemplate(ProcessStepVo vo);

	int insertProcessStepTask(ProcessStepTaskConfigVo tmpVo);

	int updateProcess(ProcessVo processVo);

	int deleteProcessStepByProcessUuid(String processUuid);

	int deleteProcessStepRelByProcessUuid(String processUuid);

	int deleteProcessStepTeamByProcessUuid(String processUuid);

	int deleteProcessStepWorkerPolicyByProcessUuid(String processUuid);

	int deleteProcessStepFormAttributeByProcessUuid(String processUuid);

	int deleteProcessByUuid(String uuid);

	int deleteProcessFormByProcessUuid(String processUuid);

	int deleteProcessDraft(ProcessDraftVo processDraftVo);

	int deleteProcessDraftByUuid(String uuid);

	int deleteProcessSlaByProcessUuid(String uuid);

	int deleteProcessStepCommentTemplate(String stepUuid);

	int deleteProcessStepTagByProcessUuid(String processUuid);

    int deleteProcessStepTaskByProcessStepUuid(String processStepUuid);

}
