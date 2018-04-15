
package org.rookit.test.generator;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.RepeatedTest;

@SuppressWarnings("javadoc")
public class PastLocalDateGeneratorTest extends AbstractGeneratorTest<PastLocalDateGenerator> {

    @RepeatedTest(CONFIDENCE_THRESHOLD)
    public final void testCreatedDateIsInPast() {
        final LocalDate date = this.testResource.createRandom();

        assertThat(date)
                .as("The generated date")
                .isBefore(LocalDate.now());
    }

    @Override
    protected Class<PastLocalDateGenerator> getTestClass() {
        return PastLocalDateGenerator.class;
    }

}
