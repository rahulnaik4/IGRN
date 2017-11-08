package test.java.numerals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;
import main.java.numerals.RomanNumerals;

public class RomanNumeralsTest {
  private static final RomanNumerals mInstance = RomanNumerals.getInstance();

  @Test
  public void testSymbolListToDecimal_withValidList() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "i", "v");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 14);
  }

  @Test
  public void testSymbolListToDecimal_withValidCaseIncensitiveList() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("X", "i", "V");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 14);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition1() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i", "i");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 2);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition2() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i", "i", "i");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 3);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition3() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 20);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition4() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "x", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 30);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition5() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "x", "x", "i", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 39);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition6() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c", "c");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 200);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition7() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c", "c", "c");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 300);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition8() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c", "c", "c", "x", "c");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 390);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition9() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "m");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 2000);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition10() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "m", "m");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 3000);
  }

  @Test
  public void testSymbolListToDecimal_withValidRepetition11() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "m", "m", "c", "m");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 3900);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction1() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i", "v");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 4);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction2() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 9);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction3() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "l");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 40);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction4() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "c");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 90);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction5() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c", "d");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 400);
  }

  @Test
  public void testSymbolListToDecimal_withValidSubtraction6() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c", "m");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 900);
  }

  @Test
  public void testSymbolListToDecimal_withValidAddition1() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "i");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1001);
  }

  @Test
  public void testSymbolListToDecimal_withValidAddition2() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "l", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1060);
  }

  @Test
  public void testSymbolListToDecimal_withValidAddition3() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "m", "m", "c", "m", "l", "i", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 3959);
  }

  @Test(expected = NullPointerException.class)
  public void testSymbolListToDecimal_withNullList() throws IgnConversionException {
    List<String> rSymbolList = null;
    mInstance.symbolListToDecimal(rSymbolList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSymbolListToDecimal_withEmptyList() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList();
    mInstance.symbolListToDecimal(rSymbolList);
  }

  private void callSymbolListToDecimal_withInvalidPattern(List<String> cSymbolList) {
    try {
      mInstance.symbolListToDecimal(cSymbolList);
      fail("Since invalid patterns are being tested, we should never reach here");
    } catch (IgnConversionException e) {
      assertTrue(e.getError() == ConversionErrorCode.ILLEGAL_PATTERN_IN_SYMBOL_LIST);
    }
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation1() {
    List<String> rSymbolList = Arrays.asList("v", "v");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation2() {
    List<String> rSymbolList = Arrays.asList("l", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation3() {
    List<String> rSymbolList = Arrays.asList("d", "d");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation4() {
    List<String> rSymbolList = Arrays.asList("i", "i", "i", "i");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation5() {
    List<String> rSymbolList = Arrays.asList("c", "c", "c", "c");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation6() {
    List<String> rSymbolList = Arrays.asList("x", "x", "x", "x");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidRepetation7() {
    List<String> rSymbolList = Arrays.asList("m", "m", "m", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction1() {
    List<String> rSymbolList = Arrays.asList("l", "c");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction2() {
    List<String> rSymbolList = Arrays.asList("l", "d");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction3() {
    List<String> rSymbolList = Arrays.asList("l", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction4() {
    List<String> rSymbolList = Arrays.asList("v", "x");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction5() {
    List<String> rSymbolList = Arrays.asList("v", "c");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction6() {
    List<String> rSymbolList = Arrays.asList("v", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction7() {
    List<String> rSymbolList = Arrays.asList("v", "d");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction8() {
    List<String> rSymbolList = Arrays.asList("v", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction9() {
    List<String> rSymbolList = Arrays.asList("d", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction10() {
    List<String> rSymbolList = Arrays.asList("i", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction11() {
    List<String> rSymbolList = Arrays.asList("i", "d");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction12() {
    List<String> rSymbolList = Arrays.asList("i", "c");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction13() {
    List<String> rSymbolList = Arrays.asList("i", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction14() {
    List<String> rSymbolList = Arrays.asList("x", "d");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction15() {
    List<String> rSymbolList = Arrays.asList("x", "m");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction16() {
    List<String> rSymbolList = Arrays.asList("i", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction17() {
    List<String> rSymbolList = Arrays.asList("i", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction18() {
    List<String> rSymbolList = Arrays.asList("x", "l", "c");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidSubtraction19() {
    List<String> rSymbolList = Arrays.asList("i", "i", "x");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidAddition1() {
    List<String> rSymbolList = Arrays.asList("i", "x", "i");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidAddition2() {
    List<String> rSymbolList = Arrays.asList("x", "c", "l");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_witInvalidAddition3() {
    List<String> rSymbolList = Arrays.asList("i", "x", "x");
    callSymbolListToDecimal_withInvalidPattern(rSymbolList);
  }

  @Test
  public void testisValidSymbol_withI() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("i"));
  }

  @Test
  public void testisValidSymbol_withV() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("v"));
  }

  @Test
  public void testisValidSymbol_withX() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("x"));
  }

  @Test
  public void testisValidSymbol_withL() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("l"));
  }

  @Test
  public void testisValidSymbol_withC() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("c"));
  }

  @Test
  public void testisValidSymbol_withD() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("d"));
  }

  @Test
  public void testisValidSymbol_withUpperCase() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("M"));
  }

  @Test(expected = NullPointerException.class)
  public void testisValidSymbol_withNullCharacter() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol(null));
  }

  @Test
  public void testisValidSymbol_withLowerCase() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol("m"));
  }

  @Test
  public void testisValidSymbol_withInvalidCharacter() throws IgnConversionException {
    assertFalse(mInstance.isValidSymbol("z"));
  }

  @Test
  public void testSymbolListToDecimal_withI() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1);
  }

  @Test
  public void testSymbolListToDecimal_withV() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("v");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 5);
  }

  @Test
  public void testSymbolListToDecimal_withX() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 10);
  }

  @Test
  public void testSymbolListToDecimal_withL() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("l");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 50);
  }

  @Test
  public void testSymbolListToDecimal_withC() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("c");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 100);
  }

  @Test
  public void testSymbolListToDecimal_withD() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("d");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 500);
  }

  @Test
  public void testSymbolListToDecimal_withUpperCase() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("M");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1000);
  }

  @Test(expected = NullPointerException.class)
  public void testSymbolListToDecimal_withNullCharcter() throws IgnConversionException {
    assertTrue(mInstance.isValidSymbol(null));
  }

  @Test
  public void testSymbolListToDecimal_withLowerCase() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1000);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSymbolListToDecimal_withInvalidCharacter() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("z");
    mInstance.symbolListToDecimal(rSymbolList);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSymbolListToDecimal_withInvalidList() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("x", "z", "v");
    mInstance.symbolListToDecimal(rSymbolList);
  }

  @Test
  public void testSymbolListToDecimal_withMinReturnValue() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("i");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 1);
  }

  @Test
  public void testSymbolListToDecimal_withMaxReturnValue() throws IgnConversionException {
    List<String> rSymbolList = Arrays.asList("m", "m", "m", "c", "m", "x", "c", "i", "x");
    assertTrue(mInstance.symbolListToDecimal(rSymbolList) == 3999);
  }
}
