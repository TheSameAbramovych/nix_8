package com.nixsolutions.searcher;

import org.reflections.Reflections;
import org.reflections.Store;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ApplicationSearcher {

    private final Reflections scanner;

    public ApplicationSearcher(String rootPackage) {
        this.scanner = new Reflections(rootPackage);
    }

    public Reflections getScanner() {
        return scanner;
    }

    public Set<Class<?>> findAllServiceInterfaces(ClassLoader appClassLoader, String rootPackage) {
        Store store = scanner.getStore();
        Collection<Map<String, Set<String>>> values = store.values();
        Set<String> allClassesName = new HashSet<>();
        values.forEach((v) -> {
            Set<String> packageClasses = v.keySet();
            allClassesName.addAll(packageClasses);
        });
        Set<Class<?>> collect = allClassesName
                .stream()
                .filter(this::isServiceInterface)
                .filter(s -> isStartWithRootPackage(s, rootPackage))
                .map(className -> scanner.forClass(className, appClassLoader))
                .filter(this::hasOnlyCchildClass)
                .collect(Collectors.toSet());

        return collect;
    }

    private boolean isServiceInterface(String className) {
        return className.endsWith("Dao") || className.endsWith("Service") || className.endsWith("Controller");
    }

    private boolean isStartWithRootPackage(String className, String rootPackage) {
        return className.startsWith(rootPackage);
    }

    private boolean hasOnlyCchildClass(Class<?> interfaceClass) {
        Set<Class<?>> classes = scanner.getSubTypesOf((Class<Object>) interfaceClass);
        return classes.stream().noneMatch(Class::isInterface);
    }
}
