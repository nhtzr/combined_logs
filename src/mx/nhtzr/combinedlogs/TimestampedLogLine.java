package mx.nhtzr.combinedlogs;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class TimestampedLogLine implements Comparable<TimestampedLogLine> {

  private Date timestamp;
  private String line;

  @Override
  public int compareTo(TimestampedLogLine o) {
    return this.timestamp.compareTo(o.getTimestamp());
  }

  public TimestampedLogLine(String line, Date timestamp) {
    this.timestamp = timestamp;
    this.line = line;
  }

  public static TimestampedLogLine build(String line, DateFormat format, String splitRegex) {
    try {
      final String source = extractTimestampSource(line, splitRegex);
      final Date parsed = format.parse(source);
      return new TimestampedLogLine(line, parsed);
    }
    catch (ParseException e) {
      throw new RuntimeException("No handling for non-timestamped lines", e);
    }
  }

  public static String extractTimestampSource(String line, String splitRegex) {
    final String[] split = line.split(splitRegex);
    if (split.length < 2) {
      throw new RuntimeException("log line has no initial timestamp");
    }
    return split[0];
  }

  public Date getTimestamp() {
    return timestamp;
  }

  public String getLine() {
    return line;
  }
}
