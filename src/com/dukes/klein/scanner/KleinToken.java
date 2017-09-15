/*
 * Copyright (C) 2017 Coved W Oswald
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
package com.dukes.klein.scanner;

import com.dukes.klein.scanner.AbstractToken;
import com.dukes.klein.scanner.IllegalTokenTypeException;
import com.dukes.klein.scanner.KleinTokenType;

/**
 * <p>A {@code KleinToken} as defined by the language specification. This is
 * a {@code KleinTokenType} followed by a {@code String}.</p>
 * @since 1.0
 * @author Coved W Oswald
 * @version 1.0
 */
public class KleinToken extends AbstractToken<KleinTokenType, String>
{
  /**
   * Creates a {@code KleinToken} with the provided token type and value.
   * @param tokenType The token type.
   * @param tokenValue The value of the token, as a string.
   * @throws IllegalTokenTypeException If data is not given or given, depending
   *         on the token type.
   */
  public KleinToken(KleinTokenType tokenType, String tokenValue)
                      throws IllegalTokenTypeException
  {
    boolean defined = (tokenValue != null);
    boolean throwException = false;
    
    switch(tokenType)
    {
      case LEFTPARENTHESIS:
      case RIGHTPARENTHESIS:
      case STARTCOMMENT:
      case ENDCOMMENT:
      case IF:
      case THEN:
      case ELSE:
      case NOT:
      case EOF:
        throwException = defined;
        break;
      default:
        throwException = !defined;
    }
    
    if(throwException)
    {
      if(defined)
      {
        throw new IllegalTokenTypeException("The given token type, " +
                                            tokenType +
                                            " does not need the value " +
                                            tokenValue + "!");
      }
      else
      {
        throw new IllegalTokenTypeException("The given token type, " +
                                            tokenType + " is not given a " + 
                                            "necessary value!");
      }
    }
    
    this.tokenType = tokenType;
    this.tokenValue = tokenValue;
  }
  
  /**
   * Creates a token with the given token type. The default value is
   * {@code null}, and will throw an error if not handled correctly.
   * @param tokenType The token type.
   * @see #KleinToken(KleinTokenType, String)
   */
  public KleinToken(KleinTokenType tokenType)
  {
    this(tokenType, null);
  }
}
