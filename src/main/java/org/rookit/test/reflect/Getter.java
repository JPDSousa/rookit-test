
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
