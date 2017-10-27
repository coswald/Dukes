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

/**
 * Describes a generic testing class to be overriden and modified.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public abstract class Test extends Object implements Runnable
{
  /**
   * The GNU Intro string.
   */
  public final String INTRO;
  
  /**
   * The help message to be printed.
   */
  public final String HELP;
  
  /**
   * The GNU Conditions string.
   */
  public static final String CONDITIONS = 
      "You may convey verbatim copies of the Program's source code as you " + 
      "receive it,\nin any medium, provided that you conspicuously and " +
      "appropriately publish on\neach copy an appropriate copyright notice; " +
      "keep intact all notices stating that\nthis License and any " +
      "non-permissive terms added in accord with section 7 apply\nto the " +
      "code; keep intact all notices of the absence of any warranty; and " +
      "give\nall recipients a copy of this License along with the " +
      "Program.\n\nYou may charge any price or no price for each copy that " +
      "you convey, and you may\n offer support or warranty protection for a " +
      "fee.\n";
  
  /**
   * The GNU Warranty string.
   */
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
  
  /**
   * The file to work on.
   */
  protected String fileName;
  
  private String[] args;
  private int exitStatus = -1;
  
  
  /**
   * Makes a test program with the given help message and the arguments.
   * @param help The help message.
   * @param args The args to pass on.
   */
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
  
  /**
   * Runs the program. This will parse through the arguments and then call the
   * {@link #doRun()} method. An exit code is generated at the end.
   */
  @Override
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
        //fnfe.printStackTrace();
        System.err.println(
            fnfe.getClass().getSimpleName().replaceAll(
            "Exception", "Error") + ": " +
            fnfe.getMessage());
      this.exitStatus = 1;
    }
  }
  
  /**
   * Gets the exit status of the program. If the program has not yet run, the
   * exit status is -1.
   * @return The exit status of the program.
   */
  public int getExitStatus() {
    return this.exitStatus;
  }
  
  /**
   * Actaully does the test.
   */
  public abstract void doRun() throws Exception;
}
