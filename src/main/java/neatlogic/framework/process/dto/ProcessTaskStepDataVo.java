package neatlogic.framework.process.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import neatlogic.framework.util.SnowflakeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessTaskStepDataVo {
    static Logger logger = LoggerFactory.getLogger(ProcessTaskStepDataVo.class);
    private Long id;
    private Long processTaskId;
    private Long processTaskStepId;
    private JSONObject data;
    private String type;
    private String fcu;

    public ProcessTaskStepDataVo() {

    }

    public ProcessTaskStepDataVo(Long processTaskId, Long processTaskStepId, String type,String fcu) {
        this.processTaskId = processTaskId;
        this.processTaskStepId = processTaskStepId;
        this.type = type;
        this.fcu = fcu;
    }

    public synchronized Long getId() {
        if (id == null) {
            id = SnowflakeUtil.uniqueLong();
        }
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(String data) {
        try {
            this.data = JSON.parseObject(data);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
        }
    }

    @JSONField(serialize = false)
    public String getDataStr() {
        if (data != null) {
            return data.toJSONString();
        }
        return null;
    }

    public String getFcu() {
        return fcu;
    }

    public void setFcu(String fcu) {
        this.fcu = fcu;
    }

}
