/*******************************************************************************
 * Copyright (C) 2018 Joao Sousa
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 ******************************************************************************/
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
