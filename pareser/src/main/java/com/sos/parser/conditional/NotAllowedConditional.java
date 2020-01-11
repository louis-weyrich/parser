package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

public class NotAllowedConditional implements Conditional {

	public NotAllowedConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(object.getContent().length() == 1 && context.getTokensNotAllowed().contains(object.toString().charAt(0)))
		{
			NodeContainer node = listener.getStack().peekTop();
			if(node.getType() != NodeType.STATEMENT && node.getType() != NodeType.QUOTED_TEXT)
			{
				listener.startStatement();
			}
			
			if(node.getType() != NodeType.QUOTED_TEXT)
			{
				listener.tokenNotAllowed(object);
			}
			
			return true;
		}
		return false;
	}

}
