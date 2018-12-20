package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll
import MaskSymbol.*

@Slf4j
class RequiredLengthCalculatorTest extends Specification {

    RequiredLengthCalculator subject = new RequiredLengthCalculator()

    @Unroll
    def "passwords with #pattern chars can only be cracked in #years with cracking speeds of #speed tries per second when they are #expected long"() {

        given:
        subject

        when:
        int result = subject.requiredLength(pattern, BigInteger.valueOf(years), BigInteger.valueOf(speed))

        then:
        result == expected


        where:
        pattern                     |   years   |   speed               ||  expected
        MaskSymbol.ALPHANUMERIC     |   100     |   10000000000000000L  ||  14L

    }

    @Unroll
    def "Log #x (#a) is #expected"() {

        given:
        subject

        when:
        double result = subject.logX(x, a)

        then:
        result == expected


        where:
        x   |   a       ||  expected
        10  |   100     ||  2
        10  |   1000    ||  3
        3   |   27      ||  3
        2   |   64      ||  6
    }


}
