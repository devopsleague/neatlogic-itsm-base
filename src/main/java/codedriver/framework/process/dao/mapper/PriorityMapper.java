package codedriver.framework.process.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	/**
	 * 
	* @Time:2020年7月8日
	* @Description: 从fromSort到toSort之间（fromSort和toSort）的序号加一 
	* @param fromSort
	* @param toSort
	* @return int
	 */
	public int updateSortIncrement(@Param("fromSort")Integer fromSort, @Param("toSort")Integer toSort);
	/**
	 * 
	* @Time:2020年7月8日
	* @Description: 从fromSort到toSort之间（fromSort和toSort）的序号减一 
	* @param fromSort
	* @param toSort
	* @return int
	 */
	public int updateSortDecrement(@Param("fromSort")Integer fromSort, @Param("toSort")Integer toSort);

	public int deletePriorityByUuid(String uuid);
}
