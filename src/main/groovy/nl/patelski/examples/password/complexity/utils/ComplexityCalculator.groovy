package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

@Slf4j
class ComplexityCalculator {

    // Hash-speeds from https://www.netmux.com/blog/how-to-build-a-password-cracking-rig
    enum Encoding {
        MD5(    76526900000),
        SHA1(   25963300000),
        SHA256(  9392100000),
        SHA512(  3235000000),
        SCRYPT(     1872400),
        BCRYPT(       43551)

        long speed
        Encoding(long speed) {
            this.speed = speed
        }
    }

    static Encoding ENCRYPTION_USED = Encoding.SHA1
    static ComplexityCalculator calculator = new ComplexityCalculator()
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
        log.info("Cracking speed on a \$5000,- rig of passwords encrypted with ${ENCRYPTION_USED.name()}")

        List<Password> passwords = [
            new Password(" 3x Number-slot", 3, 10),
            new Password(" Reverse Number-slot", 10, 3),
            new Password(" 10x Lowercase", 10, 26),
            new Password(" Pattern like 'London18'", "ullllldd"),
            new Password(" 4x English words", "EEEE"),
            new Password(" 4x English Dictionary", "DDDD"),
            new Password(" 10x All alphanumeric", 10, 26+26+10),
            new Password(" 26x numerical", 26, 10),
            new Password(" 10x ASCII", 10, 128),
            new Password(" 8x UTF8 (smilies ;-)", 8, 137000),
            new Password(" 42x numerical", 42, 10),
            new Password(" 30x alphanumeric or symbol", 's'*30),
            new Password(" 30x UTF8", 30, 137000)
        ]

        log.info("|        |" + passwords.collect{
            String.format("%-30s", it.description)
        }.join("|"))

        Encoding.values().collect { encoding ->
            return "|${String.format("%-8s", " ${encoding.name()}")}|" +
            passwords.collect {
                duration(it.passwordSpace(), encoding)
            }.join("|")
        }.each {
            log.info(it)
        }
    }

    private void printExamples(){
        log.info("Cracking speed on a \$5000,- rig of passwords encrypted with ${ENCRYPTION_USED.name()}")
        log.info("|${String.format(CH[0], " Description")}|${String.format(CH[1], " Length")}|${String.format(CH[2], " Characters")}|${String.format(CH[3], " Password space")}| ${String.format(CH[4], " Time to crack")}")
        log.info("|${"*"*C[0]}|${"*"*C[1]}|${"*"*12}|${"*"*50}|${"*"*100}|")
        log.info(outputFor(" 3x Number-slot", 3, 10))
        log.info(outputFor(" Reverse Number-slot", 10, 3))
        log.info(outputFor(" 10x Lowercase", 10, 26))
        log.info(outputFor(" Pattern like 'London18'", "ullllldd"))
        log.info(outputFor(" 4x English words", "EEEE"))
        log.info(outputFor(" 4x English Dictionary", "DDDD"))
        log.info(outputFor(" 10x All alphanumeric", 10, 26+26+10))
        log.info(outputFor(" 26x numerical", 26, 10))
        log.info(outputFor(" 10x ASCII", 10, 128))
        log.info(outputFor(" 8x UTF8 (smilies ;-)", 8, 137000))
        log.info(outputFor(" 42x numerical", 42, 10))
        log.info(outputFor(" 30x alphanumeric or symbol", 's'*30))
        log.info(outputFor(" 30x UTF8", 30, 137000))
    }

    String outputFor(Password password) {
        if (password.pattern) {
            return outputFor(password.description, password.pattern)
        } else {
            return outputFor(password.description, password.length, password.options)
        }
    }

    String duration(BigInteger space, Encoding encoding) {
        BigInteger durationInSeconds = space.divide(encoding.speed)
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return printOnly(duration)
    }

    String outputFor(String description, int positions, int options) {
        Password password = new Password(description, positions, options)
        BigInteger result = password.passwordSpace()
        BigInteger durationInSeconds = result.divide(ENCRYPTION_USED.speed)
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return "|${String.format(CF[0], description)}|${String.format(CF[1], positions)}|${String.format(CF[2], options)}|${String.format(CF[3], result)}|${String.format(CF[4], printAll(duration))}|"
    }

    String outputFor(String description, String pattern) {
        Password password = new Password(description, pattern)
        BigInteger result = password.passwordSpace()
        BigInteger durationInSeconds = result.divide(ENCRYPTION_USED.speed)
        ExtraLongDuration duration = new ExtraLongDuration(durationInSeconds)
        return "|${String.format(CF[0], description)}|${String.format(CF[1], pattern.size())}|${String.format(CF[2], "")}|${String.format(CF[3], result)}|${String.format(CF[4], printAll(duration))}|"
    }

    def multiply = { BigInteger result, long i ->
        result*(i)
    }

    private printAll(ExtraLongDuration duration) {
        StringBuffer result = new StringBuffer()
        result.append(printPart(duration.millennia, " millennia, ", 30))
        result.append(printPart(duration.years, " millennia, ", 30))
        result.append(printPart(duration.days, " years, ", 4))
        result.append(printPart(duration.hours, " days, ", 3))
        result.append(printPart(duration.minutes, " hours, ", 2))
        result.append(String.format("%2s", duration.seconds) + " seconds")
        return result.toString()
    }

    private String printPart(BigInteger remaining, String description, int positions) {
        return String.format("%${positions}s", remaining + description)
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
