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
}
