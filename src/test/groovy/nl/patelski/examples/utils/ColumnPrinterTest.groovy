package nl.patelski.examples.utils

import groovy.util.logging.Slf4j
import nl.patelski.examples.pwndpassword.PwndPassword
import spock.lang.Specification
import spock.lang.Unroll

@Slf4j
class ColumnPrinterTest extends Specification {

    ColumnPrinter subject = new ColumnPrinter()

    void "List with empty string should align to List with empty string"() {
        given:
        List<String> input = [""].toList()

        when:
        def result = subject.align(input, TablePrinter.Side.LEFT)

        then:
        result[0] == ""

    }

    void "List with strings should align to List with space-padded strings"() {
        given:
        List<String> input = ["hi", "test", "this is a test"].toList()

        when:
        def result = subject.align(input, TablePrinter.Side.LEFT)

        then:
        result == ["hi            ", "test          ", "this is a test"].toList()
    }

    void "List with strings should align right to List with left space-padded strings"() {
        given:
        List<String> input = ["hi", "test", "this is a test"].toList()

        when:
        def result = subject.align(input, TablePrinter.Side.RIGHT)

        then:
        result == ["            hi", "          test", "this is a test"].toList()
    }

    void "Table of values outlined to the right"() {
        given:
        List<List<String>> input =
                [
                        ["hi", "test", "this is a test"].toList(),
                        ["", "", ""].toList(),
                        ["1", "2", "3"].toList()
                ].toList()
        List<TablePrinter.Side> sides = [TablePrinter.Side.RIGHT, TablePrinter.Side.RIGHT, TablePrinter.Side.RIGHT].toList()

        when:
        def result = subject.table(input, sides)

        then:
        result[0] == ["hi", "test", "this is a test"].toList()
        result[1] == ["  ", "    ", "              "].toList()
        result[2] == [" 1", "   2", "             3"].toList()
    }

}
