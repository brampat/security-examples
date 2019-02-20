package nl.patelski.examples.utils

import groovy.util.logging.Slf4j

@Slf4j
/**
 * Prints a table of elements using a separator and aligning each column correctly by adding extra spaces to make each element of a column the same width
 */
class TablePrinter {

    ColumnPrinter columnPrinter = new ColumnPrinter()

    enum Side {
        LEFT, RIGHT
    }

    /**
     * Prints a table given a list of columns
     * @param table The list of rows. Each row should have exactly the same number of elements
     * @return A string representing the table, using '\n' for end of row
     */
    String print(List<List<String>> table) {
        List<Side> sides = []
        sides.addAll(Collections.nCopies(table[0].size(), ColumnPrinter.Side.LEFT))
        print(table, sides)
    }

    /**
     * Prints a table given a list of columns
     * @param table The list of rows. Each row should have exactly the same number of elements
     * @param alignment The alignment for each column. Should have exactly the same number of elements as rows.
     * @return A string representing the table, using '\n' for end of row
     */
    String print(List<List<String>> table, List<Side> alignment) {
        List<List<String>> alignedTable = columnPrinter.table(table, alignment)
        print(alignedTable, ' | ')
    }

    /**
     * Prints a table given a list of columns
     * @param table The list of rows. Each row should have exactly the same number of elements
     * @param separator String used to join column-cells
     * @return A string representing the table, using '\n' for end of row
     */
    String print(List<List<String>> table, String separator) {
        table.collect { row ->
            "| ${row.join(separator)} |"
        }.join('\n')
    }

}
