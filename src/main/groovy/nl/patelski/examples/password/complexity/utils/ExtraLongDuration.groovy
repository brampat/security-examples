package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class ExtraLongDuration {

    private static long SECONDS_IN_MINUTE = 60
    private static long SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60
    private static long SECONDS_IN_DAY = SECONDS_IN_HOUR * 24
    private static long SECONDS_IN_YEAR = SECONDS_IN_DAY * 365
    private static long SECONDS_IN_MILLENNIUM = SECONDS_IN_YEAR * 1000

    private BigInteger millennia
    private BigInteger years
    private BigInteger days
    private BigInteger hours
    private BigInteger minutes
    private BigInteger seconds

    ExtraLongDuration(BigInteger seconds) {
        BigInteger[] remaining = seconds.divideAndRemainder(SECONDS_IN_MILLENNIUM)
        this.millennia = remaining[0]
        remaining = remaining[1].divideAndRemainder(SECONDS_IN_YEAR)
        this.years = remaining[0]
        remaining = remaining[1].divideAndRemainder(SECONDS_IN_DAY)
        this.days = remaining[0]
        remaining = remaining[1].divideAndRemainder(SECONDS_IN_HOUR)
        this.hours = remaining[0]
        remaining = remaining[1].divideAndRemainder(SECONDS_IN_MINUTE)
        this.minutes = remaining[0]
        this.seconds = remaining[1]
    }

}
