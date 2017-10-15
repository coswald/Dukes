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
package com.dukes.lang.scanner;

/**
 * <p>Represents a token within a given grammar. The {@code AbstractToken} can
 * be used to represent a token within <b>any</b> programming language.</p>
 *
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.1.0
 */
public abstract class AbstractToken<E extends Enum<E>, V> extends Object {
  /**
   * The instance of the enum that provides token types.
   */
  protected E tokenType;

  /**
   * The value of the token. This can be {@code null} if the token type does
   * not need a value.
   */
  protected V tokenValue = null;

  /**
   * This is needed for an empty constructor to be present. This is made
   * protected, as subclasses don't inherit constructors.
   */
  protected AbstractToken() {
  }//why the hell this has to be here I'll never know

  /**
   * Constructs an {@code AbstractToken} with the token type describing what
   * kind of token it is, while also describing the value of that token. This
   * and any other constructor in this class is used as a means to help
   * subclasses conform to the standards of a {@code Token}.
   *
   * @param tokenType  The type of token, as a value from an {@code Enum}
   * @param tokenValue The value of a token.
   */
  protected AbstractToken(E tokenType, V tokenValue) {
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
  }

  /**
   * Constructs an {@code AbstractToken} with the token type. The value of the
   * token is {@code null}
   */
  protected AbstractToken(E tokenType) {
    this(tokenType, null);
  }

  /**
   * Determines whether this token is of the type provided.
   *
   * @param tokenType The type to compare to.
   * @return {@code true} if the current token's tokenType is equivalent to the
   * parameter, {@code false} otherwise.
   */
  public boolean isTokenType(E tokenType) {
    return this.tokenType == tokenType;
  }


  /**
   * Returns this token's type.
   *
   * @return The token type of this token.
   */
  public E getTokenType() {
    return this.tokenType;
  }

  /**
   * Returns the value of the current token. Remember this can be {@code null}.
   *
   * @return The token value of this token.
   */
  public V getTokenValue() {
    return this.tokenValue;
  }

  /**
   * Shows the programmer a {@code String} representation of the
   * {@code AbstractToken}. This is the token type as a string, followed by a
   * tab and then the token value. If the token value is null, this method will
   * exclude the tab and the token value.
   *
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Token Type: " + this.tokenType.toString() + (tokenValue != null ?
        "\t\tToken Value: " + this.tokenValue.toString() : "");
  }
}
