package neatlogic.framework.process.condition.core;

import neatlogic.framework.condition.core.IConditionHandler;
import neatlogic.framework.dto.condition.ConditionGroupVo;
import neatlogic.framework.process.dto.ProcessTaskStepVo;
import neatlogic.framework.process.dto.SqlDecoratorVo;
import neatlogic.framework.process.workcenter.dto.JoinTableColumnVo;

import java.util.List;

public interface IProcessTaskCondition extends IConditionHandler {

    @Override
    default String getBelong() {
        return "processtask";
    }


    /**
     * 获取sql where 条件
     * @param groupVo 条件组
     * @param index condition 下表
     * @param sqlSb 拼装好的sql
     */
    default void getSqlConditionWhere(ConditionGroupVo groupVo, Integer index, StringBuilder sqlSb){}

    /**
     * @Description: 获取对应条件需要关联的表和字段
     * @Author: 89770
     * @Date: 2021/1/22 16:59
     * @Params: * @param null:
     * @Returns: * @return: null
     **/
    List<JoinTableColumnVo> getJoinTableColumnList(SqlDecoratorVo sqlDecoratorVo);

    /**
     * 获取条件分流需要判断的数据
     *
     * @return 数据
     */
    default Object getConditionParamData(ProcessTaskStepVo processTaskStepVo) {
        return null;
    }

    /**
     * 获取条件分流需要判断的数据，人性化数据，拥有javascript判断
     *
     * @return 数据
     */
    default Object getConditionParamDataForHumanization(ProcessTaskStepVo processTaskStepVo) {
        return getConditionParamData(processTaskStepVo);
    }

    /**
     * 获取条件分流需要判断的数据
     *
     * @return 数据
     */
    default Object getConditionParamDataNew(ProcessTaskStepVo processTaskStepVo, String formTag) {
        return getConditionParamData(processTaskStepVo);
    }

    /**
     * 获取条件分流需要判断的数据，人性化数据，拥有javascript判断
     *
     * @return 数据
     */
    default Object getConditionParamDataForHumanizationNew(ProcessTaskStepVo processTaskStepVo, String formTag) {
        return getConditionParamDataNew(processTaskStepVo, formTag);
    }

}
