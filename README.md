# Security examples

This project is a collection of code that illustrates several security and privacy related concepts.


## Password complexity calculator

This application calculates several aspects of passwords. The application's main method ```ComplexityCalculator``` lists a number of examples and calculates:
* The password space, the number of different (completely random) options with this password's characteristics
* The time to crack with an [example](https://www.netmux.com/blog/how-to-build-a-password-cracking-rig) PC

The time to crack uses no optimizations like:
* checking for common / leaked passwords first
* checking for common patterns first (like London18 and London18!)
* using wordlists / dictionary attacks
So the timings are based on fully random passwords as generate by password-generators

It's input can be:
* length and options
  * length indicates the password length
  * options indicates the number of options for each position
* mask
  * a mask for the password, eg:
    * "dddd" for a 4 length numerical (decimal) password
    * "llllllllll" for a 10 character length lowercase only password (no spaces)
    * "ullllldd" for an 8 character length password like "London18"

The pattern can be customized using the enum MaskSymbol, which contains the following options:
* d: Decimal number
* l: Lowercase character
* u: Uppercase character, which has the same number of options as lowercase.
* A: Alphanumeric character, lowercase, uppercase or numeric
* a: Alphanumeric or symbol
* I: ASCII character, 128 options
* 8: UTF8 character, 137k options since [june 2018](http://www.babelstone.co.uk/Unicode/HowMany.html)
* E: English word known by an average English person, 35k options "Most adult native test-takers range from 20,000–35,000 words. " - [source](https://www.economist.com/johnson/2013/05/29/lexical-facts)
* D: English Dictionary word, 171k "The [] English Dictionary [] contains [] 171,476 words in current use" - [source](https://en.oxforddictionaries.com/explore/how-many-words-are-there-in-the-english-language/)
* c: USA Cities, about 27k different options
* C: World Cities, estimated at 2M options. Note that this number is not corrected for duplicate names. [source](https://www.quora.com/How-many-towns-and-cities-are-there-in-the-world)

## Diffie Hellman Demo

This application takes user input for Alice and Bob and calculates Diffie Hellman secrets for both paths.
Based on Alice and Bob's secrets (a and b), the application calculates A and B which both Alice and Bob
can use to calculate a shared S when combined with their respective secret a and b.

The base-values for p and g in the formula have been set to 23 and 5 respectively.
Given a prime for p you could set g to it's primitive root module g using the table [here](https://en.wikipedia.org/wiki/Primitive_root_modulo_n)
 
To run it:
* Build using ```./gradlew uberjar```
* Run jar using ```java -jar ./build/libs/security-examples.jar```

