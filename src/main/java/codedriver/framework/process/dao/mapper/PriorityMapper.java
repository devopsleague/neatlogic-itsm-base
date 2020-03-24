package codedriver.framework.process.dao.mapper;

import java.util.List;

import codedriver.framework.process.dto.PriorityVo;

public interface PriorityMapper {

	public int searchPriorityCount(PriorityVo priorityVo);

	public List<PriorityVo> searchPriorityList(PriorityVo priorityVo);

	public int checkPriorityIsExists(String uuid);

	public PriorityVo getPriorityByUuid(String uuid);

	public int checkPriorityNameIsRepeat(PriorityVo priorityVo);

	public Integer getMaxSort();

	public int insertPriority(PriorityVo priorityVo);

	public int updatePriority(PriorityVo priorityVo);

	public int deletePriorityByUuid(String uuid);

}
