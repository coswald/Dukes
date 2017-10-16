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

public class kleinp extends Test {
  public kleinp(String... args) {
      super("Usage: kleinp [-chw] [file]\n" +
      "Generates a program's Abstract syntax tree.",
      args);
  }
  
  @Override
  public void doRun() throws Exception {
    FileInputter fi = new FileInputter(new FileInputStream(fileName));
    KleinScanner ks = new KleinScanner(fi);
    KleinParser kp = new KleinParser(ks);
    AbstractSyntaxNode ast = kp.generateAST();
    System.out.print(kleinp.prettyPrint(ast, 0));
  }

  public static String prettyPrint(AbstractSyntaxNode ast, int indent) {
    String ret = "";
    for(int i = 0; i < indent; i++) {
      ret += "  ";
    }
    ret += (ast.getClass().getSimpleName()).replaceAll("Node", "") + " " +
        ast.dataAsString() + "\n";

    if(ast.getChildren().length == 0) {
      if(ast instanceof NullNode) {
        return "";
      }
      return ret;
    }
    else {
      for(int i = 0; i < ast.getChildren().length; i++) {
        ret += kleinp.prettyPrint(ast.getChildren()[i], indent + 1);
      }
      return ret;
    }
  }
  
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
