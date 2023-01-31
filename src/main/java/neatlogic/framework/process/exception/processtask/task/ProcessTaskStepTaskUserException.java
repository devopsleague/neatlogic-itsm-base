/*
 * Copyright (c)  2021 TechSure Co.,Ltd.  All Rights Reserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package neatlogic.framework.process.exception.processtask.task;

import neatlogic.framework.process.exception.core.ProcessTaskRuntimeException;

/**
 * @author lvzk
 * @since 2021/8/31 14:24
 **/
public class ProcessTaskStepTaskUserException extends ProcessTaskRuntimeException {

    private static final long serialVersionUID = -7858629768016359202L;

    public ProcessTaskStepTaskUserException(Long stepTaskUserId) {
        super("回复的stepUserId: "+stepTaskUserId+" 的用户得和 当前登录用户一致");
    }
}