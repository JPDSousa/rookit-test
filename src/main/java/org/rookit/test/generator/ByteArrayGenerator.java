
package org.rookit.test.generator;

import com.google.inject.Inject;

import java.util.Random;

class ByteArrayGenerator extends AbstractGenerator<byte[]> {

    private static final short MAX_BYTES = 128;

    private final Random random;

    @Inject
    private ByteArrayGenerator(final Random random) {
        super();
        this.random = random;
    }

    @Override
    public byte[] createRandom() {
        final byte[] bytes = new byte[MAX_BYTES];
        this.random.nextBytes(bytes);
        return bytes;
    }

}
