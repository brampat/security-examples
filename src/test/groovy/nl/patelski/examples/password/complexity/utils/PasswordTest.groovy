package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class PasswordTest extends Specification {

    @Unroll
    void "A number-slot with #positions dials and #options options per dials should have #space number of possibilities"() {
        given:
        password

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == BigDecimal.valueOf(space)

        where:
        password                        ||  space
        Password.DECIMAL_NUMBERS_3X     ||  1000L
        Password.REVERSE_DECIMALS_3X    ||  59049L

    }

    @Unroll
    void "A password of length #positions and #options options per character should have #space number of possibilities"() {
        given:
        password

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == space

        where:
        password                ||  space
        Password.LOWERCASE_10X  ||  new BigInteger("141167095653376")
        Password.DECIMAL_26X    ||  new BigInteger("100000000000000000000000000")

    }

    @Unroll
    void "A password with pattern #password should have #space number of possibilities"() {
        given:
        password

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == space

        where:
        password                        ||  space
        Password.DECIMAL_NUMBERS_3X     ||  new BigInteger("1000")
        Password.DECIMAL_26X            ||  new BigInteger("100000000000000000000000000")
        Password.LOWERCASE_10X          ||  new BigInteger(26L*26L*26L*26L*26*26*26*26*26*26)
        Password.LONDON18_PATTERN       ||  new BigInteger("30891577600")
        Password.ENGLISH_DICTIONARY_4X  ||  new BigInteger("864596308417753067776")    // 4x English Dictionary - exceeds the MAX of Long

    }



}
