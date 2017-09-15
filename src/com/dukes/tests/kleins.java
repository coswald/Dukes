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
import java.lang.Object;

import com.dukes.klein.scanner.FileInputter;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
//import com.dukes.klein.scanner.StringInputter;

public class kleins extends Object
{
  public static final String HELP       = "Usage: kleins [-chw] [file]\n" + 
                                          "Generates klein tokens from a " + 
                                          "given file.\n\nWith no file, this" +
                                          " help message is generated.\n" + 
                                          "  -c\t\tShows conditions\n" +
                                          "  -h\t\tShows this help " + 
                                          "message\n  -w\t\tShows the" +
                                          " warranty.";
  
  public static final String CONDITIONS = "";
  public static final String WARRANTY   = "";
  
  public static void main(String[] args)
  {
    /*
    String parse = "(* Hello world *) " +
                   "function main() : integer\n  print(-1)\n  1";
    StringInputter si = new StringInputter(parse);
    KleinScanner ks = new KleinScanner(si);
    */
    if(args.length <= 0)
    {
      System.out.println(HELP);
      System.exit(0);
    }
    
    String fileName = null;
    for(int i = 0; i < args.length; i++)
    {
      if(args[i].endsWith(".kln"))
      {
        if(fileName == null)
          fileName = args[i];
        else
        {
          //System.out.println(fileName);
          //System.out.println(args[i]);
          System.out.println("Did not expect more than one Klein File!");
          System.out.println(HELP);
          System.exit(1);
        }
      }
      else
      {
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
    
    try
    {
      FileInputter fi = new FileInputter(new FileInputStream(fileName));
      KleinScanner ks = new KleinScanner(fi);
      KleinToken currentToken = null;
      while(!(ks.peek()).isTokenType(KleinTokenType.EOF))
      {
        System.out.println(ks.next());
      }
      System.out.println(ks.peek());
    }
    catch(FileNotFoundException fnfe)
    {
      System.err.println(fileName + " not found.\n" + HELP);
    }
  }
}
