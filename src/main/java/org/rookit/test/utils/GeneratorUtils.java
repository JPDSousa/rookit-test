
package org.rookit.test.utils;

import com.tngtech.archunit.thirdparty.com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

import org.rookit.test.TestValidator;
import org.rookit.test.generator.Generator;

@SuppressWarnings("javadoc")
public final class GeneratorUtils {

    public static final TestValidator VALIDATOR = TestValidator.getSingleton();
    private static final Random RANDOM = new Random();

    public static <E> Optional<E> randomlyConsume(final Consumer<E> consumer, final Generator<E> generator) {
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        
        if (RANDOM.nextBoolean()) {
            final E item = generator.createRandom();
            consumer.accept(item);
            return Optional.of(item);
        }
        return Optional.empty();
    }

    public static <E> Collection<E> randomlyConsumeCollection(final Consumer<Collection<E>> consumer,
            final Generator<E> generator) {
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        
        if (RANDOM.nextBoolean()) {
            return consumeCollection(consumer, generator.createRandomSet());
        }
        return ImmutableSet.of();
    }
    
    public static <E> Collection<E> randomlyConsumeCollection(final Consumer<Collection<E>> consumer,
            final Generator<E> generator,
            final Collection<E> exceptions) {
        VALIDATOR.checkArgument().isNotNull(consumer, "consumer");
        VALIDATOR.checkArgument().isNotNull(generator, "generator");
        VALIDATOR.checkArgument().isNotNull(exceptions, "exceptions");
        
        if (RANDOM.nextBoolean()) {
            return consumeCollection(consumer, generator.createRandomUniqueSet(exceptions));
        }
        return ImmutableSet.of();
    }
    
    private static <E> Collection<E> consumeCollection(final Consumer<Collection<E>> consumer,
            final Collection<E> items) {
        consumer.accept(items);
        return items;
    }

    private GeneratorUtils() {
    }

}
