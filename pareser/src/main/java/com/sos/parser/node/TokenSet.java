package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class TokenSet extends ContentContainer {

	public TokenSet(String text) {
		addContent(text);
	}

	public void validate(ParserContext context) throws ParserException {
		// TODO Auto-generated method stub

	}

	@Override
	public NodeType getType() {
		return NodeType.TOKEN_SET;
	}

}
