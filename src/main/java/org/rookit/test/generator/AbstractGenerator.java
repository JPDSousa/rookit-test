
package org.rookit.test.generator;

import com.google.common.base.MoreObjects;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Generated;

import org.apache.logging.log4j.Logger;
import org.rookit.test.TestValidator;

@SuppressWarnings("javadoc")
public abstract class AbstractGenerator<T> implements Generator<T> {

    protected static final TestValidator VALIDATOR = TestValidator.getSingleton();

    /**
     * Logger for AbstractGenerator.
     */
    private static final Logger logger = VALIDATOR.getLogger(AbstractGenerator.class);

    private static final int MAX_SIZE = 10;

    private final Random random = new Random();

    @Override
    public List<T> createRandomList() {
        return Stream.generate(this)
                .limit(this.random.nextInt(MAX_SIZE) + 1)
                .collect(Collectors.toList());
    }

    @Override
    public Set<T> createRandomSet() {
        return Stream.generate(this)
                .limit(this.random.nextInt(MAX_SIZE) + 1)
                .collect(Collectors.toSet());
    }

    @Override
    public T createRandomUnique(final T item) {
        return Stream.generate(this::createRandom)
                .filter(generated -> !Objects.equals(item, generated))
                .limit(MAX_SIZE)
                .findFirst()
                .orElseGet(() -> VALIDATOR.handleException()
                        .runtimeException("Cannot find a unique value for: " + item));
    }

    @Override
    public Set<T> createRandomUniqueSet(final Collection<T> items) {
        final Set<T> uniqueSet = Sets.newHashSetWithExpectedSize(items.size());
        while (uniqueSet.size() < items.size()) {
            final T item = createRandom();
            if (!items.contains(item)) {
                uniqueSet.add(item);
            }
        }
        return uniqueSet;
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public boolean equals(final Object object) {
        return object instanceof AbstractGenerator;
    }

    @Override
    public T get() {
        return createRandom();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public int hashCode() {
        return Objects.hash();
    }

    @Override
    @Generated(value = "GuavaEclipsePlugin")
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("super", super.toString())
                .add("VALIDATOR", VALIDATOR)
                .add("logger", logger)
                .add("MAX_SIZE", MAX_SIZE)
                .add("random", this.random)
                .toString();
    }

}
