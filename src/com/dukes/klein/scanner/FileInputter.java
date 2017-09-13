package com.dukes.klein.scanner;

import com.dukes.klein.scanner.Inputter;
import com.dukes.klein.scanner.LexicalScanningException;

import java.io.FileInputStream;
import java.io.IOException;

public class FileInputter extends Inputter
{
  
  private FileInputStream fis;
  
  public FileInputter(FileInputStream fis)
  {
    this.fis = fis;
  }
  
  @Override
  public boolean hasNext()
  {
    try
    {
      return fis.available() != 0;
    }
    catch(IOException io)
    {
      throw new LexicalScanningException("While Parsing a file, the file " +
                                         "input stream went awry!");
    }
  }
  
  @Override
  protected char lookAhead()
  {
    try
    {
      return (char)fis.read();
    }
    catch(IOException io)
    {
      throw new LexicalScanningException("While parsing a file, the file " +
                                         "input ran into an unexpected error!");
    }
  }
}