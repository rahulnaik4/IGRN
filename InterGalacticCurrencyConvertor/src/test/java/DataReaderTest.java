package test.java;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import main.java.DataProcessResult;
import main.java.DataReader;
import main.java.numerals.InterGalacticNumerals;

/**
 * Does an integration test of the whole program.
 */
public class DataReaderTest {
  private static List<Data> mDataStore;
  private static DataReader mReader;

  @BeforeClass
  public static void init() {
    mReader = new DataReader();
    mDataStore = new ArrayList<>(12);
    // InterGalacticToRomanMapStatement
    mDataStore.add(new Data("glob is I", null, true));
    mDataStore.add(new Data("Prok is V", null, true));
    mDataStore.add(new Data("pish is X", null, true));
    mDataStore.add(new Data("tegj is L", null, true));
    mDataStore.add(new Data("cojo is C", null, true));
    mDataStore.add(new Data("dojo is D", null, true));
    mDataStore.add(new Data("mojo is M", null, true));
    mDataStore.add(new Data("glob glob Silver is 34 Credits", null, true));
    mDataStore.add(new Data("glob prok Gold is 57800 Credits", null, true));
    mDataStore.add(new Data("pish pish Iron is 3910 Credits", null, true));

    for (Data data : mDataStore) {
      DataProcessResult actual = mReader.processData(data.getDataLine());
      assertEquals(data.getExpected(), actual);
    }
  }

  public DataReaderTest() {
  }

  private void processData(Data data) {
    DataProcessResult actual = mReader.processData(data.getDataLine());
    assertEquals(data.getExpected(), actual);
  }

  @Test
  public void testInterGalacticToRomanMapStatementProcessor_withInvalidInput() {
    Data data = new Data("tegj is Z", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToRomanMapStatementProcessor_withDuplicateIGMapping() {
    Data data =
              new Data("tegj are L", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToRomanMapStatementProcessor_withDuplicateIGRemapping() {
    Data data =
              new Data("tegj are m", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToRomanMapStatementProcessor_withDuplicateConversionNumeralMapping() {
    Data data =
              new Data("mish are M", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testMaterialCostStatementProcessor_withNewMaterialName() {
    Data data = new Data("glob glob Diamond is 34 Credits", null, true);
    processData(data);
  }

  @Test
  public void testMaterialCostStatementProcessor_withInvalidStructure() {
    Data data = new Data("glob glob Diamond are 34 Credits",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testMaterialCostStatementProcessor_withCaseInsensitive() {
    Data data = new Data("gLob gLob Diamond is 34 creDits", null, true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput1() {
    // Roman Numeral Sequence - XXIX
    Data data = new Data("how much is pish tegj glob glob ?", "pish tegj glob glob is 42",
                         true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput2() {
    // Roman Numeral Sequence - XLII
    Data data = new Data("how much is pish pish pish glob pish ?",
                         "pish pish pish glob pish is 39", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput3() {
    // Roman Numeral Sequence - MCMLXIV
    Data data = new Data("how much is mojo cojo mojo tegj pish glob prok ?",
                         "mojo cojo mojo tegj pish glob prok is 1964", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput4() {
    // Roman Numeral Sequence - MMVI
    Data data = new Data("how much is mojo mojo prok glob ?",
                         "mojo mojo prok glob is 2006", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput5() {
    // Roman Numeral Sequence - MCMXLIV
    Data data = new Data("how much is mojo cojo mojo pish tegj glob prok ?",
                         "mojo cojo mojo pish tegj glob prok is 1944", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput6() {
    // Roman Numeral Sequence - MCMIII
    Data data = new Data("how much is mojo cojo mojo glob glob glob ?",
                         "mojo cojo mojo glob glob glob is 1903", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput7() {
    // Roman Numeral Sequence - MMCD
    Data data = new Data("how much is mojo mojo cojo dojo ?",
                         "mojo mojo cojo dojo is 2400", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput7AndExtraWhitespaces() {
    // Roman Numeral Sequence - MMCD
    Data data = new Data("  how    much is\tmojo\tmojo cojo   dojo ?  ",
                         "mojo mojo cojo dojo is 2400", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput8() {
    // Roman Numeral Sequence - CMX
    Data data = new Data("how much is cojo mojo pish ?  ", "cojo mojo pish is 910", true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withValidInput9() {
    // Roman Numeral Sequence - XLIX
    Data data = new Data("how much is pish tegj glob pish ?", "pish tegj glob pish is 49",
                         true);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withIFourTimes() {
    // Roman Numeral Sequence - IIII
    Data data = new Data("how much is glob glob glob glob ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withXFourTimes() {
    // Roman Numeral Sequence - XXXX
    Data data = new Data("how much is pish pish pish pish ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withCFourTimes() {
    // Roman Numeral Sequence - CCCC
    Data data = new Data("how much is cojo cojo cojo cojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withMFourTimes() {
    // Roman Numeral Sequence - MMMM
    Data data = new Data("how much is mojo mojo mojo mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm1() {
    // Roman Numeral Sequence - IIV
    Data data = new Data("how much is glob glob prok ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm2() {
    // Roman Numeral Sequence - IVI
    Data data = new Data("how much is glob prok glob ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm3() {
    // Roman Numeral Sequence - IVX
    Data data = new Data("how much is glob prok pish ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm4() {
    // Roman Numeral Sequence - VIIIX
    Data data = new Data("how much is prok glob glob glob pish ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm5() {
    // Roman Numeral Sequence - XVV
    Data data = new Data("how much is pish prok prok ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm6() {
    // Roman Numeral Sequence - MDD
    Data data = new Data("how much is mojo dojo dojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm7() {
    // Roman Numeral Sequence - MLL
    Data data = new Data("how much is mojo tegj tegj ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm8() {
    // Roman Numeral Sequence - VM
    Data data = new Data("how much is prok mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm9() {
    // Roman Numeral Sequence - MILM
    Data data = new Data("how much is mojo glob tegj mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm10() {
    // Roman Numeral Sequence - MMDM
    Data data = new Data("how much is mojo mojo dojo mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm11() {
    // Roman Numeral Sequence - IM
    Data data = new Data("how much is glob mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm12() {
    // Roman Numeral Sequence - XM
    Data data = new Data("how much is  pish mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm13() {
    // Roman Numeral Sequence - IXX
    Data data = new Data("how much is  glob pish pish ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withInvalidRomanNumeralForm14() {
    // Roman Numeral Sequence - IXX
    Data data = new Data("how much is mojo mojo cojo dojo mojo ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testInterGalacticToDecimalQueryProcessor_withUnkownIGNumeral() {
    // Roman Numeral Sequence - X?
    Data data = new Data("how much is  pish slob ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withValidInput1() {
    Data data = new Data("how many Credits is glob prok Silver ?",
                         "glob prok Silver is 68 Credits", true);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withValidInput2() {
    Data data = new Data("how many Credits is glob prok Gold ?",
                         "glob prok Gold is 57800 Credits", true);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withValidInput3() {
    Data data = new Data("how many Credits is glob prok Iron ?",
                         "glob prok Iron is 782 Credits", true);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withValidInput4AndExtraWhiteSpaces() {
    Data data = new Data("    how\tmany Credits   is  glob prok Iron   ?\r\n",
                         "glob prok Iron is 782 Credits", true);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withValidInput5() {
    Data data = new Data("how many Credits is glob prok Iron ?",
                         "glob prok Iron is 782 Credits", true);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withInvalidQueryStructure() {
    Data data =
              new Data("how much wood could a woodchuck chuck if a woodchuck could chuck wood ?",
                       "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testMaterialCostQueryProcessor_withInvalidMaterailName() {
    Data data = new Data("how many Credits is glob prok Jewel ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @AfterClass
  public static void destroy() {
    InterGalacticNumerals.getInstance().resetConvertor();
  }
}