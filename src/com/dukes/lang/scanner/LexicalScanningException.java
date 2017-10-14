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
 * <p>An exception that is thrown when there is characters' present in a
 * {@code Inputter} that does not conform to the language grammar or would
 * otherwise cause an error in the reading of code.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public class LexicalScanningException extends RuntimeException {
  /**
   * Creates a {@code LexicalScanningException} with no detailed message.
   */
  public LexicalScanningException() {
    this("No detailed message provided.");
  }

  /**
   * Creates a {@code LexicalScanningException} with the given detailed
   * message.
   *
   * @param s The detailed message.
   * @see java.lang.RuntimeException#RuntimeException(String)
   */
  public LexicalScanningException(String s) {
    super(s);
  }

  /**
   * Creates a {@code LexicalScanningException} with the detailed message given
   * as well as the {@code Throwable} cause.
   *
   * @param message The detailed message.
   * @param cause   The cause.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public LexicalScanningException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a {@code LexicalScanningException} with the {@code Throwable}
   * cause.
   *
   * @param cause The cause of the exception.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public LexicalScanningException(Throwable cause) {
    super(cause);
  }
}
