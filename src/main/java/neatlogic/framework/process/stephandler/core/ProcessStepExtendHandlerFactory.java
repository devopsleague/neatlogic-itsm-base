/*Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.

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

package neatlogic.framework.process.stephandler.core;

import neatlogic.framework.applicationlistener.core.ModuleInitializedListenerBase;
import neatlogic.framework.bootstrap.NeatLogicWebApplicationContext;
import neatlogic.framework.common.RootComponent;
import org.springframework.core.annotation.Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RootComponent
@Order(11)
public class ProcessStepExtendHandlerFactory extends ModuleInitializedListenerBase {
    private static final Map<String, List<IProcessStepExtendHandler>> componentMap = new HashMap<>();

    public static List<IProcessStepExtendHandler> getHandlers(String handler) {
        return componentMap.get(handler);
    }


    @Override
    public void onInitialized(NeatLogicWebApplicationContext context) {
        Map<String, IProcessStepExtendHandler> myMap = context.getBeansOfType(IProcessStepExtendHandler.class);
        for (Map.Entry<String, IProcessStepExtendHandler> entry : myMap.entrySet()) {
            IProcessStepExtendHandler component = entry.getValue();
            if (component.getHandler() != null) {
                if (!componentMap.containsKey(component.getHandler())) {
                    componentMap.put(component.getHandler(), new ArrayList<>());
                }
                componentMap.get(component.getHandler()).add(component);
            }
        }
    }

    @Override
    protected void myInit() {

    }
}
