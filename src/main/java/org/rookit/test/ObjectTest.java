
package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.rookit.test.preconditions.TestPreconditions.assure;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public interface ObjectTest<T> extends RookitTest<T> {

    @Test
    default void testEquals() {
        final T testResource = getTestResource();

        assertThat(testResource)
                .as("The self object")
                .isEqualTo(createTestResource())
                .isNotEqualTo(new Object())
                .isNotEqualTo(null)
                .isEqualTo(testResource);
    }

    @Test
    default void testHashCode() {
        final T testResource1 = getTestResource();
        final T testResrouce2 = createTestResource();
        assure().isEquals(testResource1, testResrouce2, "test resources");

        assertThat(testResource1.hashCode())
                .as("The self hash code")
                .isEqualTo(testResource1.hashCode())
                .isEqualTo(testResrouce2.hashCode());
    }

    @Test
    default void testToString() {
        final T testResource = getTestResource();

        assertThat(testResource.toString())
                .as("The string version of the test resource")
                .isNotEmpty();
    }

}
