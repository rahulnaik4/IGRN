package main.java;

import java.util.Objects;

/**
 * Stores error information resulting from invalid user input.
 */
public final class IgnConversionException extends Exception {
  private static final long         serialVersionUID = 4312898069081147211L;
  private final ConversionErrorCode mError;
  private final String              mAdditionalInfo;

  public IgnConversionException(ConversionErrorCode error, String additionalInfo) {
    super();
    mError = Objects.requireNonNull(error);
    mAdditionalInfo = Objects.requireNonNull(additionalInfo);
  }

  public ConversionErrorCode getError() {
    return mError;
  }

  public String getAdditionalInfo() {
    return mAdditionalInfo;
  }
}
