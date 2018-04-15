
package org.rookit.test.generator;

import com.google.inject.Inject;

import java.util.Random;

class ShortGenerator extends AbstractGenerator<Short> {

    private final Random random;

    @Inject
    private ShortGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Short createRandom() {
        return new Short((short) this.random.nextInt(Short.MAX_VALUE));
    }

}
