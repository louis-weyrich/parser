package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Keyword extends ContentContainer {

	public Keyword(String keyword) {
		super();
		this.addContent(keyword);
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}


	@Override
	public NodeType getType() {
		return NodeType.KEYWORD;
	}

}
