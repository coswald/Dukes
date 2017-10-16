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
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class FunctionNode extends AbstractSyntaxNode {
  private TerminalNode name;
  private TerminalNode returnType;

  public FunctionNode(TerminalNode name, TerminalNode returnType,
                      BodyNode bodyNode, FormalNode... formals) {
    super(AbstractSyntaxNode.concat(bodyNode, formals));
    this.name = name;
    this.returnType = returnType;
  }

  public TerminalNode getName() {
    return this.name;
  }

  @Override
  public String dataAsString() {
    return "[Name: " + this.name.toString() +
        ", Return Type: " + this.returnType.toString() + "]";
  }

  public String getReturnType() {
    return this.returnType.getValue();
  }
}
