package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

public class IgnorableTokenCondition implements Conditional {

	public IgnorableTokenCondition() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		String content = object.getContent();
		
		if(content.length() > 0 && context.getIgnorableSet().contains(content.charAt(0)))
		{
			if(context.isShowIgnorables()) 
			{
				listener.parsedIgnorableToken(object);
			}
			return true;
		}
		
		return false;
	}

}
