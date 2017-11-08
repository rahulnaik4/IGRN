package main.java;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Stores the user input that needs to be processed after removing leading,
 * trailing and any extra spaces between words.
 */
public final class ProcessorData {
  private static final String REMOVE_INBETWEEN_WHITESPACES = "\\s+";
  private final String        mData;

  public ProcessorData(String data) {
    Objects.requireNonNull(data, "Null String data provided");
    Pattern pattern = Pattern.compile(REMOVE_INBETWEEN_WHITESPACES);
    Matcher matcher = pattern.matcher(data.trim());
    mData = new String(matcher.replaceAll(" "));
  }

  public String getData() {
    return mData;
  }
}