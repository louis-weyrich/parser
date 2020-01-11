package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

public class QuoteConditional implements Conditional {

	public QuoteConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		NodeContainer container = listener.getStack().peekTop();
		String value = object.getContent();
		if(context.isMatchQuotes())
		{
			if(container.getType() == NodeType.QUOTED_TEXT && value.length() == 1 && context.getQuoteTokens().contains(value.charAt(0)))
			{
				listener.endQuotedText(object);
				return true;
			}
			else if(container.getType() != NodeType.QUOTED_TEXT && value.length() == 1 && context.getQuoteTokens().contains(value.charAt(0)))
			{
				if(listener.getStack().peekTop().getType() != NodeType.STATEMENT)
				{
					listener.startStatement();
				}
				
				listener.startQuotedText(object);
				return true;
			}
			else if(container.getType() == NodeType.QUOTED_TEXT && !context.getQuoteTokens().contains(value.charAt(0)))
			{
				listener.addText(object);
				return true;
			}
		}
		
		return false;
	}

}
