package codedriver.framework.process.exception.processtaskserialnumberpolicy;

import codedriver.framework.exception.core.ApiRuntimeException;

public class ProcessTaskSerialNumberPolicyHandlerNotFoundException extends ApiRuntimeException {

    private static final long serialVersionUID = -62442577594790614L;

    public ProcessTaskSerialNumberPolicyHandlerNotFoundException(String handler) {
        super("工单号生成策略：'" + handler + "'不存在");
    }
}