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

public class kleins extends Test {
  public kleins(String... args) {
    super("Usage: kleins [-chw] [file]\nGenerates klein tokens from a given " +
      "file.", args);
  }
  
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
