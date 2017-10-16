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

import com.dukes.klein.parser.KleinParser;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.lang.scanner.FileInputter;
import com.dukes.lang.tests.Test;

import java.io.FileInputStream;

/**
 * Defines a test for the parser. The test will say, "Valid Proram" if a
 * program is valid, or throw an error and print it otherwise.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class kleinf extends Test {
  /**
   * Constructs the test with the given arguments.
   * @param args The arguments.
   */
  public kleinf(String... args) {
    super("Usage: kleinf [-chw] [file]\nTests whether a klein program is " +
      "has valid syntax.", args);
  }

  /**
   * Runs the parser over the file.
   * @throws Exception If the parser throws an exception.
   */
  @Override
  public void doRun() throws Exception {
    FileInputter fi = new FileInputter(new FileInputStream(fileName));
    KleinScanner ks = new KleinScanner(fi);
    KleinParser kp = new KleinParser(ks);
    System.out.println((kp.isValid() ? "Valid Program" : "Invalid Program"));
  }
  
  /**
   * Main function.
   * @param args The arguments.
   */
  public static void main(String... args) {
    kleinf run = new kleinf(args);
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
