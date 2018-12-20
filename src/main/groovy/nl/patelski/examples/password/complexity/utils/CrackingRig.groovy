package nl.patelski.examples.password.complexity.utils

import groovy.transform.AutoClone

import java.time.LocalDate

@AutoClone
class CrackingRig {

    String url
    String motherboard
    String processors
    String gpus
    String storage
    String memory
    String price
    LocalDate buildTime
    HashMap<Hash, Long> speeds

}
