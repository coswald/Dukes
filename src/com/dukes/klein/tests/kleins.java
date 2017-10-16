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
package com.dukes.klein.tests;

import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.dukes.lang.scanner.FileInputter;
import com.dukes.lang.tests.Test;
//import com.dukes.lang.scanner.StringInputter;

import java.io.FileInputStream;

/**
 * Makes a test that runs the scanner on a file.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public class kleins extends Test {
  
  /**
   * Constructs a scanner test with the arguments given.
   * @param args The argument.
   */
  public kleins(String... args) {
    super("Usage: kleins [-chw] [file]\nGenerates klein tokens from a given " +
      "file.", args);
  }
  
  /**
   * Runs the scanner over a file.
   * @throws Exception When the scanner throws an exception.
   */
  @Override
  public void doRun() throws Exception {
    FileInputter fi = new FileInputter(new FileInputStream(fileName));
    KleinScanner ks = new KleinScanner(fi);
    KleinToken currentToken = null;
    while(!(ks.peek()).isTokenType(KleinTokenType.EOF)) {
      System.out.println(ks.next());
    }
    System.out.println(ks.peek());
  }
  
  /**
   * Main function.
   * @param args The arguments.
   */
  public static void main(String... args) {
    kleins run = new kleins(args);
    Thread t = new Thread(run);
    t.start();
    try {
      t.join();
    }
    catch(InterruptedException ie) {
      ie.printStackTrace();
    }
    System.exit(run.getExitStatus());
  }
}
