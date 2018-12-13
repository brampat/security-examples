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
    static ExtraLongDuration duration = new ExtraLongDuration()

    static void main(String[] args) {
        log.info("Cracking speed on a \$5000,- rig of passwords encrypted with ${ENCRYPTION_USED.name()}")
        log.info("|${String.format("%-30s", " Description")}|${String.format("%-8s", " Length")}|${String.format("%-12s", " Characters")}|${String.format("%-50s", " Password space")}| ${String.format("%-100s", " Time to crack")}")
        log.info("|${"*"*30}|${"*"*8}|${"*"*12}|${"*"*50}|${"*"*100}|")
        log.info(outputFor(" 10x Number-slot", 3, 10))
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

    static String outputFor(String description, int positions, int options) {
        BigInteger result = calculator.passwordSpace(positions, options)
        return "|${String.format("%-30s", description)}|${String.format("%8s", positions)}|${String.format("%12s", options)}|${String.format("%50s", result)}|${String.format("%100s", duration.print(result.divide(ENCRYPTION_USED.speed)))}|"
    }

    static String outputFor(String description, String pattern) {
        BigInteger result = calculator.passwordSpace(pattern)
        return "|${String.format("%-30s", description)}|${String.format("%8s", pattern.size())}|${String.format("%12s", "")}|${String.format("%50s", result)}|${String.format("%100s", duration.print(result.divide(ENCRYPTION_USED.speed)))}|"
    }

    BigInteger passwordSpace(int positions, int options) {
        return options.power(positions)
    }

    BigInteger passwordSpace(List<MaskSymbol> pattern) {
        pattern*.options.inject(1, multiply)
    }

    BigInteger passwordSpace(String pattern) {
        return passwordSpace(pattern.collect{ MaskSymbol.getEnum(it) })
    }

    def multiply = { BigInteger result, long i ->
        result*(i)
    }

}
