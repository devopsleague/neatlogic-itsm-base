package codedriver.framework.process.constvalue;

import java.util.ArrayList;
import java.util.List;

import codedriver.framework.knowledge.dto.SyncSourceVo;
import codedriver.framework.knowledge.source.ISyncSource;

public enum KnowledgeSyncSource implements ISyncSource{
    PROCESSTASK("processtask", "工单");

    private String value;
    private String name;

    private KnowledgeSyncSource(String _value, String _name) {
        this.value = _value;
        this.name = _name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static String getValue(String _value) {
        for (KnowledgeSyncSource s : KnowledgeSyncSource.values()) {
            if (s.getValue().equals(_value)) {
                return s.getValue();
            }
        }
        return null;
    }

    public static String getName(String _value) {
        for (KnowledgeSyncSource s : KnowledgeSyncSource.values()) {
            if (s.getValue().equals(_value)) {
                return s.getName();
            }
        }
        return "";
    }

    @Override
    public List<SyncSourceVo> getSyncSource() {
        List<SyncSourceVo> list = new ArrayList<>();
        for (KnowledgeSyncSource s : KnowledgeSyncSource.values()) {
           SyncSourceVo syncSource = new SyncSourceVo();
           syncSource.setSource(s.value);
           syncSource.setSourceName(s.name);
           list.add(syncSource);
        }
        return list;
    }
    
}
