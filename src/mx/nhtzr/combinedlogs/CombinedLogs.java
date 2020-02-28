package mx.nhtzr.combinedlogs;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static mx.nhtzr.combinedlogs.MergeIterator.mergeStreams;

/**
 * Expects log files to have all lines start with a timestamp similar to: 2020-02-13T21:07:35Z No
 * other timezones Expects log files to have all lines in order from earliest to latest supported
 * Output to stdout
 */
public class CombinedLogs {

  public static void main(String[] args) {
    try (
      MultiAutoClosable<Stream<String>> ctx = loadLinesOfFiles(args)
    ) {
      mergeStreams(
        getTimestampedLogLineStreams(ctx, "yyyy-MM-dd'T'HH:mm:ss'Z'", ", "),
        comparingNaturalOrderOf(timestampedLogLine -> timestampedLogLine.getTimestamp().getTime())
      ).forEachRemaining(s ->
        System.out.println(s.getLine()));
    }
  }

  private static MultiAutoClosable<Stream<String>> loadLinesOfFiles(String[] args) {
    return new MultiAutoClosable<>(
      Arrays.stream(args)
        .map(Paths::get)
        .map(path -> {
          try {
            return Files.lines(path);
          }
          catch (IOException e) {
            e.printStackTrace();
            return null;
          }
        })
        .filter(Objects::nonNull)
        .collect(Collectors.toList()));
  }

  private static Comparator<TimestampedLogLine> comparingNaturalOrderOf(
    Function<TimestampedLogLine, Long> keyExtractor
  ) {
    return Comparator.comparing(keyExtractor, Comparator.naturalOrder());
  }

  private static Stream<Stream<TimestampedLogLine>> getTimestampedLogLineStreams(
    final MultiAutoClosable<Stream<String>> ctx,
    final String datePattern,
    final String splitPattern
  ) {
    return ctx
      .getInner()
      .stream()
      .map(file -> file.map(line ->
        TimestampedLogLine.build(line, new SimpleDateFormat(datePattern), splitPattern)
      ));
  }

}
