/*Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.*/

package neatlogic.framework.process.workcenter.dto;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import neatlogic.framework.asynchronization.threadlocal.UserContext;
import neatlogic.framework.common.constvalue.ApiParamType;
import neatlogic.framework.common.constvalue.DeviceType;
import neatlogic.framework.common.constvalue.GroupSearch;
import neatlogic.framework.common.util.CommonUtil;
import neatlogic.framework.dto.AuthorityVo;
import neatlogic.framework.dto.condition.ConditionVo;
import neatlogic.framework.process.condition.core.IProcessTaskCondition;
import neatlogic.framework.process.condition.core.ProcessTaskConditionFactory;
import neatlogic.framework.process.constvalue.ProcessFieldType;
import neatlogic.framework.process.constvalue.ProcessWorkcenterType;
import neatlogic.framework.process.dto.SqlDecoratorVo;
import neatlogic.framework.restful.annotation.EntityField;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorkcenterVo extends SqlDecoratorVo implements Serializable {
    private static final long serialVersionUID = 1952066708451908924L;

    @EntityField(name = "工单中心分类uuid", type = ApiParamType.STRING)
    private String uuid;
    @EntityField(name = "工单中心分类名", type = ApiParamType.STRING)
    private String name;
    @EntityField(name = "custom类型,工单中心分类所属人", type = ApiParamType.ENUM)
    private String owner;
    @EntityField(name = "判断当前工单分类是否属于当前用户", type = ApiParamType.BOOLEAN)
    private boolean isMine;
    @EntityField(name = "default:默认出厂  system：系统分类  custom：自定义分类", type = ApiParamType.ENUM)
    private String type = ProcessWorkcenterType.CUSTOM.getValue();
    @JSONField(serialize = false)
    @EntityField(name = "排序", type = ApiParamType.INTEGER)
    private Integer sort;
    @EntityField(name = "数量", type = ApiParamType.INTEGER)
    private String count;
    @EntityField(name = "过滤条件", type = ApiParamType.JSONOBJECT)
    private JSONObject conditionConfig;
    @JSONField(serialize = false)
    private String conditionConfigStr;
    @EntityField(name = "all:所有设备,mobile:手机端,pc:电脑端", type = ApiParamType.ENUM)
    private String support = DeviceType.ALL.getValue();
    @JSONField(serialize = false)
    @EntityField(name = "显示的字段", type = ApiParamType.JSONARRAY)
    private JSONArray headerList;
    @JSONField(serialize = false)
    private List<AuthorityVo> authorityList;
    @EntityField(name = "角色列表", type = ApiParamType.JSONARRAY)
    private List<String> authList;
    @EntityField(name = "是否拥有编辑权限", type = ApiParamType.INTEGER)
    private Integer isCanEdit;
    @EntityField(name = "是否拥有授权权限", type = ApiParamType.INTEGER)
    private Integer isCanRole;
    //@EntityField(name = "是否附加待我处理条件", type = ApiParamType.INTEGER)
    //private Integer isProcessingOfMine = 0;
    @EntityField(name = "待我处理的数量", type = ApiParamType.STRING)
    private String processingOfMineCount;
    @JSONField(serialize = false)
    @EntityField(name = "设备类型", type = ApiParamType.STRING)
    private String device;
    @EntityField(name = "排序的字段", type = ApiParamType.JSONOBJECT)
    private JSONObject sortConfig;
    /*@EntityField(name = "上报时间条件", type = ApiParamType.JSONOBJECT)
    private JSONObject startTimeCondition;*/
    @EntityField(name = "关键字搜索条件", type = ApiParamType.JSONOBJECT)
    private JSONArray keywordConditionList;
    @EntityField(name = "菜单类型id", type = ApiParamType.LONG)
    private Long catalogId;
    @EntityField(name = "菜单类型名称", type = ApiParamType.STRING)
    private String catalogName;
    @EntityField(name = "simple：简单模式  custom：高级模式", type = ApiParamType.STRING)
    private String handlerType;
    @EntityField(name = "是否显示总数，默认0：显示待办数", type = ApiParamType.INTEGER)
    private Integer isShowTotal = 0;

    //params
    private List<String> channelUuidList;
    private JSONArray resultColumnList;

    private List<WorkcenterTheadVo> theadVoList;

    private List<Long> processTaskIdList;

    //关键字全文检索字段名（工单号、工单标题、工单内容）
    private String keywordHandler;

    private String keywordPro;

    private String keywordColumn;

    private String keywordText;

    public WorkcenterVo() {
    }

    @Override
    public void init() {
        super.init(this.getConditionConfig());
    }

    public boolean getIsMine() {
        if (StringUtils.isNotBlank(this.owner)) {
            isMine = this.owner.equalsIgnoreCase(UserContext.get().getUserUuid(true));
        }
        return isMine;
    }


    public WorkcenterVo(String _name) {
        this.name = _name;
    }

    public WorkcenterVo(JSONObject jsonObj) {
        super(jsonObj.getJSONObject("conditionConfig"));
        this.conditionConfig = jsonObj.getJSONObject("conditionConfig");
        super.setKeyword(jsonObj.getString("keyword"));
        //this.isProcessingOfMine = this.conditionConfig.getInteger("isProcessingOfMine") != null ? this.conditionConfig.getInteger("isProcessingOfMine") : 0;
        this.sortConfig = jsonObj.getJSONObject("sortConfig");
        this.uuid = jsonObj.getString("uuid");
        this.setCurrentPage(jsonObj.getInteger("currentPage"));
        this.setExpectOffsetRowNum(jsonObj.getInteger("expectOffsetRowNum"));
        this.setPageSize(jsonObj.getInteger("pageSize"));
        this.resultColumnList = this.conditionConfig.getJSONArray("resultColumnList");
        /*JSONArray conditionGroupArray = conditionConfig.getJSONArray("conditionGroupList");
        if (CollectionUtils.isNotEmpty(conditionGroupArray)) {
            channelUuidList = new ArrayList<String>();
            for (Object conditionGroup : conditionGroupArray) {
                JSONObject conditionGroupJson = (JSONObject) JSONObject.toJSON(conditionGroup);
                JSONArray channelArray = conditionGroupJson.getJSONArray("channelUuidList");
                List<String> channelUuidListTmp = new ArrayList<String>();
                if (CollectionUtils.isNotEmpty(channelArray)) {
                    channelUuidListTmp = JSONObject.parseArray(channelArray.toJSONString(), String.class);
                }
                channelUuidList.addAll(channelUuidListTmp);
            }
        }*/
        //上报时间过滤条件
        /*if (conditionConfig.containsKey("startTimeCondition")) {
            startTimeCondition = conditionConfig.getJSONObject("startTimeCondition");
        } else {
            startTimeCondition = JSONObject.parseObject("{\"timeRange\":\"1\",\"timeUnit\":\"year\"}");//默认展示一年
        }*/

        this.keywordConditionList = conditionConfig.getJSONArray("keywordConditionList");
    }


    public String getUuid() {
        if (StringUtils.isBlank(uuid)) {
            uuid = UUID.randomUUID().toString().replace("-", "");
        }
        return uuid;
    }

    public void setUuid(String uuid) {
        if (StringUtils.isNotBlank(uuid)) {
            this.uuid = uuid;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AuthorityVo> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<AuthorityVo> authorityList) {
        this.authorityList = authorityList;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public JSONObject getConditionConfig() {
        if (conditionConfig == null && StringUtils.isNotBlank(conditionConfigStr)) {
            try {
                conditionConfig = JSONObject.parseObject(conditionConfigStr);
            } catch (Exception ignored) {

            }
        }
        return conditionConfig;
    }

    public void setConditionConfig(JSONObject conditionConfig) {
        this.conditionConfig = conditionConfig;
    }

    public String getSupport() {
        return support;
    }

    public void setSupport(String support) {
        this.support = support;
    }

    public JSONArray getHeaderList() {
        return headerList;
    }

    public void setHeaderList(JSONArray headerArray) {
        this.headerList = headerArray;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<String> getAuthList() {
        if (authList == null) {
            authList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(this.authorityList)) {
                for (AuthorityVo workcenterAuthorityVo : this.authorityList) {
                    if (workcenterAuthorityVo.getType().equals(GroupSearch.ROLE.getValue())) {
                        authList.add(GroupSearch.ROLE.getValuePlugin() + workcenterAuthorityVo.getUuid());
                    } else if (workcenterAuthorityVo.getType().equals(GroupSearch.USER.getValue())) {
                        authList.add(GroupSearch.USER.getValuePlugin() + workcenterAuthorityVo.getUuid());
                    }
                    if (workcenterAuthorityVo.getType().equals(GroupSearch.COMMON.getValue())) {
                        authList.add(GroupSearch.COMMON.getValuePlugin() + workcenterAuthorityVo.getUuid());
                    }
                }
            }
        }
        return authList;
    }

    public void setAuthList(List<String> authList) {
        this.authList = authList;
    }

    public Integer getIsCanEdit() {
        return isCanEdit;
    }

    public void setIsCanEdit(Integer isCanEdit) {
        this.isCanEdit = isCanEdit;
    }

    public Integer getIsCanRole() {
        return isCanRole;
    }

    public void setIsCanRole(Integer isCanRole) {
        this.isCanRole = isCanRole;
    }

    public List<String> getChannelUuidList() {
        if (CollectionUtils.isEmpty(channelUuidList) && this.getConditionConfig() != null) {
            JSONArray conditionGroupArray = this.getConditionConfig().getJSONArray("conditionGroupList");
            if (CollectionUtils.isNotEmpty(conditionGroupArray)) {
                channelUuidList = new ArrayList<>();
                for (Object conditionGroup : conditionGroupArray) {
                    JSONObject conditionGroupJson = (JSONObject) JSONObject.toJSON(conditionGroup);
                    JSONArray channelArray = conditionGroupJson.getJSONArray("channelUuidList");
                    List<String> channelUuidListTmp = new ArrayList<>();
                    if (CollectionUtils.isNotEmpty(channelArray)) {
                        channelUuidListTmp = JSONObject.parseArray(channelArray.toJSONString(), String.class);
                    }
                    channelUuidList.addAll(channelUuidListTmp);
                }
            }
        }
        return channelUuidList;
    }

    public void setChannelUuidList(List<String> channelUuidList) {
        this.channelUuidList = channelUuidList;
    }

    /*public Integer getIsProcessingOfMine() {
        return isProcessingOfMine;
    }*/

    /*public void setIsProcessingOfMine(Integer isProcessingOfMine) {
        this.isProcessingOfMine = isProcessingOfMine;
    }*/

    public String getProcessingOfMineCount() {
        return processingOfMineCount;
    }

    public void setProcessingOfMineCount(String processingOfMineCount) {
        this.processingOfMineCount = processingOfMineCount;
    }

    public JSONArray getResultColumnList() {
        return resultColumnList;
    }

    public void setResultColumnList(JSONArray resultColumnList) {
        this.resultColumnList = resultColumnList;
    }

    public String getDevice() {
        if (device == null) {
            device = CommonUtil.getDevice();
        }
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public JSONObject getSortConfig() {
        return sortConfig;
    }

    public void setSortConfig(JSONObject sortConfig) {
        this.sortConfig = sortConfig;
    }

    public List<Long> getProcessTaskIdList() {
        return processTaskIdList;
    }

    public void setProcessTaskIdList(List<Long> processTaskIdList) {
        this.processTaskIdList = processTaskIdList;
    }

    public List<WorkcenterTheadVo> getTheadVoList() {
        return theadVoList;
    }

    public void setTheadVoList(List<WorkcenterTheadVo> theadVoList) {
        this.theadVoList = theadVoList;
    }

    /*public JSONObject getStartTimeCondition() {
        //上报时间过滤条件
        if (this.getConditionConfig() != null && this.getConditionConfig().containsKey("startTimeCondition")) {
            startTimeCondition = this.getConditionConfig().getJSONObject("startTimeCondition");
        } else {
            startTimeCondition = JSONObject.parseObject("{\"timeRange\":\"1\",\"timeUnit\":\"year\"}");//默认展示一年
        }
        return startTimeCondition;
    }

    public void setStartTimeCondition(JSONObject startTimeCondition) {
        this.startTimeCondition = startTimeCondition;
    }*/

    public String getKeywordHandler() {
        return keywordHandler;
    }

    public void setKeywordHandler(String keywordHandler) {
        this.keywordHandler = keywordHandler;
    }

    public String getKeywordPro() {
        return keywordPro;
    }

    public void setKeywordPro(String keywordPro) {
        this.keywordPro = keywordPro;
    }

    public String getKeywordColumn() {
        return keywordColumn;
    }

    public void setKeywordColumn(String keywordColumn) {
        this.keywordColumn = keywordColumn;
    }

    public String getKeywordText() {
        return keywordText;
    }

    public void setKeywordText(String keywordText) {
        this.keywordText = keywordText;
    }

    public JSONArray getKeywordConditionList() {
        if (this.getConditionConfig() != null) {
            this.keywordConditionList = this.getConditionConfig().getJSONArray("keywordConditionList");
        }
        return keywordConditionList;
    }

    public void setKeywordConditionList(JSONArray keywordConditionList) {
        this.keywordConditionList = keywordConditionList;
    }

    public Long getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Long catalogId) {
        this.catalogId = catalogId;
    }

    public String getCatalogName() {
        return catalogName;
    }

    public void setCatalogName(String catalogName) {
        this.catalogName = catalogName;
    }

    public String getConditionConfigStr() {
        if (conditionConfig != null) {
            conditionConfigStr = conditionConfig.toJSONString();
        }
        return conditionConfigStr;
    }

    public void setConditionConfigStr(String conditionConfigStr) {
        this.conditionConfigStr = conditionConfigStr;
    }

    public String getHandlerType() {
        if (StringUtils.isBlank(handlerType) && MapUtils.isNotEmpty(this.getConditionConfig())) {
            //兼容老数据
            handlerType = this.getConditionConfig().getString("handlerType");
            /*if (StringUtils.isBlank(handlerType) && this.getConditionConfig().containsKey("conditionConfig")) {
                conditionConfigJson = conditionConfigJson.getJSONObject("conditionConfig");
                handlerType = conditionConfigJson.getString("handlerType");
            }*/
        }
        return handlerType;
    }

    public Integer getIsShowTotal() {
        return isShowTotal;
    }

    public void setIsShowTotal(Integer isShowTotal) {
        this.isShowTotal = isShowTotal;
    }

    @Override
    public void buildMyConditionWhereSql(StringBuilder sqlSb, String handler, List<ConditionVo> conditionVoList, int conditionIndex, String searchMode) {
        ConditionVo conditionVo = conditionVoList.get(conditionIndex);
        if (conditionVo.getType().equals(ProcessFieldType.FORM.getValue())) {
            handler = ProcessFieldType.FORM.getValue();
        }
        IProcessTaskCondition sqlCondition = ProcessTaskConditionFactory.getHandler(handler);
        sqlCondition.getSqlConditionWhere(conditionVoList, conditionIndex, sqlSb);
    }
}
