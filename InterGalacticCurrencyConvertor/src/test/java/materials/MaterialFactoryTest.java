package test.java.materials;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;
import main.java.materials.MaterialFactory;

public class MaterialFactoryTest {
  @Test
  public void testAdd_withValidMaterialName() throws IgnConversionException {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    materialFactory.add("iron", 10, 100);
    assertTrue(materialFactory.isAvailable("iron"));
    assertTrue(10.0 == materialFactory.getUnitPrice("iron"));
  }

  @Test(expected = NullPointerException.class)
  public void testAdd_withNullMaterialName() throws IgnConversionException {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    materialFactory.add(null, 10, 100);
  }

  @Test
  public void testAdd_withNegativeNumUnits() {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    try {
      materialFactory.add("jewel", -1, 100);
      fail("This should not occur as we provided a negative input for no. units");
    } catch (IgnConversionException e) {
      assertTrue(e.getError() == ConversionErrorCode.MATERIAL_UNIT_LE_ZERO);
    }
  }

  @Test
  public void testAdd_withZeroTotalCost() {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    try {
      materialFactory.add("jewel", 10, 0.0);
      fail("This should not occur as we provided a zero input for total cost");
    } catch (IgnConversionException e) {
      assertTrue(e.getError() == ConversionErrorCode.MATERIAL_TOTAL_COST_LE_ZERO);
    }
  }

  @Test(expected = NullPointerException.class)
  public void testGetUnitPrice_withNullMaterialName() throws IgnConversionException {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    materialFactory.getUnitPrice(null);
  }

  @Test
  public void testGetUnitPrice_withErrorLessThanEpsilon() throws IgnConversionException {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    materialFactory.add("diamond", 3, 10);
    double expected = 10.0 / 3;
    double actual = materialFactory.getUnitPrice("diamond");
    double epsilon = 0.000001;
    assertTrue(Math.abs(expected - actual) < epsilon);
  }

  @Test
  public void testIsAvailable_withNonExistentMaterialName() {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    assertFalse(materialFactory.isAvailable("redjewel"));
  }

  @Test
  public void testIsAvailable_withExistingMaterialName() throws IgnConversionException {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    materialFactory.add("jewel", 10, 100);
    assertTrue(materialFactory.isAvailable("jewel"));
  }

  @Test
  public void testIsAvailable_withNullMaterialName() {
    MaterialFactory materialFactory = MaterialFactory.getInstance();
    assertFalse(materialFactory.isAvailable(null));
  }
}