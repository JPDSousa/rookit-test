
package org.rookit.test.generator;

import com.devskiller.jfairy.Fairy;
import com.google.inject.Inject;

class StringGenerator extends AbstractGenerator<String> {

    private static final short MAX_WORDS = 10;

    private final Fairy fairy;

    @Inject
    private StringGenerator() {
        this.fairy = Fairy.create();
    }

    @Override
    public String createRandom() {
        return this.fairy.textProducer().sentence(MAX_WORDS);
    }

}
