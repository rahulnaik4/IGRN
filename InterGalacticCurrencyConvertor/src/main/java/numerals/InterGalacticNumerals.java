package main.java.numerals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;

/**
 * Keeps track of InterGalactic Numerals and their symbol conversion mapping.
 */
public class InterGalacticNumerals {
  private final HashMap<String, String>      mConversionMap = new HashMap<>();
  private static final InterGalacticNumerals mInstance      = new InterGalacticNumerals();
  private IDecimalConvertor                  mConvertor;

  private InterGalacticNumerals() {
  }

  private String convertSymbol(String igSymbol) throws IgnConversionException {
    Objects.requireNonNull(igSymbol, "Null InterGalactic Numeral Provided");
    igSymbol = igSymbol.toLowerCase();
    if (!mConversionMap.containsKey(igSymbol)) {
      String message = String.format("No mapping found for symbol %s", igSymbol);
      throw new IgnConversionException(ConversionErrorCode.INVALID_IGN_SYMBOL, message);
    }
    return mConversionMap.get(igSymbol);
  }

  private List<String> convertSymbolList(List<String> igSymbolList) throws IgnConversionException {
    Objects.requireNonNull(igSymbolList, "Null InterGalactic Numeral List Provided");
    if (igSymbolList.size() == 0) {
      throw new IllegalArgumentException("Empty InterGalactic Numeral List");
    }
    List<String> cSymbolList = new ArrayList<>();
    for (String igSymbol : igSymbolList) {
      cSymbolList.add(convertSymbol(igSymbol));
    }
    return cSymbolList;
  }

  public Integer symbolListToDecimal(List<String> igSymbolList) throws IgnConversionException {
    if (mConvertor == null) {
      throw new IgnConversionException(ConversionErrorCode.DECIMAL_CONVERTOR_UNINITIALIZED,
                                       "No mappings provided by user");
    }
    List<String> cSymbolList = convertSymbolList(igSymbolList);
    return mConvertor.symbolListToDecimal(cSymbolList);
  }

  public void resetConvertor() {
    mConversionMap.clear();
    mConvertor = null;
  }

  public void setConvertor(String symbol) throws IgnConversionException {
    if (mConvertor != null) {
      return;
    }
    mConvertor = DecimalConvertorFactory.getInstance().create(symbol);
  }

  public static InterGalacticNumerals getInstance() {
    return mInstance;
  }

  public boolean addMapping(String igSymbol,
                            String cSymbol) throws IgnConversionException {
    if (mConvertor == null) {
      throw new IgnConversionException(ConversionErrorCode.DECIMAL_CONVERTOR_UNINITIALIZED,
                                       "No mappings provided by user");
    }
    Objects.requireNonNull(igSymbol, "Null InterGalactic Numeral");
    String message = null;
    if (mConversionMap.containsKey(igSymbol)) {
      message = String.format("Duplicate entry of Ign symbol %s", igSymbol);
      throw new IgnConversionException(ConversionErrorCode.DUPLICATE_IGN_SYMBOL_MAPPING,
                                       message);
    } else if (mConversionMap.containsValue(cSymbol)) {
      message = String.format("Duplicate entry of Ign symbol %s", cSymbol);
      throw new IgnConversionException(ConversionErrorCode.DUPLICATE_CONVERSION_SYMBOL_MAPPING,
                                       message);
    } else if (!mConvertor.isValidSymbol(cSymbol)) {
      message = String.format("Invalid conversion symbol %s for Ign Symbol %s", cSymbol,
                              igSymbol);
      throw new IgnConversionException(ConversionErrorCode.INVALID_CONVERSION_SYMBOL,
                                       message);
    }
    mConversionMap.put(igSymbol.toLowerCase(), cSymbol.toLowerCase());
    return true;
  }
}