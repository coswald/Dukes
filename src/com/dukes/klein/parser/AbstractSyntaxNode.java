package com.dukes.klein.parser;

import com.dukes.klein.scanner.AbstractToken;

import java.util.List;

public abstract class AbstractSyntaxNode<E extends AbstractToken> {

    private E token;
    private List<AbstractSyntaxNode<E>> children;

    protected AbstractSyntaxNode(E token, List<AbstractSyntaxNode<E>> children)
    {
        this.token = token;
        this.children = children;
    }

    public E getToken()
    {
        return this.token;
    }

    public AbstractSyntaxNode<E> getChild(int index)
    {
        return this.children.get(index);
    }
}
