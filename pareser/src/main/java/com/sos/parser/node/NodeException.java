package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;

public class NodeException implements NodeContainer {

	private Exception exception;
	private ParserObject object;
	
	public NodeException(Exception exception, ParserObject object) {
		this.addContent(exception);
		this.object = object;
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
	
	public ParserObject getParserobject()
	{
		return this.object;
	}


	public void validate(ParserContext cntxt) throws ParserException  {
		// TODO Auto-generated method stub
		
	}

}
