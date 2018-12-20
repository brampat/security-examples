package nl.patelski.examples.password.complexity.utils

import groovy.util.logging.Slf4j

import java.time.LocalDate

@Slf4j
class ExampleCrackingRigs {

    static Map<String, CrackingRig> rigs = [
            "4xGTX1070": new CrackingRig(
                    url: "https://www.netmux.com/blog/how-to-build-a-password-cracking-rig",
                    speeds: [
                            MD5: 76526900000,
                            SHA1: 25963300000,
                            SHA256: 9392100000,
                            SHA512: 3235000000,
                            SCRYPT: 1872400,
                            BCRYPT: 43551
                    ],
                    motherboard: "1 x SuperMicro SYS-7048GR-TR 4U Server with X10DRG-Q Motherboard",
                    processors: "2 x Intel Xeon E5-2620 v3 2.4 GHz LGA 2011-3 85W",
                    gpus: "4 x Nvidia GTX 1070 Founders Edition",
                    storage: "2 x Samsung 850 Pro 512GB SATA3 SSD",
                    memory: "4 x Kingston Server ValueRAM DDR4 2133MHz 16GB",
                    price: "5001.31",
                    buildTime: LocalDate.of(2018, 1, 6)
            ),
            "25xAMD Radeons": new CrackingRig(
                    url: "http://passwords12.at.ifi.uio.no/Jeremi_Gosney_Password_Cracking_HPC_Passwords12.pdf",
                    speeds: [
                            MD5: 1,
                            SHA1: 1,
                            SHA256: 1,
                            SHA512: 1,
                            SCRYPT: 1,
                            BCRYPT: 1
                    ],
                    motherboard: "",
                    processors: "",
                    gpus: "",
                    storage: "",
                    memory: "",
                    price: "",
                    buildTime: LocalDate.of(1, 1, 6)
            ),
            "Brutalis": new CrackingRig(
                    url: "https://gist.github.com/epixoip/ace60d09981be09544fdd35005051505",
                    speeds: [
                            MD5:   549700000000,
                            SHA1:  101300000000,
                            SHA256: 39269400000,
                            SHA512: 13043300000,
                            SCRYPT:     6353500,
                            BCRYPT:      184800
                    ],
                    motherboard: "",
                    processors: "",
                    gpus: "8x Nvidia GTX 1080",
                    storage: "",
                    memory: "",
                    price: "",
                    buildTime: LocalDate.of(1, 1, 6)
            )
    ]

    static {
        rigs.put("Brutalis40x", multiplyCrackingRig(40, "Brutalis"))
    }


    static multiplyCrackingRig(int multiplier, String baseRig) {
        CrackingRig result = rigs[baseRig].clone()
        result.speeds.each { entry ->
            entry.value *= multiplier
        }
        return result
    }

}
