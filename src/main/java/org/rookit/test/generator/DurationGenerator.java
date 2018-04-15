
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;

import java.time.Duration;
import java.util.Objects;

import javax.annotation.Generated;

class DurationGenerator extends AbstractGenerator<Duration> {

    private final Generator<Long> longGenerator;

    @Inject
    private DurationGenerator(final Generator<Long> longGenerator) {
        super();
        this.longGenerator = longGenerator;
    }

    @Override
    public Duration createRandom() {
        return Duration.ofMillis(this.longGenerator.createRandom().longValue() + 1);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof DurationGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final DurationGenerator that = (DurationGenerator) object;
            return Objects.equals(this.longGenerator, that.longGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.longGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("longGenerator", this.longGenerator)
                .toString();
    }

}
