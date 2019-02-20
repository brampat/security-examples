package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j
import nl.patelski.examples.utils.TablePrinter

import static nl.patelski.examples.utils.TablePrinter.Side

@Slf4j
/**
 * Calculates time to crack for password-patterns for a number of hashing methods given specific hardware
 */
class ComplexityCalculator {

    Hash usedHash = Hash.MD5
    CrackingRig usedRig =
//            ExampleCrackingRigs.rigs["4xGTX1070"]
//            ExampleCrackingRigs.rigs["Brutalis"]
    ExampleCrackingRigs.rigs["Brutalis40x"]

    TablePrinter tablePrinter = new TablePrinter()

    static void main(String[] args) {
        ComplexityCalculator calculator = new ComplexityCalculator()
        calculator.printExamples()
//        calculator.printMatrix()
    }

    /**
     * Prints a matrix of all password-types and time to crack (least significant only)
     */
    private void printMatrix() {
        List<List<String>> table = []
        log.info("Cracking speed")
        List<String> header = []
        header.add("")
        header.addAll(Password.values().collect{ it.description})
        table.add(header)
        table.addAll(Hash.values().collect { encoding ->
            List<String> row = []
            row.add(encoding.name())
            row.addAll(Password.values().collect { duration(it.passwordSpace(), usedRig.speeds[encoding.name()]) })
            row
        })
        List<Side> alignments = [Side.LEFT]
        alignments.addAll(Collections.nCopies(Password.values().size(), Side.RIGHT))
        log.info(tablePrinter.print(table, alignments))
    }

    /**
     * Prints a table with all Password-types and for each: description, length, characters, password space and (full) time to crack.
     */
    private void printExamples(){
        List<List<String>> table = []
        table.add(["Description", "Length", "Characters", "Password space", "Time to crack"])
        table.addAll(
            Password.values().collect { password ->
                outputFor(password)
            }
        )
        log.info(tablePrinter.print(table, [Side.LEFT] + Collections.nCopies(4, Side.RIGHT)))
    }

    /**
     * Supplies a list of this password's description, length, options per position, password-space and (full) time to crack given the Hash algorithm and cracking-rig used
     * @param password The password to print info for
     * @return List of information
     */
    List<String> outputFor(Password password) {
        BigInteger durationInSeconds = password.passwordSpace().divide(BigInteger.valueOf(usedRig.speeds[usedHash.name()]))
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return [password.description, password.length, password.options, password.passwordSpace(), duration.printAll()]
    }

    /**
     * Calculates what time is needed to brute-force a password-space given a specific speed, but only returns the least significant part of the duration
     * @param space The password-space to brute-force
     * @param speed The speed at which brute-forcing can take place.
     * @return The least significant part of the duration
     */
    String duration(BigInteger space, Long speed) {
        BigInteger durationInSeconds = space.divide(speed)
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return duration.printLargest()
    }

}
