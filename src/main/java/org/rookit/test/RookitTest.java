package org.rookit.test;

@SuppressWarnings("javadoc")
public interface RookitTest<T> {
    
    T getTestResource();
    
    T createTestResource();

}
