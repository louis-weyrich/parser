package com.sos.parser;

import com.sos.parser.exception.TokenNotAllowedException;

public class ParserStatement extends ParserObject{
	
	 

	public ParserStatement(String content, int startIndex, int endIndex, TokenNotAllowedException exception) {
		super(content, startIndex, endIndex, exception);
	}

}
