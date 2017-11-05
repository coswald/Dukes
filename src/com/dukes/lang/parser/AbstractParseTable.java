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
package com.dukes.lang.parser;

import java.util.List;

/**
 * <p>Constructs a parse functionParamTable with defined rules. A parse functionParamTable is accessed by
 * two enums, one being non-terminal types and the other being terminal values.
 * That is why the first two generics exist; the third is for describing what
 * type of rules are stored in the parse functionParamTable.</p>
 * @author Coved W Oswald
 * @version 1.0
 * @since 0.2.0
 */
public abstract class AbstractParseTable<K1 extends Enum<K1>,
    K2 extends Enum<K2>, V extends AbstractRule> {
  
  /**
   * Describes the functionParamTable as a two-dimensional list.
   */
  protected List<List<V>> table;
  
  /**
   * The only constructor. Provided so that it is not needed to be specified
   * in the subclasses.
   */
  protected AbstractParseTable() { }

  /**
   * Returns the rule found by the two keys.
   * @param nonTerminal The non terminal value to look up.
   * @param terminalType The terminal found to compare to the non terminal.
   * @return The rule found at the non terminal and terminal value.
   */
  protected abstract V getRule(K1 nonTerminal, K2 terminalType);
  
  /**
   * Adds a rule to the parse functionParamTable.
   * @param nonTerminal The non terminal value to look up.
   * @param terminalType The terminal found to compare to the non terminal.
   * @param value The new rule to add.
   */
  protected abstract void addRule(K1 nonTerminal, K2 terminalType, V value);
}
