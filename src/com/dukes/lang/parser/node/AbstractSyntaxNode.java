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
 * <p>Represents an abstract syntax tree. This class is extended to allow the
 * use of other programmers to make their own syntax.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractSyntaxNode extends Object {

  /**
   * Tells the compiler that this is an identifier.
   */
  public static final int IDENTIFIER_TYPE = 1;

  /**
   * Tells the compiler that this is a boolean.
   */
  public static final int BOOLEAN_TYPE = 2;

  /**
   * Tells the compiler that this is an integer.
   */
  public static final int INTEGER_TYPE = 4;

  /**
   * The children of the tree node. This doesn't have a predetermined size, so
   * the implementing class can have as many children as needed.
   */
  protected AbstractSyntaxNode[] children;

  /**
   * The node data type to be defined my a semantic checker
   */
  protected int type;

  /**
   * Constructs a node with the following children.
   * @param children the children of the AST.
   */
  protected AbstractSyntaxNode(AbstractSyntaxNode... children) {
    this.children = new AbstractSyntaxNode[children.length];
    for(int i = children.length - 1, j = 0; i >= 0; i--, j++) {
      this.children[j] = children[i];
    }
  }

  /**
   * Gets the children of the syntax node.
   * @return The children of this syntax node.
   */
  public AbstractSyntaxNode[] getChildren() {
    return this.children;
  }

  /**
   * Returns specific data as a string representation. This can be used to
   * print the tree very pretty. Each sublcass should if possible override this
   * method to return the string representations of each of it's data,
   * seperated by commas. Preferably, they should be enclosed with brackets.
   * @return An empty string.
   */
  public String dataAsString() {
    return "";
  }

  /**
   * Returns a string representation of this node. Does some pretty cool
   * formatting, and assumes that the name of a subclass ends with "Node" and
   * doesn't contain it anywhere else. To change how this is pretty printed, 
   * change the {@link #prettyPrint()} method.
   * @return A string representation of this node.
   */
  @Override
  public String toString() {
    return this.prettyPrint();
  }
  
  /**
   * Returns a pretty printed version of the node.
   * @return A pretty strnig representaton.
   */
  protected String prettyPrint()
  {
    return AbstractSyntaxNode.prettyPrint(this, 0);
  }
  
  /**
   * Concatenates the front node given to the front of the list provided.
   * @param frontNode The node to add to the front. 
   * @param end The list to add to.
   */
  public static AbstractSyntaxNode[] concat(AbstractSyntaxNode frontNode,
                                            AbstractSyntaxNode... end) {
    AbstractSyntaxNode[] ret = new AbstractSyntaxNode[end.length + 1];
    ret[0] = frontNode;
    System.arraycopy(end, 0, ret, 1, end.length);
    return ret;
  }
  
  private static String prettyPrint(AbstractSyntaxNode ast, int indent) {
    String ret = "";
    //System.out.println(ast.getClass().getSimpleName() + " " + indent);
    for(int i = 0; i < indent; i++) {
      ret += "  ";
    }
    ret += (ast.getClass().getSimpleName()).replaceAll("Node", "") + " " +
        ast.dataAsString() + "\n";
    
    if(ast.getChildren().length == 0) {
      if(ast instanceof NullNode) {
        return "";
      }
      return ret;
    }
    else {
      for(int i = 0; i < ast.getChildren().length; i++) {
        ret += prettyPrint(ast.getChildren()[i], indent + 1);
      }
      return ret;
    }
  }

  /**
   * Gets the type of the syntax node.
   * @return The type of this syntax node.
   */
  public int getType() {
    return this.type;
  }

  /**
   * Returns a string representation of the type.
   * @return A string that represents the type.
   */
  protected String typeToString() {
    switch(this.type) {
      case AbstractSyntaxNode.IDENTIFIER_TYPE:
        return "Identifier";
      case AbstractSyntaxNode.BOOLEAN_TYPE:
        return "Boolean";
      case AbstractSyntaxNode.INTEGER_TYPE:
        return "Integer";
      case AbstractSyntaxNode.IDENTIFIER_TYPE | AbstractSyntaxNode.BOOLEAN_TYPE:
        return "Boolean Identifier";
      case AbstractSyntaxNode.IDENTIFIER_TYPE | AbstractSyntaxNode.INTEGER_TYPE:
        return "Integer Identifier";
      default:
        throw new IllegalArgumentException(
            "Invalid type given to typeToString!");
    }
  }

}
