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
package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;

import io.github.glytching.junit.extension.folder.TemporaryFolder;
import io.github.glytching.junit.extension.folder.TemporaryFolderExtension;

import java.util.Collection;
import java.util.function.Supplier;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.rookit.test.reflect.Getter;
import org.rookit.test.reflect.Setter;
import org.rookit.test.reflect.Type;

@SuppressWarnings("javadoc")
@ExtendWith(TemporaryFolderExtension.class)
public abstract class AbstractTest<T> implements ObjectTest<T>, ArchTest {

    protected static final TestValidator VALIDATOR = TestValidator.getSingleton();

    public static <E> void testCollectionOps1(final CollectionOps<E> collectionOps, final Supplier<E> itemSupplier) {
        CollectionOpsTester.test(collectionOps, itemSupplier);
    }

    protected TemporaryFolder temporaryFolder;

    protected T testResource;
    private Type type;
    private JavaClasses javaClass;

    @Override
    public JavaClasses getJavaClasses() {
        return this.javaClass;
    }

    @Override
    public T getTestResource() {
        return this.testResource;
    }

    @BeforeEach
    public final void setupGuineaPig() {
        this.testResource = createTestResource();
        this.type = new Type(this.testResource);
        this.javaClass = new ClassFileImporter().importClasses(this.testResource.getClass());
    }

    @Test
    public final void testCreatorDoesNotReturnNull() {
        assertThat(createTestResource())
                .as("Creating a non null test object")
                .isNotNull();
    }

    @Test
    public final void testGetters() {
        final SoftAssertions softAssertions = new SoftAssertions();
        for (final Getter getter : this.type.getGetters()) {
            testGetter(getter, softAssertions);
        }
        softAssertions.assertAll();
    }

    @Test
    public final void testSetter() {
        final SoftAssertions softAssertions = new SoftAssertions();
        for (final Setter setter : this.type.getSetters()) {
            testSetter(setter, softAssertions);
        }
        softAssertions.assertAll();
    }

    private void testGetter(final Getter getter, final SoftAssertions softAssertions) {
        final String className = this.testResource.getClass().getName();
        final String methodName = getter.getName();

        final Class<?> returnType = getter.getReturnType();

        softAssertions.assertThat(returnType)
                .as("The method %s in class %s with return type %s", methodName, className, returnType)
                .isNotEqualTo(Void.class)
                .isNotEqualTo(void.class);

        final Object returnValue = getter.get();
        softAssertions.assertThat(returnValue)
                .as("Invoking getter method %s of class %s does not return null",
                        methodName, className)
                .isNotNull();

        if (Collection.class.isAssignableFrom(returnType)) {
            final Collection<?> returnedCollection = (Collection<?>) returnValue;
            softAssertions.assertThatThrownBy(() -> returnedCollection.add(null))
                    .as("The returned collection must be immutable")
                    .isInstanceOf(UnsupportedOperationException.class);
        }
    }

    private void testSetter(final Setter setter, final SoftAssertions softAssertions) {
        final String className = this.testResource.getClass().getName();
        softAssertions.assertThatThrownBy(() -> setter.accept(new Object[]{null}))
                .as("Setting a value with null on method %s of class %s", setter.getName(), className)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("cannot be null");
    }

}
