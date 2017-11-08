package main.java.query.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.DataProcessResult;
import main.java.IProcessor;
import main.java.IgnConversionException;
import main.java.ProcessorData;
import main.java.numerals.InterGalacticNumerals;

/**
 * Processes query of the form "how much is x ?" where "x" denotes a number of
 * InterGalactic numerals separated by white-spaces.
 */
public class InterGalacticToDecimalQueryProcessor implements IProcessor {
  private static final String DATA_FORMAT_REGEX = "^how much is (\\b.+\\b) \\?$";

  private Matcher canProcessInternal(ProcessorData pData) {
    Objects.requireNonNull(pData, "Null processor data provided");
    Pattern pattern = Pattern.compile(DATA_FORMAT_REGEX, Pattern.CASE_INSENSITIVE);
    return pattern.matcher(pData.getData());
  }

  @Override
  public boolean canProcess(ProcessorData pData) {
    return canProcessInternal(pData).find();
  }

  @Override
  public DataProcessResult process(ProcessorData pData) {
    DataProcessResult result = DataProcessResult.EMPTY_RESULT;
    boolean hasIGSymbols = false;
    String igSymbols = null;
    Matcher matcher = canProcessInternal(pData);
    if (matcher.find()) {
      hasIGSymbols = true;
      igSymbols = matcher.group(1);
    }
    if (!hasIGSymbols) {
      return result;
    }
    String[] tokens = igSymbols.split(" ");
    List<String> igSymbolList = new ArrayList<>();
    for (String igSymbol : tokens) {
      igSymbolList.add(igSymbol);
    }
    InterGalacticNumerals ignInstance = InterGalacticNumerals.getInstance();
    try {
      StringBuilder output = new StringBuilder();
      int decimalVal = ignInstance.symbolListToDecimal(igSymbolList);
      output.append(igSymbols);
      output.append(" is ");
      output.append(decimalVal);
      result = DataProcessResult.create(output.toString(), true);
    } catch (NullPointerException | IgnConversionException e) {
      // TODO: can be used in future to give user better error diagnosis
    }
    return result;
  }
}
