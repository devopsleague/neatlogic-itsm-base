/*
 * Copyright(c) 2023 NeatLogic Co., Ltd. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package neatlogic.framework.process.workcenter.table;

import neatlogic.framework.applicationlistener.core.ModuleInitializedListenerBase;
import neatlogic.framework.bootstrap.NeatLogicWebApplicationContext;
import neatlogic.framework.common.RootComponent;

import java.util.HashMap;
import java.util.Map;

@RootComponent
public class ProcessTaskSqlTableFactory extends ModuleInitializedListenerBase {

	public static Map<String, ISqlTable> tableComponentMap = new HashMap<>();

	public static ISqlTable getHandler(String name) {
		return tableComponentMap.get(name);
	}

	@Override
	public void onInitialized(NeatLogicWebApplicationContext context) {
		Map<String, ISqlTable> myMap = context.getBeansOfType(ISqlTable.class);
		for (Map.Entry<String, ISqlTable> entry : myMap.entrySet()) {
			ISqlTable table = entry.getValue();
			tableComponentMap.put(table.getName(), table);
		}
	}

	@Override
	protected void myInit() {
		// TODO Auto-generated method stub
		
	}

}
