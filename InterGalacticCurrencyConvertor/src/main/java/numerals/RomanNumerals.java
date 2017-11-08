package main.java.numerals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;

/**
 * Keeps track of Roman Numerals, its validation and decimal conversion.
 */
public class RomanNumerals implements IDecimalConvertor {
  private final HashMap<Character, Integer> mRnToDecimalMap;
  private static final RomanNumerals        mInstance               = new RomanNumerals();
  private static final String               MATCH_CHAR_OTHERTHAN_VX = "[^vx]";
  private static final String               MATCH_CHAR_OTHERTHAN_LC = "[^lc]";
  private static final String               MATCH_CHAR_OTHERTHAN_DM = "[^dm]";
  private static final String               MATCH_CHAR_VLD          = "[vld]";

  private RomanNumerals() {
    mRnToDecimalMap = new HashMap<>();
    mRnToDecimalMap.put('i', 1);
    mRnToDecimalMap.put('v', 5);
    mRnToDecimalMap.put('x', 10);
    mRnToDecimalMap.put('l', 50);
    mRnToDecimalMap.put('c', 100);
    mRnToDecimalMap.put('d', 500);
    mRnToDecimalMap.put('m', 1000);
  }

  public static RomanNumerals getInstance() {
    return mInstance;
  }

  private boolean hasSubtractionRulePassed(Character curChar,
                                           Character prevChar,
                                           int idx,
                                           List<Integer> rSymbolValues) {
    boolean passed = false;
    int curVal = mRnToDecimalMap.get(curChar);
    if (String.valueOf(prevChar).matches(MATCH_CHAR_VLD)) {
      return passed;
    } else if (prevChar.equals('i')
               && String.valueOf(curChar).matches(MATCH_CHAR_OTHERTHAN_VX)) {
      return passed;
    } else if (prevChar.equals('x')
               && String.valueOf(curChar).matches(MATCH_CHAR_OTHERTHAN_LC)) {
      return passed;
    } else if (prevChar.equals('c')
               && String.valueOf(curChar).matches(MATCH_CHAR_OTHERTHAN_DM)) {
      return passed;
    }
    for (int j = idx - 2; j >= 0; --j) {
      if (rSymbolValues.get(j) < curVal) {
        return passed;
      }
    }
    return passed = true;
  }

  private boolean hasAdditionRulePassed(Character curChar,
                                        int idx,
                                        List<Integer> values) {
    boolean passed = false;
    int curVal = mRnToDecimalMap.get(curChar);
    for (int j = idx - 2; j >= 0; --j) {
      if (values.get(j) <= curVal) {
        return passed;
      }
    }
    return passed = true;
  }

  private boolean hasRepetitionRulePassed(Character curChar,
                                          int idx,
                                          List<Integer> values,
                                          HashMap<Character, Integer> charCntMap) {
    boolean passed = false;
    if (String.valueOf(curChar).matches(MATCH_CHAR_VLD)) {
      return passed;
    } else {
      int curVal = mRnToDecimalMap.get(curChar);
      List<Character> repChars = Arrays.asList('i', 'x', 'c', 'm');
      for (Character c : repChars) {
        if (charCntMap.get(c) == 3) {
          return passed;
        }
      }
      for (int j = idx - 2; j >= 0; --j) {
        if (values.get(j) < curVal) {
          return passed;
        }
      }
    }
    return passed = true;
  }

  private boolean isValidSymbolList(List<String> rSymbolList) throws IgnConversionException {
    boolean isValid = false;
    Objects.requireNonNull(rSymbolList, "Null Roman Numeral List Provided");
    if (rSymbolList.size() == 0) {
      throw new IllegalArgumentException("Empty Roman Numeral List");
    }
    ArrayList<Integer> values = new ArrayList<>();
    int minimum = Integer.MAX_VALUE;
    int prevVal = minimum;
    Character prevChar = ' ';
    HashMap<Character, Integer> charCntMap = new HashMap<>();
    for (Character rSymbol : mRnToDecimalMap.keySet()) {
      charCntMap.put(rSymbol, 0);
    }
    for (int i = 0; i < rSymbolList.size(); ++i) {
      if (!isValidSymbol(rSymbolList.get(i))) {
        // This should never occur
        throw new IllegalArgumentException("Invalid conversion symbol "
                                           + rSymbolList.get(i)
                                           + " in list");
      }
      Character curChar = rSymbolList.get(i).toLowerCase().charAt(0);
      int curVal = mRnToDecimalMap.get(curChar);
      if (prevVal < curVal) {
        if (!hasSubtractionRulePassed(curChar, prevChar, i, values)) {
          return isValid;
        }
      } else if (prevVal > curVal) {
        if (!hasAdditionRulePassed(curChar, i, values)) {
          return isValid;
        }
      } else {
        if (!hasRepetitionRulePassed(curChar, i, values, charCntMap)) {
          return isValid;
        }
      }
      prevChar = curChar;
      prevVal = curVal;
      minimum = Math.min(minimum, curVal);
      values.add(curVal);
      int charOccurrence = charCntMap.get(curChar);
      charCntMap.put(curChar, charOccurrence + 1);
    }
    return isValid = true;
  }

  @Override
  public boolean isValidSymbol(String rSymbol) throws IgnConversionException {
    Objects.requireNonNull(rSymbol, "Null Roman Numeral Provided");
    if (rSymbol.length() == 0) {
      throw new IllegalArgumentException("Invalid Roman Numeral " + rSymbol);
    } else if (rSymbol.length() > 1) {
      String message = String.format("Invalid length of conversion symbol %s", rSymbol);
      throw new IgnConversionException(ConversionErrorCode.INVALID_CONVERSION_SYMBOL_LENGTH,
                                       message);
    }
    return mRnToDecimalMap.containsKey(rSymbol.toLowerCase().charAt(0));
  }

  @Override
  public Integer symbolListToDecimal(List<String> rSymbolList) throws IgnConversionException {
    if (!isValidSymbolList(rSymbolList)) {
      throw new IgnConversionException(ConversionErrorCode.ILLEGAL_PATTERN_IN_SYMBOL_LIST,
                                       "Illegal pattern of Ign symbols provided by user");
    }
    int prevVal = 0, total = 0;
    for (int i = 0, curVal = 0; i < rSymbolList.size(); ++i) {
      curVal = mRnToDecimalMap.get(rSymbolList.get(i).toLowerCase().charAt(0));
      total += (prevVal < curVal) ? -prevVal : prevVal;
      prevVal = curVal;
    }
    total += prevVal;
    return total;
  }
}