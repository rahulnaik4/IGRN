package main.java;

/**
 * Contract that defines a user input processor.
 */
public interface IProcessor {
  /**
   * Reports if the processor can handle the user input.
   *
   * @param data wraps the user input for processing
   * @return status whether the processor can handle the user input
   */
  boolean canProcess(ProcessorData data);

  /**
   *
   * @param data wraps the user input for processing
   * @return result of processing the user input
   */
  DataProcessResult process(ProcessorData data);
}