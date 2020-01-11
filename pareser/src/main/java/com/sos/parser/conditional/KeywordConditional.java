package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

public class KeywordConditional implements Conditional {

	public KeywordConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		if(context.getKeywords().contains((context.isCaseSensitive())?object.getContent():object.getContent().toLowerCase()))
		{
			NodeContainer node = listener.getStack().peekTop();
			if(node.getType() != NodeType.STATEMENT)
			{
				listener.startStatement();
			}
			listener.parsedKeyword(object);
			return true;
		}
		return false;
	}

}
