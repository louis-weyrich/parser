package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Document extends ChildContainer{

	
	public Document() {
		super();
	}

	@Override
	public NodeType getType() {
		return NodeType.DOCUMENT;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}

}
