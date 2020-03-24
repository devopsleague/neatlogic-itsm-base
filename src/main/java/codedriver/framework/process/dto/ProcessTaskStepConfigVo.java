package codedriver.framework.process.dto;

public class ProcessTaskStepConfigVo {

	private String hash;
	private String config;

	public ProcessTaskStepConfigVo() {

	}

	public ProcessTaskStepConfigVo(String _hash, String _config) {
		hash = _hash;
		config = _config;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

}
