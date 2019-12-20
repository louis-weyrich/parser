package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class Block extends ChildContainer{
	
	public Block(Object content) {
		super();
		this.addContent(content);
	}

	@Override
	public NodeType getType() {
		return NodeType.BLOCK;
	}

	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}


}
