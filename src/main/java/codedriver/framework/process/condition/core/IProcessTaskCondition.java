package codedriver.framework.process.condition.core;

import java.util.List;
import codedriver.framework.condition.core.IConditionHandler;
import codedriver.framework.dto.condition.ConditionVo;

public interface IProcessTaskCondition extends IConditionHandler { 
	
	/**
	 * @Description: 拼接ES where条件
	 * @Param: 
	 * @return: boolean
	 * @Date: 2020/4/13
	 */
	public String  getEsWhere(List<ConditionVo> conditionList,Integer index);
	
	/**
	* @Author 89770
	* @Time 2020年10月19日  
	* @Description: 拼接ES orderby
	* @Param 
	* @return
	 */
	public String  getEsOrderBy(String sort);
}
