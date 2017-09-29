package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinTokenType;

import java.util.ArrayList;

/**
 * @author Dan Holland
 */
public class ParseTable {
    private ArrayList<ArrayList<KleinRule>> PARSETABLE;

    public ParseTable(){
        this.PARSETABLE = new ArrayList<>();
    }

    public KleinRule getRule(NonTerminalType nt, KleinTokenType tk){
        return this.PARSETABLE.get(nt.getId()).get(tk.getId());
    }

    public void addRule(NonTerminalType t1, KleinTokenType t2, KleinRule kr){
        this.PARSETABLE.get(t1.getId()).add(t2.getId(), kr);
    }

    public void addRule(int t1, int t2, KleinRule kr){
        this.PARSETABLE.get(t1).add(t2, kr);
    }


}
