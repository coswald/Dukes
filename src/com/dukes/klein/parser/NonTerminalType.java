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
 * @author Dan Holland
 * @version 1.0
 */
public enum NonTerminalType {
  PROGRAM(0),
  DEFINITIONS(1),
  DEF(2),
  FORMALS(3),
  NONEMPTYFORMALS(4),
  NONEMPTYFORMALS_PRIME(5),
  FORMAL(6),
  BODY(7),
  TYPE(8),
  EXPR(9),
  SIMPLE_EXPR(10),
  TERM(11),
  FACTOR(12),
  FACTOR_SYMBOL(13),
  EXPR_SYMBOL(14),
  SIMPLE_EXPR_SYMBOL(15),
  TERM_SYMBOL(16),
  ACTUALS(17),
  NONEMPTYACTUALS(18),
  NONEMPTYACTUALS_PRIME(19),
  LITERAL(20),
  IDENTIFIER(21),
  PRINT_STATEMENT(22),
  EXPR_END(23),
  SIMPLE_EXPR_END(24),
  TERM_END(25),
  FACTOR_END(26);

  private final int id;

  NonTerminalType(int id) {
    this.id = id;
  }

  public int getId() {
    return id;
  }
}
