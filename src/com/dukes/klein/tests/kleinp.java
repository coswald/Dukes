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
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.parser.node.NullNode;
import com.dukes.lang.scanner.FileInputter;
import com.dukes.lang.tests.Test;

import java.io.FileInputStream;

/**
 * Runs the klein parser, and prints the AST that is generated.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class kleinp extends Test {
  
  /**
   * Constructs the test with the given arguments.
   * @param args The arguments.
   */
  public kleinp(String... args) {
      super("Usage: kleinp [-chw] [file]\n" +
      "Generates a program's Abstract syntax tree.",
      args);
  }
  
  /**
   * Runs the test by generating an AST based off of the file given.
   * @throws Exception If the parsing throws and exception.
   */
  @Override
  public void doRun() throws Exception {
    FileInputter fi = new FileInputter(new FileInputStream(fileName));
    KleinScanner ks = new KleinScanner(fi);
    KleinParser kp = new KleinParser(ks);
    AbstractSyntaxNode ast = kp.generateAST();
    System.out.print(ast.toString());
  }
  
  /**
   * Main function.
   * @param args The arguments.
   */
  public static void main(String... args) {
    kleinp run = new kleinp(args);
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
