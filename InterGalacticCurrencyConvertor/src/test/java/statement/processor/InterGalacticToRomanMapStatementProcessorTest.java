package test.java.statement.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import main.java.DataProcessResult;
import main.java.ProcessorData;
import main.java.statement.processor.InterGalacticMappingStatementProcessor;
import test.java.Data;

public class InterGalacticToRomanMapStatementProcessorTest {
  static private InterGalacticMappingStatementProcessor mInstance;

  static {
    mInstance = new InterGalacticMappingStatementProcessor();
  }

  private void processData(Data data) {
    ProcessorData pData = new ProcessorData(data.getDataLine());
    DataProcessResult actual = mInstance.process(pData);
    assertEquals(data.getExpected(), actual);
  }

  @Test
  public void testCanProcess_withValidData() {
    ProcessorData data = new ProcessorData("pish is L");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withValidData_withWhitespaces() {
    ProcessorData data = new ProcessorData("\tpish \tis    L\r\n");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withInvalidRomanNumeral() {
    // This checks that the data is in required format
    ProcessorData data = new ProcessorData("pish is Z");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withMultiCharacterRomanNumeral() {
    ProcessorData data = new ProcessorData("pish is ju");
    assertFalse(mInstance.canProcess(data));
  }

  @Test(expected = NullPointerException.class)
  public void testCanProcess_withNullData() {
    mInstance.canProcess(null);
  }

  @Test
  public void testProcess_withValidData() {
    Data data = new Data("lojo is L", null, true);
    processData(data);
  }

  @Test
  public void testProcess_withValidData_withWhitespaces() {
    Data data = new Data("\t \n  zojo \tis   C\n", null, true);
    processData(data);
  }

  @Test
  public void testProcess_withExisingIGNumeralMapping() {
    Data data = new Data("lish is L", null, true);
    processData(data);
    // This will fail as lish already exists
    data = new Data("lish is I", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testProcessStatement_withInvalidRomanNumeral() {
    Data data =
              new Data("tegj are z", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testProcess_withExistingIGNumeralMap() {
    Data data =
              new Data("tegj are L", "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test(expected = NullPointerException.class)
  public void testProcess_withNullData() {
    mInstance.process(null);
  }
}
