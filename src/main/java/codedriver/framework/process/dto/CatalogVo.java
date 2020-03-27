package codedriver.framework.process.dto;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;

import codedriver.framework.apiparam.core.ApiParamType;
import codedriver.framework.common.constvalue.GroupSearch;
import codedriver.framework.common.dto.BasePageVo;
import codedriver.framework.restful.annotation.EntityField;

public class CatalogVo extends BasePageVo implements ITree{
	
	@EntityField(name = "服务目录uuid", type = ApiParamType.STRING)
	private String uuid;
	
	@EntityField(name = "服务目录名称", type = ApiParamType.STRING)
	private String name;
	
	@EntityField(name = "服务目录父级uuid", type = ApiParamType.STRING)
	private String parentUuid;
	
	@EntityField(name = "是否启用，0：禁用，1：启用", type = ApiParamType.INTEGER)
	private Integer isActive;
	
	@EntityField(name = "图标", type = ApiParamType.STRING)
	private String icon;
	
	@EntityField(name = "颜色", type = ApiParamType.STRING)
	private String color;
	
	@EntityField(name = "描述", type = ApiParamType.STRING)
	private String desc;
	
	@EntityField(name = "是否打开，false：未选中，true：已选中", type = ApiParamType.BOOLEAN)
	private boolean open = false;
	
	@EntityField(name = "是否已选中，false：未选中，true：已选中", type = ApiParamType.BOOLEAN)
	private boolean selected = false;
	
	@EntityField(name = "子目录或通道", type = ApiParamType.JSONARRAY)
	private List<ITree> children;
	
	@EntityField(name = "类型", type = ApiParamType.STRING)
	private String type = "catalog";
	
	@EntityField(name = "授权对象", type = ApiParamType.JSONARRAY)
	private List<String> workerList;
	
	private transient List<AuthorityVo> authorityList;
	
	private transient ITree parent;
	
	private transient Integer sort;
	@EntityField(name = "子节点数", type = ApiParamType.INTEGER)
	private int childrenCount = 0;
	
	private transient List<Integer> sortList;
	
	private transient List<String> nameList;
	
	public CatalogVo() {
	}
	
	public CatalogVo(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String getUuid() {
		if(uuid == null) {
			uuid = UUID.randomUUID().toString().replace("-", "");
		}
		return uuid;
	}
	@Override
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getParentUuid() {
		return parentUuid;
	}
	@Override
	public void setParentUuid(String parentUuid) {
		this.parentUuid = parentUuid;
	}
	
	public Integer getIsActive() {
		return isActive;
	}
	
	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}
	@Override
	public boolean isSelected() {
		return selected;
	}
	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	@Override
	public List<ITree> getChildren() {
		return children;
	}
	@Override
	public void setChildren(List<ITree> children) {
		this.children = children;
	}
	@Override
	public boolean addChild(ITree child) {
		if(children == null) {
			children = new ArrayList<>();
		}
		if(children.add(child)) {
			childrenCount++;
			return true;
		}else {
			return false;
		}		
	}
	@Override
	public boolean removeChild(ITree child) {
		if(children == null || children.isEmpty()) {
			return false;
		}
		Iterator<ITree> iterator = children.iterator();
		while(iterator.hasNext()) {
			ITree iTree = iterator.next();
			if(iTree.getUuid().equals(child.getUuid())) {
				iterator.remove();
				childrenCount--;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public ITree getParent() {
		return parent;
	}
	@Override
	public void setParent(ITree parent) {
		this.parent = parent;
		parent.addChild(this);
	}
	
	@Override
	public void setOpenCascade(boolean open) {
		this.open = open;
		if(parent != null) {
			parent.setOpenCascade(open);
		}
	}
	@Override
	public void setSelectedCascade(boolean selected) {
		this.selected = selected;
		if(parent != null) {
			parent.setSelectedCascade(selected);
		}
	}

	@Override
	public Integer getSort() {
		return sort;
	}

	@Override
	public void setSort(Integer sort) {
		this.sort = sort;
	}

	@Override
	public String getType() {
		return type;
	}

	public List<String> getWorkerList() {
		if(workerList == null && CollectionUtils.isNotEmpty(authorityList)) {
			workerList = new ArrayList<>();
			for(AuthorityVo authorityVo : authorityList) {
				if(authorityVo.getUserId() != null) {
					workerList.add(GroupSearch.USER.getValuePlugin() + authorityVo.getUserId());
				}
				if(authorityVo.getTeamUuid() != null) {
					workerList.add(GroupSearch.TEAM.getValuePlugin() + authorityVo.getTeamUuid());
				}
				if(authorityVo.getRoleName() != null) {
					workerList.add(GroupSearch.ROLE.getValuePlugin() + authorityVo.getRoleName());
				}
			}
		}
		return workerList;
	}

	public void setWorkerList(List<String> workerList) {
		this.workerList = workerList;
	}

	public List<AuthorityVo> getAuthorityList() {
		if(authorityList == null && CollectionUtils.isNotEmpty(workerList)) {
			authorityList = new ArrayList<>();
			for(String worker : workerList) {
				AuthorityVo authorityVo = new AuthorityVo();
				authorityVo.setCatalogUuid(uuid);
				String[] split = worker.split("#");
				if(GroupSearch.USER.getValue().equals(split[0])) {
					authorityVo.setUserId(split[1]);
				}else if(GroupSearch.TEAM.getValue().equals(split[0])) {
					authorityVo.setTeamUuid(split[1]);
				}else if(GroupSearch.ROLE.getValue().equals(split[0])) {
					authorityVo.setRoleName(split[1]);
				}else {
					continue;
				}
				authorityList.add(authorityVo);
			}
		}
		return authorityList;
	}
	public void setAuthorityList(List<AuthorityVo> authorityList) {
		this.authorityList = authorityList;
	}

	@Override
	public int getChildrenCount() {
		return childrenCount;
	}
	@Override
	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}
	@Override
	public List<Integer> getSortList() {
		if(sortList != null) {
			return sortList;
		}
		if(parent != null) {
			sortList = new ArrayList<>(parent.getSortList());			
		}else {
			sortList = new ArrayList<>();
		}		
		sortList.add(sort);
		return sortList;
	}
	@Override
	public void setSortList(List<Integer> sortList) {
		this.sortList = sortList;
	}

	@Override
	public String toString() {
		return "CatalogVo [uuid=" + uuid + ", name=" + name + ", parentUuid=" + parentUuid + ", sort=" + sort + ", sortList=" + sortList + "]";
	}

	@Override
	public List<String> getNameList() {
		if(nameList != null) {
			return nameList;
		}
		if(parent != null && !ITree.ROOT_UUID.equals(parent.getUuid())) {
			nameList = new ArrayList<>(parent.getNameList());
		}else {
			nameList = new ArrayList<>();
		}
		nameList.add(name);
		return nameList;
	}

	@Override
	public void setNameList(List<String> nameList) {
		this.nameList = nameList;		
	}

	@Override
	public boolean isAncestorOrSelf(String uuid) {
		if(this.uuid.equals(uuid)) {
			return true;
		}
		if(parent == null) {
			return false;
		}	
		return parent.isAncestorOrSelf(uuid);
	}

	

}
