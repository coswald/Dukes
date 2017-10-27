package com.dukes.lang.semanticchecker;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class SemanticException extends RuntimeException {
  /**
   * Creates a {@code SemanticException} with no detailed message.
   */
  public SemanticException() {
    this("No detailed message provided.");
  }

  /**
   * Creates a {@code SemanticException} with the given detailed
   * message.
   *
   * @param s The detailed message.
   * @see java.lang.RuntimeException#RuntimeException(String)
   */
  public SemanticException(String s) {
    super(s);
  }

  /**
   * Creates a {@code SemanticException} with the detailed message given
   * as well as the {@code Throwable} cause.
   *
   * @param message The detailed message.
   * @param cause   The cause.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public SemanticException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a {@code SemanticException} with the {@code Throwable}
   * cause.
   *
   * @param cause The cause of the exception.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public SemanticException(Throwable cause) {
    super(cause);
  }
}
