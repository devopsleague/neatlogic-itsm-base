/*
 * Copyright(c) 2022 TechSureCo.,Ltd.AllRightsReserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package neatlogic.framework.process.exception.operationauth;

/**
 * @author linbq
 * @since 2022/3/1 11:27
 **/
public class ProcessTaskStepNoNeedReapprovalStepException extends ProcessTaskPermissionDeniedException {
    private static final long serialVersionUID = 9216337410118158660L;

    public ProcessTaskStepNoNeedReapprovalStepException() {
        super("没有需要重审的步骤");
    }
}