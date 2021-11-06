package com.nixsolutions;

import com.nixsolutions.store.BeanStore;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ApplicationStarter {

    public void start() {
        BeanStore.getInstance().getBeanMap().forEach((k, v) -> {
            if (k.getName().endsWith("Controller")) {
                Method[] method = k.getMethods();
                for (Method method1 : method) {
                    if (method1.getName().equals("run")) {
                        try {
                            method1.invoke(v);
                        } catch (IllegalAccessException | InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}
