package nl.patelski.examples.diffiehellman

import groovy.util.logging.Slf4j
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class DiffieHellmanCalculatorTest extends Specification {

    DiffieHellmanCalculator calculator = new DiffieHellmanCalculator()

    @Unroll
    void "calcA should calculate a correct A #expected based on a #a, p #p and g #g"() {
        given:
        a
        p
        g

        when:
        def A = calculator.calcA(a, p, g)

        then:
        A == expected

        where:
        a   |   p   |   g   ||  expected
        4   |   23  |   5   ||  4
        3   |   23  |   7   ||  21
        6   |   23  |   11  ||  9
        7   |   17  |   5   ||  10
        5   |   13  |   6   ||  2
        97  |   353 |   3   ||  40
    }

    @Unroll
    void "calcB should calculate a correct B #expected based on b #b, p #p and g #g"() {
        given:
        b
        p
        g

        when:
        def B = calculator.calcA(b, p, g)

        then:
        B == expected

        where:
        b   |   p   |   g   ||  expected
        3   |   23  |   5   ||  10
        6   |   23  |   7   ||  4
        5   |   23  |   11  ||  5
        5   |   17  |   5   ||  14
        4   |   13  |   6   ||  9
        233 |   353 |   3   ||  248
    }

    @Unroll
    void "calcSusingAb should calculate a correct s #expected based on A #A, b #b and p #p"() {
        given:
        A
        b
        p

        when:
        def s = calculator.calcSusingAb(A, b, p)

        then:
        s == expected

        where:
        A   |   b   |   p   ||  expected
        4   |   3   |   23  ||  18
        21  |   6   |   23  ||  18
        9   |   5   |   23  ||  8
        10  |   5   |   17  ||  6
        2   |   4   |   13  ||  3
        40  |   233 |   353 ||  160
    }

    @Unroll
    void "calcSusingaB should calculate a correct s #expected based on a #a, B #B and p #p"() {
        given:
        a
        B
        p

        when:
        def s = calculator.calcSusingaB(a, B, p)
        log.info("Test info message")

        then:
        s == expected

        where:
        a   |   B   |   p   ||  expected
        4   |   10  |   23  ||  18
        3   |   4   |   23  ||  18
        6   |   5   |   23  ||  8
        7   |   14  |   17  ||  6
        5   |   9   |   13  ||  3
        97  |   248 |   353 ||  160
    }

}
