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
  private TerminalNode type;
  
  /**
   * Constructs a formal node.
   * @param identifier The identifier.
   * @param type The type of the identifier.
   */
  public FormalNode(TerminalNode identifier, TerminalNode type) {
    super();
    this.identifier = identifier;
    this.type = type;
  }

  /**
   * Returns the identifier.
   * @return The identifier.
   */
  public String getIdentifier() {
    return this.identifier.getValue();
  }

  /**
   * Returns the type.
   * @return The type.
   */
  public String getType() {
    return this.type.getValue();
  }
  
  /**
   * Returns the data seperated by two brackets.
   * @return The identifier and type of the formal.
   */
  @Override
  public String dataAsString() {
    return "[Identifier: " + this.getIdentifier() +
        ", Type: " + this.getType() + "]";
  }
}
