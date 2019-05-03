package nl.patelski.examples.pwndpassword

/**
 * Simple Groovy script that calls the Pwned Passwords API using k-Anonymity to check a given password
 * Source of the data is https://haveibeenpwned.com/
 */
class PwndPassword {

    static String PWND_PASSWORDS_URI = "https://api.pwnedpasswords.com/range/"

    /**
     * Check the given password.
     * @param password The password to check
     * @return true if the password is a known (leaked) password in Pwned Passwords' database
     *         false if the password is not known in Pwned Passwords' database
     */
    Boolean check(String password) {
        String sha1 = password.digest('SHA-1').toUpperCase()
        return checkBySHA1(sha1)
    }

    /**
     * Check the given SHA1.
     * @param sha1 The sha1 to check
     * @return true if the sha1 is from a known (leaked) password in Pwned Passwords' database
     *         false if the sha1 is from a password not in Pwned Passwords' database
     */
    Boolean checkBySHA1(String sha1) {
        String sha1start = sha1[0..4]
        String sha1end = sha1.substring(5)
        HttpURLConnection connection = new URL( PWND_PASSWORDS_URI + sha1start ).openConnection() as HttpURLConnection
        return connection.inputStream.text.contains(sha1end)
    }

}
