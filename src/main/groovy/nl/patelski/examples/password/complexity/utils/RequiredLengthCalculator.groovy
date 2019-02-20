package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import nl.patelski.examples.utils.TablePrinter

import static nl.patelski.examples.utils.TablePrinter.Side

@Slf4j
/**
 * Calculates the required length of a password given specific cracking hardware and a minimum required cracking period
 */
class RequiredLengthCalculator {


    TablePrinter tablePrinter = new TablePrinter()

    // The minimum required period to brute-force in years
    static int REQUIRED_YEARS = 100

    // The cracking rig to be used
//    static CrackingRig USED_CRACKING_RIG = ExampleCrackingRigs.rigs["4xGTX1070"]
//    static CrackingRig USED_CRACKING_RIG = ExampleCrackingRigs.rigs.Brutalis
    static CrackingRig USED_CRACKING_RIG = ExampleCrackingRigs.rigs.Brutalis40x

    /**
     * Calculates the minimum required length of a password consisting of @param symbol characters
     * @param symbol The character used in this password
     * @param years The number of years it should take at minimum to brute-force this password
     * @param speed The speed at which brute-forcing can be done
     * @return The minimum required length for this password to be brute-force proof
     */
    int requiredLength(MaskSymbol symbol, BigInteger years, BigInteger speed) {
        return Math.ceil( logX(symbol.options, (speed * years * ExtraLongDuration.SECONDS_IN_YEAR)))
    }

    /**
     * Prints a matrix of minimum required legths for each MaskSymbol and Hash-type
     */
    static void main(String[] args) {
        RequiredLengthCalculator calculator = new RequiredLengthCalculator()
        calculator.printMatrix()
    }

    /**
     * Prints a matrix of minimum required legths for each MaskSymbol and Hash-type
     */
    private void printMatrix() {
        List<List<String>> output = []
        List<String> header = [""]
        header.addAll(MaskSymbol.values().collect{ it.name() })
        output.add(header)
        output.addAll(
            Hash.values().collect { encoding ->
                List<String> row = [encoding.name()]
                row.addAll(MaskSymbol.values().collect {
                    requiredLength(it, REQUIRED_YEARS, USED_CRACKING_RIG.speeds[encoding.name()])
                })
                row
            }
        )
        List<Side> sides = [TablePrinter.Side.LEFT]
        sides.addAll(Collections.nCopies(MaskSymbol.values().size(), TablePrinter.Side.RIGHT))
        log.info(tablePrinter.print(output, sides))
    }

    static double logX(long x, double a) {
        return Math.round(Math.log(a) / Math.log(x));
    }

}
