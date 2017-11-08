package test.java;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import test.java.materials.MaterialFactoryTest;
import test.java.numerals.InterGalacticNumeralsTest;
import test.java.numerals.RomanNumeralsTest;
import test.java.query.processor.InterGalacticToDecimalQueryProcessorTest;
import test.java.query.processor.MaterialCostQueryProcessorTest;
import test.java.statement.processor.InterGalacticToRomanMapStatementProcessorTest;
import test.java.statement.processor.MaterialCostStatementProcessorTest;

@RunWith(Suite.class)
@SuiteClasses({ DataReaderTest.class,
                MaterialFactoryTest.class,
                InterGalacticNumeralsTest.class,
                RomanNumeralsTest.class,
                InterGalacticToDecimalQueryProcessorTest.class,
                MaterialCostQueryProcessorTest.class,
                InterGalacticToRomanMapStatementProcessorTest.class,
                MaterialCostStatementProcessorTest.class })
public class AllTests { }