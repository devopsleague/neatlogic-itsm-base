/*
 * Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neatlogic.framework.process.steptaskhandler.core;

import neatlogic.framework.applicationlistener.core.ModuleInitializedListenerBase;
import neatlogic.framework.bootstrap.NeatLogicWebApplicationContext;
import neatlogic.framework.common.RootComponent;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RootComponent
@Order(10)
public class ProcessStepTaskHandlerFactory extends ModuleInitializedListenerBase {
    private static final Map<String, IProcessStepTaskHandler> componentMap = new HashMap<>();

    public static IProcessStepTaskHandler getHandler(String handler) {
        return componentMap.get(handler);
    }

    @Override
    protected void onInitialized(NeatLogicWebApplicationContext context) {
        Map<String, IProcessStepTaskHandler> myMap = context.getBeansOfType(IProcessStepTaskHandler.class);
        for (Map.Entry<String, IProcessStepTaskHandler> entry : myMap.entrySet()) {
            IProcessStepTaskHandler component = entry.getValue();
            List<String> handlerList = component.getHandlerList();
            if (CollectionUtils.isNotEmpty(handlerList)) {
                for (String handler : handlerList) {
                    componentMap.put(handler, component);
                }
            }
        }
    }

    @Override
    protected void myInit() {

    }
}
