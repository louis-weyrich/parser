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
		NodeContainer node = listener.getStack().peek();
		if(object.getContent().equals("\n") && (node.getType() == NodeType.DOCUMENT || node.getType() == NodeType.BLOCK))
		{
			listener.startStatement();
		}
		else if(context.getIgnorableSet().contains(object.getContent().charAt(0)))
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
