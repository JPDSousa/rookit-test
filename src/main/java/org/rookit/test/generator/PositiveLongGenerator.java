
package org.rookit.test.generator;

import com.google.inject.Inject;

import java.util.Random;

class PositiveLongGenerator extends AbstractGenerator<Long> {

    private final Random random;

    @Inject
    private PositiveLongGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Long createRandom() {
        return new Long(Math.abs(this.random.nextLong()));
    }

}
