package com.dukes.lang.generator;

public interface CodeGenerator<I, O>
{
    public O generateCode(I ast); //if Java 8, would like to be a static method
}
