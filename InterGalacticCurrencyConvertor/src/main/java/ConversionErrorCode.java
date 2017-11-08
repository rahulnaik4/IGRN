package main.java;

/**
 * Defines the errors resulting from invalid user input.
 */
public enum ConversionErrorCode {
  DECIMAL_CONVERTOR_UNINITIALIZED("No IGN symbol mapping provided"),
  INVALID_IGN_SYMBOL("Invalid IGN symbol provided"),
  DUPLICATE_IGN_SYMBOL_MAPPING("IGN mapping duplicated"),
  INVALID_CONVERSION_SYMBOL("Invalid conversion symbol provided"),
  INVALID_CONVERSION_SYMBOL_LENGTH("Invalid conversion symbol length provided"),
  DUPLICATE_CONVERSION_SYMBOL_MAPPING("IGN conversion symbol mapping duplicated"),
  INVALID_MATERIAL_MAPPING("Material mapping does not exist"),
  MATERIAL_UNIT_LE_ZERO("Material units less then or equal to zero"),
  MATERIAL_TOTAL_COST_LE_ZERO("Material total cost less then or equal to zero"),
  ILLEGAL_PATTERN_IN_SYMBOL_LIST("IGN symbols don't follow legal conversion pattern");

  private final String mErrorMessage;

  private ConversionErrorCode(String errorMessage) {
    mErrorMessage = errorMessage;
  }

  public String getMessage() {
    return mErrorMessage;
  }
}
