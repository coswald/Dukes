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

import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.dukes.klein.scanner.LexicalAnalysisException;
import com.dukes.klein.scanner.LexicalScanningException;
import com.dukes.klein.scanner.Inputter;

import java.lang.Character;
import java.lang.Long;
import java.lang.Object;
import java.lang.String;
import java.math.BigInteger;

public class KleinScanner extends AbstractScanner<KleinToken>
{
  public static final long MAX_VALUE = (1L << 32) - 1;  
  public static final long MIN_VALUE = ~MAX_VALUE;
  
  private boolean inComment;
  
  public KleinScanner(Inputter input)
  {
    super(input);
    this.inComment = false;
  }
  
  public KleinToken generateNextToken()
                    throws LexicalScanningException, LexicalAnalysisException
  {
    this.skipWhiteSpace();
    if(this.inComment)
      this.skipUntilEndComment();
    //EOF
    if(!this.input.hasNext())
    {
      return new KleinToken(KleinTokenType.EOF);
    }
    //CommentsStart or LeftParenthesis
    char current = this.input.currentChar();
    if(current == '(')
    {
      if(this.input.lookAhead() == '*')
      {
        this.input.next();
        this.input.next();
        this.inComment = true;
        return new KleinToken(KleinTokenType.STARTCOMMENT);
      }
      else
      {
        this.input.next();
        return new KleinToken(KleinTokenType.LEFTPARENTHESIS);
      }
    }
    //CommentEnd or *
    else if(current == '*')
    {
      if(this.input.lookAhead() == ')')
      {
        this.input.next();
        this.input.next();
        this.inComment = false;
        return new KleinToken(KleinTokenType.ENDCOMMENT);
      }
      else //Should never happen, but just to make sure.
      {
        this.input.next();
        return new KleinToken(KleinTokenType.TERM, new String("*"));
      }
    }
    
    //Rightparenthesis
    if(current == ')')
    {
      this.input.next();
      return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
    }
    //Single-char SimpleExpression
    else if(KleinScanner.isSimpleExpression(current) &&
            !(current == '-' && KleinScanner.isDigit(this.input.lookAhead())))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.SIMPLEEXPRESSION,
                            Character.toString(current));
    }
    //Expression
    else if(KleinScanner.isExpression(current))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.EXPRESSION,
                            Character.toString(current));
    }
    //Single-char Term
    else if(KleinScanner.isTerm(current))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.TERM,
                            Character.toString(current));
    } 
    //Seperator
    else if(KleinScanner.isSeparator(current))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.SEPARATOR,
                            Character.toString(current));
    }
    //Integer
    else if(KleinScanner.isDigit(current) || current == '-')
    {
      String num = this.getNumber();
      long t = Long.parseLong(num);
      if(t > MAX_VALUE || t < MIN_VALUE)
      {
        throw new LexicalAnalysisException("Number " + t + " out of the " +
                                           "range" + MAX_VALUE + " " + 
                                           MIN_VALUE + "!");
      }
      return new KleinToken(KleinTokenType.INTEGER, num);
    }
    //Keyword, Identifier, Type, boolean, SimpleExpression, Term, If, then,
    //not, and else
    else if(KleinScanner.isLetter(current))
    {
      String word = this.getWord();
      
      //Non-single char singleexpression and term.
      if(KleinScanner.isSimpleExpression(word))
        return new KleinToken(KleinTokenType.SIMPLEEXPRESSION, word);
      else if(KleinScanner.isTerm(word))
        return new KleinToken(KleinTokenType.TERM, word);
      
      if(KleinScanner.isKeyword(word))
        return new KleinToken(KleinTokenType.KEYWORD, word);
      else if(KleinScanner.isType(word))
        return new KleinToken(KleinTokenType.TYPE, word);
      else if(word.equals("true") || word.equals("false"))
        return new KleinToken(KleinTokenType.BOOLEAN, word);
      else if(word.equals("if"))
        return new KleinToken(KleinTokenType.IF);
      else if(word.equals("then"))
        return new KleinToken(KleinTokenType.THEN);
      else if(word.equals("else"))
        return new KleinToken(KleinTokenType.ELSE);
      else if(word.equals("not"))
        return new KleinToken(KleinTokenType.NOT);
      else
        return new KleinToken(KleinTokenType.IDENTIFIER, word);
    }
    //Unrecognized character/tokentype.
    else
    {
      String error = this.getWord();
      throw new LexicalScanningException("The characters " + error +
                                     " are invalid.");
    }
  }
  
  private static boolean isSimpleExpression(char c)
  {
    switch(c)
    {
      case '+':
      case '-':
        return true;
      default:
        return false;
    }
  }
  
  private static boolean isSimpleExpression(String s)
  {
    return s.equals("or");
  }
  
  private static boolean isExpression(char c)
  {
    return c == '<' || c == '=';
  }
  
  private static boolean isTerm(char c)
  {
    return c == '*' || c == '/';
  }
  
  private static boolean isTerm(String s)
  {
    return s.equals("and");
  }
  
  private static boolean isSeparator(char c)
  {
    return c == ',' || c == ':';
  }
  
  private static boolean isDigit(char c)
  {
    return c >= 48 && c <= 57; //'0' through '9'
  }
  
  private static boolean isLetter(char c)
  {
    return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
  }
  
  private static boolean isKeyword(String s)
  {
    return s.equals("function");
  }
  
  private static boolean isType(String s)
  {
    switch(s)
    {
      case "integer":
      case "boolean":
        return true;
      default:
        return false;
    }
  }
  
  private void skipWhiteSpace()
  {
    while(this.input.hasNext() &&
          Character.isWhitespace(this.input.currentChar()))
    {
      this.input.next();
    }
  }
  
  private void skipUntilEndComment()
  {
    while(this.input.hasNext() &&
          !(this.input.lookAhead() == ')' && this.input.currentChar() == '*'))
    {
      this.input.next();
    }
  }
  
  private String getNumber()
  {
    //this still allows for negative zero to be a thing.
    String s = "";
    if(this.input.currentChar() == '-')
    {
      s += Character.toString(this.input.currentChar());
      this.input.next();
    }
    
    if(this.input.currentChar() == '0' && 
       KleinScanner.isDigit(this.input.lookAhead()))
    {
      throw new LexicalAnalysisException("Number cannot start with 0!");
    }
    
    while(this.input.hasNext() &&
          KleinScanner.isDigit(this.input.currentChar()))
    {
      s += this.input.currentChar();
      this.input.next();
    }
    return s;
  }
  
  private String getWord()
  {
    String s = "";
    while(this.input.hasNext() &&
          KleinScanner.isLetter(this.input.currentChar()))
    {
      s += this.input.currentChar();
      this.input.next();
    }
    return s;
  }
}
