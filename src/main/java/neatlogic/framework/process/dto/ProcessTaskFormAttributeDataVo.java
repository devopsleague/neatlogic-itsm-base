package neatlogic.framework.process.dto;

import neatlogic.framework.form.dto.AttributeDataVo;

public class ProcessTaskFormAttributeDataVo extends AttributeDataVo {
//    @ESKey(type = ESKeyType.PKEY, name = "processTaskId")
    private Long processTaskId;
//    private Integer sort;

    public ProcessTaskFormAttributeDataVo() {}

    public ProcessTaskFormAttributeDataVo(Long processTaskId, AttributeDataVo attributeDataVo) {
        this.processTaskId = processTaskId;
        this.setId(attributeDataVo.getId());
        this.setFormUuid(attributeDataVo.getFormUuid());
        this.setAttributeUuid(attributeDataVo.getAttributeUuid());
        this.setAttributeKey(attributeDataVo.getAttributeKey());
        this.setAttributeLabel(attributeDataVo.getAttributeLabel());
        this.setHandler(attributeDataVo.getHandler());
        this.setData(attributeDataVo.getData());
        this.setTag(attributeDataVo.getTag());
    }

    public Long getProcessTaskId() {
        return processTaskId;
    }

    public void setProcessTaskId(Long processTaskId) {
        this.processTaskId = processTaskId;
    }

//    public Integer getSort() {
//        return sort;
//    }
//
//    public void setSort(Integer sort) {
//        this.sort = sort;
//    }

}
