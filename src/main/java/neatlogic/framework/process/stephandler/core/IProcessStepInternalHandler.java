package neatlogic.framework.process.stephandler.core;

import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.process.constvalue.ProcessFlowDirection;
import neatlogic.framework.process.dto.ProcessStepVo;
import neatlogic.framework.process.dto.ProcessTaskStepInOperationVo;
import neatlogic.framework.process.dto.ProcessTaskStepVo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Title: IProcessStepInternalHandler
 * @Package neatlogic.framework.process.stephandler.core
 * @Description: 处理流程组件内部业务逻辑
 * @Author: chenqiwei
 * @Date: 2021/1/20 15:55
 **/
public interface IProcessStepInternalHandler {

    String getHandler();

    /**
     * @param @return
     * @return Object
     * @Time: 2020年7月27日
     * @Description: 该步骤特有的步骤信息（当该步骤是开始节点时调用该方法）
     */
    Object getHandlerStepInfo(ProcessTaskStepVo currentProcessTaskStepVo);

    /**
     * @param @return
     * @return Object
     * @Time: 2020年8月12日
     * @Description: 该步骤特有的步骤初始化信息 （当该步骤不是开始节点时调用该方法）
     */
    Object getHandlerStepInitInfo(ProcessTaskStepVo currentProcessTaskStepVo);

    /**
     * @param @param processStepVo
     * @param @param stepConfigObj
     * @return void
     * @Author: chenqiwei
     * @Time: Feb 10, 2020
     * @Description: 组装步骤节点信息，将步骤stepConfig配置信息中的字段值写入到ProcessStepVo对象对应属性中
     */
    void makeupProcessStep(ProcessStepVo processStepVo, JSONObject stepConfigObj);

    /**
     * @param processTaskId
     * @param processTaskStepId
     * @return void
     * @Description: 子任务状态发生变化后，对子任务处理人的在 processtask_step_worker表和processtask_step_user表的数据做对应的变化
     */
    void updateProcessTaskStepUserAndWorker(Long processTaskId, Long processTaskStepId);

    /**
     * @param configObj
     * @return JSONObject
     * @Time: 2020年6月30日
     * @Description: 构造节点管理配置数据，初始化节点管理中各个节点的全局配置信息，设置默认值，校正节点的全局配置数据，对配置数据中没用的字段删除，对缺失的字段用默认值补充。
     */
    default JSONObject makeupConfig(JSONObject configObj) {
        if (configObj == null) {
            configObj = new JSONObject();
        }
        return configObj;
    }

    /**
     * 初始化流程步骤的默认配置信息，校正流程步骤配置数据，对配置数据中没用的字段删除，对缺失的字段用默认值补充。
     *
     * @param configObj 配置数据
     * @return
     */
    default JSONObject regulateProcessStepConfig(JSONObject configObj) {
        return configObj;
    }

    /**
     * @param processTaskStepId
     * @return void
     * @Time:2020年9月15日
     * @Description: 根据工单步骤id获取自定义按钮文案映射
     */
    Map<String, String> getCustomButtonMapByProcessTaskStepId(Long processTaskStepId);

    /**
     * @Description: 根据步骤configHash和handler获取自定义按钮文案映射
     * @Author: linbq
     * @Date: 2020/9/15 12:17
     * @Params:[configHash, handler]
     * @Returns:java.util.Map<java.lang.String,java.lang.String>
     **/
    Map<String, String> getCustomButtonMapByConfigHashAndHandler(String configHash, String handler);

    /**
     * @Description: 根据步骤configHash和handler、status获取状态自定义按钮文案
     * @Author: linbq
     * @Date: 2020/9/15 12:17
     * @Params:[configHash, handler, status]
     * @Returns:java.lang.String
     **/
    String getStatusTextByConfigHashAndHandler(String configHash, String handler, String status);

    /**
     * @param configHash
     * @return Integer
     * @Time:2020年11月23日
     * @Description: 获取步骤配置信息中isRequired(回复是否必填)字段值
     */
    Integer getIsRequiredByConfigHash(String configHash);

    /**
     * @param configHash
     * @return Integer
     * @Time:2020年11月23日
     * @Description: 获取步骤配置信息中isNeedContent(回复是否启用)字段值
     */
    Integer getIsNeedContentByConfigHash(String configHash);

    /**
     * 获取步骤配置信息中isNeedUploadFile(是否启用上传文件)字段值
     *
     * @param configHash
     * @return
     */
    Integer getIsNeedUploadFileByConfigHash(String configHash);

    /**
     * 获取步骤配置信息中enableReapproval(启用重审)字段值
     *
     * @param configHash
     * @return
     */
    Integer getEnableReapprovalByConfigHash(String configHash);

    /**
     * 获取步骤配置信息中formSceneUuid(表单场景)字段值
     *
     * @param configHash
     * @return
     */
    String getFormSceneUuidByConfigHash(String configHash);

    /**
     * 向processtask_step_in_operation表中插入步骤正在操作记录，等到该步骤操作完成时会删除这条记录
     *
     * @param processTaskStepInOperationVo
     * @return
     */
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    int insertProcessTaskStepInOperation(ProcessTaskStepInOperationVo processTaskStepInOperationVo);

    /**
     * 获取该步骤中的附件id列表
     *
     * @param currentProcessTaskStepVo
     * @return
     */
    default List<Long> getFileIdList(ProcessTaskStepVo currentProcessTaskStepVo) {
        return new ArrayList<>();
    }

    /**
     * 获取可流转步骤列表
     *
     * @param currentProcessTaskStepVo
     * @return
     */
    List<ProcessTaskStepVo> getNextStepList(ProcessTaskStepVo currentProcessTaskStepVo, ProcessFlowDirection processFlowDirection);
}
