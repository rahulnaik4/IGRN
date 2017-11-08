package main.java.statement.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.DataProcessResult;
import main.java.IProcessor;
import main.java.IgnConversionException;
import main.java.ProcessorData;
import main.java.materials.MaterialFactory;
import main.java.numerals.InterGalacticNumerals;

/**
 * Processes statement of the form "x m is d credits" where "x" denotes a number
 * of InterGalactic numerals separated by white-spaces, "m" is the material name
 * and "d" is the decimal value equivalent to "x" amount of material "m".
 */
public class MaterialCostStatementProcessor implements IProcessor {
  private static final String DATA_FORMAT_REGEX =
                                                "^(\\b.+\\b) ((\\w+) is) (0*(\\d{1,9})) credits$";

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
    boolean hasMatch = false;
    String igSymbols = null;
    String materialName = null;
    double totalCost = 0.0;
    Matcher matcher = canProcessInternal(pData);
    if (matcher.find()) {
      hasMatch = true;
      igSymbols = matcher.group(1);
      materialName = matcher.group(3);
      totalCost = Double.valueOf(matcher.group(5));
    }
    if (!hasMatch) {
      return result;
    }
    String[] tokens = igSymbols.split(" ");
    List<String> igSymbolList = new ArrayList<>();
    for (String igSymbol : tokens) {
      igSymbolList.add(igSymbol);
    }
    InterGalacticNumerals ignInstance = InterGalacticNumerals.getInstance();
    try {
      Integer numUnits = ignInstance.symbolListToDecimal(igSymbolList);
      MaterialFactory.getInstance().add(materialName, numUnits, totalCost);
      result = DataProcessResult.create(null, true);
    } catch (NullPointerException | IgnConversionException e) {
      // TODO: can be used in future to give user better error diagnosis
    }
    return result;
  }
}