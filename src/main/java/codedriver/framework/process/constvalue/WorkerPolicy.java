package codedriver.framework.process.constvalue;

public enum WorkerPolicy {
	ASSIGN("assign", "自定义"), 
	//MANUAL("manual", "处理人抢单"), 
	AUTOMATIC("automatic", "分派器"),
	//FROMER("fromer", "前置步骤指定"), 
	COPY("copy", "复制前置步骤处理人"),
	//ATTRIBUTE("attribute", "属性值"), 
	FORM("form", "表单值"),
	PRESTEPASSIGN("prestepassign","由前置步骤处理人指定");
	private String policy;
	private String text;

	private WorkerPolicy(String _policy, String _text) {
		this.policy = _policy;
		this.text = _text;
	}

	public String getValue() {
		return policy;
	}

	public String getText() {
		return text;
	}

	public static String getValue(String _policy) {
		for (WorkerPolicy s : WorkerPolicy.values()) {
			if (s.getValue().equals(_policy)) {
				return s.getValue();
			}
		}
		return null;
	}

	public static String getText(String _policy) {
		for (WorkerPolicy s : WorkerPolicy.values()) {
			if (s.getValue().equals(_policy)) {
				return s.getText();
			}
		}
		return "";
	}

	public static WorkerPolicy getWorkerPolicy(String _policy){
		for (WorkerPolicy s : WorkerPolicy.values()) {
			if (s.getValue().equals(_policy)) {
				return s;
			}
		}
		return null;
	}
}
