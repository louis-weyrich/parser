package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Text extends ContentContainer {

	public Text(String text) 
	{
		addContent(text);
	}

	public void validate(ParserContext context) throws ParserException {
		// TODO Auto-generated method stub

	}

	@Override
	public NodeType getType() {
		return NodeType.TEXT;
	}

}
