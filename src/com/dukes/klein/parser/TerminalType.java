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
package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinToken;

/**
 * @author Daniel J. Holland
 * @version 1.0
 *          Created on 9/28/2017.
 */
public enum TerminalType {
  FUNCTION(0),
  MAIN(1),
  PRINT(2),
  IF(3),
  THEN(4),
  ELSE(5),
  LEFTPAREN(6),
  RIGHTPAREN(7),
  COMMA(8),
  COLON(9),
  LESSTHAN(10),
  EQUAL(11),
  PLUS(12),
  MINUS(13),
  PRODUCT(14),
  DIVIDE(15),
  AND(16),
  OR(17),
  NOT(18),
  INTEGER_STR(19),
  BOOLEAN_STR(20),
  STRING(21),
  NUMBER(22),
  BOOLEAN(23),
  EOF(24),
  EMPTY(25);

  private final int id;

  TerminalType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }

  public static TerminalType getTerminal(KleinToken kt) {
    switch(kt.getTokenType()) {
      case BOOLEAN:
        return TerminalType.BOOLEAN;
      case INTEGER:
        return TerminalType.NUMBER;
      case IDENTIFIER:
        return TerminalType.STRING;
      case LEFTPARENTHESIS:
        return TerminalType.LEFTPAREN;
      case RIGHTPARENTHESIS:
        return TerminalType.RIGHTPAREN;
      case SEPARATOR:
        switch(kt.getTokenValue()) {
          case ":":
            return TerminalType.COLON;
          case ",":
            return TerminalType.COMMA;
          default:
            //ERROR
        }
      case KEYWORD:
        switch(kt.getTokenValue()) {
          case "function":
            return TerminalType.FUNCTION;
          case "main":
            return TerminalType.STRING;
          case "print":
            return TerminalType.PRINT;
          case "if":
            return TerminalType.IF;
          case "then":
            return TerminalType.THEN;
          case "else":
            return TerminalType.ELSE;
          default:
            // ERROR
        }
      case TYPE:
        switch(kt.getTokenValue()) {
          case "boolean":
            return TerminalType.BOOLEAN_STR;
          case "integer":
            return TerminalType.INTEGER_STR;
          default:
            //ERROR?
        }
      case SYMBOL:
        switch(kt.getTokenValue()) {
          //+, -, <, =, *, /, and, or, not
          case "+":
            return TerminalType.PLUS;
          case "-":
            return TerminalType.MINUS;
          case "<":
            return TerminalType.LESSTHAN;
          case "=":
            return TerminalType.EQUAL;
          case "*":
            return TerminalType.PRODUCT;
          case "/":
            return TerminalType.DIVIDE;
          case "and":
            return TerminalType.AND;
          case "or":
            return TerminalType.OR;
          case "not":
            return TerminalType.NOT;
          default:
            // ERROR
        }
      case EOF:
        return TerminalType.EOF;
      case STARTCOMMENT:
      case ENDCOMMENT:
      default:
        return TerminalType.EMPTY;
    }
  }
}
