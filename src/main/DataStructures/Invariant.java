package DataStructures;

public interface Invariant<E> {

    boolean condition(E elem);

    default void check(E elem) {
        assert condition(elem);
    }
}
