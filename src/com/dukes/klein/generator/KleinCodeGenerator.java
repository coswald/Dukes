package com.dukes.klein.generator;

import com.dukes.klein.parser.node.*;
import com.dukes.klein.semanticchecker.KleinFunctionTable;
import com.dukes.lang.generator.CodeGenerator;
import com.dukes.lang.parser.node.AbstractSyntaxNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public final class KleinCodeGenerator
    implements CodeGenerator<AbstractSyntaxNode, String> {
  private static int line = 0;
  private static ArrayList<String> placeHolders = new ArrayList<String>();
  private static ArrayList<String> memHolders = new ArrayList<String>();

  private KleinFunctionTable kft;
  private HashMap<String, Integer> functionLocation;
  private int tempMemoryAddress = 15;
  private static int tempMemoryAddress2 = 513;

  //false means that they are free
  private boolean[] usedRegisters = new boolean[4]; //we use register 0 for the constant 0, register 6 for return addressing, and 7 is the PC.

  public KleinCodeGenerator(KleinFunctionTable kft) {
    this.kft = kft;
    this.functionLocation = new HashMap<String, Integer>();
  }

  @Override
  public String generateCode(AbstractSyntaxNode ast) {
    String s = "";
    KleinTreeTraverser ktt = new KleinTreeTraverser(ast);
    ktt.cheeseIt();
    s = generateProlog();
    s += generateCodeHelper(ktt.getTop());
    s = backtraceProgram(s);
    return s;
  }

  private String generateProlog() {
    String s = "*\n* PROGRAM\n*\n";

    ArrayList<Integer> parameters = kft.getFunctionParameterTypes("main");
    
    /*
    KleinScanner ks;
    KleinToken value;
    String toGenerate = "";
    int tempAddress = 2;
    for(int i = 0; i < parameters.size(); i++) {
      toGenerate = "";
      ks = new KleinScanner(new StringInputter(args[i]));
      value = ks.generateNextToken();
      if(parameters.get(i).intValue() == AbstractSyntaxNode.INTEGER_TYPE) {
        if(value.getTokenType() == KleinTokenType.SYMBOL && value.getTokenValue().equals("-")) {
          toGenerate += "-";
          value = ks.generateNextToken();
        }
        if(value.getTokenType() == KleinTokenType.INTEGER) {
          toGenerate += value.getTokenValue();
        }
        else {
          throw new IllegalArgumentException("Argument not an integer!");
        }
      }
      else { //Boolean
        if(value.getTokenType() == KleinTokenType.BOOLEAN) {
          toGenerate += (value.getTokenValue().equals("true")) ? "1" : "0";
        }
        else {
          throw new IllegalArgumentException("Argument not a boolean!");
        }
      }
      s += KleinCodeGenerator.emitCode("LDC", "1", toGenerate, "0");
      s += KleinCodeGenerator.emitCode("ST", "1", Integer.toString(tempAddress++), "0");
    }
    */
    ArrayList<String> params = kft.getFunctionParameterNames("main");
    for(int i = 0; i < parameters.size(); i++) {
      String t = KleinCodeGenerator.getPlaceHolder();
      s += KleinCodeGenerator.emitCode("LD", t, Integer.toString(i + 1), "0");
      s += KleinCodeGenerator.emitCode("ST", t, "<main," + params.get(i) + ">", "0");
    }

    s += KleinCodeGenerator.emitCode("LDA", "6", "1", "7");
    s += KleinCodeGenerator.emitCode("LDA", "7", "main", "0");
    s += KleinCodeGenerator.emitCode("OUT", "5", "0", "0");
    s += KleinCodeGenerator.emitCode("HALT", "0", "0", "0");

    if(kft.hasPrint()) {
      functionLocation.put("print", line);
      s += "*\n* PRINT\n*\n";
      s += KleinCodeGenerator.emitCode("LD", "5", "512", "0");
      s += KleinCodeGenerator.emitCode("OUT", "5", "0", "0");
      s += KleinCodeGenerator.emitCode("LDA", "7", "0", "6");
    }

    return s;
  }

  private String generateCodeHelper(AbstractSyntaxNode ast) {
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
      String then = "";
      String s = "";
      String t = KleinCodeGenerator.getPlaceHolder();
      String x = KleinCodeGenerator.getPlaceHolder();
      String y = KleinCodeGenerator.getMemoryHolder();
      ast.setReturnRegister(y);

      test += generateCodeHelper(children[0]);

      int lnum = line;
      line += 2;
      then += generateCodeHelper(children[1]);
      if(children[1] instanceof CallNode) {
        then += KleinCodeGenerator.emitCode("ADD", x, "5", "0");
      }
      else {
        then += KleinCodeGenerator.emitCode("LD", x, children[1].getReturnRegister(), "0");
      }
      then += "* End then Statement\n";

      int snum = line++;
      s += generateCodeHelper(children[2]);
      if(children[2] instanceof CallNode) {
        s += KleinCodeGenerator.emitCode("ADD", x, "5", "0");
      }
      else {
        s += KleinCodeGenerator.emitCode("LD", x, children[2].getReturnRegister(), "0");
      }
      s += "* End else Statement\n";

      test += KleinCodeGenerator.emitCode(lnum, "LD", t, children[0].getReturnRegister(), "0");
      test += KleinCodeGenerator.emitCode(lnum + 1, "JNE", t, Integer.toString(snum + 1), "0");
      test += "* End test condition\n";
      then += KleinCodeGenerator.emitCode(snum, "LDA", "7", Integer.toString(line), "0");

      s += KleinCodeGenerator.emitCode("ST", x, y, "0");
      return test + then + s;
    }
    else if(ast instanceof FunctionNode) {
      AbstractSyntaxNode[] children = ast.getChildren();
      String s = "*\n* Function: " + ((FunctionNode) ast).getName().getValue().toUpperCase() + "\n*\n";
      functionLocation.put(((FunctionNode) ast).getName().getValue(), new Integer(line));

      for(AbstractSyntaxNode c : children) {
        s += generateCodeHelper(c);
      }
      s += ast.toTargetCode();
      return s;
    }
    else if(ast instanceof PrintNode) {
      AbstractSyntaxNode child = ast.getChildren()[0];
      String s = "* CALL PRINT \n";
      if(child instanceof DeclaredNode) {
        s += generateCodeHelper(child);
        s += KleinCodeGenerator.emitCode("ST", child.getReturnRegister(), "512", "0");
        s += ast.toTargetCode();
      }
      else {
        child.setReturnRegister("512");
        s += generateCodeHelper(child);
        s += ast.toTargetCode();
      }
      return s;
    }
    else if(ast instanceof CallNode) {
      HashMap<String, Integer> paramLocs =
          kft.getParamLocationValues(((CallNode) ast).getIdentifier());
      String s =
          "* CALL " + ((CallNode) ast).getIdentifier().toUpperCase() + "\n";
      AbstractSyntaxNode[] children = ast.getChildren();
      AbstractSyntaxNode c;
      for(int i = 0; i < children.length; i++) {
        ArrayList<String> params =
            kft.getFunctionParameterNames(((CallNode) ast).getIdentifier());
        c = children[i];
        if(c instanceof DeclaredNode) {
          s += generateCodeHelper(c);
          s += KleinCodeGenerator.emitCode("ST", c.getReturnRegister(),
              Integer.toString(paramLocs.get(params.get(i))), "0");
        }
        else {
          c.setReturnRegister(Integer.toString(paramLocs.get(params.get(i))));
          s += generateCodeHelper(c);
        }
        s += ast.toTargetCode();
      }
      return s;
    }
    else {
      AbstractSyntaxNode[] children = ast.getChildren();
      String s = "";
      for(AbstractSyntaxNode c : children) {
        s += generateCodeHelper(c);
      }
      s += ast.toTargetCode();
      return s;
    }
  }

  public String backtraceProgram(String s) {

    for(String f : kft.getParamLocations().keySet()) {
      for(String p : kft.getFunctionParameterNames(f)) {
        s = s.replaceAll("<" + f + "," + p + ">", kft.getParamLocationValues(f, p).toString());
      }
    }

    for(String key : functionLocation.keySet()) {
      s = s.replaceAll("," + key + "\\(", "," + Integer.toString(functionLocation.get(key)) + "(");
    }
    for(String holder : placeHolders) {
      s = s.replaceAll(holder, Integer.toString(this.getRegister()));
    }
    for(String mem : memHolders) {
      s = s.replaceAll(mem, Integer.toString(this.getMemory()));
    }
    return s;
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

    for(int i = 0; i < this.usedRegisters.length; i++) {
      this.usedRegisters[i] = false;
    }
    //There are no available registers, so free a random one.
    //This could be smarter.
    //int free = (int)(Math.random() * this.usedRegisters.length);

    //TO IMPLEMENT, for now the templates will handle storing values
    //Each template should use less than 4 registers.

    //this.usedRegisters[free] = false;
    return this.getRegister();
  }

  public int getMemory() {
    return this.tempMemoryAddress++;
  }

  public static int getStackInstance() {
    return tempMemoryAddress2++;
  }

  private static String randomString() {
    String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String SALTCHARS2 = "1234567890";
    StringBuilder salt = new StringBuilder();
    Random rnd = new Random();
    String saltStr = "";
    while(salt.length() < 12) { // length of the random string.
      int index = (int) (rnd.nextFloat() * SALTCHARS.length());
      salt.append(SALTCHARS.charAt(index));
    }
    salt.append(SALTCHARS2.charAt((int) (rnd.nextFloat() * SALTCHARS2.length())));
    saltStr = salt.toString();
    return saltStr;
  }

  public static String getPlaceHolder() {
    String saltStr = "";
    while(true) {
      saltStr = KleinCodeGenerator.randomString();
      if(!placeHolders.contains(saltStr)) {
        break;
      }
    }
    placeHolders.add(saltStr);
    return saltStr;
  }

  public static String getMemoryHolder() {
    String saltStr = "";
    while(true) {
      saltStr = KleinCodeGenerator.randomString();
      if(!memHolders.contains(saltStr)) {
        break;
      }
    }
    memHolders.add(saltStr);
    return saltStr;
  }

  public static String emitCode(int lineNum, String instruction, String op1, String op2, String op3) {
    return lineNum + ": " + instruction + " " + op1 + "," + op2 + emitCodeHelper(instruction, op3) + "\n";
  }

  public static String emitCode(String instruction, String op1, String op2, String op3) {
    return KleinCodeGenerator.emitCode(line++, instruction, op1, op2, op3);
  }

  public static int getLine() {
    return line;
  }

  private static String emitCodeHelper(String instruction, String op) {
    switch(instruction) {
      case "IN":
      case "OUT":
      case "ADD":
      case "SUB":
      case "MUL":
      case "DIV":
      case "HALT":
        return "," + op;
      case "LDC":
      case "LDA":
      case "LD":
      case "ST":
      case "JEQ":
      case "JNE":
      case "JLT":
      case "JLE":
      case "JGT":
      case "JGE":
      default:
        return "(" + op + ")";
    }
  }
}
