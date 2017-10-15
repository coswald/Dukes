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

import com.dukes.lang.parser.AbstractParseTable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Coved W Oswald
 * @author Dan Holland
 */
public class KleinParseTable extends
    AbstractParseTable<NonTerminalType, TerminalType, KleinRule> {
  
  public KleinParseTable() {
    this.table = new ArrayList<List<KleinRule>>();
    for(int i = 0; i < NonTerminalType.values().length; i++) {
      this.table.add(new ArrayList<KleinRule>());
      for(int j = 0; j < TerminalType.values().length; j++) {
        this.table.get(i).add(new KleinRule());
      }
    }
  }

  @Override
  public KleinRule getRule(NonTerminalType nt, TerminalType tk) {
    return this.table.get(nt.getId()).get(tk.getId());
  }
  
  @Override
  public void addRule(NonTerminalType t1, TerminalType t2, KleinRule kr) {
    this.table.get(t1.getId()).set(t2.getId(), kr);
  }
}
