package com.sos.parser.exception;

public class TokenNotAllowedException extends ParserException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -5673267781847578474L;

	public TokenNotAllowedException(String message, char token, int index) {
		super(message, token, index);
	}

	

}
