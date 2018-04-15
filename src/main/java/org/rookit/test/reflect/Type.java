package org.rookit.test.reflect;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

@SuppressWarnings("javadoc")
public class Type {
    
    private final Collection<Getter> getters;
    private final Collection<Setter> setters;
    
    public Type(final Object type) {
        final Class<?> clazz = type.getClass();
        
        this.getters = Arrays.stream(clazz.getDeclaredMethods())
                .map(method -> Getter.parse(type, method))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        this.setters = Arrays.stream(clazz.getDeclaredMethods())
                .map(method -> Setter.parse(type, method))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    public Collection<Getter> getGetters() {
        return Collections.unmodifiableCollection(this.getters);
    }

    public Collection<Setter> getSetters() {
        return Collections.unmodifiableCollection(this.setters);
    }
    
}
