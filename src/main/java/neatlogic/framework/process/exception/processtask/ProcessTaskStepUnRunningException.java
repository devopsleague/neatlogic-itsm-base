/*
 * Copyright (c)  2021 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package neatlogic.framework.process.exception.processtask;

import neatlogic.framework.process.exception.core.ProcessTaskRuntimeException;

public class ProcessTaskStepUnRunningException extends ProcessTaskRuntimeException {

	private static final long serialVersionUID = 1458252264390320510L;

	public ProcessTaskStepUnRunningException() {
		super("当前步骤不是‘处理中’状态");
	}
}
