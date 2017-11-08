package main.java;

import java.util.Objects;

/**
 * Stores the processed result of a Statement or Query.
 *
 * @param mOuput stores the processor result
 * @param mSuccess stores operation success status
 */
public final class DataProcessResult {
  private final String                  mOutput;
  private final boolean                 mSuccess;
  public final static DataProcessResult EMPTY_RESULT;

  static {
    EMPTY_RESULT = new DataProcessResult("I have no idea what you are talking about",
                                         false);
  }

  private DataProcessResult(String processorResult, boolean processorStatus) {
    mOutput = processorResult;
    mSuccess = processorStatus;
  }

  public static DataProcessResult create(String processorResult,
                                         boolean processorSuccStatus) {
    return new DataProcessResult(processorResult, processorSuccStatus);
  }

  public String getProcessorResult() {
    return mOutput;
  }

  public boolean isSuccess() {
    return mSuccess;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    } else if (other instanceof DataProcessResult) {
      DataProcessResult otherObj = (DataProcessResult) other;
      return Objects.equals(mOutput, otherObj.mOutput)
             && Objects.equals(mSuccess, otherObj.mSuccess);
    }
    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(mOutput, mSuccess);
  }
}
