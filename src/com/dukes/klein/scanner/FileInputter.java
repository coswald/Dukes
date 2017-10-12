/*
 * Copyright (C) 2017 Coved W Oswald
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
package com.dukes.klein.scanner;

import com.dukes.klein.scanner.Inputter;
import com.dukes.klein.scanner.LexicalScanningException;

import java.io.FileInputStream;
import java.util.Scanner;

/**
 * Reads characters from a file.
 * @since 1.0
 * @author Coved W Oswald
 * @version 1.0
 */
public class FileInputter extends Inputter
{
  
  private Scanner scan;
  private char nextChar;
  /**
   * Creates a {@code FileInputter} that reads it's input from the
   * {@code FileInputStream} provided. This object will not close the stream,
   * but future implementations may close it when {@link #hasNext()} returns
   * {@code false}.
   * @param fis The input stream to use.
   */
  public FileInputter(FileInputStream fis)
  {
    this.scan = new Scanner(fis);
    this.scan.useDelimiter(""); //One char at a time.
    if (this.hasNext()){
      this.next();
    }
    this.nextChar = 0;
  }
  
  /**
   *
   */
  @Override
  public void next()
  {
    super.next();
    this.nextChar = 0;
  }
  
  /**
   * Determines whether the stream has any available bytes.
   * @return {@code true} if the file has any available bytes.
   * @see java.io.FileInputStream#available()
   */
  @Override
  public boolean hasNext()
  {
    return (this.nextChar != 0 || this.scan.hasNext() ); //fis.available() != 0;
  }
  
  /**
   * {@inheritDoc}
   */
  @Override
  public char lookAhead()
  {
    if(this.nextChar == 0)
      this.nextChar = this.scan.next().charAt(0);
    return this.nextChar;
  }
}
