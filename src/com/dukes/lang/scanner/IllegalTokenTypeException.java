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

//Writing this class felt like plagiarism...

/**
 * <p>An exception that should be thrown when a given token isn't given the
 * proper data. This can either be the case when a token is given extra data
 * when it doesn't need it, or when it doesn't receive enough data.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @see java.lang.IllegalArgumentException
 * @since 0.1.0
 */
public class IllegalTokenTypeException extends IllegalArgumentException {
  /**
   * Creates an {@code IllegalTokenTypeException} with no detail message.
   */
  public IllegalTokenTypeException() {
    this("No detailed message provided.");
  }

  /**
   * Creates a {@code IllegalTokenTypeException} with a given detail message.
   *
   * @param s The detailed message.
   * @see java.lang.IllegalArgumentException#IllegalArgumentException(String)
   */
  public IllegalTokenTypeException(String s) {
    super(s);
  }

  /**
   * Creates a {@code IllegalTokenTypeException} with a given detail message,
   * as well as a cause.
   *
   * @param message A detail message.
   * @param cause   The cause.
   */
  public IllegalTokenTypeException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a {@code IllegalTokenTypeException} with a given cause.
   *
   * @param cause The cause.
   */
  public IllegalTokenTypeException(Throwable cause) {
    super(cause);
  }
}
