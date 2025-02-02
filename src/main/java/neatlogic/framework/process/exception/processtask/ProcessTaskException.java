/*Copyright (C) 2023  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.exception.processtask;

import neatlogic.framework.exception.core.ApiException;
import neatlogic.framework.util.$;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class ProcessTaskException extends ApiException {

	private static final long serialVersionUID = 4314481891500443152L;

	public ProcessTaskException() {
		super();
	}

	public ProcessTaskException(Exception e) {
		super(ExceptionUtils.getStackTrace(e));
	}

	public ProcessTaskException(String msg) {
		super(msg);
	}

	public ProcessTaskException(String key, Object... values) {
		super($.t(key, values));
	}
}
