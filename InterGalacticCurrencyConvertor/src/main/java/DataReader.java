package main.java;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

/**
 * Reads and processes lines of user input that can either be statements or
 * queries.
 */
public final class DataReader {
  private final DataProcessor mDataProcessor;

  public DataReader() {
    mDataProcessor = DataProcessor.getInstance();
  }

  private void printResult(DataProcessResult result) {
    Objects.requireNonNull(result, "Invalid Result Argument");
    if (result.getProcessorResult() != null) {
      System.out.println(result.getProcessorResult());
    }
  }

  public DataProcessResult processData(String line) {
    return mDataProcessor.processData(line);
  }

  private void read(InputStreamReader streamReader) throws IOException {
    DataProcessResult result = null;
    try (BufferedReader br = new BufferedReader(streamReader)) {
      String line = null;
      while ((line = br.readLine()) != null) {
        result = processData(line);
        if (result != null && result.getProcessorResult() != null
            && result.getProcessorResult().equals("exit")) {
          break;
        } else if (result != null && result.getProcessorResult() != null
                   && result.getProcessorResult().equals("help")) {
          continue;
        }
        printResult(result);
      }
    }
  }

  public void readFromFile(String filename) {
    Objects.requireNonNull(filename, "No filename provided");
    try (FileReader freader = new FileReader(filename);) {
      read(freader);
    } catch (IOException e) {
      System.out.println("Error: while reading data from file " + filename);
    }
  }

  public void readFromStandardInput() {
    InputStreamReader streamReader = new InputStreamReader(System.in);
    try {
      mDataProcessor.userHelpGuide();
      read(streamReader);
    } catch (Exception e) {
      System.out.println("Error: while reading data from Standard Input");
    }
  }

  public static void main(String[] args) {
    DataReader reader = new DataReader();
    reader.readFromStandardInput();
  }
}