
package org.rookit.test.generator;

import com.google.inject.Inject;

import java.util.Random;

class DoubleGenerator extends AbstractGenerator<Double> {

    private final Random random;

    @Inject
    private DoubleGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Double createRandom() {
        return new Double(this.random.nextDouble());
    }

}
