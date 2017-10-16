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
package com.dukes.lang.parser.node;

/**
 * Defines a node of an AST that can only contain itself. This is done to
 * ensure tha parts of a program cannot contain other parts that are not
 * expressions. This could include import statements or functions, but it
 * really depends on the implemented language.
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class ExpressionNode extends AbstractSyntaxNode {
  
  /**
   * Constructs an expression node with the given children.
   * @param exprNode
   */
  public ExpressionNode(ExpressionNode... exprNode) {
    super(exprNode);
  }
}
