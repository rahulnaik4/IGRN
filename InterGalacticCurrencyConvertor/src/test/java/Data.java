package test.java;

import main.java.DataProcessResult;

/**
 * Stores test data : the input and the expected output;
 */
public final class Data {
  private final String mDataLine;
  private final DataProcessResult mExpected;

  public Data(String inLine, String inResult, Boolean inSuccess) {
    mDataLine = inLine;
    mExpected = DataProcessResult.create(inResult, inSuccess);
  }

  public String getDataLine() {
    return mDataLine;
  }

  public DataProcessResult getExpected() {
    return mExpected;
  }
}
