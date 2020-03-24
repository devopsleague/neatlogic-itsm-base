package codedriver.framework.process.dao.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import codedriver.framework.process.dto.ChannelPriorityVo;
import codedriver.framework.process.dto.ChannelRoleVo;
import codedriver.framework.process.dto.ChannelVo;

public interface ChannelMapper {

	int searchChannelCount(ChannelVo channelVo);

	List<ChannelVo> searchChannelList(ChannelVo channelVo);

	Set<String> searchChannelParentUuidList(ChannelVo channelVo);

	ChannelVo getChannelByUuid(String channelUuid);

	int searchChannelRoleCount(ChannelRoleVo channelRoleVo);
	
	List<ChannelRoleVo> searchChannelRoleList(ChannelRoleVo channelRoleVo);
	
	List<ChannelRoleVo> getChannelRoleListByChannelUuid(String channelUuid);
	
	int getMaxSortByParentUuid(String parentUuid);

	List<ChannelPriorityVo> getChannelPriorityListByChannelUuid(String uuid);
	
	int checkChannelIsExists(String channelUuid);

	int checkChannelNameIsRepeat(ChannelVo channelVo);

	List<ChannelVo> getChannelListForTree(Integer isActive);

	String getProcessUuidByChannelUuid(String channelUuid);
	
	int replaceChannelUser(@Param("userId")String userId, @Param("channelUuid")String channelUuid);	

	int replaceChannel(ChannelVo channelVo);

	int replaceChannelRole(ChannelRoleVo channelRole);

	int insertChannelPriority(ChannelPriorityVo channelPriority);
	
	int replaceChannelProcess(@Param("channelUuid")String channelUuid, @Param("processUuid")String processUuid);

	int replaceChannelWorktime(@Param("channelUuid")String channelUuid, @Param("worktimeUuid")String worktimeUuid);

	int updateChannelForMove(ChannelVo channelVo);

	int updateSortIncrement(@Param("parentUuid")String parentUuid, @Param("fromSort")Integer fromSort, @Param("toSort")Integer toSort);

	int updateSortDecrement(@Param("parentUuid")String parentUuid, @Param("fromSort")Integer fromSort, @Param("toSort")Integer toSort);

	int deleteChannelUser(@Param("userId")String userId, @Param("channelUuid")String channelUuid);
	
	int deleteChannelByUuid(String uuid);

	int deleteChannelRole(ChannelRoleVo channelRole);

	int deleteChannelPriorityByChannelUuid(String channelUuid);

	int deleteChannelProcessByChannelUuid(String channelUuid);

	int deleteChannelWorktimeByChannelUuid(String channelUuid);

	int deleteChannelRoleByChannelUuid(String channelUuid);

	int deleteChannelUserByChannelUuid(String channelUuid);
	
}
