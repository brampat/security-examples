package nl.patelski.examples.pwndpassword

/**
 * Simple Groovy script that calls the Pwned Passwords API using k-Anonymity to check a given password
 */
class PwndPassword {

    static String PWND_PASSWORDS_URI = "https://api.pwnedpasswords.com/range/"

    /**
     * Check the given password.
     * @param password The password to check
     * @return true if the password is a known (leaked) password in Pwned Passwords' database
     *         flase if the password is not known in Pwned Passwords' database
     */
    Boolean check(String password) {
        String sha1 = password.digest('SHA-1').toUpperCase()
        println(sha1)
        String sha1start = sha1[0..4]
        String sha1end = sha1.substring(5)
        String result = doRequest(PWND_PASSWORDS_URI + sha1start)
        return result.contains(sha1end)
    }

    private String doRequest(String url) {
        HttpURLConnection connection = new URL( url ).openConnection() as HttpURLConnection
        connection.setRequestProperty( 'User-Agent', 'groovy-pwndpasswords-example' )
        return connection.inputStream.text
    }

}