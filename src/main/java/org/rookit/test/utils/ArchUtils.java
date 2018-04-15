
package org.rookit.test.utils;

import static com.tngtech.archunit.core.domain.JavaCall.Predicates.target;
import static com.tngtech.archunit.core.domain.properties.HasName.Predicates.nameMatching;
import static com.tngtech.archunit.lang.conditions.ArchConditions.callMethodWhere;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.lang.ArchCondition;

@SuppressWarnings("javadoc")
public final class ArchUtils {

    public static ArchCondition<JavaClass> accessJavaImmutables() {
        return callMethodWhere(target(nameMatching("empty*")))
                .as("Using java util collections to manage immutable collections.");
    }

    private ArchUtils() {
    }

}
