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
import com.dukes.lang.parser.node.ExpressionNode;

/**
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public class BodyNode extends AbstractSyntaxNode {
  public BodyNode(ExpressionNode exprNode, PrintNode... prints) {
    super(AbstractSyntaxNode.concat(exprNode, prints));
  }

  public AbstractSyntaxNode getExpression() {
    return this.children[0];
  }

  public AbstractSyntaxNode[] getPrintNodes() {
    AbstractSyntaxNode[] ret =
        new AbstractSyntaxNode[this.children.length - 1];

    System.arraycopy(this.children, 1, ret, 0, ret.length);
    return ret;
  }
}
