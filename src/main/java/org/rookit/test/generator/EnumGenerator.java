
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;

import java.util.Objects;
import java.util.Random;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class EnumGenerator<T extends Enum<T>> extends AbstractGenerator<T> {

    private final Class<T> clazz;
    private final Random random;

    public EnumGenerator(final Random random, final Class<T> clazz) {
        super();
        this.random = random;
        this.clazz = clazz;
    }

    @Override
    public T createRandom() {
        final T[] values = this.clazz.getEnumConstants();
        final int index = this.random.nextInt(values.length);

        return values[index];
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof EnumGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final EnumGenerator<?> that = (EnumGenerator<?>) object;
            return Objects.equals(this.clazz, that.clazz);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.clazz);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("clazz", this.clazz)
                .toString();
    }

}
