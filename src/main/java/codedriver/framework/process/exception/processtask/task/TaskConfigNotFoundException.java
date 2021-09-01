/*
 * Copyright (c)  2021 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.exception.processtask.task;

import codedriver.framework.exception.core.ApiRuntimeException;

public class TaskConfigNotFoundException extends ApiRuntimeException {

	private static final long serialVersionUID = 5137756922568843278L;

	public TaskConfigNotFoundException(String name) {
		super("任务：'" + name + "'不存在");
	}
}
