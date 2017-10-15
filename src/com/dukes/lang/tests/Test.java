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
package com.dukes.lang.tests;

import java.io.FileNotFoundException;

public abstract class Test extends Object implements Runnable
{
  public final String INTRO;
  public final String HELP;
  public static final String CONDITIONS = 
      "Everyone is permitted to copy and distribute verbatim copies of this " +
      "license\ndocument, but changing it is not allowed.";
  public static final String WARRANTY =
      "THERE IS NO WARRANTY FOR THE PROGRAM, TO THE EXTENT PERMITTED BY " + 
      "APPLICABLE\nLAW. EXCEPT WHEN OTHERWISE STATED IN WRITING THE " +
      "COPYRIGHT HOLDERS AND/OR OTHER\nPARTIES PROVIDE THE PROGRAM \"AS IS\"" +
      " WITHOUT WARRANTY OF ANY KIND, EITHER\nEXPRESSED OR IMPLIED, " +
      "INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF\n" +
      "MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE ENTIRE RISK" +
      "AS TO THE\nQUALITY AND PERFORMANCE OF THE PROGRAM IS WITH YOU. SHOULD" +
      "THE PROGRAM PROVE\nDEFECTIVE, YOU ASSUME THE COST OF ALL NECESSARY " +
      "SERVICING, REPAIR OR\nCORRECTION.";
  
  private String[] args;
  private int exitStatus = -1;
  
  protected String fileName;
  
  protected Test(String help, String... args) {
    this.INTRO = this.getClass().getSimpleName() + " Copyright (C) 2017 " + 
        "Coved W Oswald, Dan Holland, and Patrick Sedlacek\n" +
        "This program comes with ABSOLUTELY NO WARRANTY; " + 
        "for details run with '-w'.\n" +
        "This is free softare, and you are welcome to redistribute it\n" +
        "under certain conditions; run with '-c' for details.\n";
    this.HELP = help + "\n\nWith no file, this help message is generated.\n" +
      "  -c\t\tShows conditions\n" +
      "  -h\t\tShows this help message\n" +
      "  -w\t\tShows the warranty";
    this.args = args;
  }
  
  public void run() {
    if(this.args.length <= 0) {
      System.out.println(HELP);
      this.exitStatus = 0;
    }

    this.fileName = null;
    for(int i = 0; i < args.length; i++) {
      if(args[i].endsWith(".kln")) {
        if(fileName == null)
          this.fileName = args[i];
        else {
          //System.out.println(fileName);
          //System.out.println(args[i]);
          System.out.println("Did not expect more than one Klein File!");
          System.out.println(this.HELP);
          this.exitStatus = 1;
        }
      }
      else {
        if(args[i].contains("c"))
          System.out.println(CONDITIONS);
        else if(args[i].contains("w"))
          System.out.println(WARRANTY);
        else if(args[i].contains("h"))
          System.out.println(this.HELP);
        else
          System.out.println("Ignoring option: " + this.args[i]);
      }
    }

    try {
      if(this.fileName != null) {
        System.out.println(this.INTRO);
        this.doRun();
        this.exitStatus = 0;
      }
    }
    catch(Exception fnfe) {
      if(fnfe instanceof FileNotFoundException)
        System.err.println(fileName + " not found.\n" + HELP);
      else
        System.err.println(
            fnfe.getClass().getSimpleName().replaceAll(
            "Exception", "Error") + ": " +
            fnfe.getMessage());
      this.exitStatus = 1;
    }
  }
  
  public int getExitStatus() {
    return this.exitStatus;
  }

  public abstract void doRun() throws Exception;
}
