package org.rookit.test;

import org.rookit.Rookit;
import org.rookit.utils.log.AbstractLogCategory;
import org.rookit.utils.log.LogManager;
import org.rookit.utils.log.Validator;

@SuppressWarnings("javadoc")
public final class TestValidator extends Validator {
	
	private static final TestValidator SINGLETON = new TestValidator(new AbstractLogCategory() {
		
		@Override
		public Package getPackage() {
			return Rookit.class.getPackage();
		}
		
		@Override
		public String getName() {
			return "RookitTest";
		}
	});
	
	public static TestValidator getSingleton() {
		return SINGLETON;
	}

	private TestValidator(final AbstractLogCategory category) {
		super(LogManager.create(category));
	}

}
