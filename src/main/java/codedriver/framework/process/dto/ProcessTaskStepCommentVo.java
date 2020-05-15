package codedriver.framework.process.dto;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import codedriver.framework.apiparam.core.ApiParamType;
import codedriver.framework.file.dto.FileVo;
import codedriver.framework.process.audithandler.core.IProcessTaskStepAuditDetailHandler;
import codedriver.framework.process.audithandler.core.ProcessTaskStepAuditDetailHandlerFactory;
import codedriver.framework.process.constvalue.ProcessTaskAuditDetailType;
import codedriver.framework.restful.annotation.EntityField;

public class ProcessTaskStepCommentVo {
	//private Long auditId;
	@EntityField(name = "描述内容", type = ApiParamType.STRING)
	private String content;
	@EntityField(name = "附件列表", type = ApiParamType.JSONARRAY)
	private List<FileVo> fileList = new ArrayList<>();
	
	public ProcessTaskStepCommentVo() {
	}
	public ProcessTaskStepCommentVo(ProcessTaskStepAuditVo processTaskStepAuditVo) {
		//auditId = processTaskStepAuditVo.getId();
		List<ProcessTaskStepAuditDetailVo> processTaskStepAuditDetailList = processTaskStepAuditVo.getAuditDetailList();
		for(ProcessTaskStepAuditDetailVo processTaskStepAuditDetailVo : processTaskStepAuditDetailList) {
			IProcessTaskStepAuditDetailHandler auditDetailHandler = ProcessTaskStepAuditDetailHandlerFactory.getHandler(processTaskStepAuditDetailVo.getType());		
			if(ProcessTaskAuditDetailType.CONTENT.getValue().equals(processTaskStepAuditDetailVo.getType())) {
				if(auditDetailHandler != null) {
					auditDetailHandler.handle(processTaskStepAuditDetailVo);
				}
				content = processTaskStepAuditDetailVo.getNewContent();
			}else if(ProcessTaskAuditDetailType.FILE.getValue().equals(processTaskStepAuditDetailVo.getType())){
				if(auditDetailHandler != null) {
					auditDetailHandler.handle(processTaskStepAuditDetailVo);
					fileList = JSON.parseArray(processTaskStepAuditDetailVo.getNewContent(), FileVo.class);
				}
			}
		}
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
	public void setFileList(List<FileVo> fileList) {
		this.fileList = fileList;
	}

//	public Long getAuditId() {
//		return auditId;
//	}
//	public void setAuditId(Long auditId) {
//		this.auditId = auditId;
//	}

}
