package org.instancio;

/**
 * A collection of static factory methods for creating {@link Binding}s.
 * <p>
 * A binding allows targeting a specific class or field.
 */
// TODO add examples
public class Bindings {
    private Bindings() {
        // non-instantiable
    }

    /**
     * Creates a binding for the given class's field.
     *
     * @param declaringClass class declaring the field
     * @param fieldName      field name to bind
     * @return binding
     */
    public static Binding field(final Class<?> declaringClass, final String fieldName) {
        return Binding.fieldBinding(declaringClass, fieldName);
    }

    /**
     * Creates a binding for a field that belongs to the class being created.
     * <p>
     * Example
     * <pre>{@code
     *     Person person = Instancio.of(Person.class)
     *             .ignore(field("fullName")) // Person.fullName
     *             .create();
     * }</pre>
     *
     * @param fieldName field name to bind
     * @return binding
     */
    public static Binding field(final String fieldName) {
        return Binding.fieldBinding(fieldName);
    }

    /**
     * Creates a binding for the given type.
     *
     * @param type to bind
     * @return binding
     */
    public static Binding all(final Class<?> type) {
        return Binding.typeBinding(type);
    }

    /**
     * Shorthand for {@code all(String.class)}.
     *
     * @return binding for all Strings
     */
    public static Binding allStrings() {
        return all(String.class);
    }

    /**
     * Shorthand for {@code all(Integer.class)}.
     *
     * @return binding for all integers
     */
    public static Binding allInts() {
        return all(Integer.class);
    }

}