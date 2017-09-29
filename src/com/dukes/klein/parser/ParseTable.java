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
        for (int i = 0; i <= 26; i++){
            this.PARSETABLE.add(new ArrayList<>());
            for (int j = 0; j <= 17; j++){
                this.PARSETABLE.get(i).add(new KleinRule());
            }
        }
    }

    public KleinRule getRule(NonTerminalType nt, KleinTokenType tk){
        return this.PARSETABLE.get(nt.getId()).get(tk.getId());
    }

    public void addRule(NonTerminalType t1, KleinTokenType t2, KleinRule kr){
        this.PARSETABLE.get(t1.getId()).set(t2.getId(), kr);
    }

}
