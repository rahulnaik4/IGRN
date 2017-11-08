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
import main.java.materials.MaterialFactory;
import main.java.numerals.InterGalacticNumerals;

/**
 * Processes query of the form "how many credits is x m ?" where "x" denotes a
 * number of InterGalactic numerals separated by white-spaces and "m" is the
 * name of the material.
 */
public class MaterialCostQueryProcessor implements IProcessor {
  private static final String DATA_FORMAT_REGEX =
                                                "^how many credits is (\\b.+\\b) (\\w+) \\?$";

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
    String materialName = null;
    Matcher matcher = canProcessInternal(pData);
    if (matcher.find()) {
      hasIGSymbols = true;
      igSymbols = matcher.group(1);
      materialName = matcher.group(2);
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
      MaterialFactory mtlFactory = MaterialFactory.getInstance();
      boolean materialExists = mtlFactory.isAvailable(materialName);
      if (!materialExists) {
        return result;
      }
      StringBuilder output = new StringBuilder();
      int numUnits = ignInstance.symbolListToDecimal(igSymbolList);
      Double totalCost = mtlFactory.getUnitPrice(materialName) * numUnits;
      output.append(igSymbols + " ");
      output.append(materialName);
      output.append(" is ");
      output.append(totalCost.intValue());
      output.append(" Credits");
      result = DataProcessResult.create(output.toString(), true);
    } catch (NullPointerException | IgnConversionException e) {
      // TODO: can be used in future to give user better error diagnosis
    }
    return result;
  }
}