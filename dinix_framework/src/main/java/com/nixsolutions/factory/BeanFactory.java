package com.nixsolutions.factory;

import com.nixsolutions.annotations.Service;
import com.nixsolutions.searcher.ApplicationSearcher;

import java.util.Set;

public class BeanFactory {

    private final ApplicationSearcher applicationSearcher;

    public BeanFactory(ApplicationSearcher applicationSearcher) {
        this.applicationSearcher = applicationSearcher;
    }

    public <IFC> IFC createBeanByInterface(Class<IFC> ifc) {
        System.out.println("ifc = " + ifc);
        if (ifc.isInterface()) {
            Set<Class<? extends IFC>> impls = applicationSearcher.getScanner().getSubTypesOf(ifc);
            System.out.println("impls = " + impls);
            for (Class<? extends IFC> impl : impls) {
                if (impl.isAnnotationPresent(Service.class)) {
                    try {
                        return impl.getDeclaredConstructor().newInstance();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }
}
