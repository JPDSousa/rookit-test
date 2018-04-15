package org.rookit.test.generator;

import com.google.inject.Guice;
import com.google.inject.Injector;

import org.rookit.test.AbstractTest;
import org.rookit.test.SingletonTest;

abstract class AbstractGeneratorTest<T extends Generator<?>> extends AbstractTest<T> implements SingletonTest<T> {

    protected static final short CONFIDENCE_THRESHOLD = 10;
    private static final Injector INJECTOR = Guice.createInjector(new BaseGeneratorModule());
    
    protected abstract Class<T> getTestClass();
    
    @Override
    public T createTestResource() {
        return INJECTOR.getInstance(getTestClass());
    }

}
