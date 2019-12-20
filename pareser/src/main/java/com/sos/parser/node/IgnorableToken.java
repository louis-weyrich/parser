package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class IgnorableToken extends ContentContainer{
	
	public IgnorableToken(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.IGNORABLE_TOKEN;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}

}
