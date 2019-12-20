package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Token extends ContentContainer {

	public Token(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.TOKEN;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}


}
