package codedriver.framework.process.timeoutpolicy.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import codedriver.framework.process.constvalue.TimeoutPolicy;
import codedriver.framework.process.dao.mapper.ProcessTaskMapper;
import codedriver.framework.process.dto.ProcessTaskStepTimeoutPolicyVo;
import codedriver.framework.process.dto.ProcessTaskStepVo;

@Service
public class SimpleTimeoutPolicyHandler implements ITimeoutPolicyHandler {
	@Autowired
	private ProcessTaskMapper processTaskMapper;

	public String getType() {
		return TimeoutPolicy.SIMPLE.getValue();
	}

	public String getName() {
		return TimeoutPolicy.SIMPLE.getText();
	}
	
	public Boolean execute(ProcessTaskStepTimeoutPolicyVo timeoutPolicyVo, ProcessTaskStepVo currentProcessTaskStepVo) {
		if (timeoutPolicyVo.getConfigObj() != null) {
			String uuid = timeoutPolicyVo.getConfigObj().getString("uuid");
			String targetValue = timeoutPolicyVo.getConfigObj().getString("targetvalue");
			/*if (StringUtils.isNotBlank(uuid)) {
				List<ProcessTaskAttributeValueVo> valueList = processTaskMapper.getProcessTaskAttributeValue(currentProcessTaskStepVo.getProcessTaskId(), uuid);
				if (valueList != null && valueList.size() > 0) {
					for (ProcessTaskAttributeValueVo valueVo : valueList) {
						if (valueVo.getValue().equalsIgnoreCase(targetValue)) {
							processTaskMapper.updateProcessTaskStepExpireTime(currentProcessTaskStepVo);
							return true;
						}
					}
				}
			}*/
		}
		return false;
	}
}
