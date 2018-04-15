
package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.junit.jupiter.api.Test;

@SuppressWarnings("javadoc")
public interface SerializationTest<T extends Serializable> extends ObjectTest<T> {

    public static final Gson GSON = new GsonBuilder().create();
    
    Class<T> getTestResourceType();

    @Test
    default void testJavaRoundtripSerialization() throws Exception {
        final T testResource = getTestResource();
        final ByteArrayOutputStream bytesOutput = new ByteArrayOutputStream();
        final ObjectOutputStream serializer = new ObjectOutputStream(bytesOutput);
        serializer.writeObject(testResource);

        final ByteArrayInputStream bytesInput = new ByteArrayInputStream(bytesOutput.toByteArray());
        final ObjectInputStream deserializer = new ObjectInputStream(bytesInput);
        final Object deserialized = deserializer.readObject();

        assertThat(deserialized)
                .as("The deserialized object")
                .isEqualTo(testResource);

    }

    @Test
    default void testJsonRoundtripSerialization() {
        final T testResource = getTestResource();
        final Class<T> testResourceClass = getTestResourceType();
        final String serialized = GSON.toJson(testResource);

        final T deserialized = GSON.fromJson(serialized, testResourceClass);

        assertThat(deserialized)
                .as("The deserialized object")
                .isEqualTo(testResource);
    }

}
