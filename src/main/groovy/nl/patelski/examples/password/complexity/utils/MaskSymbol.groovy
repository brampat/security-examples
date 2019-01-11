package nl.patelski.examples.password.complexity.utils

/**
 * Mask options for passwords, based on Hashcat mask, as found here: https://www.4armed.com/blog/perform-mask-attack-hashcat/
 * These can be used to create password-patterns that either fit a password-policy or a usage-pattern.
 */
enum MaskSymbol {

    DECIMAL(10L, "d"), // 0123456789
    LOWERCASE(26L, "l"), // abcdefghijklmnopqrstuvwxyz
    UPPERCASE(26L, "u"), // ABCDEFGHIJKLMNOPQRSTUVWXYZ
    ALPHANUMERIC(62L, "A"), // abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789
    ASCII(128L, "I"), // All ASCII
    SYMBOL(33, "s"), //  !"#$%&'()*+,-./:;<=>?@[\]^_`{|}~ (including space)
    ALPHA_SYMBOL(95L, "a"), // Alphanumeric or symbol, used by most password-generators
    UTF8(137000L, "8"), // The UTF8 characterset knows about 137k visible non-control (enter, backspace) characters
    ENGLISH_WORDS(35000L, "E"), // The average person knows about 20-35k words
    DICTIONARY_WORDS(171476L, "D"), // The English dictionary holds about 171k words
    USA_CITIES(27000L, "c"), // Cities in the USA according to https://www.quora.com/How-many-towns-and-cities-are-there-in-the-world
    CITIES(2000000L, "C"), // Cities in the world according to https://www.quora.com/How-many-towns-and-cities-are-there-in-the-world
    UNKNOWN(0L, "")

    MaskSymbol(long options, String symbol) {
        this.options = options
        this.symbol = symbol
    }

    private long options
    private String symbol

    static MaskSymbol getEnum(String symbol) {
        MaskSymbol result = values().find{ it.symbol == symbol }
        return result ?: UNKNOWN
    }

}