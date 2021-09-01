/*
 * Copyright (c)  2021 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.exception.processtask.task;

import codedriver.framework.process.exception.core.ProcessTaskRuntimeException;

/**
 * @author lvzk
 * @since 2021/8/31 14:24
 **/
public class TaskUserIllegalException extends ProcessTaskRuntimeException {

    private static final long serialVersionUID = -7334568530564862545L;

    public TaskUserIllegalException(String illegalUserUuids) {
        super("用户uuid: '" + illegalUserUuids + "' 不在配置范围内,请联系管理员");
    }
}
