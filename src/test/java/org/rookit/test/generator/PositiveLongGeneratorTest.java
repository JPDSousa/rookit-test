
package org.rookit.test.generator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.RepeatedTest;

@SuppressWarnings("javadoc")
public class PositiveLongGeneratorTest extends AbstractGeneratorTest<PositiveLongGenerator> {

    @RepeatedTest(CONFIDENCE_THRESHOLD)
    public final void testGreaterOrEqualThanZero() {
        final Long value = this.testResource.createRandom();

        assertThat(value)
                .as("The value generated")
                .isNotNegative();
    }

    @Override
    protected Class<PositiveLongGenerator> getTestClass() {
        return PositiveLongGenerator.class;
    }

}
