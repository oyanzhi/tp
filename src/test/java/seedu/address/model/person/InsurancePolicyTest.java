package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for {@link InsurancePolicy}.
 *
 * NOTE: These tests assume the typical AB-3 style value object:
 *  - public final String value;
 *  - static boolean isValidPolicy(String)
 *  - public static final String MESSAGE_CONSTRAINTS
 *  - equals / hashCode / toString implemented by value
 */
public class InsurancePolicyTest {

    private static final String VALID_SIMPLE = "AIA HealthShield Gold Max";
    private static final String VALID_WITH_PUNCT = "PRUShield Plus (A)";
    private static final String VALID_MIXED = "Great Eastern LifePlan-2025";

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new InsurancePolicy(null));
    }

    @Test
    public void constructor_invalidPolicy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new InsurancePolicy(""));
        assertThrows(IllegalArgumentException.class, () -> new InsurancePolicy("   "));
    }

    @Test
    public void constructor_validPolicy_success() {
        assertDoesNotThrow(() -> new InsurancePolicy(VALID_SIMPLE));
        assertDoesNotThrow(() -> new InsurancePolicy(VALID_WITH_PUNCT));
        assertDoesNotThrow(() -> new InsurancePolicy(VALID_MIXED));
    }

    @Test
    public void isValidPolicy_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> InsurancePolicy.isValidPolicy(null));
    }

    @Test
    public void isValidPolicy_invalid_returnsFalse() {
        assertFalse(InsurancePolicy.isValidPolicy(""));
        assertFalse(InsurancePolicy.isValidPolicy("   "));
    }

    @Test
    public void isValidPolicy_valid_returnsTrue() {
        assertTrue(InsurancePolicy.isValidPolicy(VALID_SIMPLE));
        assertTrue(InsurancePolicy.isValidPolicy(VALID_WITH_PUNCT));
        assertTrue(InsurancePolicy.isValidPolicy(VALID_MIXED));
    }

    @Test
    public void equals_sameValue_returnsTrue() {
        InsurancePolicy a = new InsurancePolicy(VALID_SIMPLE);
        InsurancePolicy b = new InsurancePolicy(VALID_SIMPLE);
        assertEquals(a, b);
        assertEquals(a, a);
        assertNotEquals(a, null);
        assertNotEquals(a, 42);
    }

    @Test
    public void equals_differentValue_returnsFalse() {
        InsurancePolicy a = new InsurancePolicy(VALID_SIMPLE);
        InsurancePolicy c = new InsurancePolicy(VALID_WITH_PUNCT);
        assertNotEquals(a, c);
    }

    @Test
    public void hashCode_consistentWithEquals() {
        InsurancePolicy a1 = new InsurancePolicy(VALID_SIMPLE);
        InsurancePolicy a2 = new InsurancePolicy(VALID_SIMPLE);
        InsurancePolicy b = new InsurancePolicy(VALID_WITH_PUNCT);

        assertEquals(a1.hashCode(), a2.hashCode());
        assertNotEquals(a1.hashCode(), b.hashCode());
    }

    @Test
    public void toString_returnsValue() {
        InsurancePolicy p = new InsurancePolicy(VALID_MIXED);
        assertEquals(VALID_MIXED, p.toString());
    }

    @Test
    public void value_exposesUnderlyingString() {
        InsurancePolicy p = new InsurancePolicy(VALID_SIMPLE);
        assertEquals(VALID_SIMPLE, p.toString());
    }
}
