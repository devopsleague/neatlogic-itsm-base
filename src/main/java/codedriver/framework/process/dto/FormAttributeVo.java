package codedriver.framework.process.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import codedriver.framework.common.constvalue.ApiParamType;
import codedriver.framework.common.constvalue.Expression;
import codedriver.framework.dto.ExpressionVo;
import codedriver.framework.process.constvalue.ProcessConditionModel;
import codedriver.framework.process.formattribute.core.FormAttributeHandlerFactory;
import codedriver.framework.process.formattribute.core.IFormAttributeHandler;
import codedriver.framework.restful.annotation.EntityField;

public class FormAttributeVo implements Serializable {
    private static final long serialVersionUID = 8282018124626035430L;
    @EntityField(name = "属性uuid", type = ApiParamType.STRING)
    private String uuid;
    @EntityField(name = "表单uuid", type = ApiParamType.STRING)
    private String formUuid;
    @EntityField(name = "表单版本uuid", type = ApiParamType.STRING)
    private String formVersionUuid;
    @EntityField(name = "属性标签名", type = ApiParamType.STRING)
    private String label;
    @EntityField(name = "类型", type = ApiParamType.STRING)
    private String type;
    @EntityField(name = "处理器", type = ApiParamType.STRING)
    private String handler;
    @EntityField(name = "属性配置", type = ApiParamType.STRING)
    private String config;
    @EntityField(name = "属性数据", type = ApiParamType.STRING)
    private String data;
    @EntityField(name = "是否必填", type = ApiParamType.BOOLEAN)
    private boolean isRequired;
    @EntityField(name = "表达式列表", type = ApiParamType.JSONARRAY)
    List<ExpressionVo> expressionList;
    @EntityField(name = "默认表达式", type = ApiParamType.JSONOBJECT)
    ExpressionVo defaultExpression;

    @EntityField(name = "条件模型")
    private String conditionModel = ProcessConditionModel.CUSTOM.getValue();

    public FormAttributeVo() {

    }

    public FormAttributeVo(String formUuid) {
        this.formUuid = formUuid;
    }

    public FormAttributeVo(String formUuid, String formVersionUuid) {
        this.formUuid = formUuid;
        this.formVersionUuid = formVersionUuid;
    }

    public FormAttributeVo(String formUuid, String formVersionUuid, String uuid, String label, String type,
        String handler, boolean isRequired, String config, String data) {
        this.uuid = uuid;
        this.formUuid = formUuid;
        this.formVersionUuid = formVersionUuid;
        this.label = label;
        this.type = type;
        this.handler = handler;
        this.isRequired = isRequired;
        this.config = config;
        this.data = data;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getFormUuid() {
        return formUuid;
    }

    public void setFormUuid(String formUuid) {
        this.formUuid = formUuid;
    }

    public String getFormVersionUuid() {
        return formVersionUuid;
    }

    public void setFormVersionUuid(String formVersionUuid) {
        this.formVersionUuid = formVersionUuid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean isRequired) {
        this.isRequired = isRequired;
    }

    public ExpressionVo getDefaultExpression() {
        if (defaultExpression != null) {
            return defaultExpression;
        }
        if (handler == null) {
            return null;
        }
        IFormAttributeHandler formHandler = FormAttributeHandlerFactory.getHandler(handler);
        if (formHandler != null && formHandler.getParamType() != null
            && formHandler.getParamType().getDefaultExpression() != null) {
            defaultExpression = new ExpressionVo(formHandler.getParamType().getDefaultExpression());
        }
        return defaultExpression;
    }

    public void setDefaultExpression(ExpressionVo defaultExpression) {
        this.defaultExpression = defaultExpression;
    }

    public List<ExpressionVo> getExpressionList() {
        if (CollectionUtils.isNotEmpty(expressionList)) {
            return expressionList;
        }
        if (handler == null) {
            return null;
        }
        List<Expression> processExpressionList = null;
        IFormAttributeHandler formHandler = FormAttributeHandlerFactory.getHandler(handler);
        if (formHandler != null && formHandler.getParamType() != null) {
            processExpressionList = formHandler.getParamType().getExpressionList();
        }
        if (CollectionUtils.isEmpty(processExpressionList)) {
            return null;
        }
        expressionList = new ArrayList<>();
        for (Expression processExpression : processExpressionList) {
            expressionList.add(new ExpressionVo(processExpression));
        }
        return expressionList;
    }

    public void setExpressionList(List<ExpressionVo> expressionList) {
        this.expressionList = expressionList;
    }

    public String getConditionModel() {
        return conditionModel;
    }

    public void setConditionModel(String conditionModel) {
        this.conditionModel = conditionModel;
    }

    public String getHandlerName() {
        if (handler != null) {
            IFormAttributeHandler formHandler = FormAttributeHandlerFactory.getHandler(handler);
            if (formHandler != null) {
                return formHandler.getHandlerName();
            }
        }
        return null;
    }

    public String getHandlerType() {
        if (handler == null) {
            return null;
        }

        if (conditionModel == null) {
            return null;
        }
        IFormAttributeHandler formHandler = FormAttributeHandlerFactory.getHandler(handler);
        return formHandler.getHandlerType(conditionModel);
    }

    public Boolean getIsMultiple() {
        if (handler == null) {
            return null;
        }
        if ("formselect".equals(handler)) {
            JSONObject configObj = JSON.parseObject(config);
            return configObj.getBoolean("isMultiple");
        }

        if (conditionModel.equals(ProcessConditionModel.CUSTOM.getValue())) {
            if ("formcheckbox".equals(handler)) {
                return true;
            } else {
                return false;
            }
        }
        return null;
    }
}
