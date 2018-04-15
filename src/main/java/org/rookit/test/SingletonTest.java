
package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public interface SingletonTest<T> extends RookitTest<T> {

    @Test
    default void testSingleton() {
        assertThat(createTestResource())
                .as("A singleton creation")
                .isSameAs(createTestResource());
    }

}
