
package org.rookit.test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.library.GeneralCodingRules;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.rookit.test.utils.ArchUtils;

@SuppressWarnings("javadoc")
public interface ArchTest {

    JavaClasses getJavaClasses();

    @Test
    default void testNoJavaImmutableCollections() {
        noClasses()
                .should(ArchUtils.accessJavaImmutables())
                .check(getJavaClasses());
    }

    @Test
    default void testNoJavaUtilLogging() {
        GeneralCodingRules.NO_CLASSES_SHOULD_USE_JAVA_UTIL_LOGGING
                .as("Java Util Logging is disencouraged in this project")
                .check(getJavaClasses());
    }

    @Test
    default void testNoStandardArrays() {
        noClasses()
                .should()
                .callMethod(Arrays.class, "asList", String[].class)
                .check(getJavaClasses());
    }

    @Test
    default void testNoStandardStreams() {
        GeneralCodingRules.NO_CLASSES_SHOULD_ACCESS_STANDARD_STREAMS
                .as("Using Standard Streams is disencouraged in this project")
                .check(getJavaClasses());
    }

}
