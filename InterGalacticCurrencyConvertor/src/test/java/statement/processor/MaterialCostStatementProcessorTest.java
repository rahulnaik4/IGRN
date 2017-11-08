package test.java.statement.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.DataProcessResult;
import main.java.IgnConversionException;
import main.java.ProcessorData;
import main.java.materials.MaterialFactory;
import main.java.statement.processor.InterGalacticMappingStatementProcessor;
import main.java.statement.processor.MaterialCostStatementProcessor;
import test.java.Data;

public class MaterialCostStatementProcessorTest {
  static private MaterialCostStatementProcessor mInstance;

  static {
    mInstance = new MaterialCostStatementProcessor();
  }

  private static void setPrecondition(String data) {
    InterGalacticMappingStatementProcessor processor = null;
    processor = new InterGalacticMappingStatementProcessor();
    processor.process(new ProcessorData(data));
  }

  @BeforeClass
  public static void setUp() {
    MaterialCostStatementProcessorTest.setPrecondition("glob is I");
  }

  private void verifyUnitPrice(String name,
                               double expected) throws IgnConversionException {
    MaterialFactory mtlFactory = MaterialFactory.getInstance();
    assertTrue(Math.abs(mtlFactory.getUnitPrice(name) - expected) < 0.000001);
  }

  private void processData(Data data) {
    ProcessorData pData = new ProcessorData(data.getDataLine());
    DataProcessResult actual = mInstance.process(pData);
    // System.out.println(data.getDataLine());
    // System.out.println(actual.getProcessorResult());
    // System.out.println(data.getExpected().getProcessorResult());
    // System.out.println("********");
    assertEquals(data.getExpected(), actual);
  }

  @Test
  public void testCanProcess_withValidData() {
    ProcessorData data = new ProcessorData("glob glob Silver is 34 Credits");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withValidData_withWhitespaces() {
    ProcessorData data = new ProcessorData("\tglob  glob Silver is  34 Credits \r\n");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withInvalidIGNumeral() {
    // This checks that the data is in required format
    ProcessorData data = new ProcessorData("glob slob Silver is 34 Credits");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withZeroPrecedingDecimalCredit() {
    ProcessorData data = new ProcessorData("glob glob silver is 0034 credits");
    assertTrue(mInstance.canProcess(data));
  }

  @Test(expected = NullPointerException.class)
  public void testCanProcess_withNullData() {
    mInstance.canProcess(null);
  }

  @Test
  public void testProcess_withValidData() throws IgnConversionException {
    Data data = new Data("glob glob silver is 34 credits", null, true);
    processData(data);
    verifyUnitPrice("silver", 17.0);
  }

  @Test
  public void testProcess_withValidData_withWhitespaces() throws IgnConversionException {
    Data data = new Data("\t \n  \tglob  glob   \t Silver  is  34 Credits \r\n\n", null,
                         true);
    processData(data);
    verifyUnitPrice("Silver", 17.0);
  }

  @Test
  public void testProcess_withExisingMateriallMapping() throws IgnConversionException {
    testProcess_withValidData();
    // Silver material's unit price which was 17 earlier is reset to 34
    Data data = new Data("glob glob silver is 68 credits", null, true);
    processData(data);
    verifyUnitPrice("SILVER", 34.0);
  }

  @Test
  public void testProcess_withAnotherMateriall() throws IgnConversionException {
    Data data = new Data("glob aluminium is 10 credits", null, true);
    processData(data);
    verifyUnitPrice("Aluminium", 10.0);
  }

  @Test
  public void testProcessStatement_withInvalidIGNumeral() {
    Data data = new Data("glob slob silver is 34 credits",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test(expected = NullPointerException.class)
  public void testProcess_withNullData() {
    mInstance.process(null);
  }
}
