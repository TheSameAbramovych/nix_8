package com.nixsolutions;

import com.nixsolutions.annotations.Autowired;
import com.nixsolutions.factory.BeanFactory;
import com.nixsolutions.searcher.ApplicationSearcher;
import com.nixsolutions.store.BeanStore;

import java.lang.reflect.Field;
import java.util.Set;

public class ApplicationContext {

    private Set<Class<?>> serviceInterfaces;
    private ClassLoader appClassLoader;
    private String rootPackage;
    private ApplicationSearcher applicationSearcher;
    private BeanFactory beanFactory;

    public ApplicationContext(Class<?> rootClass) {
        this.appClassLoader = rootClass.getClassLoader();
        this.rootPackage = rootClass.getPackageName();
    }

    public void setApplicationSearcher(ApplicationSearcher applicationSearcher) {
        this.applicationSearcher = applicationSearcher;
    }

    public void initServiceInterfaces() {
        this.serviceInterfaces = applicationSearcher.findAllServiceInterfaces(this.appClassLoader, rootPackage);
    }

    public void initBeanMap() {
        this.serviceInterfaces.forEach(serviceInterface -> {
            Object impl = beanFactory.createBeanByInterface(serviceInterface);
            if (impl != null) {
                BeanStore.getInstance().getBeanMap().put(serviceInterface, impl);
            }
        });
    }

    public void configureBean() {
        BeanStore.getInstance().getBeanMap().forEach((ifc, impl) -> {
            Field[] fields = impl.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    field.setAccessible(true);
                    Object o = BeanStore.getInstance().getBeanMap().get(field.getType());
                    try {
                        field.set(impl, o);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
}
