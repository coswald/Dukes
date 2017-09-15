package com.dukes.tests;

import com.dukes.klein.scanner.*;

public class kleins
{
  public static void main(String[] args)
  {
    String parse = "(* Hello world *) " +
                   "function main() : integer\n  print(-1)\n  1";
    StringInputter si = new StringInputter(parse);
    KleinScanner ks = new KleinScanner(si);
    KleinToken currentToken = null;
    while(!(ks.peek()).isTokenType(KleinTokenType.EOF))
    {
      System.out.println(ks.next());
    }
    System.out.println(ks.peek());
  }
}
