package com.sos.parser.exception;

import com.sos.parser.ParserObject;

public class ParserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4361950735339958129L;
	protected char token;
	protected int index;
	protected ParserObject object;


	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, char token, int index) {
		super(message);
		this.token = token;
		this.index = index;
	}
	

	public ParserException(String message, ParserObject object) {
		super(message);
		this.object = object;
	}

	public char getToken() {
		return token;
	}

	public int getIndex() {
		return index;
	}
	
	public ParserObject getParserObject()
	{
		return this.object;
	}

}
