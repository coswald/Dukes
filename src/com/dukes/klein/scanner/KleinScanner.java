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
  private boolean atEOF = false;

  public KleinScanner(Inputter input)
  {
    super(input);
    this.inComment = false;
  }

  public boolean endOfFile(){
    if(!this.input.hasNext())
    {
      if (this.inComment) {
        throw new LexicalAnalysisException("Comment started but not ended!");
      }
      this.atEOF = true;
      return true;
    }
    return false;
  }

  public KleinToken generateNextToken()
                    throws LexicalScanningException, LexicalAnalysisException
  {
    this.skipWhiteSpace();
    if(this.inComment) {
      this.skipUntilEndComment();
    }
    //EOF
    if(this.atEOF)
    {
      return new KleinToken(KleinTokenType.EOF);
    }
    //CommentsStart or LeftParenthesis
    //if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
    char current = this.input.currentChar();
    if(current == '(')
    {
      if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
      if(this.input.lookAhead() == '*')
      {
        this.input.next();
        if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
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
      if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
      if(this.input.lookAhead() == ')')
      {
        this.input.next();
        if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
        this.input.next();
        if(!this.inComment)
          throw new LexicalAnalysisException("Comment ended but not started!");
        this.inComment = false;
        return new KleinToken(KleinTokenType.ENDCOMMENT);
      }
      else // Should never happen
      {
        this.input.next();
        return new KleinToken(KleinTokenType.SYMBOL, "*");
      }
    }

    //Rightparenthesis
    if(current == ')')
    {
      if(endOfFile()){
        return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
      }
      this.input.next();
      return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
    }
    //Single-char Expression symbol
    else if(KleinScanner.isSymbol(current))
    {
      if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
      this.input.next();
      return new KleinToken(KleinTokenType.SYMBOL, Character.toString(current));
    }
    //Seperator
    else if(KleinScanner.isSeparator(current))
    {
      if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
      this.input.next();
      return new KleinToken(KleinTokenType.SEPARATOR,
                            Character.toString(current));
    }
    //Integer
    else if(KleinScanner.isDigit(current))
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
      if(KleinScanner.isSymbol(word))
        return new KleinToken(KleinTokenType.SYMBOL, word);

      if(KleinScanner.isKeyword(word))
        return new KleinToken(KleinTokenType.KEYWORD, word);
      else if(KleinScanner.isType(word))
        return new KleinToken(KleinTokenType.TYPE, word);
      else if(word.equals("true") || word.equals("false"))
        return new KleinToken(KleinTokenType.BOOLEAN, word);
      else
      {
        if(word.length() > 256)
          throw new LexicalAnalysisException("An Identifier can not be any " +
                                             "longer than 256 character!");
        return new KleinToken(KleinTokenType.IDENTIFIER, word);
      }
    }
    //Unrecognized character/tokentype.
    else
    {
      if (this.endOfFile()){
        return new KleinToken(KleinTokenType.EOF);
      }
      throw new LexicalScanningException("The character '" + current +
              "' is invalid.");
    }
  }
  
  private static boolean isSymbol(char c)
  {
    switch(c)
    {
      case '+':
      case '-':
      case '<':
      case '=':
      case '*':
      case '/':
        return true;
      default:
        return false;
    }
  }
  
  private static boolean isSymbol(String s)
  {
    switch(s)
    {
      case "and":
      case "or":
      case "not":
        return true;
      default:
        return false;
    }
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
    switch(s)
    {
      case "function":
      case "main":
      case "print":
      case "if":
      case "then":
      case "else":
        return true;
      default:
        return false;
    }
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
    /*
    if(this.input.currentChar() == '-')
    {
      s += Character.toString(this.input.currentChar());
      this.input.next();
    }
    */
    if(this.input.currentChar() == '0' && 
       KleinScanner.isDigit(this.input.lookAhead()))
    {
      throw new LexicalAnalysisException("Number cannot start with 0!");
    }
    
    while(KleinScanner.isDigit(this.input.currentChar()))
    {
      if(!KleinScanner.isDigit(this.input.currentChar()))
        throw new LexicalAnalysisException("Non-numeric character " +
                                           this.input.currentChar() +
                                          " received when parsing number!");
      s += this.input.currentChar();
      if (this.endOfFile()) {break;}
      this.input.next();
    }

    return s;
  }
  
  private String getWord()
  {
    String s = "";
    while((KleinScanner.isLetter(this.input.currentChar()) ||
           KleinScanner.isDigit(this.input.currentChar()) ||
           this.input.currentChar() == '_'))
    {
      s += this.input.currentChar();
      if (this.endOfFile()) {break;}
      this.input.next();
    }
    
    return s;
  }
}
