package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;
import com.sos.parser.utils.MatchedTokenSet;
import com.sos.parser.utils.MatchedTokenSet.MatchedToken;

public class BlockConditional implements Conditional {

	public BlockConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		NodeContainer container = listener.getStack().peek();
		MatchedTokenSet tokens = context.getMatchedTokens();
		
		if(tokens.containsStart(object.getContent()))
		{
			listener.startNestedBlock(object);
			return true;
		}
		else if(container.getType() == NodeType.BLOCK && tokens.containsStart(container.getContent().toString()))
		{
			MatchedToken matched = tokens.getByStart(container.getContent().toString());
			if(matched.getEnd() == container.getContent().toString())
			{
				listener.endNestedBlock(object);
				return true;
			}
		}
		return false;
	}

}
