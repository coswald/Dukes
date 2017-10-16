/*
 * Copyright (C) 2017 Coved W Oswald, Dan Holland, Patrick Sedlacek
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
package com.dukes.lang.parser;

/**
 * An exception that occurs when parsing a program. This could be when a
 * program might have valid tokens, but cannot generate the AST that might be
 * associated with them. Either because they are not positioned correctly, or
 * the fact that they are not supposed to be there.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class ParsingException extends RuntimeException {
  /**
   * Creates a {@code ParsingException} with no detailed message.
   */
  public ParsingException() {
    this("No detailed message provided.");
  }

  /**
   * Creates a {@code ParsingException} with the given detailed
   * message.
   *
   * @param s The detailed message.
   * @see java.lang.RuntimeException#RuntimeException(String)
   */
  public ParsingException(String s) {
    super(s);
  }

  /**
   * Creates a {@code ParsingException} with the detailed message given
   * as well as the {@code Throwable} cause.
   *
   * @param message The detailed message.
   * @param cause   The cause.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public ParsingException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a {@code ParsingException} with the {@code Throwable}
   * cause.
   *
   * @param cause The cause of the exception.
   * @see java.lang.RuntimeException#RuntimeException(String, Throwable)
   */
  public ParsingException(Throwable cause) {
    super(cause);
  }
}
