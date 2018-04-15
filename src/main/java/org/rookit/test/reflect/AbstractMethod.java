
package org.rookit.test.reflect;

import org.rookit.test.TestValidator;

abstract class AbstractMethod implements Method {

    protected static final TestValidator VALIDATOR = TestValidator.getSingleton();
    
    protected final java.lang.reflect.Method method;

    protected AbstractMethod(final java.lang.reflect.Method method) {
        this.method = method;
    }

    @Override
    public String getName() {
        return this.method.getName();
    }

    @Override
    public boolean isAccessible() {
        return this.method.isAccessible();
    }

}
