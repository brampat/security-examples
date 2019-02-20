package nl.patelski.examples.pwndpassword

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Breach {

    String name
    String title
    String domain
    LocalDate breachDate
    LocalDateTime addedDate
    LocalDateTime modifiedDate
    long pwnCount
    String description
    String logoPath
    List<String> dataClasses
    boolean isVerified
    boolean isFabricated
    boolean isSensitive
    boolean isRetired
    boolean isSpamList

    Breach() {

    }

    Breach(Map map) {
        map?.each { k, v ->
            if ("breachDate" == k || "BreachDate" == k) {
                k = k[0].toLowerCase() + k.substring(1)
                this[k] = LocalDate.parse(v, "yyyy-MM-dd")
            } else if ("addedDate" == k || "AddedDate" == k ||
                    "modifiedDate" == k || "ModifiedDate" == k) {
                k = k[0].toLowerCase() + k.substring(1)
                this[k] = LocalDateTime.parse(v, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")) // 2017-12-10T21:44:27Z
            } else {
                k = k[0].toLowerCase() + k.substring(1)
                this[k] = v
            }
        }
        name = name.toUpperCase()
    }
}
