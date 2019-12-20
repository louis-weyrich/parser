package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;

public class NotAllowedConditional implements Conditional {

	public NotAllowedConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(object.getContent().length() == 1 && context.getTokensNotAllowed().contains(object.toString().charAt(0)))
		{
			listener.tokenNotAllowed(object);
			return true;
		}
		return false;
	}

}
