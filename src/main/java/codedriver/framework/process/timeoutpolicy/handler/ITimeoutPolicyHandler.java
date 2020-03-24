package codedriver.framework.process.timeoutpolicy.handler;

import org.springframework.transaction.annotation.Transactional;

import codedriver.framework.process.dto.ProcessTaskStepTimeoutPolicyVo;
import codedriver.framework.process.dto.ProcessTaskStepVo;

public interface ITimeoutPolicyHandler {
	public String getType();

	public String getName();
	
	/**
	* @Author: chenqiwei
	* @Time:Sep 18, 2019
	* @Description: TODO 
	* @param @param workerPolicyVo
	* @param @param currentProcessTaskStepVo
	* @param @return 
	* @return Boolean 成功计算超时日期则返回true，否则返回false
	 */
	@Transactional
	public Boolean execute(ProcessTaskStepTimeoutPolicyVo timeoutPolicyVo, ProcessTaskStepVo currentProcessTaskStepVo);
}
