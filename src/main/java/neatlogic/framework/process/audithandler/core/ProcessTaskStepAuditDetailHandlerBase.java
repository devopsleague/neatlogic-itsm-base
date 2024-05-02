package neatlogic.framework.process.audithandler.core;

import neatlogic.framework.crossover.CrossoverServiceFactory;
import neatlogic.framework.process.crossover.ISelectContentByHashCrossoverMapper;
import neatlogic.framework.process.dto.ProcessTaskStepAuditDetailVo;
import org.apache.commons.lang3.StringUtils;

public abstract class ProcessTaskStepAuditDetailHandlerBase implements IProcessTaskStepAuditDetailHandler {

	@Override
	public int handle(ProcessTaskStepAuditDetailVo processTaskStepAuditDetailVo) {
		ISelectContentByHashCrossoverMapper selectContentByHashCrossoverMapper = CrossoverServiceFactory.getApi(ISelectContentByHashCrossoverMapper.class);
		String oldContent = processTaskStepAuditDetailVo.getOldContent();
		if(StringUtils.isNotBlank(oldContent)) {
			processTaskStepAuditDetailVo.setOldContent(selectContentByHashCrossoverMapper.getProcessTaskContentStringByHash(oldContent));
		}
		String newContent = processTaskStepAuditDetailVo.getNewContent();
		if(StringUtils.isNotBlank(newContent)) {
			processTaskStepAuditDetailVo.setNewContent(selectContentByHashCrossoverMapper.getProcessTaskContentStringByHash(newContent));
		}
		return myHandle(processTaskStepAuditDetailVo);		
	}

	protected abstract int myHandle(ProcessTaskStepAuditDetailVo processTaskStepAuditDetailVo);
}
