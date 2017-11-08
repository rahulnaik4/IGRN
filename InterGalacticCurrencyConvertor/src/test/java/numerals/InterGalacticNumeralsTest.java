package test.java.numerals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;
import main.java.numerals.InterGalacticNumerals;

public class InterGalacticNumeralsTest {
  @Before
  public void initConvertor() throws IgnConversionException {
    // resetting and setting up converter and adding initial mappings
    InterGalacticNumerals.getInstance().resetConvertor();
    InterGalacticNumerals.getInstance().setConvertor("x");
    callAddMapping("pish", "x", null);
    callAddMapping("prok", "v", null);
    callAddMapping("glob", "i", null);
  }

  private void callAddMapping(String ignSymbol,
                              String cSymbol,
                              ConversionErrorCode expectedError) {
    try {
      assertTrue(InterGalacticNumerals.getInstance().addMapping(ignSymbol, cSymbol));
    } catch (IgnConversionException e) {
      assertTrue(e.getError() == expectedError);
    }
  }

  private void callSymbolListToDecimal(List<String> ignSymbolList,
                                       Integer expectedResult,
                                       ConversionErrorCode expectedError) {
    try {
      InterGalacticNumerals ignInstance = InterGalacticNumerals.getInstance();
      Integer result = ignInstance.symbolListToDecimal(ignSymbolList);
      assertTrue(result == expectedResult);
    } catch (IgnConversionException e) {
      assertTrue(e.getError() == expectedError);
    }
  }

  @Test
  public void testAddMapping_withValidInput() {
    InterGalacticNumerals ignInstance = InterGalacticNumerals.getInstance();
    try {
      Field pField = ignInstance.getClass().getDeclaredField("mConversionMap");
      pField.setAccessible(true);
      Object map = pField.get(ignInstance);
      if (HashMap.class.isInstance(map)) {
        assertTrue("x".equals(HashMap.class.cast(map).get("pish")));
      }
    } catch (Exception e) {
      e.printStackTrace();
      fail("This should not happen");
    }
  }

  @Test(expected = NullPointerException.class)
  public void testAddMapping_withNullIGNumeral() {
    callAddMapping(null, "x", null);
  }

  @Test(expected = NullPointerException.class)
  public void testAddMapping_withNullConversionNumeral() {
    callAddMapping("tish", null, ConversionErrorCode.INVALID_CONVERSION_SYMBOL);
  }

  @Test
  public void testAddMapping_withInvalidConversionNumeral() {
    callAddMapping("tish", "z", ConversionErrorCode.INVALID_CONVERSION_SYMBOL);
  }

  @Test
  public void testAddMapping_withMappedIgnNumeral() {
    callAddMapping("pish", "m", ConversionErrorCode.DUPLICATE_IGN_SYMBOL_MAPPING);
  }

  @Test
  public void testAddMapping_withMappedConversionNumeral() {
    callAddMapping("mish", "i", ConversionErrorCode.DUPLICATE_CONVERSION_SYMBOL_MAPPING);
  }

  @Test
  public void testAddMapping_withInvalidLengthConversionNumeral() {
    callAddMapping("mish", "ju", ConversionErrorCode.INVALID_CONVERSION_SYMBOL_LENGTH);
  }

  @Test
  public void testAddMapping_withUnitializedConvertor() {
    destroyConvertor();
    callAddMapping("mojo", "m", ConversionErrorCode.DECIMAL_CONVERTOR_UNINITIALIZED);
  }

  @Test
  public void testSymbolListToDecimal_withValidConversion() {
    List<String> igSymbolList = new ArrayList<>();
    igSymbolList.add("pish");
    igSymbolList.add("prok");
    igSymbolList.add("glob");
    callSymbolListToDecimal(igSymbolList, 16, null);
  }

  @Test
  public void testSymbolListToDecimal_withIllegalPattern() {
    List<String> igSymbolList = new ArrayList<>();
    igSymbolList.add("prok");
    igSymbolList.add("pish");
    callSymbolListToDecimal(igSymbolList, -1,
                            ConversionErrorCode.ILLEGAL_PATTERN_IN_SYMBOL_LIST);
  }

  @Test
  public void testSymbolListToDecimal_withInvalidIgSymbolList() {
    List<String> igSymbolList = new ArrayList<>();
    igSymbolList.add("tojo"); // Invalid IGNum Entry
    igSymbolList.add("pish");
    igSymbolList.add("glob");
    callSymbolListToDecimal(igSymbolList, -1, ConversionErrorCode.INVALID_IGN_SYMBOL);
  }

  @Test(expected = NullPointerException.class)
  public void testSymbolListToDecimal_withNullInput() {
    callSymbolListToDecimal(null, -1, null);
  }

  @After
  public void destroyConvertor() {
    InterGalacticNumerals.getInstance().resetConvertor();
  }
}