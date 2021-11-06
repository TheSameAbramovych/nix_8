package com.nixsolutions.store;

import java.util.HashMap;
import java.util.Map;

public final class BeanStore {

    private static BeanStore beanStore;
    private final Map<Class<?>, Object> beanMap = new HashMap<>();

    private BeanStore() {
    }

    public static BeanStore getInstance() {
        if (beanStore == null) {
            beanStore = new BeanStore();
        }
        return beanStore;
    }

    public Map<Class<?>, Object> getBeanMap() {
        return beanMap;
    }
}
