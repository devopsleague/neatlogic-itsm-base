package codedriver.framework.process.dao.mapper;

import java.util.List;

import codedriver.framework.process.dto.ProcessStepRelVo;
import codedriver.framework.process.dto.ProcessStepTeamVo;
import codedriver.framework.process.dto.ProcessStepUserVo;
import codedriver.framework.process.dto.ProcessStepVo;
import codedriver.framework.process.dto.ProcessStepWorkerPolicyVo;
import codedriver.framework.process.dto.ProcessVo;

public interface ProcessEventMapper {
	public int checkProcessIsExists(String processUuid);

	public List<ProcessStepRelVo> getProcessStepRelByProcessUuid(String processUuid);

	public List<ProcessStepVo> getProcessStepDetailByProcessUuid(String processUuid);

	public ProcessVo getProcessByUuid(String processUuid);

	public ProcessVo getProcessBaseInfoByUuid(String processUuid);

	public List<ProcessStepVo> searchProcessStep(ProcessStepVo processStepVo);

	public int replaceProcess(ProcessVo processVo);

	public int insertProcessStep(ProcessStepVo processStepVo);

	public int insertProcessStepRel(ProcessStepRelVo processStepRelVo);

	public int insertProcessStepUser(ProcessStepUserVo processStepUserVo);

	public int insertProcessStepTeam(ProcessStepTeamVo processStepTeamVo);

	public int insertProcessStepWorkerPolicy(ProcessStepWorkerPolicyVo processStepWorkerPolicyVo);

	public int deleteProcessStepByProcessUuid(String processUuid);

	public int deleteProcessStepRelByProcessUuid(String processUuid);

	public int deleteProcessStepUserByProcessUuid(String processUuid);

	public int deleteProcessAttributeByProcessUuid(String processUuid);

	public int deleteProcessStepTeamByProcessUuid(String processUuid);

	public int deleteProcessStepWorkerPolicyByProcessUuid(String processUuid);

	public int deleteProcessStepAttributeByProcessUuid(String processUuid);
}
