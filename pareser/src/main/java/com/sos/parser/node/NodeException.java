package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public class NodeException implements NodeContainer {

	private Exception exception;
	
	public NodeException(Exception exception) {
		this.addContent(exception);
	}

	public void addContent(Object content) 
	{
		if(content instanceof Exception)
		{
			exception = (Exception)content;
		}
	}

	public Exception getContent() 
	{
		return exception;
	}

	public NodeType getType() {
		return NodeType.NODE_EXCEPTION;
	}


	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}

}
