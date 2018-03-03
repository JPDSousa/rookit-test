package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

@SuppressWarnings("javadoc")
public abstract class AbstractTest<T> {

	protected static final TestValidator VALIDATOR = TestValidator.getSingleton();
	private static final Logger logger = VALIDATOR.getLogger(AbstractTest.class); 

	protected T guineaPig;

	protected abstract T createGuineaPig();

	@Before
	public final void assignGuineaPig() {
		this.guineaPig = createGuineaPig();
	}

	@Test
	public final void testCreatorDoesNotReturnNull() {
		assertThat(createGuineaPig())
		.as("Creating a non null test object")
		.isNotNull();
	}

	@Test
	public final void testGetters() throws Exception {
		final Class<?> guineaPigClass = this.guineaPig.getClass();
		final String className = guineaPigClass.getName();

		for (final Method method : guineaPigClass.getDeclaredMethods()) {
			final String methodName = method.getName();

			if (isGetter(methodName, method)) {
				final Class<?> returnType = method.getReturnType();

				assertThat(returnType)
				.as("The method %s in class %s with return type %s", 
						methodName, className, returnType)
				.isNotEqualTo(Void.class)
				.isNotEqualTo(void.class);

				final Object returnValue;
				if (!method.isAccessible()) {
					method.setAccessible(true);
					returnValue = method.invoke(guineaPig);
					method.setAccessible(false);
				} else {
					returnValue = method.invoke(guineaPig);
				}
				assertThat(returnValue)
				.as("Invoking getter method %s of class %s does not return null",
						methodName, className)
				.isNotNull();
			}
		}
	}

	private boolean isGetter(final String methodName, final Method method) {
		final boolean isGetterByName = Objects.nonNull(methodName) && methodName.startsWith("get");
		final int nParams = method.getParameterTypes().length;

		if (isGetterByName && nParams > 0) {
			logger.warn("Found possible getter with parameters: Method [{}] on Class [{}] "
					+ "with {} parameters.", 
					methodName, guineaPig.getClass().getName(), nParams);
			return false;
		}

		return isGetterByName;
	}

	@Test
	public final void testSetter() {
		final Class<?> guineaPigClass = this.guineaPig.getClass();

		for (final Method method : guineaPigClass.getDeclaredMethods()) {
			final String methodName = method.getName();

			if (isValidSetter(methodName, method)) {
				assertThatThrownBy(() -> invokeMethod(method))
				.as("Setting a value with null on method %s of class %s", methodName, guineaPigClass.getName())
				.isInstanceOf(IllegalArgumentException.class)
				.hasMessageContaining("null");
			}
		}
	}
	
	private void invokeMethod(final Method method) throws Throwable {
		try {
			if (!method.isAccessible()) {
				method.setAccessible(true);
				method.invoke(guineaPig, (Object) null);
				method.setAccessible(false);
			} else {
				method.invoke(guineaPig, (Object) null);
			}
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		} catch (final Exception e) {
			VALIDATOR.runtimeException(e);
		}
	}

	private boolean isValidSetter(final String methodName, final Method method) {
		final boolean isSetterByName = Objects.nonNull(methodName) 
				&& (methodName.startsWith("set") || methodName.startsWith("with"));
		final int nParams = method.getParameterTypes().length;

		if (isSetterByName && nParams != 1) {
			logger.warn("Found possible setter with an invalid number of parameters:"
					+ " Method [{}] on Class [{}] with {} parameters.",
					methodName, guineaPig.getClass().getName(), nParams);
			return false;
		}
		
		if (isSetterByName && nParams == 1 && method.getParameterTypes()[0].isPrimitive()) {
			logger.info("Found setter with primitive type params: not applicable for the test");
			return false;
		}

		return isSetterByName;
	}

}
