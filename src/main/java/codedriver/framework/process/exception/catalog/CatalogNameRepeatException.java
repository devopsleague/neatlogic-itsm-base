package codedriver.framework.process.exception.catalog;

import codedriver.framework.exception.core.ApiFieldValidRuntimeException;

public class CatalogNameRepeatException extends ApiFieldValidRuntimeException {

	private static final long serialVersionUID = -4617724920030245142L;

	public CatalogNameRepeatException(String msg) {
		super("服务目录名称：'" + msg + "'已存在");
	}
}
