package org.rookit.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Collection;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CollectionOpsTester {
    
    private CollectionOpsTester() {}
    
    private static <E> void assertAddAndRemove(final CollectionOps<E> collectionOps,
            final Supplier<E> itemSupplier,
            final Collection<E> setItems) {
        final E oneItem = itemSupplier.get();
        final Collection<E> multipleItems = Stream.generate(itemSupplier)
                .limit(20)
                .collect(Collectors.toList());
        collectionOps.addAll(multipleItems);
        collectionOps.add(oneItem);
        assertThat(collectionOps.get())
                .as("Adding multiple items should not remove previously added ones")
                .containsAll(setItems)
                .containsAll(multipleItems)
                .contains(oneItem);

        collectionOps.remove(oneItem);
        final String description = "Removing one item should delete its presence from the collection, "
                + "but not affect other previously added items";
        assertThat(collectionOps.get())
                .as(description)
                .doesNotContain(oneItem)
                .containsAll(multipleItems);
    }

    private static <E> E assertAddAndReset(final CollectionOps<E> collectionOps, final Supplier<E> itemSupplier) {
        final E oneItem = itemSupplier.get();
        collectionOps.add(oneItem);
        assertThat(collectionOps.get())
                .as("A collection with a single item")
                .containsExactly(oneItem);

        collectionOps.reset();
        assertThat(collectionOps.get())
                .as("Resetting should remove the added item")
                .isEmpty();
        return oneItem;
    }

    private static <E> void assertNullNotAllowed(final CollectionOps<E> collectionOps) {
        assertThatThrownBy(() -> collectionOps.add(null))
                .as("Adding null is not allowed")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");

        assertThatThrownBy(() -> collectionOps.addAll(null))
                .as("Adding all null is not allowed")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");

        assertThatThrownBy(() -> collectionOps.set(null))
                .as("Setting null is not allowed")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");

        assertThatThrownBy(() -> collectionOps.remove(null))
                .as("Removing null is not allowed")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");

        assertThatThrownBy(() -> collectionOps.removeAll(null))
                .as("Removing all null is not allowed")
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("null");
    }

    private static <E> void assertRemoveItems(final CollectionOps<E> collectionOps,
            final Supplier<E> itemSupplier,
            final E containedItem) {
        final Collection<E> removedItems = Stream.generate(itemSupplier)
                .filter(item -> !item.equals(containedItem))
                .limit(20)
                .collect(Collectors.toList());
        collectionOps.add(containedItem);
        collectionOps.removeAll(removedItems);
        assertThat(collectionOps.get())
                .as("Removing multiple items should delete all items, but not affect previously addedOnes")
                .doesNotContainAnyElementsOf(removedItems)
                .contains(containedItem);
    }

    private static <E> Collection<E> assertSet(final CollectionOps<E> collectionOps,
            final Supplier<E> itemSupplier,
            final E oneItem) {
        final Collection<E> setItems = Stream.generate(itemSupplier)
                .filter(item -> !item.equals(oneItem))
                .limit(20)
                .collect(Collectors.toList());
        collectionOps.set(setItems);
        assertThat(collectionOps.get())
                .as("Setting items should ovewrite previously added ones")
                .containsAll(setItems)
                .doesNotContain(oneItem);
        return setItems;
    }

    static final <E> void test(final CollectionOps<E> collectionOps,
            final Supplier<E> itemSupplier) {
        assertNullNotAllowed(collectionOps);

        collectionOps.reset();
        assertThat(collectionOps.get())
                .as("Resetting should make the collection empty")
                .isEmpty();

        final E oneItem = assertAddAndReset(collectionOps, itemSupplier);

        final Collection<E> setItems = assertSet(collectionOps, itemSupplier, oneItem);

        assertAddAndRemove(collectionOps, itemSupplier, setItems);
        assertRemoveItems(collectionOps, itemSupplier, oneItem);
    }

}
