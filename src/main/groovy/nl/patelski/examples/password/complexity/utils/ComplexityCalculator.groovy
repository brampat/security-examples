package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class ComplexityCalculator {

    Hash usedHash = Hash.MD5
    CrackingRig usedRig =
//            ExampleCrackingRigs.rigs["4xGTX1070"]
            ExampleCrackingRigs.rigs["Brutalis"]


    static int[] C = [30, 8, 12, 50, 100] // Column-formats

    static String[] CF = [
            "%-${C[0]}s",
            "%${C[1]}s",
            "%${C[2]}s",
            "%${C[3]}s",
            "%${C[4]}s"
    ]

    static String[] CH = [
            "%-${C[0]}s",
            "%-${C[1]}s",
            "%-${C[2]}s",
            "%-${C[3]}s",
            "%-${C[4]}s"
    ]

    static void main(String[] args) {
        ComplexityCalculator calculator = new ComplexityCalculator()
        calculator.printExamples()
//        calculator.printMatrix()
    }

    private void printMatrix() {
        log.info("Cracking speed")
        log.info("|        |" + Password.values().collect{
            String.format("%-30s", it.description)
        }.join("|"))

        Hash.values().collect { encoding ->
            return "|${String.format("%-8s", " ${encoding.name()}")}|" +
                    Password.values().collect {
                        String.format("%30s", duration(it.passwordSpace(), usedRig.speeds[encoding.name()]))
                    }.join("|")
        }.each {
            log.info(it)
        }
    }

    private void printExamples(){
        log.info("Cracking speed of passwords encrypted with ${usedHash.name()}")
        log.info("|${String.format(CH[0], " Description")}|${String.format(CH[1], " Length")}|${String.format(CH[2], " Characters")}|${String.format(CH[3], " Password space")}| ${String.format(CH[4], " Time to crack")}")
        log.info("|${"*"*C[0]}|${"*"*C[1]}|${"*"*12}|${"*"*50}|${"*"*100}|")

        Password.values().each {
            log.info(outputFor(it))
        }

    }

    String outputFor(Password password) {
        BigInteger durationInSeconds = password.passwordSpace().divide(BigInteger.valueOf(usedRig.speeds[usedHash.name()]))
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return "|${String.format(CF[0], password.description)}|${String.format(CF[1], password.length)}|${String.format(CF[2], password.options)}|${String.format(CF[3], password.passwordSpace())}|${String.format(CF[4], printAll(duration))}|"
    }

    String duration(BigInteger space, Long speed) {
        BigInteger durationInSeconds = space.divide(speed)
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return printOnly(duration)
    }

    private printAll(ExtraLongDuration duration) {
        StringBuffer result = new StringBuffer()
        result.append(printPart(duration.millennia, " millennia, ", 30))
        result.append(printPart(duration.years, " years, ", 30))
        result.append(printPart(duration.days, " days, ", 4))
        result.append(printPart(duration.hours, " hours, ", 3))
        result.append(printPart(duration.minutes, " minutes, ", 2))
        result.append(String.format("%2s", duration.seconds) + " seconds")
        return result.toString()
    }

    private String printPart(BigInteger remaining, String description, int positions) {
        return remaining + description
    }

    private String printOnly(ExtraLongDuration duration) {
        if (duration.millennia > 0) { return printPart(duration.millennia, " millennia", 30) }
        if (duration.years > 0) { return printPart(duration.years, " years", 30) }
        if (duration.days > 0) { return printPart(duration.days, " days", 30) }
        if (duration.hours > 0) { return printPart(duration.hours, " hours", 30) }
        if (duration.minutes > 0) { return printPart(duration.minutes, " minutes", 30) }
        return String.format("%30s", duration.seconds + " seconds")
    }

}
