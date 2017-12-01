package com.dukes.klein.generator;

import com.dukes.lang.generator.CodeGenerator;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.klein.semanticchecker.KleinFunctionTable;
import com.dukes.klein.parser.node.ProgramNode;
import com.dukes.klein.parser.node.FunctionNode;
import com.dukes.klein.parser.node.IfNode;

public final class KleinCodeGenerator
    implements CodeGenerator<AbstractSyntaxNode, String>
{
  private static int line = 0;
  
  private KleinFunctionTable kft;
  private int tempMemoryAddress = 512;
  
  //false means that they are free
  private boolean[] usedRegisters = new boolean[5]; //we use register 0 for the constant 0, register 6 for return addressing, and 7 is the PC.
  
  public KleinCodeGenerator(KleinFunctionTable kft) {
    this.kft = kft;
  }
  
  @Override
  public String generateCode(AbstractSyntaxNode ast) {
    /*return "  0:    LDA  6,1(7)\n" +
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
    */
    return generateCodeHelper(ast);
  }
  
  public String generateCodeHelper(AbstractSyntaxNode ast) {
    if(ast.getChildren().length <= 0) {
      return ast.toTargetCode();
    }
    else if(ast instanceof ProgramNode) { 
      AbstractSyntaxNode[] children = ast.getChildren();
      String s = ast.toTargetCode();
      for(AbstractSyntaxNode c : children) {
        s += generateCodeHelper(c);
      }
      return s;
    }
    else if(ast instanceof IfNode) {
      AbstractSyntaxNode[] children = ast.getChildren();
      String test = "*\n* IF\n*\n";
      String s = "";
      test += generateCodeHelper(children[2]);
      int lnum = line++;
      s += generateCodeHelper(children[1]);
      int snum = line;
      s += generateCodeHelper(children[0]);
      
      test += KleinCodeGenerator.emitCode(Integer.toString(lnum), "JNE", Integer.toString(snum), "7");
      return test + s;
    }
    else {
      AbstractSyntaxNode[] children = ast.getChildren();
      String s = (ast instanceof FunctionNode) ? ("*\n* Function: " + ((FunctionNode)ast).getName().getValue().toUpperCase() + "\n*\n") : "";
      for(AbstractSyntaxNode c : children) {
        s += generateCodeHelper(c);
      }
      s += ast.toTargetCode();
      return s;
    }
  }
  
  /*
   * THIS HAS SIDE EFFECTS
   */
  public int getRegister() {
    for(int i = 0; i < this.usedRegisters.length; i++) {
      if(!this.usedRegisters[i]) {
        this.usedRegisters[i] = true;
        return i + 1;
      }
    }
    
    //There are no available registers, so free a random one.
    //This could be smarter.
    int free = (int)(Math.random() * this.usedRegisters.length);
    
    //TO IMPLEMENT, for now the templates will handle storing values
    
    this.usedRegisters[free] = false;
    return free;
  }
  
  public static String emitCode(int lineNum, String instruction, String op1, String op2, String op3) {
    return "  " + lineNum + ":\t" + instruction + "  " + op1 + "," + op2 + emitCodeHelper(instruction, op3) + "\n";
  }
  
  public static String emitCode(String instruction, String op1, String op2, String op3) {
    return KleinCodeGenerator.emitCode(line++, instruction, op1, op2, op3);
  }
  
  public static int getLine() {
    return line;
  }
  
  private static String emitCodeHelper(String instruction, String op) {
    switch(instruction) {
      case "IN"  :
      case "OUT" :
      case "ADD" :
      case "SUB" :
      case "MUL" :
      case "DIV" :
      case "HALT":
        return "," + op;
      case "LDC" :
      case "LDA" :
      case "LD"  :
      case "ST"  :
      case "JEQ" :
      case "JNE" :
      case "JLT" :
      case "JLE" :
      case "JGT" :
      case "JGE" :
      default:
        return "(" + op + ")";
    }
  }
}
