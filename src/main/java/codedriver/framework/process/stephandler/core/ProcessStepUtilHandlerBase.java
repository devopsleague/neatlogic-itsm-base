package codedriver.framework.process.stephandler.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

import codedriver.framework.common.constvalue.GroupSearch;
import codedriver.framework.common.constvalue.TeamLevel;
import codedriver.framework.dto.TeamVo;
import codedriver.framework.notify.dto.NotifyReceiverVo;
import codedriver.framework.process.audithandler.core.IProcessTaskAuditType;
import codedriver.framework.process.constvalue.OperationType;
import codedriver.framework.process.constvalue.ProcessStepType;
import codedriver.framework.process.constvalue.ProcessTaskStepAction;
import codedriver.framework.process.constvalue.ProcessUserType;
import codedriver.framework.process.dto.CatalogVo;
import codedriver.framework.process.dto.ChannelTypeVo;
import codedriver.framework.process.dto.ChannelVo;
import codedriver.framework.process.dto.PriorityVo;
import codedriver.framework.process.dto.ProcessStepHandlerVo;
import codedriver.framework.process.dto.ProcessStepVo;
import codedriver.framework.process.dto.ProcessTaskConfigVo;
import codedriver.framework.process.dto.ProcessTaskFormAttributeDataVo;
import codedriver.framework.process.dto.ProcessTaskFormVo;
import codedriver.framework.process.dto.ProcessTaskStepReplyVo;
import codedriver.framework.process.dto.ProcessTaskStepContentVo;
import codedriver.framework.process.dto.ProcessTaskStepUserVo;
import codedriver.framework.process.dto.ProcessTaskStepVo;
import codedriver.framework.process.dto.ProcessTaskStepWorkerVo;
import codedriver.framework.process.dto.ProcessTaskVo;
import codedriver.framework.process.exception.core.ProcessTaskRuntimeException;
import codedriver.framework.process.exception.process.ProcessStepHandlerNotFoundException;
import codedriver.framework.process.notify.core.NotifyTriggerType;

public abstract class ProcessStepUtilHandlerBase extends ProcessStepHandlerUtilBase implements IProcessStepUtilHandler {



	@Override
	public void activityAudit(ProcessTaskStepVo currentProcessTaskStepVo, IProcessTaskAuditType action) {
		AuditHandler.audit(currentProcessTaskStepVo, action);
	}

	@Override
	public List<String> getProcessTaskStepActionList(Long processTaskId, Long processTaskStepId) {
		return ActionRoleChecker.getProcessTaskStepActionList(processTaskId, processTaskStepId);
	}

	@Override
	public List<String> getProcessTaskStepActionList(Long processTaskId, Long processTaskStepId, List<String> verifyActionList) {
		return ActionRoleChecker.getProcessTaskStepActionList(processTaskId, processTaskStepId, verifyActionList);
	}

	@Override
	public boolean verifyActionAuthoriy(Long processTaskId, Long processTaskStepId, ProcessTaskStepAction action) {
		return ActionRoleChecker.verifyActionAuthoriy(processTaskId, processTaskStepId, action);
	}

	@Override
	public List<ProcessTaskStepVo> getProcessableStepList(Long processTaskId) {
		return ActionRoleChecker.getProcessableStepList(processTaskId);
	}

	@Override
	public Set<ProcessTaskStepVo> getRetractableStepList(Long processTaskId) {
		return ActionRoleChecker.getRetractableStepListByProcessTaskId(processTaskId);
	}

	@Override
	public List<ProcessTaskStepVo> getUrgeableStepList(Long processTaskId) {
		return ActionRoleChecker.getUrgeableStepList(processTaskId);
	}

	@Override
	public void notify(ProcessTaskStepVo currentProcessTaskStepVo, NotifyTriggerType trigger) {
		NotifyHandler.notify(currentProcessTaskStepVo, trigger);
	}
	@Override
	public boolean verifyOperationAuthoriy(Long processTaskId, Long processTaskStepId, OperationType operation) {
		return true;
	}

    @Override
    public String getHandler() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getHandlerStepInfo(Long processTaskStepId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object getHandlerStepInitInfo(Long processTaskStepId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void makeupProcessStep(ProcessStepVo processStepVo, JSONObject stepConfigObj) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateProcessTaskStepUserAndWorker(Long processTaskId, Long processTaskStepId) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public JSONObject makeupConfig(JSONObject configObj) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ProcessTaskVo getProcessTaskDetailById(Long processTaskId) {
      //获取工单基本信息(title、channel_uuid、config_hash、priority_uuid、status、start_time、end_time、expire_time、owner、ownerName、reporter、reporterName)
        ProcessTaskVo processTaskVo = processTaskMapper.getProcessTaskBaseInfoById(processTaskId);
        //获取工单流程图信息
        ProcessTaskConfigVo processTaskConfig = processTaskMapper.getProcessTaskConfigByHash(processTaskVo.getConfigHash());
        if(processTaskConfig == null) {
            throw new ProcessTaskRuntimeException("没有找到工单：'" + processTaskId + "'的流程图配置信息");
        }
        processTaskVo.setConfig(processTaskConfig.getConfig());
        
        //优先级
        PriorityVo priorityVo = priorityMapper.getPriorityByUuid(processTaskVo.getPriorityUuid());
        if(priorityVo == null) {
            priorityVo = new PriorityVo();
            priorityVo.setUuid(processTaskVo.getPriorityUuid());
        }
        processTaskVo.setPriority(priorityVo);
        //上报服务路径
        ChannelVo channelVo = channelMapper.getChannelByUuid(processTaskVo.getChannelUuid());
        if(channelVo != null) {
            CatalogVo catalogVo = catalogMapper.getCatalogByUuid(channelVo.getParentUuid());
            if(catalogVo != null) {
                List<CatalogVo> catalogList = catalogMapper.getAncestorsAndSelfByLftRht(catalogVo.getLft(), catalogVo.getRht());
                List<String> nameList = catalogList.stream().map(CatalogVo::getName).collect(Collectors.toList());
                nameList.add(channelVo.getName());
                processTaskVo.setChannelPath(String.join("/", nameList));
            }
            ChannelTypeVo channelTypeVo =  channelMapper.getChannelTypeByUuid(channelVo.getChannelTypeUuid());
            if(channelTypeVo == null) {
                channelTypeVo = new ChannelTypeVo();
                channelTypeVo.setUuid(channelVo.getChannelTypeUuid());
            }
            processTaskVo.setChannelType(channelTypeVo);
        }
        //耗时
        if(processTaskVo.getEndTime() != null) {
            long timeCost = worktimeMapper.calculateCostTime(processTaskVo.getWorktimeUuid(), processTaskVo.getStartTime().getTime(), processTaskVo.getEndTime().getTime());
            processTaskVo.setTimeCost(timeCost);
        }
        
        //获取工单表单信息
        ProcessTaskFormVo processTaskFormVo = processTaskMapper.getProcessTaskFormByProcessTaskId(processTaskId);
        if(processTaskFormVo != null && StringUtils.isNotBlank(processTaskFormVo.getFormContent())) {
            processTaskVo.setFormConfig(processTaskFormVo.getFormContent());            
            List<ProcessTaskFormAttributeDataVo> processTaskFormAttributeDataList = processTaskMapper.getProcessTaskStepFormAttributeDataByProcessTaskId(processTaskId);
            for(ProcessTaskFormAttributeDataVo processTaskFormAttributeDataVo : processTaskFormAttributeDataList) {
                processTaskVo.getFormAttributeDataMap().put(processTaskFormAttributeDataVo.getAttributeUuid(), processTaskFormAttributeDataVo.getDataObj());
            }
        }
        /** 上报人公司列表 **/
        List<String> teamUuidList = teamMapper.getTeamUuidListByUserUuid(processTaskVo.getOwner());
        if(CollectionUtils.isNotEmpty(teamUuidList)) {
            List<TeamVo> teamList = teamMapper.getTeamByUuidList(teamUuidList);
            for(TeamVo teamVo : teamList) {
                List<TeamVo> companyList = teamMapper.getAncestorsAndSelfByLftRht(teamVo.getLft(), teamVo.getRht(), TeamLevel.COMPANY.getValue());
                if(CollectionUtils.isNotEmpty(companyList)) {
                    processTaskVo.getOwnerCompanyList().addAll(companyList);
                }
            }
        }
        processTaskVo.setStartProcessTaskStep(getStartProcessTaskStepByProcessTaskId(processTaskId));
        return processTaskVo;
    }
    
    private ProcessTaskStepVo getStartProcessTaskStepByProcessTaskId(Long processTaskId) {
        //获取开始步骤id
        List<ProcessTaskStepVo> processTaskStepList = processTaskMapper.getProcessTaskStepByProcessTaskIdAndType(processTaskId, ProcessStepType.START.getValue());
        if(processTaskStepList.size() != 1) {
            throw new ProcessTaskRuntimeException("工单：'" + processTaskId + "'有" + processTaskStepList.size() + "个开始步骤");
        }

        ProcessTaskStepVo startProcessTaskStepVo = processTaskStepList.get(0);
        String stepConfig = processTaskMapper.getProcessTaskStepConfigByHash(startProcessTaskStepVo.getConfigHash());
        startProcessTaskStepVo.setConfig(stepConfig);
        ProcessStepHandlerVo processStepHandlerConfig = processStepHandlerMapper.getProcessStepHandlerByHandler(startProcessTaskStepVo.getHandler());
        if(processStepHandlerConfig != null) {
            startProcessTaskStepVo.setGlobalConfig(processStepHandlerConfig.getConfig());                    
        }

        ProcessTaskStepReplyVo comment = new ProcessTaskStepReplyVo();
        //获取上报描述内容
        List<Long> fileIdList = new ArrayList<>();
        List<ProcessTaskStepContentVo> processTaskStepContentList = processTaskMapper.getProcessTaskStepContentByProcessTaskStepId(startProcessTaskStepVo.getId());
        for(ProcessTaskStepContentVo processTaskStepContent : processTaskStepContentList) {
            if (ProcessTaskStepAction.STARTPROCESS.getValue().equals(processTaskStepContent.getType())) {
                fileIdList = processTaskMapper.getFileIdListByContentId(processTaskStepContent.getId());
                comment.setContent(processTaskMapper.getProcessTaskContentStringByHash(processTaskStepContent.getContentHash()));
                break;
            }
        }
        //附件       
        if(CollectionUtils.isNotEmpty(fileIdList)) {
            comment.setFileList(fileMapper.getFileListByIdList(fileIdList));
        }
        startProcessTaskStepVo.setComment(comment);
        /** 当前步骤特有步骤信息 **/
        IProcessStepUtilHandler startProcessStepUtilHandler = ProcessStepUtilHandlerFactory.getHandler(startProcessTaskStepVo.getHandler());
        if(startProcessStepUtilHandler == null) {
            throw new ProcessStepHandlerNotFoundException(startProcessTaskStepVo.getHandler());
        }
        startProcessTaskStepVo.setHandlerStepInfo(startProcessStepUtilHandler.getHandlerStepInfo(startProcessTaskStepVo.getId()));
        return startProcessTaskStepVo;
    }
    
    @Override
    public void getReceiverMap(Long processTaskId, Long processTaskStepId,
        Map<String, List<NotifyReceiverVo>> receiverMap) {
        ProcessTaskVo processTaskVo = processTaskMapper.getProcessTaskBaseInfoById(processTaskId);
        if (processTaskVo != null) {
            /** 上报人 **/
            if(StringUtils.isNotBlank(processTaskVo.getOwner())) {
                List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.OWNER.getValue());
                if(notifyReceiverList == null) {
                    notifyReceiverList = new ArrayList<>();
                    receiverMap.put(ProcessUserType.OWNER.getValue(), notifyReceiverList);
                }
                notifyReceiverList.add(new NotifyReceiverVo(GroupSearch.USER.getValue(), processTaskVo.getOwner()));
            }
            /** 代报人 **/
            if(StringUtils.isNotBlank(processTaskVo.getReporter())) {
                List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.REPORTER.getValue());
                if(notifyReceiverList == null) {
                    notifyReceiverList = new ArrayList<>();
                    receiverMap.put(ProcessUserType.REPORTER.getValue(), notifyReceiverList);
                }
                notifyReceiverList.add(new NotifyReceiverVo(GroupSearch.USER.getValue(), processTaskVo.getReporter()));
            }
        }
        /** 主处理人 **/
        List<ProcessTaskStepUserVo> majorUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.MAJOR.getValue());
        if (CollectionUtils.isNotEmpty(majorUserList)) {
            List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.MAJOR.getValue());
            if(notifyReceiverList == null) {
                notifyReceiverList = new ArrayList<>();
                receiverMap.put(ProcessUserType.MAJOR.getValue(), notifyReceiverList);
            }
            notifyReceiverList.add(new NotifyReceiverVo(GroupSearch.USER.getValue(), majorUserList.get(0).getUserUuid()));
        }
        /** 子任务处理人 **/
        List<ProcessTaskStepUserVo> minorUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.MINOR.getValue());
        if(CollectionUtils.isNotEmpty(minorUserList)) {
            List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.MINOR.getValue());
            if(notifyReceiverList == null) {
                notifyReceiverList = new ArrayList<>();
                receiverMap.put(ProcessUserType.MINOR.getValue(), notifyReceiverList);
            }
            for(ProcessTaskStepUserVo processTaskStepUserVo : minorUserList) {
                notifyReceiverList.add(new NotifyReceiverVo(GroupSearch.USER.getValue(), processTaskStepUserVo.getUserUuid()));
            }
        }
        /** 待办人 **/
        List<ProcessTaskStepUserVo> agentUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.AGENT.getValue());
        if(CollectionUtils.isNotEmpty(agentUserList)) {
            List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.AGENT.getValue());
            if(notifyReceiverList == null) {
                notifyReceiverList = new ArrayList<>();
                receiverMap.put(ProcessUserType.AGENT.getValue(), notifyReceiverList);
            }
            for(ProcessTaskStepUserVo processTaskStepUserVo : agentUserList) {
                notifyReceiverList.add(new NotifyReceiverVo(GroupSearch.USER.getValue(), processTaskStepUserVo.getUserUuid()));
            }
        }
        /** 待处理人 **/
        List<ProcessTaskStepWorkerVo> workerList = processTaskMapper.getProcessTaskStepWorkerByProcessTaskStepId(processTaskStepId);
        if(CollectionUtils.isNotEmpty(workerList)) {
            List<NotifyReceiverVo> notifyReceiverList = receiverMap.get(ProcessUserType.WORKER.getValue());
            if(notifyReceiverList == null) {
                notifyReceiverList = new ArrayList<>();
                receiverMap.put(ProcessUserType.WORKER.getValue(), notifyReceiverList);
            }
            for(ProcessTaskStepWorkerVo processTaskStepWorkerVo : workerList) {
                notifyReceiverList.add(new NotifyReceiverVo(processTaskStepWorkerVo.getType(), processTaskStepWorkerVo.getUuid()));
            }
        }
    }
}