package com.dukes.klein.parser;

import com.dukes.klein.scanner.KleinToken;
import com.dukes.klein.scanner.KleinTokenType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dan Holland
 */
public class ParseTable {
    private ArrayList<ArrayList<KleinRule>> PARSETABLE;

    public ParseTable(){
        this.PARSETABLE = new ArrayList<>();
    }

    public KleinRule getRule(NonTerminalType nt, TerminalType tk){
        return this.PARSETABLE.get(nt.getId()).get(tk.getId());
    }

    public KleinRule getRule(NonTerminalType nt, KleinTokenType tk){
        return this.PARSETABLE.get(nt.getId()).get(tk.getId());
    }


    public void addRule(NonTerminalType nonTerminal, TerminalType terminal, KleinRule kr){
        this.PARSETABLE.get(nonTerminal.getId()).add(terminal.getId(), kr);
    }

    public void addRule(int nonTerminal, int terminal, KleinRule kr){
        this.PARSETABLE.get(nonTerminal).add(terminal, kr);
    }


}
