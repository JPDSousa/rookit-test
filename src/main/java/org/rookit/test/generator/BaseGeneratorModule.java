package org.rookit.test.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.Random;

@SuppressWarnings("javadoc")
public class BaseGeneratorModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Random.class).toInstance(new Random());
        
        bind(new TypeLiteral<Generator<String>>() {
            }).to(StringGenerator.class).in(Singleton.class);
        bind(StringGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Boolean>>() {
            }).to(BooleanGenerator.class).in(Singleton.class);
        bind(BooleanGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<byte[]>>() {
            }).to(ByteArrayGenerator.class).in(Singleton.class);
        bind(ByteArrayGenerator.class).in(Singleton.class);
        
        bindNumeric();
        
        bindTime();
    }

    private void bindTime() {
        bind(new TypeLiteral<Generator<Duration>>() {
            }).to(DurationGenerator.class).in(Singleton.class);
        bind(DurationGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<LocalDate>>() {
        }).to(PastLocalDateGenerator.class).in(Singleton.class);
        bind(PastLocalDateGenerator.class).in(Singleton.class);
    }

    private void bindNumeric() {
        bind(new TypeLiteral<Generator<Short>>() {
        }).to(ShortGenerator.class).in(Singleton.class);
        bind(ShortGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Double>>() {
            }).to(DoubleGenerator.class).in(Singleton.class);
        bind(DoubleGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Integer>>() {
        }).to(IntegerGenerator.class).in(Singleton.class);
        bind(IntegerGenerator.class).in(Singleton.class);
        
        bind(new TypeLiteral<Generator<Long>>() {
        }).to(PositiveLongGenerator.class).in(Singleton.class);
        bind(PositiveLongGenerator.class).in(Singleton.class);
    }
    
    @Provides
    private Generator<Month> getMonthGenerator(final Random random) {
        return new EnumGenerator<>(random, Month.class);
    }

}
