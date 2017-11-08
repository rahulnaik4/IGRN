package main.java.numerals;

import java.util.List;

import main.java.IgnConversionException;

/**
 * Contract that defines a decimal converter.
 */
public interface IDecimalConvertor {
  /**
   * Validates an InterGalactic conversion symbol mapping.
   *
   * @param symbol stores InterGalactic conversion symbol
   * @return status if InterGalactic conversion symbol is valid
   * @throws IgnConversionException
   */
  boolean isValidSymbol(String symbol) throws IgnConversionException;

  /**
   * Converts a list of InterGalactic conversion symbols to decimal.
   *
   * @param symbolList contains a list of InterGalactic conversion symbols
   * @return decimal conversion of the list
   * @throws IgnConversionException
   */
  Integer symbolListToDecimal(List<String> symbolList) throws IgnConversionException;
}
