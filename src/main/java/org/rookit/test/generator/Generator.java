
package org.rookit.test.generator;

import com.google.inject.Provider;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

@SuppressWarnings("javadoc")
public interface Generator<T> extends Supplier<T>, Provider<T> {

    T createRandom();

    List<T> createRandomList();

    Set<T> createRandomSet();

    T createRandomUnique(T item);

    Set<T> createRandomUniqueSet(Collection<T> items);

}
