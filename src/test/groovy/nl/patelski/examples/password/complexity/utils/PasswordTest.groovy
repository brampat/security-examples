package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class PasswordTest extends Specification {

    @Unroll
    void "A number-slot with #positions dials and #options options per dials should have #space number of possibilities"() {
        given:
        Password password = new Password("", positions, options)

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == BigDecimal.valueOf(space)

        where:
        positions   |   options ||  space
        3           |   10      ||  1000L
        10          |   3       ||  59049L

    }

    @Unroll
    void "A password of length #positions and #options options per character should have #space number of possibilities"() {
        given:
        Password password = new Password("", positions, options)

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == space

        where:
        positions   |   options ||  space
        10          |   26      ||  new BigInteger("141167095653376")
        26          |   10      ||  new BigInteger("100000000000000000000000000")

    }

    @Unroll
    void "A password with pattern #pattern should have #space number of possibilities"() {
        given:
        Password password = new Password("", pattern)

        when:
        BigInteger result = password.passwordSpace()

        then:
        result == space

        where:
        pattern         ||  space
        "ddd"           ||  new BigInteger("1000")
        "dddddddddd"    ||  new BigInteger("10000000000")
        "llllllllll"    ||  new BigInteger(26L*26L*26L*26L*26*26*26*26*26*26)
        "llllllllll"    ||  new BigInteger("141167095653376")
        "dlA"           ||  new BigInteger("16120")
        "ullllldd"      ||  new BigInteger("30891577600")
        "DDDD"          ||  new BigInteger("864596308417753067776")    // 4x English Dictionary - exceeds the MAX of Long

    }



}
