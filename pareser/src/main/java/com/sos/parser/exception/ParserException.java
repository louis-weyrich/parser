package com.sos.parser.exception;

public class ParserException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4361950735339958129L;
	protected char token;
	protected int index;


	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, char token, int index) {
		super(message);
		this.token = token;
		this.index = index;
	}

	public char getToken() {
		return token;
	}

	public int getIndex() {
		return index;
	}

}
