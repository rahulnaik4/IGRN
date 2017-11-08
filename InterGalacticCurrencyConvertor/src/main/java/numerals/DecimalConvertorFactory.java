package main.java.numerals;

import java.util.ArrayList;
import java.util.List;

import main.java.IgnConversionException;

/**
 * Keeps track of all the Convertor's available.
 */
public class DecimalConvertorFactory {
  private static final DecimalConvertorFactory mInstance = new DecimalConvertorFactory();
  private final List<IDecimalConvertor>        mConvertorList;

  private DecimalConvertorFactory() {
    // Need to add new Convertor's here
    mConvertorList = new ArrayList<>();
    mConvertorList.add(RomanNumerals.getInstance());
  }

  public static DecimalConvertorFactory getInstance() {
    return mInstance;
  }

  public IDecimalConvertor create(String symbol) throws IgnConversionException {
    for (IDecimalConvertor c : mConvertorList) {
      if (c.isValidSymbol(symbol)) {
        return c;
      }
    }
    return null;
  }
}
