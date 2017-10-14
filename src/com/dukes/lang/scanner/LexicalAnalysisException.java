/*
 * Copyright (C) 2017 Coved W Oswald, Daniel Holland, and Patrick Sedlacek
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  
*/
package com.dukes.lang.scanner;

/**
 * <p>An exception that is thrown when there is a potential token present that
 * is valid in the context-free environment but does not conform to the
 * language specific context.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public class LexicalAnalysisException extends RuntimeException {
  /**
   * Creates a {@code LexicalAnalysisException} with no detailed message.
   */
  public LexicalAnalysisException() {
    this("No detailed message provided.");
  }

  /**
   * Creates a {@code LexicalAnalysisException} with the given detailed
   * message.
   *
   * @param s The detailed message.
   * @see java.lang.RuntimeException#RuntimeException(String)
   */
  public LexicalAnalysisException(String s) {
    super(s);
  }

  /**
   * Creates a {@code LexicalAnalysisException} with the detailed message given
   * as well as the {@code Throwable} cause.
   *
   * @param message The detailed message.
   * @param cause   The cause.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public LexicalAnalysisException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a {@code LexicalAnalysisException} with the {@code Throwable}
   * cause.
   *
   * @param cause The cause of the exception.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public LexicalAnalysisException(Throwable cause) {
    super(cause);
  }
}
