package codedriver.framework.process.exception.channeltype;

import codedriver.framework.exception.core.ApiRuntimeException;

public class ChannelTypeHasReferenceException extends ApiRuntimeException {

	private static final long serialVersionUID = -1130799055432844626L;

	public ChannelTypeHasReferenceException(String uuid) {
		super("服务类型：'" + uuid + "'已关联服务，不可删除");
	}
}
