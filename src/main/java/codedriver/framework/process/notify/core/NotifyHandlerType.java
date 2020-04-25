package codedriver.framework.process.notify.core;

public enum NotifyHandlerType {

	EMAIL("email","邮件通知"),
	REMIND("remind","消息通知");
	
	private String value;
	private String text;
	
	private NotifyHandlerType(String value, String text) {
		this.value = value;
		this.text = text;
	}
	
	public String getValue() {
		return value;
	}
	public String getText() {
		return text;
	}
}
