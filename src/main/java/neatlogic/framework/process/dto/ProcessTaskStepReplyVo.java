package neatlogic.framework.process.dto;

import com.alibaba.fastjson.annotation.JSONField;
import neatlogic.framework.common.constvalue.ApiParamType;
import neatlogic.framework.common.dto.BaseEditorVo;
import neatlogic.framework.dto.WorkAssignmentUnitVo;
import neatlogic.framework.file.dto.FileVo;
import neatlogic.framework.restful.annotation.EntityField;

import java.util.ArrayList;
import java.util.List;

public class ProcessTaskStepReplyVo extends BaseEditorVo {
	@EntityField(name = "回复id", type = ApiParamType.LONG)
	private Long id;
	@EntityField(name = "工单id", type = ApiParamType.LONG)
	private Long processTaskId;
	@EntityField(name = "步骤id", type = ApiParamType.LONG)
	private Long processTaskStepId;
	@EntityField(name = "步骤名", type = ApiParamType.STRING)
    private String processTaskStepName;
	@EntityField(name = "描述内容", type = ApiParamType.STRING)
	private String content;
	@EntityField(name = "类型", type = ApiParamType.STRING)
    private String type;
	@EntityField(name = "附件列表", type = ApiParamType.JSONARRAY)
	private List<FileVo> fileList = new ArrayList<>();
	@EntityField(name = "附件id列表", type = ApiParamType.JSONARRAY)
    private List<Long> fileIdList = new ArrayList<>();
	
	@EntityField(name = "是否可编辑", type = ApiParamType.INTEGER)
	private Integer isEditable;
	@EntityField(name = "是否可删除", type = ApiParamType.INTEGER)
	private Integer isDeletable;
	@JSONField(serialize=false)
	private String contentHash;
	@EntityField(name = "来源", type = ApiParamType.STRING)
	private String source;
	@EntityField(name = "来源中文名", type = ApiParamType.STRING)
	private String sourceName;
	@EntityField(name = "操作人角色", type = ApiParamType.STRING)
	private String operatorRole;
	@EntityField(name = "目标对象列表", type = ApiParamType.JSONARRAY)
	List<WorkAssignmentUnitVo> targetList;

	public ProcessTaskStepReplyVo() {}
	public ProcessTaskStepReplyVo(ProcessTaskStepContentVo processTaskStepContentVo) {
	    super.setFcd(processTaskStepContentVo.getFcd());
        super.setLcd(processTaskStepContentVo.getLcd());
	    super.setFcu(processTaskStepContentVo.getFcu());
	    super.setLcu(processTaskStepContentVo.getLcu());
	    this.id = processTaskStepContentVo.getId();
	    this.processTaskId = processTaskStepContentVo.getProcessTaskId();
	    this.processTaskStepId = processTaskStepContentVo.getProcessTaskStepId();
	    this.type = processTaskStepContentVo.getType();
	    this.contentHash = processTaskStepContentVo.getContentHash();
	    this.source = processTaskStepContentVo.getSource();
	    this.sourceName = processTaskStepContentVo.getSourceName();
	}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public List<FileVo> getFileList() {
		return fileList;
	}
	public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public void setFileList(List<FileVo> fileList) {
		this.fileList = fileList;
	}
	public Long getProcessTaskId() {
		return processTaskId;
	}
	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}
	public Long getProcessTaskStepId() {
		return processTaskStepId;
	}
	public void setProcessTaskStepId(Long processTaskStepId) {
		this.processTaskStepId = processTaskStepId;
	}

	public String getProcessTaskStepName() {
        return processTaskStepName;
    }
    public void setProcessTaskStepName(String processTaskStepName) {
        this.processTaskStepName = processTaskStepName;
    }
    public List<Long> getFileIdList() {
		return fileIdList;
	}
	public void setFileIdList(List<Long> fileIdList) {
		this.fileIdList = fileIdList;
	}

	public Integer getIsEditable() {
		return isEditable;
	}
	public void setIsEditable(Integer isEditable) {
		this.isEditable = isEditable;
	}
	public Integer getIsDeletable() {
		return isDeletable;
	}
	public void setIsDeletable(Integer isDeletable) {
		this.isDeletable = isDeletable;
	}
    public String getContentHash() {
        return contentHash;
    }
    public void setContentHash(String contentHash) {
        this.contentHash = contentHash;
    }

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getOperatorRole() {
		return operatorRole;
	}

	public void setOperatorRole(String operatorRole) {
		this.operatorRole = operatorRole;
	}

	public List<WorkAssignmentUnitVo> getTargetList() {
		return targetList;
	}

	public void setTargetList(List<WorkAssignmentUnitVo> targetList) {
		this.targetList = targetList;
	}
}
