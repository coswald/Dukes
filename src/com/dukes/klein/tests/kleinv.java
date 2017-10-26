package com.dukes.klein.tests;

import com.dukes.klein.parser.KleinParser;
import com.dukes.klein.parser.node.KleinTreeTraverser;
import com.dukes.klein.scanner.KleinScanner;
import com.dukes.lang.parser.node.AbstractSyntaxNode;
import com.dukes.lang.scanner.FileInputter;
import com.dukes.lang.tests.Test;

import java.io.FileInputStream;

/**
 * @author Daniel J. Holland
 * @version 1.0
 */
public class kleinv extends Test{

  /**
   * Constructs the test with the given arguments.
   * @param args The arguments.
   */
  public kleinv(String... args) {
    super("Usage: kleinv [-chw] [file]\n" +
            "Runs semantic analyzer that ensures a Klein program " +
            "satisfies the entire language specification.",
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
    System.out.print(ast.toString());
  }

  /**
   * Main function.
   * @param args The arguments.
   */
  public static void main(String... args) {
    kleinv run = new kleinv(args);
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
