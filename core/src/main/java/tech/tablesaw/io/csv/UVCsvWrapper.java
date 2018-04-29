package tech.tablesaw.io.csv;

import com.univocity.parsers.common.IterableResult;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.ResultIterator;

public class UVCsvWrapper implements CsvWrapper {

    private ResultIterator<String[], ParsingContext> iterator;

    public UVCsvWrapper(IterableResult<String[], ParsingContext> result) {
        this.iterator = result.iterator();
    }

    @Override
    public String[] readNext() {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

}
