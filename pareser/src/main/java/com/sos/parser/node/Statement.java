package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Statement extends ChildContainer{
	
	
	public Statement() {
		super();
	}
	
	@Override
	public NodeType getType() {
		return NodeType.STATEMENT;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}

}
