/*
 * Copyright(c) 2022 TechSureCo.,Ltd.AllRightsReserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package codedriver.framework.process.exception.operationauth;

import codedriver.framework.process.constvalue.ProcessTaskOperationType;

/**
 * @author linbq
 * @since 2022/3/1 11:27
 **/
public class ProcessTaskAutomaticHandlerNotEnableOperateException extends ProcessTaskPermissionDeniedException {
    private static final long serialVersionUID = 9216337410118158664L;

    public ProcessTaskAutomaticHandlerNotEnableOperateException(ProcessTaskOperationType operationType) {
        super("自动处理节点不支持'" + operationType.getText() + "'操作");
    }
}
