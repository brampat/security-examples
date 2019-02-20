package nl.patelski.examples.utils

import groovy.util.logging.Slf4j
import static nl.patelski.examples.utils.TablePrinter.Side

@Slf4j
class ColumnPrinter {

    /**
     * Align a column of values by padding extra spaces to the left or right side of the value
     * @param column The list with values for this column
     * @param side The side that the values should align to. Typically use RIGHT for numbers, LEFT for words
     * @return A list with padded values
     */
    List<String> align(List<String> column, Side side ) {
        int maxLength = column.isEmpty() ? 0 : column.max { it.toString().length() }.toString().length()
        String format = side == Side.LEFT ? "%-${maxLength}s" : "%${maxLength}s"
        return column.collect {
            maxLength > 0 ? String.format(format, "${it}") : ""
        }.asList()
    }


    /**
     * Align all columns of values by padding extra spaces to the left or right side of the value
     * @param rows All values as rows of values, each with an identical number of values
     * @param sides The sides for each column to align to. One Side for each value per row.
     * @return The table of padded values per row
     */
    List<List<String>> table(List<List<String>> rows, List<Side> sides) {
        int wrongSizeRows = 0
        rows.eachWithIndex { it, i ->
            if (it.size() != sides.size()) {
                log.error("Row $i has ${it.size()} values, while there are ${sides.size()} expected")
                wrongSizeRows++
            }
        }
        if (wrongSizeRows > 0) {
            log.error("$wrongSizeRows rows were not of expected size")
            return null
        }
        List<List<String>> columns = sides.withIndex().collect { side, index ->
            align(rows.collect {it[index]}, side)
        }

        List<List<String>> result = rows.withIndex().collect { side, index ->
            columns.collect{ it[index] }
        }
    }
}
