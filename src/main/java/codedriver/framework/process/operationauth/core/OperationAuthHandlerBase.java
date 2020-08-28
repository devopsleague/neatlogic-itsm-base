package codedriver.framework.process.operationauth.core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.common.constvalue.GroupSearch;
import codedriver.framework.common.constvalue.UserType;
import codedriver.framework.dao.mapper.TeamMapper;
import codedriver.framework.process.constvalue.ProcessTaskOperationType;
import codedriver.framework.process.constvalue.ProcessStepMode;
import codedriver.framework.process.constvalue.ProcessTaskGroupSearch;
import codedriver.framework.process.constvalue.ProcessUserType;
import codedriver.framework.process.dao.mapper.ChannelMapper;
import codedriver.framework.process.dao.mapper.ProcessStepHandlerMapper;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dto.ProcessStepHandlerVo;
import codedriver.framework.process.dto.ProcessTaskStepUserVo;
import codedriver.framework.process.dto.ProcessTaskStepVo;
import codedriver.framework.process.dto.ProcessTaskStepWorkerVo;
import codedriver.framework.process.dto.ProcessTaskVo;
import codedriver.framework.process.exception.process.ProcessStepHandlerNotFoundException;
import codedriver.framework.process.stephandler.core.IProcessStepHandler;
import codedriver.framework.process.stephandler.core.ProcessStepHandlerFactory;

public abstract class OperationAuthHandlerBase implements IOperationAuthHandler {
    
    @Autowired
    protected static ProcessTaskMapper processTaskMapper;
    @Autowired
    protected static TeamMapper teamMapper;
    @Autowired
    protected static ChannelMapper channelMapper;
    @Autowired
    protected static ProcessStepHandlerMapper processStepHandlerMapper;

	public static void setProcessTaskMapper(ProcessTaskMapper processTaskMapper) {
        OperationAuthHandlerBase.processTaskMapper = processTaskMapper;
    }

    public static void setTeamMapper(TeamMapper teamMapper) {
        OperationAuthHandlerBase.teamMapper = teamMapper;
    }

    public static void setChannelMapper(ChannelMapper channelMapper) {
        OperationAuthHandlerBase.channelMapper = channelMapper;
    }

    public static void setProcessStepHandlerMapper(ProcessStepHandlerMapper processStepHandlerMapper) {
        OperationAuthHandlerBase.processStepHandlerMapper = processStepHandlerMapper;
    }

    private OperationAuthHandlerBase nextHandler;
	private OperationAuthHandlerBase prevHandler;

	public OperationAuthHandlerBase setNext(OperationAuthHandlerBase next) {
		if (next != null) {
			OperationAuthHandlerBase temp = moveToLast();
			temp.nextHandler = next;
			next.prevHandler = temp;
		}
		return next;
	}

	public OperationAuthHandlerBase moveToLast() {
		OperationAuthHandlerBase firstHandler = this;
		while (firstHandler.nextHandler != null) {
			firstHandler = firstHandler.nextHandler;
		}
		return firstHandler;
	}

	public OperationAuthHandlerBase moveToFirst() {
		OperationAuthHandlerBase firstHandler = this;
		while (firstHandler.prevHandler != null) {
			firstHandler = firstHandler.prevHandler;
		}
		return firstHandler;
	}

	@Override
	public final Map<ProcessTaskOperationType, Boolean> getFinalOperateMap(Long processTaskId, Long processTaskStepId) {
		Map<ProcessTaskOperationType, Boolean> operateMap = getOperateMap(processTaskId, processTaskStepId);
		if (nextHandler != null) {
		    Map<ProcessTaskOperationType, Boolean> nextOperateMap = nextHandler.getFinalOperateMap(processTaskId, processTaskStepId);
		    if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
	            operateMap.putAll(nextOperateMap);
		    }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
		        operateMap = nextOperateMap;
		    }
		}
		return operateMap;
	}

	public abstract Map<ProcessTaskOperationType, Boolean> getOperateMap(Long processTaskId, Long processTaskStepId);
	
	@Override
    public final Map<ProcessTaskOperationType, Boolean> getFinalOperateMap(Long processTaskId, Long processTaskStepId, List<ProcessTaskOperationType> operationTypeList) {
	    Map<ProcessTaskOperationType, Boolean> operateMap = getOperateMap(processTaskId, processTaskStepId, operationTypeList);
        if (nextHandler != null) {
            Map<ProcessTaskOperationType, Boolean> nextOperateMap = nextHandler.getFinalOperateMap(processTaskId, processTaskStepId, operationTypeList);
            if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                operateMap.putAll(nextOperateMap);
            }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                operateMap = nextOperateMap;
            }
        }
        return operateMap;
    }

	public abstract Map<ProcessTaskOperationType, Boolean> getOperateMap(Long processTaskId, Long processTaskStepId, List<ProcessTaskOperationType> operationTypeList);
	
	/**
     * 
     * @Time:2020年4月3日
     * @Description: 获取当前用户在当前步骤中工单干系人列表
     * @param processTaskVo     工单信息
     * @param processTaskStepId 步骤id
     * @return List<String>
     */
    protected List<String> getCurrentUserProcessUserTypeList(ProcessTaskVo processTaskVo, Long processTaskStepId) {
        List<String> currentUserProcessUserTypeList = new ArrayList<>();
        currentUserProcessUserTypeList.add(UserType.ALL.getValue());
        if (UserContext.get().getUserUuid(true).equals(processTaskVo.getOwner())) {
            currentUserProcessUserTypeList.add(ProcessUserType.OWNER.getValue());
        }
        if (UserContext.get().getUserUuid(true).equals(processTaskVo.getReporter())) {
            currentUserProcessUserTypeList.add(ProcessUserType.REPORTER.getValue());
        }
        List<ProcessTaskStepUserVo> majorUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.MAJOR.getValue());
        List<String> majorUserUuidList = majorUserList.stream().map(ProcessTaskStepUserVo::getUserUuid).collect(Collectors.toList());
        if (majorUserUuidList.contains(UserContext.get().getUserUuid(true))) {
            currentUserProcessUserTypeList.add(ProcessUserType.MAJOR.getValue());
        }
        List<ProcessTaskStepUserVo> minorUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.MINOR.getValue());
        List<String> minorUserUuidList = minorUserList.stream().map(ProcessTaskStepUserVo::getUserUuid).collect(Collectors.toList());
        if (minorUserUuidList.contains(UserContext.get().getUserUuid(true))) {
            currentUserProcessUserTypeList.add(ProcessUserType.MINOR.getValue());
        }
        List<ProcessTaskStepUserVo> agentUserList = processTaskMapper.getProcessTaskStepUserByStepId(processTaskStepId, ProcessUserType.AGENT.getValue());
        List<String> agentUserUuidList = agentUserList.stream().map(ProcessTaskStepUserVo::getUserUuid).collect(Collectors.toList());
        if (agentUserUuidList.contains(UserContext.get().getUserUuid(true))) {
            currentUserProcessUserTypeList.add(ProcessUserType.AGENT.getValue());
        }
        List<ProcessTaskStepWorkerVo> workerList = processTaskMapper.getProcessTaskStepWorkerByProcessTaskStepId(processTaskStepId);
        if (CollectionUtils.isNotEmpty(workerList)) {
            List<String> currentUserTeamList = teamMapper.getTeamUuidListByUserUuid(UserContext.get().getUserUuid(true));
            for (ProcessTaskStepWorkerVo worker : workerList) {
                if (GroupSearch.USER.getValue().equals(worker.getType()) && UserContext.get().getUserUuid(true).equals(worker.getUuid())) {
                    currentUserProcessUserTypeList.add(ProcessUserType.WORKER.getValue());
                    break;
                } else if (GroupSearch.TEAM.getValue().equals(worker.getType()) && currentUserTeamList.contains(worker.getUuid())) {
                    currentUserProcessUserTypeList.add(ProcessUserType.WORKER.getValue());
                    break;
                } else if (GroupSearch.ROLE.getValue().equals(worker.getType()) && UserContext.get().getRoleUuidList().contains(worker.getUuid())) {
                    currentUserProcessUserTypeList.add(ProcessUserType.WORKER.getValue());
                    break;
                }
            }
        }

        return currentUserProcessUserTypeList;
    }
    
    /**
     * 
     * @Time:2020年4月2日
     * @Description: 获取流程节点配置中的当前用户的拥有的权限
     * @param processTaskVo
     * @param processTaskStepVo
     * @param actionList                     要获取的权限集合
     * @param currentUserProcessUserTypeList 当前用户工单干系人列表
     * @return List<String>
     */
    protected boolean getProcessTaskStepConfigActionList(ProcessTaskVo processTaskVo, ProcessTaskStepVo processTaskStepVo, ProcessTaskOperationType operationType) {
        String stepConfig = processTaskMapper.getProcessTaskStepConfigByHash(processTaskStepVo.getConfigHash());
        JSONObject stepConfigObj = JSON.parseObject(stepConfig);
        JSONArray authorityList = stepConfigObj.getJSONArray("authorityList");
        // 如果步骤自定义权限设置为空，则用组件的全局权限设置
        if (CollectionUtils.isEmpty(authorityList)) {
            ProcessStepHandlerVo processStepHandlerVo = processStepHandlerMapper.getProcessStepHandlerByHandler(processTaskStepVo.getHandler());
            if(processStepHandlerVo != null) {
                JSONObject handlerConfigObj = processStepHandlerVo.getConfig();
                if(MapUtils.isNotEmpty(handlerConfigObj)) {
                    authorityList = handlerConfigObj.getJSONArray("authorityList");
                }
            }
        }

        if (CollectionUtils.isNotEmpty(authorityList)) {
            for (int i = 0; i < authorityList.size(); i++) {
                JSONObject authorityObj = authorityList.getJSONObject(i);
                String action = authorityObj.getString("action");
                if(operationType.getValue().equals(action)) {
                    JSONArray acceptList = authorityObj.getJSONArray("acceptList");
                    if (CollectionUtils.isNotEmpty(acceptList)) {
                        List<String> currentUserProcessUserTypeList = getCurrentUserProcessUserTypeList(processTaskVo, processTaskStepVo.getId());
                        List<String> currentUserTeamList = teamMapper.getTeamUuidListByUserUuid(UserContext.get().getUserUuid(true));
                        for (int j = 0; j < acceptList.size(); j++) {
                            String accept = acceptList.getString(j);
                            String[] split = accept.split("#");
                            if (GroupSearch.COMMON.getValue().equals(split[0])) {
                                if (currentUserProcessUserTypeList.contains(split[1])) {
                                    return true;
                                }
                            } else if (ProcessTaskGroupSearch.PROCESSUSERTYPE.getValue().equals(split[0])) {
                                if (currentUserProcessUserTypeList.contains(split[1])) {
                                    return true;
                                }
                            } else if (GroupSearch.USER.getValue().equals(split[0])) {
                                if (UserContext.get().getUserUuid(true).equals(split[1])) {
                                    return true;
                                }
                            } else if (GroupSearch.TEAM.getValue().equals(split[0])) {
                                if (currentUserTeamList.contains(split[1])) {
                                    return true;
                                }
                            } else if (GroupSearch.ROLE.getValue().equals(split[0])) {
                                if (UserContext.get().getRoleUuidList().contains(split[1])) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * 
     * @Time:2020年4月3日
     * @Description: 获取工单中当前用户能撤回的步骤列表
     * @param processTaskId
     * @return Set<ProcessTaskStepVo>
     */
    protected Set<ProcessTaskStepVo> getRetractableStepListByProcessTaskId(Long processTaskId) {
        Set<ProcessTaskStepVo> resultSet = new HashSet<>();
        List<ProcessTaskStepVo> processTaskStepList = processTaskMapper.getProcessTaskStepBaseInfoByProcessTaskId(processTaskId);
        for (ProcessTaskStepVo stepVo : processTaskStepList) {
            /** 找到所有已激活步骤 **/
            if (stepVo.getIsActive().equals(1)) {
                resultSet.addAll(getRetractableStepListByProcessTaskStepId(stepVo.getId()));
            }
        }
        return resultSet;
    }
    
    /**
     * 
     * @Author: 14378
     * @Time:2020年4月3日
     * @Description: 获取当前步骤的前置步骤列表中处理人是当前用户的步骤列表
     * @param processTaskStepId 已激活的步骤id
     * @return List<ProcessTaskStepVo>
     */
    private List<ProcessTaskStepVo> getRetractableStepListByProcessTaskStepId(Long processTaskStepId) {
        List<ProcessTaskStepVo> resultList = new ArrayList<>();
        /** 所有前置步骤 **/
        List<ProcessTaskStepVo> fromStepList = processTaskMapper.getFromProcessTaskStepByToId(processTaskStepId);
        /** 找到所有已完成步骤 **/
        for (ProcessTaskStepVo fromStep : fromStepList) {
            IProcessStepHandler handler = ProcessStepHandlerFactory.getHandler(fromStep.getHandler());
            if (handler != null) {
                if (ProcessStepMode.MT == handler.getMode()) {// 手动处理节点
                    // 获取步骤处理人
                    List<ProcessTaskStepUserVo> majorUserList = processTaskMapper.getProcessTaskStepUserByStepId(fromStep.getId(), ProcessUserType.MAJOR.getValue());
                    List<String> majorUserUuidList = majorUserList.stream().map(ProcessTaskStepUserVo::getUserUuid).collect(Collectors.toList());
                    if (majorUserUuidList.contains(UserContext.get().getUserUuid(true))) {
                        resultList.add(fromStep);
                    }
                } else {// 自动处理节点，继续找前置节点
                    resultList.addAll(getRetractableStepListByProcessTaskStepId(fromStep.getId()));
                }
            } else {
                throw new ProcessStepHandlerNotFoundException(fromStep.getHandler());
            }
        }
        return resultList;
    }
}
