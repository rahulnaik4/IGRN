package main.java.materials;

import java.util.HashMap;
import java.util.Objects;

import main.java.ConversionErrorCode;
import main.java.IgnConversionException;

/**
 * Keeps track of different types of materials that can be sold
 * InterGalactically.
 */
public final class MaterialFactory {
  private final HashMap<String, Material> mValidMaterials = new HashMap<>();
  private static final MaterialFactory    mInstance       = new MaterialFactory();

  public static MaterialFactory getInstance() {
    return mInstance;
  }

  public void add(String name,
                  int numUnits,
                  double totalCost) throws IgnConversionException {
    Objects.requireNonNull(name, "Invalid Material Name");
    mValidMaterials.put(name.toLowerCase(), new Material(name, numUnits, totalCost));
  }

  public boolean isAvailable(String name) {
    return Objects.nonNull(name) && mValidMaterials.containsKey(name.toLowerCase());
  }

  public double getUnitPrice(String name) throws IgnConversionException {
    Objects.requireNonNull(name, "Invalid Material Name");
    if (!isAvailable(name)) {
      String message = String.format("Invalid Material %s provided", name);
      throw new IgnConversionException(ConversionErrorCode.INVALID_MATERIAL_MAPPING,
                                       message);
    }
    return mValidMaterials.get(name.toLowerCase()).getUnitPrice();
  }
}

/**
 * Defines a material and its unit cost.
 */
final class Material {
  private final String mName;
  private final double mUnitPrice;

  Material(String name, int numUnits, double totalCost) throws IgnConversionException {
    Objects.requireNonNull(name, "Invalid Material Name");
    String message = null;
    if (name.length() == 0) {
      throw new IllegalArgumentException("Material name length should be > zero");
    } else if (numUnits <= 0) {
      message = String.format("Num units of Material %s is %d but should be > zero", name,
                              numUnits);
      throw new IgnConversionException(ConversionErrorCode.MATERIAL_UNIT_LE_ZERO,
                                       message);
    } else if (totalCost <= 0.0) {
      message = String.format("Total cost of Material %s is %f but should be > zero",
                              name, totalCost);
      throw new IgnConversionException(ConversionErrorCode.MATERIAL_TOTAL_COST_LE_ZERO,
                                       message);
    }
    mName = name.toLowerCase();
    mUnitPrice = totalCost / numUnits;
  }

  String getName() {
    return mName;
  }

  double getUnitPrice() {
    return mUnitPrice;
  }
}
