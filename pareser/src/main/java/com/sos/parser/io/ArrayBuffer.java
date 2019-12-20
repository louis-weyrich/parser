package com.sos.parser.io;

public interface ArrayBuffer
{
	public char [] subset(int startIndex, int length) 
			throws ArrayIndexOutOfBoundsException;
	
	public String getSubsetAsString(int startIndex, int length) 
			throws ArrayIndexOutOfBoundsException;
	
	public char chatAt(int index);
	
	public int getBufferSize();
	
	public boolean exceedsMaxBufferSize(int growth);
	
	public int available();
}
