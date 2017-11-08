package main.java.statement.processor;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.DataProcessResult;
import main.java.IProcessor;
import main.java.IgnConversionException;
import main.java.ProcessorData;
import main.java.numerals.InterGalacticNumerals;

/**
 * Processes statement of the form "x is r" where "x" denotes a InterGalactic
 * numeral and "r" denotes a Roman Numeral.
 */
public class InterGalacticMappingStatementProcessor implements IProcessor {
  private static final String DATA_FORMAT_REGEX = "^(\\w+) is (\\w)$";

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
    boolean hasSymbols = false;
    String igSymbol = null;
    String cSymbol = null;
    Matcher matcher = canProcessInternal(pData);
    if (matcher.find()) {
      hasSymbols = true;
      igSymbol = matcher.group(1);
      cSymbol = matcher.group(2);
    }
    if (!hasSymbols) {
      return result;
    }
    InterGalacticNumerals ignInstance = InterGalacticNumerals.getInstance();
    try {
      ignInstance.setConvertor(cSymbol);
      if (ignInstance.addMapping(igSymbol, cSymbol)) {
        result = DataProcessResult.create(null, true);
      }
    } catch (NullPointerException | IgnConversionException e) {
      // TODO: can be used in future to give user better error diagnosis
    }
    return result;
  }
}
