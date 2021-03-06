package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

/**
 * This enum class describes a number of Password-patterns and policies.
 * These can then be used to calculate password-space, which is the theoretical total number of options this pattern or
 * policy can support. This password space can often be calculated by raising <options> to the power of <length>,
 * where <options> is the number of options for each position and <length> is the length measured in <option> instances.
 * So the formula is O^L and is noted for each enum-instance in comment. For patterns with different options the formula is longer
 *
 * Please note that human preference will result in a much smaller space actually being used and password cracking techniques can prioritize these preferences.
 * Due to this, users may always want to use good randomization to choose a password and preferably icw. password-managers
 */
@Slf4j
enum Password {

    // 10^3: A regular 3 position number-slot with decimal dials as seen on suitcases
    DECIMAL_NUMBERS_3X(" 3x Number-slot", 3, 10),

    // 3^10: The reverse of DECIMAL_NUMBERS_3X, to indicate that length has more impact than complexity
    REVERSE_DECIMALS_3X(" Reverse Number-slot", 10, 3),

    // 26^10: Only using lowercase characters
    LOWERCASE_10X(" 10x Lowercase", 10, 26),

    // 35000 * 10^2: A random word with a number. Fits most password-policies and widely used, but has very limited password-space.
    ULLLLLdd_PATTERN(" Random pattern like 'Ullllldd'", "Ullllldd"),

    // 35000 * 10^2: A random word with a number. Fits most password-policies and widely used, but has very limited password-space.
    RANDOM18_PATTERN(" Pattern like 'Random18'", "Edd"),

    // Dictionary attack using leaked password-set of 500M passwords
    LEAKED_PASSWORDS("Pwned Password dictionary", 1, 500000000),

    // 27000 * 10^2: A random USA city with a number. Fits most password-policies and widely used, but has very limited password-space.
    DENVER18_PATTERN(" Pattern like 'Denver18'", "cdd"),

    // 2000000 * 10^2: A random world city with a number. Fits most password-policies and widely used, but has very limited password-space.
    WORLDCITY_18_PATTERN(" 'Denver18' or 'London19'", "Cdd"),

    // 95^8: Using the most widely accepted symbols, lower- and uppercase and decimals, an 8-length random password
    ALPHANUMERIC_SYMBOLS_8X(" 8x alphanumeric or symbol", 'a'*8),

    // 35000^4: The average person knows about 20-35k words. This pattern illustrates a passphrase using that vocabulary
    ENGLISH_WORDS_4X(" 4x English words", "EEEE"),

    // 171000^4: When basing a passphrase on the English dictionary, containing 171k words, the password-space is much bigger
    ENGLISH_DICTIONARY_4X(" 4x English Dictionary", "DDDD"),

    // 7776^5: derived from 5x standard D6 dicerolls
    DICEWARE_WORDS_5X("5x standard Diceware random word", "WWWWW"),

    // 62^8: Using all lower- and uppercase characters and decimal numbers
    ALPHANUMERIC_8X(" 8x All alphanumeric", 8, 26+26+10),

    // 62^10: Using all lower- and uppercase characters and decimal numbers
    ALPHANUMERIC_10X(" 10x All alphanumeric", 10, 26+26+10),

    // 62^14: Using all lower- and uppercase characters and decimal numbers, but a bit longer
    ALPHANUMERIC_14X(" 14x All alphanumeric", 14, 26+26+10),

    // 10^26: Reverse of LOWERCASE_10X (not ALPHANUMERIC_10X which is beaten as well), again complexity is trumped hard by length
    DECIMAL_26X(" 26x numerical", 26, 10),

    // 128^10: This is where some systems stop supporting and start refusing certain characters
    ASCII_10X(" 10x ASCII", 10, 128),

    // 137000^8: Yes, we would like to add smilies to our passwords. Why are some characters more special than others?
    UTF8_8X(" 8x UTF8 (smilies ;-)", 8, 137000),

    // 10^42: Fails horribly passing password-policies, but is nowhere near crackable
    DECIMAL_42X(" 42x numerical", 42, 10),

    // 95^30: Using the most widely accepted symbols, lower- and uppercase and decimals, a 30-length random password is very much sufficient
    ALPHANUMERIC_SYMBOLS_30X(" 30x alphanumeric or symbol", 'a'*30),

    // 137000^30: If only we could enter all UTF8 characters. How fast are these quantum computers again?
    UTF8_30X(" 30x UTF8", 30, 137000)


    private String description
    private int length
    private int options
    private String pattern

    /**
     * A password with all similar elements and a given length
     * @param description A description of this password
     * @param length The length of this password
     * @param options The number of options for each position
     */
    Password(String description, int length, int options) {
        this.description = description
        this.length = length
        this.options = options
        this.pattern = ""
    }

    /**
     * A password following a specific pattern
     * @param description A description of this password
     * @param pattern The pattern for this password, using {@link nl.patelski.examples.password.complexity.utils.MaskSymbol}
     */
    Password(String description, String pattern) {
        this.description = description
        this.pattern = pattern
        this.length = pattern.length()
    }

    /**
     * Calculates the full password-space for a given password.
     * @return A BigInteger with the full password-space
     */
    BigInteger passwordSpace() {
        if (this.pattern) {
            return space(this.pattern)
        } else {
            return space(this.length, this.options)
        }
    }

    /**
     * Calculates the password space for a password with only similar elements
     * @param positions The number of positions (length) of the password
     * @param options The number of options (complexity) for each position
     * @return A BigInteger with the full password-space
     */
    private BigInteger space(int positions, int options) {
        return options.power(positions)
    }

    /**
     * Calculates the password space for a pattern-based password
     * @param pattern The pattern of the password
     * @return A BigInteger with the full password-space
     */
    private BigInteger space(List<MaskSymbol> pattern) {
        // Multiply each position's options, starting with base 1.
        return pattern.options.inject(1L) { BigInteger count, BigInteger it -> count * it }
    }

    /**
     * Calculates the password space for a pattern-based password
     * @param pattern The pattern of the password
     * @return A BigInteger with the full password-space
     */
    private BigInteger space(String pattern) {
        return space(pattern.collect{ MaskSymbol.getEnum(it) }.toList())
    }


}
