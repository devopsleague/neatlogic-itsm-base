package codedriver.framework.process.dto;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import codedriver.framework.common.constvalue.ApiParamType;
import codedriver.framework.common.dto.BasePageVo;
import codedriver.framework.process.constvalue.ProcessMatrixType;
import codedriver.framework.restful.annotation.EntityField;

/**
 * @program: codedriver
 * @description:
 * @create: 2020-03-26 18:59
 **/
public class ProcessMatrixVo extends BasePageVo {
    private String keyword;
    @EntityField( name = "自增ID", type = ApiParamType.LONG)
    private Long id;
    @EntityField( name = "唯一主键uuid", type = ApiParamType.STRING)
    private String uuid;
    @EntityField( name = "数据源名称", type = ApiParamType.STRING)
    private String name;
    @EntityField( name = "类型",  type = ApiParamType.STRING)
    private String type;
    @EntityField( name = "类型名称", type = ApiParamType.STRING)
    private String typeName;
    @EntityField( name = "创建人",  type = ApiParamType.STRING)
    private String fcu;
    @EntityField( name = "创建时间",  type = ApiParamType.LONG)
    private Date fcd;
    @EntityField( name = "修改人",  type = ApiParamType.STRING)
    private String lcu;
    @EntityField(name = "修改时间", type = ApiParamType.LONG)
    private Date lcd;
    @EntityField(name = "引用次数", type = ApiParamType.INTEGER)
    private int usedCount;

    private List<ProcessMatrixDispatcherVo> dispatcherVoList;
    private List<ProcessMatrixFormComponentVo> componentVoList;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFcu() {
        return fcu;
    }

    public void setFcu(String fcu) {
        this.fcu = fcu;
    }

    public Date getFcd() {
		return fcd;
	}

	public void setFcd(Date fcd) {
		this.fcd = fcd;
	}

	public Date getLcd() {
		return lcd;
	}

	public void setLcd(Date lcd) {
		this.lcd = lcd;
	}

	public String getLcu() {
        return lcu;
    }

    public void setLcu(String lcu) {
        this.lcu = lcu;
    }

    public List<ProcessMatrixDispatcherVo> getDispatcherVoList() {
        return dispatcherVoList;
    }

    public void setDispatcherVoList(List<ProcessMatrixDispatcherVo> dispatcherVoList) {
        this.dispatcherVoList = dispatcherVoList;
    }

    public List<ProcessMatrixFormComponentVo> getComponentVoList() {
        return componentVoList;
    }

    public void setComponentVoList(List<ProcessMatrixFormComponentVo> componentVoList) {
        this.componentVoList = componentVoList;
    }

    public int getUsedCount() {
        usedCount = 0;
        if (CollectionUtils.isNotEmpty(componentVoList)){
            usedCount = usedCount + componentVoList.size();
        }
        if (CollectionUtils.isNotEmpty(dispatcherVoList)){
            usedCount = usedCount + dispatcherVoList.size();
        }
        return usedCount;
    }

    public void setUsedCount(int usedCount) {
        this.usedCount = usedCount;
    }

    public String getTypeName() {
        if (StringUtils.isNotBlank(type)){
            return ProcessMatrixType.getName(type);
        }
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

}
