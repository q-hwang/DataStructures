package DataStructures;

/**
 * Invariant that can be checked
 * @param <E> The type of element that the invariant applies to
 */
public interface Invariant<E> {

    boolean condition(E elem);

    default void check(E elem) {
        assert condition(elem);
    }
}
