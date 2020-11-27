package codedriver.framework.process.formattribute.core;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import codedriver.framework.common.RootComponent;

@RootComponent
public class FormAttributeHandlerFactory implements ApplicationListener<ContextRefreshedEvent> {
    private static Map<String, IFormAttributeHandler> handlerMap = new HashMap<>();
    private static List<IFormAttributeHandler> handlerList = new ArrayList<>();

    public static IFormAttributeHandler getHandler(String type) {
        return handlerMap.get(type);
    }

    public static List<IFormAttributeHandler> getHandlerList() {
        handlerList.sort(new Comparator<IFormAttributeHandler>() {
            @Override
            public int compare(IFormAttributeHandler o1, IFormAttributeHandler o2) {
                return o1.getSort() - o2.getSort();
            }
        });
        return handlerList;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        ApplicationContext context = event.getApplicationContext();
        Map<String, IFormAttributeHandler> myMap = context.getBeansOfType(IFormAttributeHandler.class);
        for (Map.Entry<String, IFormAttributeHandler> entry : myMap.entrySet()) {
            IFormAttributeHandler handler = entry.getValue();
            if (handler.getHandler() != null) {
                if (handlerMap.containsKey(handler.getHandler())) {
                    System.err.print("表单插件：" + handler.getHandler() + "已存在，请检查代码");
                } else {
                    handlerMap.put(handler.getHandler(), handler);
                    handlerList.add(handler);
                }
            }
        }
    }
}
