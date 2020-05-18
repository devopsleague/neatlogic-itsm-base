package codedriver.framework.process.notify.core;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import codedriver.framework.dao.mapper.UserMapper;
import codedriver.framework.dto.UserVo;
import codedriver.framework.process.exception.notify.NotifyNoReceiverException;
import codedriver.framework.process.notify.dto.NotifyVo;

public abstract class NotifyHandlerBase implements INotifyHandler {
	@Autowired
	private UserMapper userMapper;


	public final void execute(NotifyVo notifyVo) {
		if (CollectionUtils.isEmpty(notifyVo.getToUserList())) {
			if (CollectionUtils.isNotEmpty(notifyVo.getToUserUuidList())) {
				for (String userUuid : notifyVo.getToUserUuidList()) {
					UserVo userVo = userMapper.getUserBaseInfoByUuid(userUuid);
					if (userVo != null) {
						notifyVo.addUser(userVo);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(notifyVo.getToTeamIdList())) {
				for (String teamId : notifyVo.getToTeamIdList()) {
					List<UserVo> teamUserList = userMapper.getActiveUserByTeamId(teamId);
					for (UserVo userVo : teamUserList) {
						notifyVo.addUser(userVo);
					}
				}
			}
			if (CollectionUtils.isNotEmpty(notifyVo.getToRoleNameList())) {
				for (String roleName : notifyVo.getToRoleNameList()) {
					List<UserVo> roleUserList = userMapper.getActiveUserByRoleName(roleName);
					for (UserVo userVo : roleUserList) {
						notifyVo.addUser(userVo);
					}
				}
			}
		}
		if (StringUtils.isNotBlank(notifyVo.getFromUser())) {
			UserVo userVo = userMapper.getUserBaseInfoByUuid(notifyVo.getFromUser());
			if (userVo != null && StringUtils.isNotBlank(userVo.getEmail())) {
				notifyVo.setFromUserEmail(userVo.getEmail());
			}
		}
		if (CollectionUtils.isNotEmpty(notifyVo.getToUserList())) {
			myExecute(notifyVo);
		} else {
			throw new NotifyNoReceiverException();
		}
	}

	protected abstract void myExecute(NotifyVo notifyVo);
}
