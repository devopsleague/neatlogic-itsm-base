package codedriver.framework.process.dto;

import com.alibaba.fastjson.JSON;

public class AttributeDataVo {
	private String attributeUuid;
	private String data;

	public String getAttributeUuid() {
		return attributeUuid;
	}

	public void setAttributeUuid(String attributeUuid) {
		this.attributeUuid = attributeUuid;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Object getDataObj() {
		if(data == null) {
			return null;
		}
		if(data.startsWith("[") && data.endsWith("]")) {
			return JSON.parseArray(data);
		}else if(data.startsWith("{") && data.endsWith("}")){
			return JSON.parseObject(data);
		}else {
			return data;
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attributeUuid == null) ? 0 : attributeUuid.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AttributeDataVo other = (AttributeDataVo) obj;
		if (attributeUuid == null) {
			if (other.attributeUuid != null)
				return false;
		} else if (!attributeUuid.equals(other.attributeUuid))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}
}
