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
package com.dukes.klein.parser.node;

import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.klein.generator.KleinCodeGenerator;

/**
 * This class describes the top most level of a Klein program. A Klein program
 * can be seen as a collection of functions, and so that is what the program
 * node is. Please note that there can be no function nodes, and that would
 * correspond to an empty program. This would be a valid program, but would not
 * pass the synatctic analyzer.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class ProgramNode extends AbstractSyntaxNode {
  
  /**
   * Makes a program off of the function nodes. This can be empty.
   * @param functions The functions of the program.
   */
  public ProgramNode(FunctionNode... functions) {
    super(functions);
  }
  
  @Override
  public String toTargetCode() {
    String s = "*\n* PROGRAM\n*\n";
    //TODO add arguments to memory by looking up Main.
    s += KleinCodeGenerator.emitCode("LDA", "6", "1", "7");
    s += KleinCodeGenerator.emitCode("LDA", "7", "main", "0");
    s += KleinCodeGenerator.emitCode("HALT", "0", "0", "0");
    
    s += "*\n* PRINT\n*\n";
    s += KleinCodeGenerator.emitCode("OUT", "5", "0", "0");
    s += KleinCodeGenerator.emitCode("LDA", "7", "0", "6");
    return s;
  }
}
