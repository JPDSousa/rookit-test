package org.rookit.test.mixin;

import java.util.Random;

@SuppressWarnings("javadoc")
public interface ByteArrayMixin {
    
    default byte[] createRandomArray(final int capacity) {
        final byte[] array = new byte[capacity];
        final Random random = new Random();
        random.nextBytes(array);
        return array;
    }

}
