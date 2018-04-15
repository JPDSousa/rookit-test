
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;

import java.util.Objects;
import java.util.Random;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class IntegerGenerator extends AbstractGenerator<Integer> {

    private final Random random;

    @Inject
    private IntegerGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public Integer createRandom() {
        return new Integer(this.random.nextInt());
    }

}
