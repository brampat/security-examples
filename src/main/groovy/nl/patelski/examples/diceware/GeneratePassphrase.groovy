package nl.patelski.examples.diceware

import groovy.util.logging.Slf4j

import java.security.SecureRandom

@Slf4j
class GeneratePassphrase {

    static final NUMBER_OF_WORDS = 5
    static final DICEWARE_WORDLIST = "src/main/resources/wordlist.txt"
    static final SecureRandom rand = new SecureRandom()

    static void main(String[] args) {

        String passphrase = generatePassphrase(NUMBER_OF_WORDS)
        log.info(passphrase)

    }

    static String generatePassphrase(int length) {
        (0..length-1).collect{
            getWordFromFile(
                    (0..4).collect{
                        getRandomInt().toString()
                    }.join()
            )
        }.join(" ")

    }

    static int getRandomInt() {
        return rand.nextInt(6) + 1
    }


    static String getWordFromFile(String search) {
        String line = new File(DICEWARE_WORDLIST).filterLine { line ->
            line.contains(search)
        }.collect().first()
        return line.tokenize().last()
    }





}
