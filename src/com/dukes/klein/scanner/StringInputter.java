package com.dukes.klein.scanner;

import com.dukes.klein.scanner.Inputter;

import java.lang.String;

//I chose not to use the Iterator<string> class, as I felt It didn't do a char
//by char comparison.
public class StringInputter extends Inputter
{
  
  private String parse;
  
  public StringInputter(String parse)
  {
    this.parse = parse + " "; //there is a reason I add a space. This is a lazy
    //way to fix a problem that needs to be handled within the kleinscanner
    //class. It will not read the last token unless there is whitespace at the
    //end of the file.
    this.position = 0;
    if(this.hasNext())
      this.next(); //needed. Otherwise, it won't scan at all.
  }
  
  @Override
  public boolean hasNext()
  {
    if(position >= parse.length())
      return false;
    return true;
  }
  
  @Override
  protected char lookAhead()
  {
    return parse.charAt(position);
  }
}