package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;


/**
 * Represents a Person (Client)'s insurance policy in the address book.
 * Guarantees: immutable;
 */
public class InsurancePolicy {
    public static final String MESSAGE_CONSTRAINTS = "Insurance policy must have a name";

    private final String value;
    /**
     * Constructs an {@code InsurancePolicy}.
     * Trims leading/trailing whitespace and validates constraints.
     */
    public InsurancePolicy(String policy) {
        requireNonNull(policy);
        final String normalised = policy.trim();
        checkArgument(isValidPolicy(normalised), MESSAGE_CONSTRAINTS);
        this.value = normalised;
    }

    /**
     * Returns if a given string is a valid policy.
     */
    public static boolean isValidPolicy(String test) {
        if (test == null) {
            return false;
        }
        final String trimmed = test.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InsurancePolicy)) {
            return false;
        }

        InsurancePolicy otherPolicy = (InsurancePolicy) other;
        return value.equals(otherPolicy.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
