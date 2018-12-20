package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class RequiredLengthCalculator {


    static int REQUIRED_YEARS = 100
//    static CrackingRig USED_CRACKING_RIG = ExampleCrackingRigs.rigs.Brutalis
    static CrackingRig USED_CRACKING_RIG = ExampleCrackingRigs.rigs["Brutalis40x"]

    int requiredLength(MaskSymbol pattern, BigInteger years, BigInteger speed) {
        return Math.ceil( logX(pattern.options, (speed * years * ExtraLongDuration.SECONDS_IN_YEAR)))
    }

    static void main(String[] args) {
        RequiredLengthCalculator calculator = new RequiredLengthCalculator()
        calculator.printMatrix()
    }


    private void printMatrix() {
        log.info("Required length")
        log.info("|        |" + MaskSymbol.values().collect{
            String.format("%-12s", it.name())
        }.join("|"))

        Hash.values().collect { encoding ->
            return "|${String.format("%-8s", " ${encoding.name()}")}|" +
                    MaskSymbol.values().collect {
                        String.format("%12s", requiredLength(it, REQUIRED_YEARS, USED_CRACKING_RIG.speeds[encoding.name()]))
                    }.join("|")
        }.each {
            log.info(it)
        }
    }



    static double logX( long x, double a )
    {
        return Math.round(Math.log(a) / Math.log(x));
    }

}
