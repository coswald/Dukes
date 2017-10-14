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
 * <p>A {@code StringInputter} parses through a string as the program
 * input.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public class StringInputter extends Inputter {

  private String parse;

  /**
   * Creates a {@code StringInputter} with the parse given. The string is not
   * used as it is parsed, that way, there is a way to manipulate the position
   * in order to go back; however, such behaviour is not encouraged.
   *
   * @param parse The program as a string.
   */
  public StringInputter(String parse) {
    this.parse = " " + parse + " "; //there is a reason I add a space. This is
    //a lazy way to fix a problem that needs to be handled within the scanner
    //class. It will not read the last token unless there is whitespace at the
    //end of the file.
    this.position = 0;
    if(this.hasNext())
      this.next(); //needed. Otherwise, it won't scan at all.
  }

  /**
   * Determines whether the string has any more characters in it.
   *
   * @return {@code true} if the inputter has a character after this one,
   * {@code false} otherwise.
   * @see Inputter#hasNext()
   */
  @Override
  public boolean hasNext() {
    if(position >= parse.length())
      return false;
    return true;
  }

  /**
   * Peeks ahead at the next character in the stream.
   *
   * @return The next character in the stream.
   */
  @Override
  public char lookAhead() {
    return parse.charAt(position);
  }
}
