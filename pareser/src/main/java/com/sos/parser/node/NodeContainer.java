package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public interface NodeContainer {
	
	
	public void addContent(Object content);
	
	public abstract Object getContent();
	
	public abstract NodeType getType();
	
	public abstract void validate(ParserContext context) throws ParserException;
}
