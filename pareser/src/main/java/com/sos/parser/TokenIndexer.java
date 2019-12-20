package com.sos.parser;

import com.sos.parser.exception.TokenNotAllowedException;
import com.sos.parser.io.ArrayBuffer;

public class TokenIndexer
{
	protected char token;
	protected int startIndex;
	protected ArrayBuffer buffer;
	protected TokenNotAllowedException exception;



	public TokenIndexer(char token, int startIndex, ArrayBuffer buffer, TokenNotAllowedException exception)
	{
		this.token = token;
		this.startIndex = startIndex;
		this.buffer = buffer;
		this.exception = exception;
	}


	public ArrayBuffer getBuffer()
	{
		return buffer;
	}


	public char getToken()
	{
		return token;
	}


	public int getStartIndex()
	{
		return startIndex;
	}
	
	
	public TokenNotAllowedException getException() {
		return exception;
	}

	

	public void setStartIndex(int startIndex)
	{
		this.startIndex = startIndex;
	}
	
	public int increaseIndexByOne() {
		return this.startIndex + 1;
	}
}
