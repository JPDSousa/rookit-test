
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;
import com.google.inject.Inject;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.Objects;
import java.util.Random;

import javax.annotation.Generated;

@SuppressWarnings("javadoc")
public class PastLocalDateGenerator extends AbstractGenerator<LocalDate> {

    private static final int YEAR_MIN = 1700;
    private static final int YEAR_MAX = Year.now().getValue();

    private final Random random;
    private final Generator<Month> monthGenerator;

    @Inject
    private PastLocalDateGenerator(final Random random, final Generator<Month> monthGenerator) {
        super();
        this.random = random;
        this.monthGenerator = monthGenerator;
    }

    @Override
    public LocalDate createRandom() {
        final int year = YEAR_MIN + this.random.nextInt(YEAR_MAX - YEAR_MIN);
        final Month month = this.monthGenerator.createRandom();
        final int dayOfMonth = 1 + this.random.nextInt(month.length(false) - 1);
        return LocalDate.of(year, month, dayOfMonth);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        if (object instanceof PastLocalDateGenerator) {
            if (!super.equals(object)) {
                return false;
            }
            final PastLocalDateGenerator that = (PastLocalDateGenerator) object;
            return Objects.equals(this.monthGenerator, that.monthGenerator);
        }
        return false;
    }

    @SuppressWarnings("boxing")
    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash(super.hashCode(), this.monthGenerator);
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("YEAR_MIN", YEAR_MIN)
                .add("YEAR_MAX", YEAR_MAX)
                .add("monthGenerator", this.monthGenerator)
                .toString();
    }

}
