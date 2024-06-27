package neatlogic.framework.process.column.core;

import neatlogic.framework.dashboard.dto.DashboardWidgetAllGroupDefineVo;
import neatlogic.framework.process.dto.ProcessTaskVo;
import neatlogic.framework.process.workcenter.dto.JoinTableColumnVo;
import neatlogic.framework.process.workcenter.dto.TableSelectColumnVo;
import neatlogic.framework.process.workcenter.table.ISqlTable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public interface IProcessTaskColumn {

    /**
     * @Description: 字段英文名
     * @Date: 2020/2/2
     */
    String getName();

    /**
     * @Description: 字段显示名
     * @Date: 2020/2/2
     */
    String getDisplayName();

    /**
     * @Description: 字段是否允许排序
     * @Date: 2020/2/2
     */
    Boolean allowSort();

    /**
     * @Description: 获取类型
     * @Date: 2020/3/25
     */
    String getType();

    /**
     * @Description: 获取展示字段样式
     * @Date: 2020/3/26
     */
    String getClassName();

    /**
     * @Description: 获取展示字段默认顺序
     * @Date: 2020/3/27
     */
    Integer getSort();


    /**
     * @Description: 是否显示该字段（控制checkbox是否默认勾选）
     * @Date: 2020/6/5
     */
    Boolean getIsShow();

    /**
     * 是否该字段是否显示（否，则永远不在table中展示这一列数据，也不会在可选的表头选项中显示）
     * @return 是｜否
     */
    Boolean getDisabled();

    /**
     * @author 89770
     * @date 2020年10月19日
     * @Description: 是否需要按该字段排序工单数据
     */
    Boolean getIsSort();

    /**
     * @author 89770
     * @date 2020年12月3日
     * @Description: 导出excel，是否需要导出该字段
     */
    Boolean getIsExport();

    /**
     * @Description: 获取需要关联的表和字段
     * @Author: 89770
     * @Date: 2021/1/26 10:41
     * @Params: []
     * @Returns: java.util.List<neatlogic.framework.process.workcenter.dto.JoinTableColumnVo>
     **/
    List<JoinTableColumnVo> getJoinTableColumnList();

    /**
     * @Description: 主table  用于sort distinct
     * @Author: 89770
     * @Date: 2021/1/26 12:08
     * @Params: []
     * @Returns: neatlogic.framework.process.workcenter.table.ISqlTable
     **/
    ISqlTable getSortSqlTable();

    /**
     * @Description: 主column 用于sort distinct
     * @Author: 89770
     * @Date: 2021/1/26 12:08
     * @Params: []
     * @Returns: java.lang.String
     **/
    String getSortSqlColumn(Boolean isColumn);

    /**
     * @Description: 重新渲染字段
     * @Author: 89770
     * @Date: 2021/1/26 15:29
     * @Params: [processTaskVo]
     * @Returns: java.lang.Object
     **/
    Object getValue(ProcessTaskVo processTaskVo);

    /**
     * @Description: 重新渲染字段 (主要获取字符串 用于导出)
     * @Author: 89770
     * @Date: 2021/1/26 15:29
     * @Params: [processTaskVo]
     * @Returns: java.lang.Object
     **/
    String getSimpleValue(ProcessTaskVo processTaskVo);

    /**
     * @Description: 获取table 需要 select 出来的 column
     * @Author: 89770
     * @Date: 2021/1/26 16:27
     * @Params: [workcenterVo]
     * @Returns: java.util.Map<neatlogic.framework.process.workcenter.table.ISqlTable, java.lang.String>
     **/
    List<TableSelectColumnVo> getTableSelectColumn();

    /**
     * @Description: 获取dashboard 映射关系
     * @Author: 89770
     * @Date: 2021/3/5 17:01
     * @Params: [dbDataMapList]
     * @Returns: com.alibaba.fastjson.JSONArray
     **/
    void getDashboardAllGroupDefine(DashboardWidgetAllGroupDefineVo dashboardWidgetAllGroupDefineVo, List<Map<String, Object>> dbDataMapList);

    /**
     * @Description: 将数据集提取groupList, 用于过滤，并按list 权重排序
     * @Author: 89770
     * @Date: 2021/3/5 17:05
     * @Params: [mapList]
     * @Returns: java.util.List<java.lang.String>
     **/
    LinkedHashMap<String, Object> getExchangeToDashboardGroupDataMap(List<Map<String, Object>> mapList);

}
