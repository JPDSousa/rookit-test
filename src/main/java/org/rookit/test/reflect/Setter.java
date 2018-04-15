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

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.Logger;
import org.rookit.utils.MethodUtils;

@SuppressWarnings("javadoc")
public class Setter extends AbstractMethod implements Consumer<Object[]> {

    /**
     * Logger for Setter.
     */
    private static final Logger logger = VALIDATOR.getLogger(Setter.class);

    private static final String[] SETTER_NAMES = {"set", "with", "add", "remove"};

    public static Optional<Setter> parse(final Object invoker, final Method method) {
        final String methodName = method.getName();
        final boolean isSetterByName = StringUtils.startsWithAny(methodName, SETTER_NAMES);
        final int nParams = method.getParameterTypes().length;

        if (!isSetterByName) {
            return Optional.empty();
        }
        if (isSetterByName && nParams != 1) {
            logger.warn("Found possible setter with an invalid number of parameters:"
                    + " Method [{}] on Class [{}] with {} parameters.",
                    methodName, invoker.getClass().getName(), Integer.valueOf(nParams));
            return Optional.empty();
        }
        if (isSetterByName && nParams == 1 && method.getParameterTypes()[0].isPrimitive()) {
            logger.info("Found setter with primitive type params: not applicable for the test");
            return Optional.empty();
        }

        return Optional.of(new Setter(invoker, method));
    }

    private final Object invoker;

    private Setter(final Object invoker, final Method method) {
        super(method);
        this.invoker = invoker;
    }

    @Override
    public void accept(final Object[] args) {
        try {
            MethodUtils.forceInvokeMethod(this.invoker, this.method, args);
        } catch (final Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
