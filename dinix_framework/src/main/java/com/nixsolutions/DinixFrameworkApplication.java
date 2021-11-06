package com.nixsolutions;

import com.nixsolutions.factory.BeanFactory;
import com.nixsolutions.searcher.ApplicationSearcher;

public class DinixFrameworkApplication {

    public static void start(Class<?> rootClass) {
        ApplicationSearcher applicationSearcher = new ApplicationSearcher(rootClass.getPackageName());
        ApplicationContext context = new ApplicationContext(rootClass);
        context.setApplicationSearcher(applicationSearcher);
        context.initServiceInterfaces();
        BeanFactory beanFactory = new BeanFactory(applicationSearcher);
        context.setBeanFactory(beanFactory);
        context.initBeanMap();
        context.configureBean();
        ApplicationStarter applicationStarter = new ApplicationStarter();
        applicationStarter.start();
    }
}
