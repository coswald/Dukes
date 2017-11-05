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
package com.dukes.klein.scanner;

import com.dukes.lang.scanner.AbstractScanner;
import com.dukes.lang.scanner.Inputter;
import com.dukes.lang.scanner.LexicalAnalysisException;
import com.dukes.lang.scanner.LexicalScanningException;

/**
 * Scans through an {@code Inputter} and generates {@code KleinToken}s.
 *
 * @author Coved W Oswald
 * @author Daniel Holland
 * @version 1.0
 * @since 0.1.0
 */
public class KleinScanner extends AbstractScanner<KleinToken> {

  /**
   * The max value that an integer can be in Klein. This corresponds to
   * 2<sup>32</sup> - 1.
   */
  public static final long MAX_VALUE = (1L << 32);

  /**
   * The min value that an integer can be in Klein. This corresponds to
   * 1 - 2<sup>32</sup>.
   */
  public static final long MIN_VALUE = ~MAX_VALUE;

  private boolean inComment;
  private boolean atEOF = false;

  /**
   * Makes a {@code KleinScanner} object. This will call the super's
   * constructor.
   *
   * @param input The {@code Inputter} class to use.
   * @see AbstractScanner
   */
  public KleinScanner(Inputter input) {
    super(input);
    this.inComment = false;
  }

  /**
   * Generates a {@code KleinToken} with the {@code KleinTokenType} found in
   * the given spot in the {@code Inputter}. This, however, does not work if
   * the {@code Inputter} finds an unknown character: this will throw an error.
   * It also doesn't work if the EOF token is found without a comment ending,
   * or if an integer is larger than the {@code MAX_VALUE}, or if an identifier
   * is longer than 256.
   *
   * @return The next {@code KleinToken} in the {@code Inputter}.
   * @throws LexicalScanningException If an invalid character is encountered.
   * @throws LexicalAnalysisException If EOF is reached without a comment
   *     ending, an integer is larger than the max value, or in an identifier
   *     is longer than 256 characters.
   */
  public KleinToken generateNextToken()
      throws LexicalScanningException, LexicalAnalysisException {
    this.skipWhiteSpace();
    if(this.inComment) {
      this.skipUntilEndComment();
    }
    //EOF
    if(this.atEOF) {
      return new KleinToken(KleinTokenType.EOF);
    }
    //CommentsStart or LeftParenthesis
    //if(endOfFile()){return new KleinToken(KleinTokenType.EOF);}
    char current = this.input.currentChar();
    if(current == '(') {
      if(endOfFile()) {
        return new KleinToken(KleinTokenType.EOF);
      }
      if(this.input.lookAhead() == '*') {
        this.input.next();
        this.inComment = true;
        if(endOfFile()) {
          return new KleinToken(KleinTokenType.EOF);
        }
        this.input.next();
        return new KleinToken(KleinTokenType.STARTCOMMENT);
      }
      else {
        this.input.next();
        return new KleinToken(KleinTokenType.LEFTPARENTHESIS);
      }
    }
    //CommentEnd or *
    else if(current == '*') {
      if(endOfFile()) {
        return new KleinToken(KleinTokenType.EOF);
      }
      if(this.input.lookAhead() == ')') {
        this.input.next();
        if(!this.inComment)
          throw new LexicalAnalysisException("Comment ended but not started!");
        this.inComment = false;
        if(endOfFile()) {
          return new KleinToken(KleinTokenType.EOF);
        }
        this.input.next();
        return new KleinToken(KleinTokenType.ENDCOMMENT);
      }
      else // Should never happen
      {
        this.input.next();
        return new KleinToken(KleinTokenType.SYMBOL, "*");
      }
    }

    //Rightparenthesis
    if(current == ')') {
      if(endOfFile()) {
        return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
      }
      this.input.next();
      return new KleinToken(KleinTokenType.RIGHTPARENTHESIS);
    }
    //Single-char Expression symbol
    else if(KleinScanner.isSymbol(current)) {
      if(endOfFile()) {
        return new KleinToken(KleinTokenType.EOF);
      }
      this.input.next();
      return new KleinToken(KleinTokenType.SYMBOL,
          Character.toString(current));
    }
    //Seperator
    else if(KleinScanner.isSeparator(current)) {
      if(endOfFile()) {
        return new KleinToken(KleinTokenType.EOF);
      }
      this.input.next();
      return new KleinToken(KleinTokenType.SEPARATOR,
          Character.toString(current));
    }
    //Integer
    else if(KleinScanner.isDigit(current)) {
      String num = this.getNumber();
      long t = Long.parseLong(num);
      if(t > MAX_VALUE || t < MIN_VALUE) {
        throw new LexicalAnalysisException("Number " + t + " out of the " +
            "range" + MAX_VALUE + " " +
            MIN_VALUE + "!");
      }
      return new KleinToken(KleinTokenType.INTEGER, num);
    }

    //Keyword, Identifier, Type, boolean, SimpleExpression, Term, If, then,
    //not, and else
    else if(KleinScanner.isLetter(current)) {
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
      else {
        if(word.length() > 256)
          throw new LexicalAnalysisException("An Identifier can not be any " +
              "longer than 256 character!");
        return new KleinToken(KleinTokenType.IDENTIFIER, word);
      }
    }
    //Unrecognized character/tokentype.
    else {
      if(this.endOfFile()) {
        return new KleinToken(KleinTokenType.EOF);
      }
      throw new LexicalScanningException("The character '" + current +
          "' is invalid.");
    }
  }

  /**
   * Determines whether a single character is a digit within Klein.
   *
   * @param c The character to test
   * @return True if the character inputted is 0 through 9
   */
  public static boolean isDigit(char c) {
    return c >= 48 && c <= 57; //'0' through '9'
  }

  /**
   * Determines whether a single character is a digit within Klein.
   *
   * @param c The character to test
   * @return True fi the character is between 65 and 90 OR 97 and 122
   */
  public static boolean isLetter(char c) {
    return (c >= 65 && c <= 90) || (c >= 97 && c <= 122);
  }

  private static boolean isSymbol(char c) {
    switch(c) {
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

  private static boolean isSymbol(String s) {
    switch(s) {
      case "and":
      case "or":
      case "not":
        return true;
      default:
        return false;
    }
  }

  private boolean endOfFile() {
    if(!this.input.hasNext()) {
      if(this.inComment) {
        throw new LexicalAnalysisException("Comment started but not ended!");
      }
      this.atEOF = true;
      return true;
    }
    return false;
  }

  private static boolean isSeparator(char c) {
    return c == ',' || c == ':';
  }

  private static boolean isKeyword(String s) {
    switch(s) {
      case "function":
      case "print":
      case "if":
      case "then":
      case "else":
        return true;
      default:
        return false;
    }
  }

  private static boolean isType(String s) {
    switch(s) {
      case "integer":
      case "boolean":
        return true;
      default:
        return false;
    }
  }

  private void skipWhiteSpace() {
    while(this.input.hasNext() &&
        Character.isWhitespace(this.input.currentChar())) {
      this.input.next();
    }
  }

  private void skipUntilEndComment() {
    while(this.input.hasNext() &&
        !(this.input.lookAhead() == ')' && this.input.currentChar() == '*')) {
      this.input.next();
    }
  }

  private String getNumber() {
    //this still allows for negative zero to be a thing.
    String s = "";
    if(this.input.currentChar() == '0' && !this.endOfFile() &&
        KleinScanner.isDigit(this.input.lookAhead())) {
      throw new LexicalAnalysisException("Number cannot start with 0!");
    }

    while(KleinScanner.isDigit(this.input.currentChar())) {
      if(!KleinScanner.isDigit(this.input.currentChar()))
        throw new LexicalAnalysisException("Non-numeric character " +
            this.input.currentChar() +
            " received when parsing number!");
      s += this.input.currentChar();
      if(this.endOfFile()) {
        break;
      }
      this.input.next();
    }

    return s;
  }

  private String getWord() {
    String s = "";
    while((KleinScanner.isLetter(this.input.currentChar()) ||
        KleinScanner.isDigit(this.input.currentChar()) ||
        this.input.currentChar() == '_')) {
      s += this.input.currentChar();
      if(this.endOfFile()) {
        break;
      }
      this.input.next();
    }

    return s;
  }
}
