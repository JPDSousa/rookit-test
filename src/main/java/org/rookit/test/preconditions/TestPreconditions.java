package org.rookit.test.preconditions;

import org.apache.logging.log4j.Logger;
import org.rookit.test.TestValidator;
import org.rookit.utils.log.validator.ObjectValidator;

@SuppressWarnings("javadoc")
public final class TestPreconditions {
    
    private TestPreconditions() {}
    
    private static final TestValidator VALIDATOR = TestValidator.getSingleton();
    
    /**
     * Logger for TestPrecondition.
     */
    private static final Logger logger = VALIDATOR.getLogger(TestPreconditions.class);
    
    private static final ObjectValidator ASSURANCE = new ObjectValidator(logger, TestException::new); 
    
    public static ObjectValidator assure() {
        return ASSURANCE;
    }

}
