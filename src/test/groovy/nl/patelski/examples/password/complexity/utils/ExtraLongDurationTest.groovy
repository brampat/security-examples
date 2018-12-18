package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class ExtraLongDurationTest extends Specification {

    @Unroll
    void "#sec seconds should be #millennia millennia, #years years, #days days, #hours hours, #minutes minutes and #exp_sec seconds"() {
        given:
        BigInteger seconds = new BigInteger(sec)

        when:
        ExtraLongDuration result = new ExtraLongDuration(seconds)

        then:
        result.millennia == BigInteger.valueOf(millennia)
        result.years == BigInteger.valueOf(years)
        result.days == BigInteger.valueOf(days)
        result.hours == BigInteger.valueOf(hours)
        result.minutes == BigInteger.valueOf(minutes)
        result.seconds == BigInteger.valueOf(exp_sec)

        where:
        sec            ||    millennia   |   years   |   days    |   hours   |   minutes |   exp_sec
        10             ||    0           |   0       |   0       |   0       |   0       |   10
        3600L          ||    0           |   0       |   0       |   1       |   0       |   0
        90000L         ||    0           |   0       |   1       |   1       |   0       |   0
        31626000L      ||    0           |   1       |   1       |   1       |   0       |   0
        31536000001L   ||    1           |   0       |   0       |   0       |   0       |   1

    }

}
