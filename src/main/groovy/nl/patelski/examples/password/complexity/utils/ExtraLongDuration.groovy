package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class ExtraLongDuration {

    private static int SECONDS_IN_MINUTE = 60
    private static int SECONDS_IN_HOUR = SECONDS_IN_MINUTE * 60
    private static int SECONDS_IN_DAY = SECONDS_IN_HOUR * 24
    private static int SECONDS_IN_YEAR = SECONDS_IN_DAY * 365
    private static int SECONDS_IN_MILLENNIUM = SECONDS_IN_YEAR * 1000

    String print(BigInteger seconds) {
        printAll(seconds)
    }

    private printAll(BigInteger remaining) {
        StringBuffer result = new StringBuffer()
        remaining = printPart(remaining, SECONDS_IN_MILLENNIUM, " millennia, ", 30, result)
        remaining = printPart(remaining, SECONDS_IN_YEAR, " years, ", 4, result)
        remaining = printPart(remaining, SECONDS_IN_DAY, " days, ", 3, result)
        remaining = printPart(remaining, SECONDS_IN_HOUR, " hours, ", 2, result)
        remaining = printPart(remaining, SECONDS_IN_MINUTE, " minutes, ", 2, result)
        result.append(String.format("%2s", remaining) + " seconds")
        return result.toString()
    }

    private BigInteger printPart(BigInteger remaining, int divider, String description, int positions, StringBuffer stringBuffer) {
        BigInteger[] result = remaining.divideAndRemainder(divider)
        stringBuffer.append(String.format("%${positions}s", result[0]) + description)
        return result[1]
    }



    private String printOnly(BigInteger remaining) {
        return ""
    }

}
