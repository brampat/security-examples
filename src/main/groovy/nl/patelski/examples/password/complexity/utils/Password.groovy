package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class Password {
    private String description
    private int length
    private int options
    private String pattern

    Password(String description, int length, int options) {
        this.description = description
        this.length = length
        this.options = options
        this.pattern = ""
    }

    Password(String description, String pattern) {
        this.description = description
        this.pattern = pattern
        this.length = pattern.length()
    }

    BigInteger passwordSpace() {
        if (this.pattern) {
            return space(this.pattern)
        } else {
            return space(this.length, this.options)
        }
    }

    private BigInteger space(int positions, int options) {
        return options.power(positions)
    }

    private BigInteger space(List<MaskSymbol> pattern) {
        return pattern.options.inject(1L) { BigInteger count, BigInteger it -> count * it }
    }

    private BigInteger space(String pattern) {
        return space(pattern.collect{ MaskSymbol.getEnum(it) }.toList())
    }


}
