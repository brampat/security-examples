package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
/**
 * Convenience class to print large number of seconds in meaningful words by splitting up into millennia, years, days, hours, minutes and seconds.
 */
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

    /**
     * Prints all parts of a duration, from millennia to seconds
     * @return A string-representation of a duration
     */
    String printAll() {
        StringBuffer result = new StringBuffer()
        result.append("${this.millennia} millennia, ")
        result.append("${this.years} years, ")
        result.append("${this.days} days, ")
        result.append("${this.hours} hours, ")
        result.append("${this.minutes} minutes, ")
        result.append("${this.seconds} seconds")
        return result.toString()
    }

    /**
     * Prints only the largest portion of the duration. Eg. For something that takes years, this leaves off the days, hours, minutes and seconds portion.
     * @param duration A duration
     * @return The largest portion of the duration
     */
    String printLargest() {
        if (this.millennia > 0) { return "${this.millennia} millennia" }
        if (this.years > 0) { return "${this.years} years" }
        if (this.days > 0) { return "${this.days} days" }
        if (this.hours > 0) { return "${this.hours} hours" }
        if (this.minutes > 0) { return "${this.minutes} minutes" }
        return this.seconds + " seconds"
    }

}
