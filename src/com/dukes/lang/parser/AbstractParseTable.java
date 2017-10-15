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
 * @author Coved W Oswald
 */
public abstract class AbstractParseTable<K1 extends Enum<K1>,
    K2 extends Enum<K2>, V extends AbstractRule> {
  
  protected List<List<V>> table;
  
  protected AbstractParseTable() { }

  public abstract V getRule(K1 nonTerminal, K2 terminalType);

  public abstract void addRule(K1 nonTerminal, K2 terminalType, V value);
}
