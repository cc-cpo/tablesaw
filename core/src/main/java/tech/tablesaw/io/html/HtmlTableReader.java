package tech.tablesaw.io.html;

import java.io.IOException;
import java.io.StringWriter;
import java.util.stream.Stream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.univocity.parsers.csv.CsvWriter;
import com.univocity.parsers.csv.CsvWriterSettings;

public class HtmlTableReader {

  public String tableToCsv(String url) throws IOException {
    Document doc = Jsoup.connect(url).get();
    Elements tables = doc.select("table");
    if (tables.size() != 1) {
      throw new IllegalStateException(
          "Reading html to table currently works if there is exactly 1 html table on the page. "
          + " The URL you passed has " + tables.size()
          + ". You may file a feature request with the URL if you'd like your pagae to be supported");
    }
    Element table = tables.get(0);
    
    try (StringWriter stringWriter = new StringWriter()) {
        CsvWriterSettings settings = new CsvWriterSettings();
        CsvWriter csvWriter = new CsvWriter(stringWriter, settings);
      for (Element row : table.select("tr")) {
        Elements headerCells = row.getElementsByTag("th");
        Elements cells = row.getElementsByTag("td");
        String[] nextLine = Stream.concat(headerCells.stream(), cells.stream())
            .map(cell -> cell.text()).toArray(size -> new String[size]);
        csvWriter.writeRow(nextLine);
      }      
      return stringWriter.toString();
    }
  }

}
