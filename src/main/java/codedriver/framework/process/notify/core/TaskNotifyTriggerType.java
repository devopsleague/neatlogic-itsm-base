package codedriver.framework.process.notify.core;

import codedriver.framework.notify.core.INotifyTriggerType;

public enum TaskNotifyTriggerType implements INotifyTriggerType {

    STARTPROCESS("startprocess", "上报","用户上报提交工单时触发通知"),
	URGE("urge", "催办","工单完成前，用户对工单进行催办时触发通知"),
	ABORTPROCESSTASK("abortprocesstask", "取消工单","工单完成前，有权限用户取消工单时触发通知"),
	RECOVERPROCESSTASK("recoverprocesstask", "恢复工单","工单完成前，有权限用户恢复工单时触发通知"),
	COMPLETEPROCESSTASK("completeprocesstask", "完成工单","工单流转至结束时触发通知"),
	SCOREPROCESSTASK("scoreprocesstask", "评分","工单完成后，有权限用户进行评分时触发通知"),
	;

	private String trigger;
	private String text;
	private String description;

	private TaskNotifyTriggerType(String _trigger, String _text, String _description) {
		this.trigger = _trigger;
		this.text = _text;
		this.description = _description;
	}

	@Override
	public String getTrigger() {
		return trigger;
	}
	@Override
	public String getText() {
		return text;
	}
	@Override
	public String getDescription() {
		return description;
	}

	public static String getText(String trigger) {
		for(TaskNotifyTriggerType n : TaskNotifyTriggerType.values()) {
			if(n.getTrigger().equals(trigger)) {
				return n.getText();
			}
		}
		return "";
	}
}
