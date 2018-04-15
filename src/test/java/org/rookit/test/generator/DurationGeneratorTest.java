
package org.rookit.test.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.RepeatedTest;

@SuppressWarnings("javadoc")
public class DurationGeneratorTest extends AbstractGeneratorTest<DurationGenerator> {

    @RepeatedTest(CONFIDENCE_THRESHOLD)
    public final void testGreaterThanZero() {
        final Duration duration = this.testResource.createRandom();

        assertThat(duration)
                .as("The generated duration")
                .isGreaterThan(Duration.ZERO);
    }

    @Override
    protected Class<DurationGenerator> getTestClass() {
        return DurationGenerator.class;
    }

}
