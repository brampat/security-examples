package nl.patelski.examples.pwndpassword

import groovy.json.JsonSlurper
import nl.patelski.examples.utils.ColumnPrinter

import java.text.NumberFormat
import java.time.LocalDate

class HibpData {

    static int WHEN = 0
    static int WHO = 1
    static int HOW_MANY = 2
    static int WHAT = 3
    static int SOURCE = 4

    static ColumnPrinter columnPrinter = new ColumnPrinter()

    static void main(String[] args) {

        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        File inputFile = new File(classLoader.getResource("breaches_in_hibp.json").getFile());

        def inputJSON = new JsonSlurper().parseText(inputFile.text)

        List<Breach> result = inputJSON.collect { it ->
            new Breach(it)
        }
        println(result.size())

        List<LocalDate> when = result.collect{it.breachDate}
        List<String> lines = new ArrayList<>()
        List<String> who = columnPrinter.align(result.collect{it.domain}.asList(), ColumnPrinter.Side.LEFT)
        List<String> howMany = columnPrinter.align(result.collect{"${NumberFormat.getNumberInstance(Locale.UK).format(it.pwnCount)}"}.asList(), ColumnPrinter.Side.RIGHT)
        List<String> what = result.collect{"${it.description}"}.asList()
        List<String> source = result.collect{"${it.name}"}.asList()

        // TODO: Use TablePrinter
        (0..(when.size()-1)).each {
            lines.add("| ${when.get(it)} | ${who.get(it)} | ${howMany.get(it)} | ${what.get(it)} | source: [HIBP](https://haveibeenpwned.com/api/v2/breach/${source.get(it)}) |")
        }
        lines.sort().reverse().each {
            println(it)
        }
    }

}
