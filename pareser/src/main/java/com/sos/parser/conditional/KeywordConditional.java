package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;

public class KeywordConditional implements Conditional {

	public KeywordConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(context.getKeywords().contains(object.getContent()))
		{
			listener.parsedKeyword(object);
			return true;
		}
		return false;
	}

}
