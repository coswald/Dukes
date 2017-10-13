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
package com.dukes.tests;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.Exception;
import java.lang.Object;

import com.dukes.klein.parser.node.AbstractSyntaxNode;
import com.dukes.klein.parser.node.NullNode;
import com.dukes.klein.parser.KleinParser;
import com.dukes.klein.scanner.FileInputter;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
//import com.dukes.klein.scanner.StringInputter;

public class kleinp extends Object {
  public static final String HELP       = "Usage: kleinf [-chw] [file]\n" +
      "Tests whether a klein program is has valid syntax generating it's AST." +
      "\n\nWith no file, this help message is generated.\n" +
      "  -c\t\tShows conditions\n" +
      "  -h\t\tShows this help message\n" +
      "  -w\t\tShows the warranty.";

  public static final String CONDITIONS = "";
  public static final String WARRANTY   = "";

  public static void main(String[] args) {
    /*
    String parse = "(* Hello world *) " +
                   "function main() : integer\n  print(-1)\n  1";
    StringInputter si = new StringInputter(parse);
    KleinScanner ks = new KleinScanner(si);
    */
    if(args.length <= 0) {
      System.out.println(HELP);
      System.exit(0);
    }

    String fileName = null;
    for(int i = 0; i < args.length; i++) {
      if(args[i].endsWith(".kln")) {
        if(fileName == null)
          fileName = args[i];
        else {
          //System.out.println(fileName);
          //System.out.println(args[i]);
          System.out.println("Did not expect more than one Klein File!");
          System.out.println(HELP);
          System.exit(1);
        }
      }
      else {
        if(args[i].contains("c"))
          System.out.println(CONDITIONS);
        if(args[i].contains("w"))
          System.out.println(WARRANTY);
        if(args[i].contains("h"))
          System.out.println(HELP);
        else
          System.out.println("Ignoring option: " + args);
      }
    }

    try {
      FileInputter fi = new FileInputter(new FileInputStream(fileName));
      KleinScanner ks = new KleinScanner(fi);
      KleinParser kp = new KleinParser(ks);
      //System.out.println(kp.generateAST());
      AbstractSyntaxNode ast = kp.generateAST();
      System.out.println(kleinp.prettyPrint(ast, 0));
    }
    catch(Exception fnfe) {
      if(fnfe instanceof FileNotFoundException)
        System.err.println(fileName + " not found.\n" + HELP);
      else
        System.err.println(
            fnfe.getClass().getSimpleName().replaceAll("Exception", "Error") +
            ": " + fnfe.getMessage());
    }
  }
  
  public static String prettyPrint(
      AbstractSyntaxNode ast, int indent) {
    String ret = "\n";
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
}
