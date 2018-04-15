
package org.rookit.test.generator;

import com.google.inject.Inject;

import java.util.Random;

class BooleanGenerator extends AbstractGenerator<Boolean> {

    private final Random random;

    @Inject
    private BooleanGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Boolean createRandom() {
        return new Boolean(this.random.nextBoolean());
    }

}
