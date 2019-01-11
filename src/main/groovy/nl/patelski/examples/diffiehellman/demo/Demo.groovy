package nl.patelski.examples.diffiehellman.demo

import groovy.util.logging.Slf4j
import nl.patelski.examples.diffiehellman.DiffieHellmanCalculator

@Slf4j
class Demo {


    static void main(String[] args) {

        DiffieHellmanCalculator calculator = new DiffieHellmanCalculator()

        BigInteger p = BigInteger.valueOf(13)
        BigInteger g = BigInteger.valueOf(6)
//        BigInteger p = new BigInteger(System.console().readLine("All, choose a prime-number: "))
//        BigInteger g = new BigInteger(System.console().readLine("All, choose another prime-number: "))

        log.info("Using p=${p} and g=${g}")
        BigInteger a = new BigInteger(System.console().readLine("Alice, choose a number a: "))
        BigInteger A = calculator.calcA(a, p, g)
        log.info("Awesome. A = ${g}^a mod ${p} = ${A}")
        BigInteger b = new BigInteger(System.console().readLine("Bob, choose a number b: "))
        BigInteger B = calculator.calcB(b, p, g)
        log.info("Awesome. B = ${g}^b mod ${p} = ${calculator.calcB(b, p, g)}")

        log.info("=================")
        log.info("Both A and B can be public, so both Alice and Bob can calculate a shared secret using their (secret) a and b respectively")
        log.info("Alice calculates sA = ${B}^a mod ${p} = ${calculator.calcSusingaB(a, B, p)}")
        log.info("Bob   calculates sB = ${A}^b mod ${p} = ${calculator.calcSusingAb(A, b, p)}")

    }

}
