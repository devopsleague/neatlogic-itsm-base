package codedriver.framework.process.column.core;

import com.alibaba.fastjson.JSONObject;
import com.techsure.multiattrsearch.MultiAttrsObject;

public interface IProcessTaskColumn {
	
	/**
	 * @Description: 字段英文名
	 * @Param:
	 * @return: java.lang.String
	 * @Date: 2020/2/2
	 */
	public String getName();
	
	/**
	 * @Description: 字段显示名
	 * @Param:
	 * @return: java.lang.String
	 * @Date: 2020/2/2
	 */
	public String getDisplayName();
	
	/**
	 * @Description: 字段是否允许排序
	 * @Param:
	 * @return: java.lang.String
	 * @Date: 2020/2/2
	 */
	public Boolean allowSort();

	/**
	 * @Description: 获取显示值 workcenter
	 * @Param: 
	 * @return: java.lang.Object
	 * @Date: 2020/2/2
	 */
	public Object getValue(MultiAttrsObject el) throws RuntimeException;
	
	/**
	 * @Description: 获取显示值 dashboard
	 * @Param: 
	 * @return: java.lang.Object
	 * @Date: 2020/2/2
	 */
	public Object getValueText(MultiAttrsObject el) throws RuntimeException;
	
	/**
	 * @Description: 获取类型
	 * @Param: 
	 * @return: java.lang.String
	 * @Date: 2020/3/25
	 */
	public String getType();
	
	/**
	 * @Description: 获取展示字段样式
	 * @Param: 
	 * @return: java.lang.String
	 * @Date: 2020/3/26
	 */
	public String getClassName();
	
	/**
	 * @Description: 获取展示字段默认顺序
	 * @Param: 
	 * @return: java.lang.String
	 * @Date: 2020/3/27
	 */
	public Integer getSort();
	

	/**
	 * @Description: 是否显示该字段
	 * @Param:
	 * @return: java.lang.String
	 * @Date: 2020/6/5
	 */
	public Boolean getIsShow();

	Object getMyValue(JSONObject json);

	public Boolean getDisabled();
	
	/**
	* @Author 89770
	* @Time 2020年10月19日  
	* @Description: 是否需要按该字段排序工单数据
	* @Param 
	* @return
	 */
	public Boolean getIsSort();

	/**
	 * 暂时用来从getValue()返回的数据抽取导出excel时需要的数据
	 * @param json
	 * @return
	 */
	Object getSimpleValue(Object json);
	
	/**
	* @Author 89770
	* @Time 2020年12月3日  
	* @Description: 导出excel，是否需要导出该字段 
	* @Param 
	* @return
	 */
	public Boolean getIsExport();

}
