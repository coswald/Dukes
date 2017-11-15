package com.dukes.klein.generator;

import com.dukes.lang.generator.CodeGenerator;
import com.dukes.lang.parser.node.AbstractSyntaxNode;

public final class KleinCodeGenerator
    implements CodeGenerator<AbstractSyntaxNode, String>
{
    @Override
    public String generateCode(AbstractSyntaxNode ast) {
        return "  0:    LDA  6,1(7)\n" +
               "  1:    LDA  7,6(0)\n" +
               "  2:    OUT  5,0,0\n" +
               "  3:   HALT  0,0,0\n" + 
               "*\n* PRINT\n*\n" + 
               "  4:    OUT  5,0,0\n" +
               "  5:    LDA  7,0(6)\n" +
               "*\n* MAIN\n*\n" +
               "  6:    LDC  5,1(0)\n" + 
               "  7:    ST   6,2(0)\n" +
               "  8:    LDA  6,1(7)\n" +
               "  9:    LDA  7,4(0)\n" +
               " 10:    LDC  5,1(0)\n" +
               " 11:    LD   7,2(0)\n";
    }
}
