package codedriver.framework.process.dao.mapper;

import codedriver.framework.process.dto.ProcessCommentTemplateAuthVo;
import codedriver.framework.process.dto.ProcessCommentTemplateVo;

import java.util.List;

public interface ProcessCommentTemplateMapper {

    public int checkTemplateExistsById(Long id);

    public ProcessCommentTemplateVo getTemplateById(Long id);

    public int searchTemplateCount(ProcessCommentTemplateVo vo);

    public List<ProcessCommentTemplateVo> searchTemplate(ProcessCommentTemplateVo vo);

    public int updateTemplate(ProcessCommentTemplateVo vo);

    public int insertTemplate(ProcessCommentTemplateVo vo);

    public int batchInsertAuthority(List<ProcessCommentTemplateAuthVo> list);

    public int deleteTemplate(Long id);

    public int deleteTemplateAuthority(Long id);
}
