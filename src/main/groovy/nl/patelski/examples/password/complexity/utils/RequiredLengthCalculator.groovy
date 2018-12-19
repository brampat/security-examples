package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class RequiredLengthCalculator {

    int requiredLength(MaskSymbol pattern, BigInteger years, BigInteger speed) {
        return Math.ceil( logX(pattern.options, (speed * years * ExtraLongDuration.SECONDS_IN_YEAR)))
    }

    static double logX( long x, double a )
    {
        return Math.round(Math.log(a) / Math.log(x));
    }

}
