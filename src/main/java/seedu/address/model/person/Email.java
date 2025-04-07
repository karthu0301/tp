package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;


/**
 * Represents a Person's email in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidEmail(String)}
 */
public class Email {

    public static final String MESSAGE_CONSTRAINTS = "Emails should be of the format local-part@domain "
            + "and must satisfy the following constraints:\n"
            + "1. The local-part (before the '@') should contain only alphanumeric characters and"
            + "special characters. "
            + "It must not contain consecutive periods (..) or start/end with a period.\n"
            + "2. The domain (after the '@') must be made up of domain labels separated by periods "
            + "(e.g., example.com).\n"
            + "   Each domain label must:\n"
            + "   - start and end with an alphanumeric character,\n"
            + "   - contain only alphanumeric characters or hyphens in between.\n"
            + "   The domain must end with a valid top-level domain (TLD), such as '.com', which must be "
            + "between 2 and 63 letters.";


    private static final String ALPHANUMERIC_NO_UNDERSCORE = "[^\\W_]+";
    private static final String LOCAL_PART_REGEX = "[a-zA-Z0-9!#$%&'*+/=?^_`{|}~.-]+";

    private static final String DOMAIN_PART_REGEX = ALPHANUMERIC_NO_UNDERSCORE
            + "(-" + ALPHANUMERIC_NO_UNDERSCORE + ")*";

    private static final String DOMAIN_REGEX = "(" + DOMAIN_PART_REGEX + "\\.)*" // subdomains
            + DOMAIN_PART_REGEX + "\\."
            + "[a-zA-Z]{2,}";

    public static final String VALIDATION_REGEX = LOCAL_PART_REGEX + "@" + DOMAIN_REGEX;

    private static final Set<String> VALID_TLDS = Set.of("com", "org", "net", "edu",
            "gov", "io", "co", "sg", "my", "info", "biz", "us", "uk", "ca");

    public final String value;

    /**
     * Constructs an {@code Email} object after validating the given string.
     *
     * @param email A string representing the email address.
     * @throws NullPointerException if {@code email} is null.
     * @throws IllegalArgumentException if {@code email} does not conform to the defined email format.
     */
    public Email(String email) {
        requireNonNull(email);
        checkArgument(isValidEmail(email), MESSAGE_CONSTRAINTS);
        value = email;
    }

    /** Checks if an email is valid with allowed TLD. */
    public static boolean isValidEmail(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }
        String[] parts = test.split("@");
        if (parts.length != 2) {
            return false;
        }
        String local = parts[0];
        String domain = parts[1];
        if (local.contains("..")) {
            return false;
        }
        int lastDot = domain.lastIndexOf('.');
        if (lastDot == -1 || lastDot == domain.length() - 1) {
            return false;
        }
        String tld = domain.substring(lastDot + 1).toLowerCase();
        return VALID_TLDS.contains(tld);
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
        if (!(other instanceof Email)) {
            return false;
        }
        Email otherEmail = (Email) other;
        return value.equalsIgnoreCase(otherEmail.value);
    }

    @Override
    public int hashCode() {
        return value.toLowerCase().hashCode();
    }
}
