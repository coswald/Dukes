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

/**
 * Ascertains a formal within the Klein language. A formal consists of an
 * identifier followed by a type.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.3.0
 */
public class FormalNode extends AbstractSyntaxNode {
  private TerminalNode identifier;
  private TerminalNode formalType;
  
  /**
   * Constructs a formal node.
   * @param identifier The identifier.
   * @param formalType The type of the identifier.
   */
  public FormalNode(TerminalNode identifier, TerminalNode formalType) {
    super();
    this.identifier = identifier;
    this.formalType = formalType;
    switch(this.formalType.getValue()){
      case "integer":
        this.type = AbstractSyntaxNode.INTEGER_TYPE;
      case "boolean":
        this.type = AbstractSyntaxNode.BOOLEAN_TYPE;
    }
  }

  /**
   * Returns the identifier.
   * @return The identifier.
   */
  public String getIdentifier() {
    return this.identifier.getValue();
  }

  /**
   * Returns the type as a string.
   * @return The type as a string.
   */
  public String getTypeString() {
    return this.formalType.getValue();
  }

  /**
   * Returns the data seperated by two brackets.
   * @return The identifier and type of the formal.
   */
  @Override
  public String dataAsString() {
    return "[Identifier: " + this.getIdentifier() +
        ", Type: " + this.getTypeString() + "]";
  }
}
