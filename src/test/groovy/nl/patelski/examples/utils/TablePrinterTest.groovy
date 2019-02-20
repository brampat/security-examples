package nl.patelski.examples.utils

import groovy.util.logging.Slf4j
import spock.lang.Specification

@Slf4j
class TablePrinterTest extends Specification {

    TablePrinter subject = new TablePrinter()

    void "Matrix containing single empty string should result in pipe-delimited empty table"() {
        given:
        List<List<String>> input = [[""].toList()].toList()

        when:
        def result = subject.print(input, [TablePrinter.Side.LEFT])

        then:
        result == "|  |"

    }

    void "Matrix with strings should result in correctly aligned table of columns"() {
        given:
        List<List<String>> input = [
            ["", "", ""].toList(),
            ["1", "2", "3"].toList(),
            ["test", "demo", "sample string"].toList()
        ].toList()

        when:
        def result = subject.print(input, [TablePrinter.Side.LEFT, TablePrinter.Side.RIGHT, TablePrinter.Side.LEFT])

        then:
        result ==
'''|      |      |               |
| 1    |    2 | 3             |
| test | demo | sample string |'''
    }

}
