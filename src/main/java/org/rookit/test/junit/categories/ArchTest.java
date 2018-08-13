package org.rookit.test.junit.categories;

import org.junit.jupiter.api.Tag;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import static org.rookit.test.junit.categories.TestCategories.ARCH;

@SuppressWarnings("javadoc")
@Target({ TYPE, METHOD })
@Retention(RUNTIME)
@Tag(ARCH)
public @interface ArchTest {
}
