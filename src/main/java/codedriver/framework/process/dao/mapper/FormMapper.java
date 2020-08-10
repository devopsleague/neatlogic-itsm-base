package codedriver.framework.process.dao.mapper;

import java.util.List;

import codedriver.framework.common.dto.ValueTextVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import codedriver.framework.process.dto.FormAttributeVo;
import codedriver.framework.process.dto.FormVersionVo;
import codedriver.framework.process.dto.FormVo;
import codedriver.framework.process.dto.ProcessFormVo;
import codedriver.framework.process.dto.ProcessVo;

@Component("processFormMapper")
public interface FormMapper {
	public FormVersionVo getActionFormVersionByFormUuid(String formUuid);

	public List<FormVo> searchFormList(FormVo formVo);

	public List<ValueTextVo> searchFormListForSelect(FormVo formVo);

	public int searchFormCount(FormVo formVo);

	public FormVo getFormByUuid(String formUuid);

	public FormVersionVo getFormVersionByUuid(String formVersionUuid);

	public List<FormVersionVo> getFormVersionByFormUuid(String formUuid);
	
	public List<FormVersionVo> getFormVersionSimpleByFormUuid(String formUuid);
	
	public int getFormReferenceCount(String formUuid);
	
	public List<ProcessVo> getFormReferenceList(ProcessFormVo processFormVo);

	public Integer getMaxVersionByFormUuid(String formUuid);

	public int checkFormIsExists(String uuid);

	public int checkFormNameIsRepeat(FormVo formVo);

	public int checkFormVersionIsExists(String uuid);

	public List<FormAttributeVo> getFormAttributeList(FormAttributeVo formAttributeVo);
	
	public List<FormAttributeVo> getFormAttributeListByChannelUuidList(@Param("channelUuidList") List<String> channelUuidList);

	public int insertForm(FormVo formVo);

	public int resetFormVersionIsActiveByFormUuid(String formUuid);

	public int updateFormVersion(FormVersionVo formVersionVo);

	public void updateForm(FormVo formVo);

	public int insertFormVersion(FormVersionVo formVersionVo);

	public int insertFormAttribute(FormAttributeVo formAttributeVo);

	public int deleteFormAttributeByFormUuid(String formUuid);

	public int deleteFormByUuid(String uuid);

	public int deleteFormVersionByFormUuid(String formUuid);

	public int deleteProcessFormByFormUuid(String formUuid);

	public int deleteFormVersionByUuid(String uuid);

	public int deleteProcessMatrixFormComponentByFormVersionUuid(String formVersionUuid);
}
