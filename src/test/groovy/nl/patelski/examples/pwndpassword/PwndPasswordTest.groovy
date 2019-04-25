package nl.patelski.examples.pwndpassword

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class PwndPasswordTest extends Specification {

    PwndPassword subject = new PwndPassword()

    @Unroll
    void "Pwned Password API says the password #password is in Pwned Passwords #expected"() {
        given:
        password

        when:
        def result = subject.check(password)

        then:
        result == expected

        where:
        password                            ||  expected
        "Password"                          ||  true
        "qwerty"                            ||  true
        "123456"                            ||  true
        "P@ssw0rd"                          ||  true
        "iwhbgf34897tsIndad;irunp938u5u"    ||  false

    }

}
