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

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class Getter extends AbstractMethod implements Supplier<Object> {

    private static final String[] GETTER_TOKENS = {"get"};

    /**
     * Logger for Getter.
     */
    private static final Logger logger = VALIDATOR.getLogger(Getter.class);

    public static Optional<Getter> parse(final Object invoker, final Method method) {
        final String methodName = method.getName();
        final boolean isGetterByName = StringUtils.startsWithAny(methodName, GETTER_TOKENS);
        final int nParams = method.getParameterTypes().length;

        if (!isGetterByName) {
            return Optional.empty();
        }
        if (isGetterByName && nParams > 0) {
            logger.warn("Found possible getter with parameters: Method [{}] on Class [{}] "
                    + "with {} parameters.",
                    methodName, invoker.getClass().getName(), Integer.valueOf(nParams));
            return Optional.empty();
        }

        return Optional.of(new Getter(invoker, method));
    }

    private final Object invoker;

    private Getter(final Object invoker, final Method method) {
        super(method);
        this.invoker = invoker;
    }
    
    public Class<?> getReturnType() {
        return this.method.getReturnType();
    }

    @Override
    public Object get() {
        try {
            return this.method.invoke(this.invoker);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            throw new RuntimeException(e);
        } catch (final InvocationTargetException e) {
            throw new RuntimeException(e.getTargetException());
        }
    }

}
