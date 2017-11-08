package test.java.query.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.DataProcessResult;
import main.java.ProcessorData;
import main.java.query.processor.InterGalacticToDecimalQueryProcessor;
import main.java.statement.processor.InterGalacticMappingStatementProcessor;
import test.java.Data;

public class InterGalacticToDecimalQueryProcessorTest {
  private static final InterGalacticToDecimalQueryProcessor mInstance;

  static {
    mInstance = new InterGalacticToDecimalQueryProcessor();
  }

  private static void setPrecondition(String data) {
    InterGalacticMappingStatementProcessor processor = null;
    processor = new InterGalacticMappingStatementProcessor();
    processor.process(new ProcessorData(data));
  }

  @BeforeClass
  public static void setUp() {
    InterGalacticToDecimalQueryProcessorTest.setPrecondition("glob is I");
    InterGalacticToDecimalQueryProcessorTest.setPrecondition("prok is v");
    InterGalacticToDecimalQueryProcessorTest.setPrecondition("tegj is L");
    InterGalacticToDecimalQueryProcessorTest.setPrecondition("pish is x");
  }

  private void processData(Data data) {
    ProcessorData pData = new ProcessorData(data.getDataLine());
    DataProcessResult actual = mInstance.process(pData);
    assertEquals(data.getExpected(), actual);
  }

  @Test
  public void testCanProcess_withValidData() {
    ProcessorData data = new ProcessorData("how much is pish tegj glob glob ?");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withValidData_withWhitespaces() {
    ProcessorData data =
                       new ProcessorData("\thow much  \tis pish  tegj glob glob  ?\r\n");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withInvalidIGNumeral() {
    // This checks that the data is in required format
    ProcessorData data = new ProcessorData("how much is pish teg glob glob ?");
    assertTrue(mInstance.canProcess(data));
  }

  @Test(expected = NullPointerException.class)
  public void testCanProcess_withNullData() {
    mInstance.canProcess(null);
  }

  @Test
  public void testProcess_withValidData() {
    Data data = new Data("how much is pish tegj glob glob ?", "pish tegj glob glob is 42",
                         true);
    processData(data);
  }

  @Test
  public void testProcess_withValidData_withWhitespaces() {
    Data data = new Data("\t \n how much is pish  tegj glob   glob ? \r\n\n",
                         "pish tegj glob glob is 42", true);
    processData(data);
  }

  @Test
  public void testProcessStatement_withInvalidIGNumeral() {
    Data data = new Data("how much is pish tegj slob glob ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test(expected = NullPointerException.class)
  public void testProcess_withNullData() {
    mInstance.process(null);
  }
}
