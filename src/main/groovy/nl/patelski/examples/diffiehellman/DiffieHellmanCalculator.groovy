package nl.patelski.examples.diffiehellman

class DiffieHellmanCalculator {

    BigInteger calcA(BigInteger a, BigInteger p, BigInteger g) {
        return calcX(a, p, g)
    }

    BigInteger calcB(BigInteger b, BigInteger p, BigInteger g) {
        return calcX(b, p, g)
    }

    BigInteger calcX(BigInteger x, BigInteger p, BigInteger g) {
        return g.power(x).divideAndRemainder(p)[1]
    }

    BigInteger calcSusingAb(BigInteger A, BigInteger b, BigInteger p) {
        return A.power(b).divideAndRemainder(p)[1]
    }

    BigInteger calcSusingaB(BigInteger a, BigInteger B, BigInteger p) {
        return B.power(a).divideAndRemainder(p)[1]
    }



}
