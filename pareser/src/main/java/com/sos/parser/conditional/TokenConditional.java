package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;

public class TokenConditional implements Conditional {

	public TokenConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(context.getParsableTokens().contains(object.getContent()))
		{
			listener.parsedToken(object);
			return true;
		}
		else if(context.getTokenTree().containsText(object.getContent()))
		{
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
