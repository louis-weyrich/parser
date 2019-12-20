package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class QuotedText extends ChildContainer {
	
	public QuotedText() {
		super();
	}

	@Override
	public NodeType getType() {
		return NodeType.QUOTED_TEXT;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}


}
