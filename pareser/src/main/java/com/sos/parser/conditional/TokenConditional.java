package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

public class TokenConditional implements Conditional {

	public TokenConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		NodeContainer node = listener.getStack().peekTop();
		
		if(context.getParsableTokens().contains(object.getContent()))
		{
			if(node.getType() != NodeType.STATEMENT && node.getType() != NodeType.BLOCK)
			{
				listener.startStatement();
			}
			
			listener.parsedToken(object);
			return true;
		}
		else if(context.getTokenTree().containsText(object.getContent()))
		{
			if(node.getType() != NodeType.STATEMENT && node.getType() != NodeType.BLOCK)
			{
				listener.startStatement();
			}
			
			listener.parsedToken(object);
			return true;
		}
		else 
		{
			if(node.getType() != NodeType.STATEMENT && node.getType() != NodeType.BLOCK)
			{
				listener.startStatement();
			}
			
			listener.addText(object);
			return true;
		}
	}

}
