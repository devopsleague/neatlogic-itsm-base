/*
 * Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neatlogic.framework.process.stephandler.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import neatlogic.framework.crossover.CrossoverServiceFactory;
import neatlogic.framework.form.attribute.core.FormAttributeDataConversionHandlerFactory;
import neatlogic.framework.form.attribute.core.IFormAttributeDataConversionHandler;
import neatlogic.framework.form.dto.FormAttributeVo;
import neatlogic.framework.form.service.IFormCrossoverService;
import neatlogic.framework.process.crossover.IProcessTaskCrossoverService;
import neatlogic.framework.process.crossover.ISelectContentByHashCrossoverMapper;
import neatlogic.framework.process.dto.ProcessTaskFormAttributeDataVo;
import neatlogic.framework.process.dto.ProcessTaskStepVo;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Optional;

public class ProcessTaskStepContext {
    private final ProcessTaskStepVo processTaskStepVo;

    //以下都是缓存
    JSONObject stepConfig;
    List<ProcessTaskFormAttributeDataVo> processTaskFormAttributeDataList;

    public ProcessTaskStepContext(ProcessTaskStepVo processTaskStepVo) {
        this.processTaskStepVo = processTaskStepVo;
    }

    /**
     * 获取步骤配置信息
     *
     * @return JSONObject
     */
    public JSONObject getStepConfig() {
        if (stepConfig == null) {
            ISelectContentByHashCrossoverMapper selectContentByHashCrossoverMapper = CrossoverServiceFactory.getApi(ISelectContentByHashCrossoverMapper.class);
            String config = selectContentByHashCrossoverMapper.getProcessTaskStepConfigByHash(processTaskStepVo.getConfigHash());
            try {
                stepConfig = JSON.parseObject(config);
            } catch (Exception ex) {

            }
        }
        return stepConfig;
    }

    /**
     * 获取表单属性定义
     *
     * @param attributeUuid 属性uuid
     * @return FormAttributeVo
     */
    public FormAttributeVo getFormAttribute(String attributeUuid) {
        IFormCrossoverService formCrossoverMapper = CrossoverServiceFactory.getApi(IFormCrossoverService.class);
        return formCrossoverMapper.getFormAttributeByUuid(attributeUuid);
    }

    /**
     * 获取表单简单属性值
     *
     * @param attributeUuid 属性uuid
     * @return Object
     */
    public Object getFormSimpleValue(String attributeUuid) {
        if (processTaskFormAttributeDataList == null) {
            IProcessTaskCrossoverService processTaskCrossoverService = CrossoverServiceFactory.getApi(IProcessTaskCrossoverService.class);
            processTaskFormAttributeDataList = processTaskCrossoverService.getProcessTaskFormAttributeDataListByProcessTaskId(processTaskStepVo.getProcessTaskId());
        }
        if (CollectionUtils.isNotEmpty(processTaskFormAttributeDataList)) {
            Optional<ProcessTaskFormAttributeDataVo> op = processTaskFormAttributeDataList.stream().filter(d -> d.getAttributeUuid().equals(attributeUuid)).findFirst();
            if (op.isPresent()) {
                ProcessTaskFormAttributeDataVo attributeDataVo = op.get();
                if (attributeDataVo.getDataObj() != null) {
                    IFormAttributeDataConversionHandler handler = FormAttributeDataConversionHandlerFactory.getHandler(attributeDataVo.getHandler());
                    if (handler != null) {
                        return handler.getSimpleValue(attributeDataVo.getDataObj());
                    } else {
                        return attributeDataVo.getDataObj();
                    }
                }
            }

        }
        return null;
    }

    /**
     * 获取表单属性值
     *
     * @param attributeUuid 属性uuid
     * @return Object
     */
    public Object getFormValue(String attributeUuid) {
        if (processTaskFormAttributeDataList == null) {
            IProcessTaskCrossoverService processTaskCrossoverService = CrossoverServiceFactory.getApi(IProcessTaskCrossoverService.class);
            processTaskFormAttributeDataList = processTaskCrossoverService.getProcessTaskFormAttributeDataListByProcessTaskId(processTaskStepVo.getProcessTaskId());
        }
        if (CollectionUtils.isNotEmpty(processTaskFormAttributeDataList)) {
            Optional<ProcessTaskFormAttributeDataVo> op = processTaskFormAttributeDataList.stream().filter(d -> d.getAttributeUuid().equals(attributeUuid)).findFirst();
            if (op.isPresent()) {
                return op.get().getDataObj();
            }

        }
        return null;
    }
}
