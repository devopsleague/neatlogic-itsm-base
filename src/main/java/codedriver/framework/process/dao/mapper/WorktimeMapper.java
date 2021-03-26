package codedriver.framework.process.dao.mapper;

import java.util.List;

import codedriver.framework.common.dto.ValueTextVo;
import org.apache.ibatis.annotations.Param;

import codedriver.framework.process.dto.WorktimeRangeVo;
import codedriver.framework.process.dto.WorktimeVo;

public interface WorktimeMapper {

	public WorktimeVo getWorktimeByUuid(String uuid);

	public WorktimeVo getWorktimeByName(String name);

	public int checkWorktimeNameIsRepeat(WorktimeVo worktimeVo);

	public int checkWorktimeIsExists(String uuid);

	public int searchWorktimeCount(WorktimeVo worktimeVo);

	public List<WorktimeVo> searchWorktimeList(WorktimeVo worktimeVo);

	public List<ValueTextVo> searchWorktimeListForSelect(WorktimeVo worktimeVo);

	public int checkWorktimeHasBeenRelatedByChannel(String uuid);

	public int checkWorktimeHasBeenRelatedByTask(String uuid);

	public List<WorktimeRangeVo> getWorktimeRangeListByWorktimeUuid(String worktimeUuid);

	public List<String> getWorktimeDateList(WorktimeRangeVo worktimeRangeVo);

	public WorktimeRangeVo getRecentWorktimeRange(WorktimeRangeVo worktimeRangeVo);
	
	public WorktimeRangeVo getRecentWorktimeRangeBackward(WorktimeRangeVo worktimeRangeVo);

	public long calculateCostTime(@Param("worktimeUuid")
	String worktimeUuid, @Param("startTime")
	long startTime, @Param("endTime")
	long endTime);

	public int checkIsWithinWorktimeRange(@Param("worktimeUuid")String worktimeUuid, @Param("value") long value);

	public int checkIsWithinWorktime(@Param("worktimeUuid")String worktimeUuid, @Param("date") String date);

	public List<WorktimeVo> getYearListByWorktimeUuidList(List<String> worktimeUuidList);

	public int insertWorktime(WorktimeVo worktimeVo);

	public int insertBatchWorktimeRange(List<WorktimeRangeVo> worktimeRangeList);

	public int updateWorktime(WorktimeVo worktimeVo);

	public int updateWorktimeDeleteStatus(WorktimeVo worktimeVo);

	public int deleteWorktimeByUuid(String uuid);

	public int deleteWorktimeRange(WorktimeRangeVo worktimeRangeVo);

}
