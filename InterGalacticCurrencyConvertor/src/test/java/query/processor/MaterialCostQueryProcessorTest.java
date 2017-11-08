package test.java.query.processor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import main.java.DataProcessResult;
import main.java.IgnConversionException;
import main.java.ProcessorData;
import main.java.materials.MaterialFactory;
import main.java.query.processor.MaterialCostQueryProcessor;
import main.java.statement.processor.InterGalacticMappingStatementProcessor;
import main.java.statement.processor.MaterialCostStatementProcessor;
import test.java.Data;

public class MaterialCostQueryProcessorTest {
  private static final MaterialCostQueryProcessor mInstance;

  static {
    mInstance = new MaterialCostQueryProcessor();
  }

  private static void setPrecondition1(String data) {
    InterGalacticMappingStatementProcessor processor = null;
    processor = new InterGalacticMappingStatementProcessor();
    processor.process(new ProcessorData(data));
  }

  private static void setPrecondition2(String data) {
    MaterialCostStatementProcessor processor = new MaterialCostStatementProcessor();
    processor.process(new ProcessorData(data));
  }

  @BeforeClass
  public static void setUp() {
    MaterialCostQueryProcessorTest.setPrecondition1("glob is I");
    MaterialCostQueryProcessorTest.setPrecondition1("prok is v");
    MaterialCostQueryProcessorTest.setPrecondition2("glob magnesium is 10 Credits");
    MaterialCostQueryProcessorTest.setPrecondition2("glob glob Silver is 34 Credits");
  }

  private void verifyUnitPrice(String name,
                               double expected) throws IgnConversionException {
    MaterialFactory mtlFactory = MaterialFactory.getInstance();
    assertTrue(Math.abs(mtlFactory.getUnitPrice(name) - expected) < 0.000001);
  }

  private void processData(Data data) {
    ProcessorData pData = new ProcessorData(data.getDataLine());
    DataProcessResult actual = mInstance.process(pData);
    assertEquals(data.getExpected(), actual);
  }

  @Test
  public void testCanProcess_withValidData() {
    ProcessorData data = new ProcessorData("how many Credits is glob magnesium ?");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withValidData_withWhitespaces() {
    ProcessorData data =
                       new ProcessorData("\thow many  Credits is glob \tmagnesium ?\r\n");
    assertTrue(mInstance.canProcess(data));
  }

  @Test
  public void testCanProcess_withInvalidIGNumeral() {
    // This checks that the data is in required format
    ProcessorData data = new ProcessorData("how many Credits is slob magnesium ?");
    assertTrue(mInstance.canProcess(data));
  }

  @Test(expected = NullPointerException.class)
  public void testCanProcess_withNullData() {
    mInstance.canProcess(null);
  }

  @Test
  public void testProcess_withValidData() throws IgnConversionException {
    Data data = new Data("how many Credits is glob Magnesium ?",
                         "glob Magnesium is 10 Credits", true);
    processData(data);
    verifyUnitPrice("magnesium", 10.0);
  }

  @Test
  public void testProcess_withValidData_withWhitespaces() throws IgnConversionException {
    Data data = new Data("\t \n how many Credits  is glob glob magnesium ? \r\n\n",
                         "glob glob magnesium is 20 Credits", true);
    processData(data);
    verifyUnitPrice("Magnesium", 10.0);
  }

  @Test
  public void testProcess_withUnknownMateriall() {
    Data data = new Data("how many Credits  is glob glob manganese ?",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test
  public void testProcessStatement_withInvalidIGNumeral() {
    Data data = new Data("how many Credits  is tlob glob manganese ?s",
                         "I have no idea what you are talking about", false);
    processData(data);
  }

  @Test(expected = NullPointerException.class)
  public void testProcess_withNullData() {
    mInstance.process(null);
  }
}
