package codedriver.framework.process.operationauth.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import codedriver.framework.asynchronization.threadlocal.UserContext;
import codedriver.framework.common.constvalue.SystemUser;
import codedriver.framework.dao.mapper.UserMapper;
import codedriver.framework.process.constvalue.ProcessTaskOperationType;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dto.ProcessTaskStepVo;
import codedriver.framework.process.dto.ProcessTaskVo;
import codedriver.framework.process.exception.operationauth.OperationAuthHandlerNotFoundException;
import codedriver.framework.process.exception.processtask.ProcessTaskNoPermissionException;

public class ProcessOperateManager {

	private List<IOperationAuthHandlerType> typeQueue;
	private List<Long> processTaskIdList;
	private Map<Long, List<Long>> processTaskStepIdListMap;
	private List<ProcessTaskOperationType> operationTypeList;
	private ProcessTaskMapper processTaskMapper;
	private UserMapper userMapper;
	private boolean isThrowException;
	private Map<Long, Set<ProcessTaskOperationType>> checkOperationTypeSetMap;
	
	public static class Builder {
	    private List<IOperationAuthHandlerType> typeQueue = new LinkedList<>();
	    private List<Long> processTaskIdList = new ArrayList<>();
	    private Map<Long, List<Long>> processTaskStepIdListMap = new HashMap<>();
	    private List<ProcessTaskOperationType> operationTypeList = new ArrayList<>();
	    private ProcessTaskMapper processTaskMapper;
	    private UserMapper userMapper;
	    private boolean isThrowException;
	    private Map<Long, Set<ProcessTaskOperationType>> checkOperationTypeSetMap = new HashMap<>();
	    
	    public Builder(ProcessTaskMapper processTaskMapper, UserMapper userMapper) {
	        this.processTaskMapper = processTaskMapper;
	        this.userMapper = userMapper;
	    }
	    public Builder setNext(IOperationAuthHandlerType type) {
	        if(OperationAuthHandlerFactory.getHandler(type.getValue()) == null) {
	            throw new OperationAuthHandlerNotFoundException(type.getText());
	        }
	        typeQueue.add(type);
	        return this;
	    }
	    public Builder addProcessTaskId(Long processTaskId) {
	        if(!processTaskIdList.contains(processTaskId)) {
	            processTaskIdList.add(processTaskId);
	        }
	        return this;
	    }
	    public Builder addProcessTaskStepId(Long processTaskId, Long processTaskStepId) {
	        if(processTaskStepId != null) {
	            processTaskStepIdListMap.computeIfAbsent(processTaskId, k -> new ArrayList<>()).add(processTaskStepId);
	        }
	        if(!processTaskIdList.contains(processTaskId)) {
                processTaskIdList.add(processTaskId);
            }
	        return this;
	    }
	    public Builder addOperationType(ProcessTaskOperationType operationType) {
	        if(!operationTypeList.contains(operationType)) {
	            operationTypeList.add(operationType);
	        }
	        return this;
	    }
	    public Builder addCheckOperationType(Long id, ProcessTaskOperationType operationType) {
	        checkOperationTypeSetMap.computeIfAbsent(id, k -> new HashSet<>()).add(operationType);
            return this;
        }
	    public Builder withIsThrowException(boolean isThrowException) {
	        this.isThrowException = isThrowException;
	        return this;
	    }
        public ProcessOperateManager build() {
			return new ProcessOperateManager(this);
		}
	}

	private ProcessOperateManager(Builder builder) {
		this.typeQueue = builder.typeQueue;
		this.processTaskIdList = builder.processTaskIdList;
		this.processTaskStepIdListMap = builder.processTaskStepIdListMap;
		this.operationTypeList = builder.operationTypeList;
		this.processTaskMapper = builder.processTaskMapper;
		this.userMapper = builder.userMapper;
		this.isThrowException = builder.isThrowException;
		this.checkOperationTypeSetMap = builder.checkOperationTypeSetMap;
	}
	
	public Map<Long, Set<ProcessTaskOperationType>> getOperateMap() {
	    Map<Long, Set<ProcessTaskOperationType>> resultMap = new HashMap<>();
	    if(CollectionUtils.isEmpty(processTaskIdList)) {
	        return resultMap;
	    }
	    if(processTaskMapper == null) {
	        return resultMap;
	    }
	    List<ProcessTaskVo> processTaskList = processTaskMapper.getProcessTaskDetailListByIdList(processTaskIdList);
	    for(ProcessTaskVo processTaskVo : processTaskList) {
	        List<String> userUuidList = new ArrayList<>();
	        userUuidList.add(UserContext.get().getUserUuid(true));
	        /** 如果当前用户接受了其他用户的授权，查出其他用户拥有的权限，叠加当前用户权限里 **/
//	        String uuid = userMapper.getUserUuidByAgentUuidAndFunc(UserContext.get().getUserUuid(true), "processtask");
//	        if (StringUtils.isNotBlank(uuid)) {
//	            userUuidList.add(uuid);
//	        }
	        if(CollectionUtils.isNotEmpty(operationTypeList)) {
	            if (OperationAuthHandlerType.TASK.getOperationTypeList().removeAll(operationTypeList)) {
	                IOperationAuthHandler handler = OperationAuthHandlerFactory.getHandler(OperationAuthHandlerType.TASK.getValue());
	                Set<ProcessTaskOperationType> resultSet = new HashSet<>();
	                for(String userUuid : userUuidList) {
	                    Map<ProcessTaskOperationType, Boolean> operateMap = handler.getOperateMap(processTaskVo, userUuid, operationTypeList);
	                    for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
	                        if(entry.getValue() == Boolean.TRUE) {
	                            resultSet.add(entry.getKey());
	                        }
	                    }
	                    resultMap.put(processTaskVo.getId(), resultSet);
	                }
	            }
	            if (OperationAuthHandlerType.STEP.getOperationTypeList().removeAll(operationTypeList)) {
	                List<Long> processTaskStepIdList = processTaskStepIdListMap.get(processTaskVo.getId());
	                if(CollectionUtils.isNotEmpty(processTaskStepIdList)) {
	                    IOperationAuthHandler handler = OperationAuthHandlerFactory.getHandler(OperationAuthHandlerType.STEP.getValue());
	                    Map<Long, ProcessTaskStepVo> processTaskStepMap = processTaskVo.getStepList().stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
	                    for(Long processTaskStepId : processTaskStepIdList) {
	                        ProcessTaskStepVo processTaskStepVo = processTaskStepMap.get(processTaskStepId);
	                        if(processTaskStepVo != null) {
	                            Set<ProcessTaskOperationType> resultSet = new HashSet<>();
	                            for(String userUuid : userUuidList) {
	                                Map<ProcessTaskOperationType, Boolean> operateMap = handler.getOperateMap(processTaskVo, processTaskStepVo, userUuid, operationTypeList);
	                                IOperationAuthHandler handler2 = OperationAuthHandlerFactory.getHandler(processTaskStepVo.getHandler());
	                                Map<ProcessTaskOperationType, Boolean> nextOperateMap = handler2.getOperateMap(processTaskVo, processTaskStepVo, userUuid, operationTypeList);
	                                if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
	                                    operateMap.putAll(nextOperateMap);
	                                }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
	                                    operateMap = nextOperateMap;
	                                }
	                                for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
	                                    if(entry.getValue() == Boolean.TRUE) {
	                                        resultSet.add(entry.getKey());
	                                    }
	                                }
	                                resultMap.put(processTaskStepVo.getId(), resultSet);
	                            }
	                        }
	                    }
	                }
	            }
	        }else {
	            IOperationAuthHandler taskHandler = OperationAuthHandlerFactory.getHandler(OperationAuthHandlerType.TASK.getValue());
                Set<ProcessTaskOperationType> resultSet1 = new HashSet<>();
                for(String userUuid : userUuidList) {
                    Map<ProcessTaskOperationType, Boolean> operateMap = taskHandler.getOperateMap(processTaskVo, userUuid);
                    for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
                        if(entry.getValue() == Boolean.TRUE) {
                            resultSet1.add(entry.getKey());
                        }
                    }
                    resultMap.put(processTaskVo.getId(), resultSet1);
                }
                
                List<Long> processTaskStepIdList = processTaskStepIdListMap.get(processTaskVo.getId());
                if(CollectionUtils.isNotEmpty(processTaskStepIdList)) {
                    IOperationAuthHandler handler = OperationAuthHandlerFactory.getHandler(OperationAuthHandlerType.STEP.getValue());
                    Map<Long, ProcessTaskStepVo> processTaskStepMap = processTaskVo.getStepList().stream().collect(Collectors.toMap(e -> e.getId(), e -> e));
                    for(Long processTaskStepId : processTaskStepIdList) {
                        ProcessTaskStepVo processTaskStepVo = processTaskStepMap.get(processTaskStepId);
                        if(processTaskStepVo != null) {
                            Set<ProcessTaskOperationType> resultSet = new HashSet<>();
                            for(String userUuid : userUuidList) {
                                Map<ProcessTaskOperationType, Boolean> operateMap = handler.getOperateMap(processTaskVo, processTaskStepVo, userUuid);
                                IOperationAuthHandler handler2 = OperationAuthHandlerFactory.getHandler(processTaskStepVo.getHandler());
                                Map<ProcessTaskOperationType, Boolean> nextOperateMap = handler2.getOperateMap(processTaskVo, processTaskStepVo, userUuid);
                                if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                                    operateMap.putAll(nextOperateMap);
                                }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                                    operateMap = nextOperateMap;
                                }
                                for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
                                    if(entry.getValue() == Boolean.TRUE) {
                                        resultSet.add(entry.getKey());
                                    }
                                }
                                resultMap.put(processTaskStepVo.getId(), resultSet);
                            }
                        }
                    }
                }
	        }
	        
	    }
	    return resultMap;
	}
	
	public boolean check() {
	    if(MapUtils.isNotEmpty(checkOperationTypeSetMap)) {
	        Map<Long, Set<ProcessTaskOperationType>> resultMap = getOperateMap();
	        for(Map.Entry<Long, Set<ProcessTaskOperationType>> entry : checkOperationTypeSetMap.entrySet()) {
                Set<ProcessTaskOperationType> value = entry.getValue();
	            Set<ProcessTaskOperationType> resultSet = resultMap.get(entry.getKey());
	            if(CollectionUtils.isNotEmpty(resultSet)) {
	                value.removeAll(resultSet);
	            }
	            if(CollectionUtils.isNotEmpty(value)) {
	                if(isThrowException) {
	                    List<String> operationTypeTextList = new ArrayList<>();
	                    for(ProcessTaskOperationType operationType : value) {
	                        operationTypeTextList.add(operationType.getText());
	                    }
	                    throw new ProcessTaskNoPermissionException(String.join("、", operationTypeTextList));
	                }else {
	                    return false;
	                }
	            }
	        }
	    }
	    return true;
	}
	public List<ProcessTaskOperationType> getOperateList(ProcessTaskVo processTaskVo, ProcessTaskStepVo processTaskStepVo, String userUuid) {
        List<ProcessTaskOperationType> resultList = new ArrayList<>();
        //系统用户拥有所有权限
        if(SystemUser.SYSTEM.getUserUuid().equals(userUuid)) {
            for(IOperationAuthHandlerType type : typeQueue) {
                for(ProcessTaskOperationType operationType : type.getOperationTypeList()) {
                    if(!resultList.contains(operationType)) {
                        resultList.add(operationType);
                    }
                }
            }
        }else {
            Map<ProcessTaskOperationType, Boolean> operateMap = new HashMap<>();
            for(IOperationAuthHandlerType type : typeQueue) {
                IOperationAuthHandler handler = OperationAuthHandlerFactory.getHandler(type.getValue());
                Map<ProcessTaskOperationType, Boolean> nextOperateMap = handler.getOperateMap(processTaskVo, processTaskStepVo, userUuid);
                if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                    operateMap.putAll(nextOperateMap);
                }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                    operateMap = nextOperateMap;
                }
            }
            if (MapUtils.isNotEmpty(operateMap)) {
                for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
                    if(entry.getValue() == Boolean.TRUE) {
                        resultList.add(entry.getKey());
                    }
                }
                return resultList;
            }
        }
        return resultList;
    }

    public List<ProcessTaskOperationType> getOperateList(ProcessTaskVo processTaskVo, ProcessTaskStepVo processTaskStepVo, String userUuid, List<ProcessTaskOperationType> operationTypeList) {
        //系统用户拥有所有权限
        if(SystemUser.SYSTEM.getUserUuid().equals(userUuid)) {
            return operationTypeList;
        }
        List<ProcessTaskOperationType> resultList = new ArrayList<>();
        Map<ProcessTaskOperationType, Boolean> operateMap = new HashMap<>();
        for(IOperationAuthHandlerType type : typeQueue) {
            IOperationAuthHandler handler = OperationAuthHandlerFactory.getHandler(type.getValue());
            Map<ProcessTaskOperationType, Boolean> nextOperateMap = handler.getOperateMap(processTaskVo, processTaskStepVo, userUuid, operationTypeList);
            if(MapUtils.isNotEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                operateMap.putAll(nextOperateMap);
            }else if(MapUtils.isEmpty(operateMap) && MapUtils.isNotEmpty(nextOperateMap)) {
                operateMap = nextOperateMap;
            }
        }
        if (MapUtils.isNotEmpty(operateMap)) {
            for(Entry<ProcessTaskOperationType, Boolean> entry : operateMap.entrySet()) {
                if(entry.getValue() == Boolean.TRUE) {
                    resultList.add(entry.getKey());
                }
            }
            return resultList;
        }
        return resultList;
    }
}
