package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import main.java.query.processor.InterGalacticToDecimalQueryProcessor;
import main.java.query.processor.MaterialCostQueryProcessor;
import main.java.statement.processor.InterGalacticMappingStatementProcessor;
import main.java.statement.processor.MaterialCostStatementProcessor;

/**
 * Processes user input based on its types: Statement or Query.
 */
public class DataProcessor {
  private final static DataProcessor    mInstance = new DataProcessor();
  private final static List<IProcessor> mProcessorList;

  static {
    // This needs to be updated when a new IProcessor implementation is added
    mProcessorList = new ArrayList<>();
    mProcessorList.add(new InterGalacticToDecimalQueryProcessor());
    mProcessorList.add(new MaterialCostQueryProcessor());
    mProcessorList.add(new InterGalacticMappingStatementProcessor());
    mProcessorList.add(new MaterialCostStatementProcessor());
  }

  private DataProcessor() {
  }

  public static DataProcessor getInstance() {
    return mInstance;
  }

  private DataProcessResult process(ProcessorData processorData) {
    DataProcessResult result = DataProcessResult.EMPTY_RESULT;
    for (IProcessor processor : mProcessorList) {
      if (processor.canProcess(processorData)) {
        result = processor.process(processorData);
        if (result.isSuccess()) {
          break;
        }
      }
    }
    return result;
  }

  void userHelpGuide() {
    System.out.println("\tThis program converts from InterGalactic to Decimal via\n");
    System.out.println("\tintermediate user IG conversion mapping\n");
    System.out.println("\tCurrtently Supports 4 types of user input\n");
    System.out.println("\t- InterGalactic to intermediate user mapping (eg. Roman)\n");
    System.out.println("\teg. glob is I\n");
    System.out.println("\t- Material cost mapping\n");
    System.out.println("\teg. glob Silver is 10 Credits\n");
    System.out.println("\t- InterGalactic to decimal query\n");
    System.out.println("\teg. how much is glob glob ?\n");
    System.out.println("\t- Material cost query\n");
    System.out.println("\teg. how many Credits is glob glob Silver ?\n");
    System.out.println("\tTo exit interactive mode use \"exit\"\n");
    System.out.println("\tTo get help on usage use \"help\"\n");
    System.out.println("\tInvalid user input will show the following message\n");
    System.out.println("\t- I have no idea what you are talking about\n");
  }

  public DataProcessResult processData(String data) {
    Objects.requireNonNull(data, "Invalid Data input");
    DataProcessResult result = DataProcessResult.EMPTY_RESULT;
    if (data.trim().equalsIgnoreCase("exit")) {
      return DataProcessResult.create("exit", true);
    } else if (data.trim().toLowerCase().equals("help")) {
      userHelpGuide();
      return DataProcessResult.create("help", true);
    } else if (data.trim().length() == 0) {
      return result;
    } else {
      ProcessorData processorData = new ProcessorData(data);
      result = process(processorData);
    }
    return result;
  }
}