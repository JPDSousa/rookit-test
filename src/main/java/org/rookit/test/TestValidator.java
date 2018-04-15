
package org.rookit.test;

import org.rookit.RookitTest;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogManager;
import org.rookit.utils.log.validator.Validator;

@SuppressWarnings("javadoc")
public final class TestValidator extends Validator {

    private static final TestValidator SINGLETON = new TestValidator(new AbstractLogCategory() {

        @Override
        public String getName() {
            return "RookitTest";
        }

        @Override
        public Package getPackage() {
            return RookitTest.class.getPackage();
        }
    });

    public static TestValidator getSingleton() {
        return SINGLETON;
    }

    private TestValidator(final AbstractLogCategory category) {
        super(LogManager.create(category));
    }

}
