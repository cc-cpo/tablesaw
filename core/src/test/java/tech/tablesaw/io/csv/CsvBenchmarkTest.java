package tech.tablesaw.io.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.google.common.primitives.Bytes;

import tech.tablesaw.api.Table;


public class CsvBenchmarkTest {

    @Test
    public void testName() throws Exception {


        final long start = System.nanoTime();
        // Read the CSV file
        final Table table = Table.read().csv(CsvReadOptions
                .builder("/Users/stefan/workspace_hacking/tablesaw/data/cities-states-zipcode.csv").sample(false));


        System.out.println("nano=" + (System.nanoTime() - start) / 1000 / 1000);

    }

    @Test
    public void testWithBusData() throws Exception {

        final byte[] singleBlock = Files.readAllBytes(Paths.get("/Users/stefan/workspace_hacking/tablesawcc/data/boundaryTest1.csv"));
//        final byte[] singleBlock = Files.readAllBytes(Paths.get("/Users/stefan/workspace_hacking/tablesawcc/data/bus_stop_test.csv"));
        final byte[] doubleBlock = Bytes.concat(singleBlock, singleBlock);

        final int repeat = 5;

        final Reader reader = new Reader() {

            @Override
            public int read(final char[] cbuf, final int off, final int len) throws IOException {

                for(int i=off; i<off+len; i++) {

                    if (i >= repeat * singleBlock.length) {
                       break;
                    }

                    final byte b = singleBlock[i % singleBlock.length];

                    cbuf[i - off] = (char) b;
                }

                return 0;
            }

            @Override
            public void close() throws IOException {
            }
        };

    }
}
