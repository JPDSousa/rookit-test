
package org.rookit.test;

import java.util.Collection;

@SuppressWarnings("javadoc")
public interface CollectionOps<T> {

    void add(T item);

    void addAll(Collection<T> items);

    Collection<T> get();

    void remove(T item);

    void removeAll(Collection<T> items);

    void reset();

    void set(Collection<T> items);

}
