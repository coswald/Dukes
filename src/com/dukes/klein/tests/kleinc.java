package com.dukes.klein.tests;

import com.dukes.klein.generator.KleinCodeGenerator;
//import com.dukes.klein.generator.PostfixGenerator;
import com.dukes.klein.parser.KleinParser;
import com.dukes.klein.parser.node.KleinTreeTraverser;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.scanner.FileInputter;
import com.dukes.lang.tests.Test;

import java.io.FileInputStream;

/**
 * @author Coved W Oswald
 * @version 1.0
 */
public class kleinc extends Test {

  /**
   * Constructs the test with the given arguments.
   * @param args The arguments.
   */
  public kleinc(String... args) {
    super("Usage: kleinc [-chw] [file]\n" +
            "Compiles a Klein Program.",
        args);
  }

  /**
   * Runs the test by running a semantic analyzer on the given klein file.
   * @throws Exception If the semantic analyzer throws and exception.
   */
  @Override
  public void doRun() throws Exception {
    FileInputter fi = new FileInputter(new FileInputStream(fileName));
    KleinScanner ks = new KleinScanner(fi);
    KleinParser kp = new KleinParser(ks);
    AbstractSyntaxNode ast = kp.generateAST();
    KleinTreeTraverser ktt = new KleinTreeTraverser(ast);
    ktt.semanticCheck();
    
    KleinCodeGenerator kcg = new KleinCodeGenerator(ktt.getFunctionTable());
    String tm = kcg.generateCode(ast);
    System.out.println(tm);
    
    /*
    PostfixGenerator pg = new PostfixGenerator();
    String tm2 = pg.generateCode(ast);
    System.out.println(tm2);
    */
  }

  /**
   * Main function.
   * @param args The arguments.
   */
  public static void main(String... args) {
    kleinc run = new kleinc(args);
    Thread t = new Thread(run);
    t.start();
    try {
      t.join();
    }
    catch(InterruptedException ie) {
      ie.printStackTrace();
    }
    System.exit(run.getExitStatus());
  }

}
