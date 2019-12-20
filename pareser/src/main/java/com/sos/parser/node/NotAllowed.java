package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class NotAllowed extends ContentContainer {
	
	public NotAllowed(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.NOT_ALLOWED;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}


}
