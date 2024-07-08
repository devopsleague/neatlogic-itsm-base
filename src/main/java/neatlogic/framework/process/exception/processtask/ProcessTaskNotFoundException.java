package neatlogic.framework.process.exception.processtask;

import neatlogic.framework.process.exception.core.ProcessTaskRuntimeException;

import java.util.List;
import java.util.stream.Collectors;

public class ProcessTaskNotFoundException extends ProcessTaskRuntimeException {

    private static final long serialVersionUID = 2861954159600811000L;

    public ProcessTaskNotFoundException(Long processTaskId) {
        super("工单“{0}”不存在", processTaskId);
    }

    public ProcessTaskNotFoundException(List<Long> processTaskIdList) {
        super("工单“{0}”不存在", processTaskIdList.stream().map(Object::toString).collect(Collectors.joining(",")));
    }

    public ProcessTaskNotFoundException() {
        super("工单不存在");
    }
}
