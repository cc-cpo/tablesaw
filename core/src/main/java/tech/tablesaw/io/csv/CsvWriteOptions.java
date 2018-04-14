package tech.tablesaw.io.csv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;

public class CsvWriteOptions {

    private final Writer writer;
    private final boolean header;
    private final char separator;
    private final char quotechar;
    private final char escapechar;
    private final String lineEnd;

    CsvWriteOptions(final Builder builder) {
        this.writer = builder.writer;
        this.header = builder.header;
        this.separator = builder.separator;
        this.quotechar = builder.quoteChar;
        this.escapechar = builder.escapeChar;
        this.lineEnd = builder.lineEnd;
    }

    Writer writer() {
        return writer;
    }

    boolean header() {
        return header;
    }

    char separator() {
        return separator;
    }

    char escapeChar() {
        return escapechar;
    }

    char quoteChar() {
        return quotechar;
    }

    String lineEnd() {
        return lineEnd;
    }

    public static class Builder {

        private final Writer writer;
        private boolean header = true;
        private char separator = ',';
        private String lineEnd = "\n"; // CSVWriter.DEFAULT_LINE_END;
        private char escapeChar = '"'; // CSVWriter.DEFAULT_ESCAPE_CHARACTER;
        private char quoteChar = '\u0000'; // CSVWriter.NO_QUOTE_CHARACTER; TODO check - what's this for

        public Builder(final String fileName) throws IOException {
            final File file = Paths.get(fileName).toFile();
            this.writer = new FileWriter(file);
        }

        public Builder(final File file) throws IOException {
            this.writer = new FileWriter(file);
        }

        public Builder(final Writer writer) {
            this.writer = writer;
        }

        public Builder(final OutputStream stream) {
            this.writer = new OutputStreamWriter(stream);
        }

        public CsvWriteOptions.Builder separator(final char separator) {
            this.separator = separator;
            return this;
        }

        public CsvWriteOptions.Builder quoteChar(final char quoteChar) {
            this.quoteChar = quoteChar;
            return this;
        }

        public CsvWriteOptions.Builder escapeChar(final char escapeChar) {
            this.escapeChar = escapeChar;
            return this;
        }

        public CsvWriteOptions.Builder lineEnd(final String lineEnd) {
            this.lineEnd = lineEnd;
            return this;
        }

        public CsvWriteOptions.Builder header(final boolean header) {
            this.header = header;
            return this;
        }

        public CsvWriteOptions build() {
            return new CsvWriteOptions(this);
        }
    }
}
