package com.dukes.klein.parser;

public interface Parser {

    public boolean isValid();
    public AbstractSyntaxNode generateAST();

}
