/*
 * Copyright(c) 2021 TechSureCo.,Ltd.AllRightsReserved.
 * 本内容仅限于深圳市赞悦科技有限公司内部传阅，禁止外泄以及用于其他的商业项目。
 */

package neatlogic.framework.process.stephandler.core;

import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * 流程组件类型工厂
 *
 * @author: linbq
 * @since: 2021/4/5 15:13
 **/
public class ProcessStepHandlerTypeFactory {
    /**
     * 标记是否未初始化数据，只初始化一次
     **/
    private static volatile boolean isUninitialized = true;

    private static Set<IProcessStepHandlerType> set = new HashSet<>();

    /**
     * 获取IProcessStepHandlerType接口所有实现枚举类集合
     *
     * @return
     */
    public static Set<IProcessStepHandlerType> getProcessStepHandlerTypeSet() {
        if (isUninitialized) {
            synchronized (ProcessStepHandlerTypeFactory.class) {
                if (isUninitialized) {
                    Reflections reflections = new Reflections("neatlogic");
                    Set<Class<? extends IProcessStepHandlerType>> classSet = reflections.getSubTypesOf(IProcessStepHandlerType.class);
                    for (Class<? extends IProcessStepHandlerType> c : classSet) {
                        for (IProcessStepHandlerType obj : c.getEnumConstants()) {
                            set.add(obj);
                        }
                    }
                    isUninitialized = false;
                }
            }
        }
        return set;
    }

    /**
     * 通过_handler值查询对应的name
     *
     * @param _handler
     * @return
     */
    public static String getName(String _handler) {
        for (IProcessStepHandlerType s : getProcessStepHandlerTypeSet()) {
            if (s.getHandler().equals(_handler)) {
                return s.getName();
            }
        }
        return "";
    }

    /**
     * 通过_handler值查询对应的type
     *
     * @param _handler
     * @return
     */
    public static String getType(String _handler) {
        for (IProcessStepHandlerType s : getProcessStepHandlerTypeSet()) {
            if (s.getHandler().equals(_handler)) {
                return s.getType();
            }
        }
        return "";
    }
}