package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeType;

public class TokenConditional implements Conditional {

	public TokenConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(context.getParsableTokens().contains(object.getContent()))
		{
//			if(listener.getStack().peekTop().getType() != NodeType.STATEMENT)
//			{
//				listener.startStatement();
//			}
			listener.parsedToken(object);
			return true;
		}
		else if(context.getTokenTree().containsText(object.getContent()))
		{
//			if(listener.getStack().peekTop().getType() != NodeType.STATEMENT)
//			{
//				listener.startStatement();
//			}
			listener.parsedToken(object);
			return true;
		}
		else 
		{
			listener.addText(object);
			return true;
		}
	}

}
