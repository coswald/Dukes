package com.dukes.klein.scanner;

import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;
import com.dukes.klein.scanner.Inputter;
import com.dukes.klein.scanner.LexicalScanningException;

import java.lang.Character;
import java.lang.Object;
import java.lang.String;

public class KleinScanner extends AbstractScanner<KleinToken>
{
  public KleinScanner(Inputter input)
  {
    super(input);
  }
  
  public KleinToken generateNextToken()
                    throws LexicalScanningException
  {
    this.skipWhiteSpace();
    
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
        return new KleinToken(KleinTokenType.COMMENTSTART);
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
        return new KleinToken(KleinTokenType.COMMENTEND);
      }
      else
      {
        this.input.next();
        return new KleinToken(KleinTokenType.OPERATOR, new String("*"));
      }
    }
    //Rightparenthesis
    else if(current == ')')
    {
      this.input.next();
      return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
    }
    //Operator
    else if(KleinScanner.isOperator(current))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.OPERATOR,
                            Character.toString(current));
    }
    //Seperator
    else if(KleinScanner.isSeperator(current))
    {
      this.input.next();
      return new KleinToken(KleinTokenType.SEPERATOR,
                            Character.toString(current));
    }
    //Integer
    else if(KleinScanner.isDigit(current))
    {
      String num = this.getNumber();
      return new KleinToken(KleinTokenType.INTEGER, num);
    }
    //Keyword, Identifier, Type, and Boolean
    else if(KleinScanner.isLetter(current))
    {
      String word = this.getWord();
      if(KleinScanner.isOperator(word))
        return new KleinToken(KleinTokenType.OPERATOR, word);//nonsinglechar ops
      if(KleinScanner.isKeyword(word))
        return new KleinToken(KleinTokenType.KEYWORD, word);
      else if(KleinScanner.isType(word))
        return new KleinToken(KleinTokenType.TYPE, word);
      else if(word.equals("true") || word.equals("false"))
        return new KleinToken(KleinTokenType.BOOLEAN, word);
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
  
  private static boolean isOperator(char c)
  {
    switch(c)
    {
      case '+':
      case '-':
      case '*':
      case '/':
      case '<':
      case '=':
        return true;
      default:
        return false;
    }
  }
  
  private static boolean isOperator(String s)
  {
    switch(s)
    {
      case "not":
      case "and":
      case "or":
        return true;
      default:
        return false;
    }
  }
  
  private static boolean isSeperator(char c)
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
      case "if":
      case "then":
      case "function":
      case "main":
      case "print":
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
  
  private String getNumber()
  {
    String s = "";
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