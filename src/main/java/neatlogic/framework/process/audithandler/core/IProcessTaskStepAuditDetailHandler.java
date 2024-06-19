package neatlogic.framework.process.audithandler.core;

import neatlogic.framework.process.dto.ProcessTaskStepAuditDetailVo;

public interface IProcessTaskStepAuditDetailHandler {

	String getType();
	
	int handle(ProcessTaskStepAuditDetailVo processTaskStepAuditDetailVo);
}
