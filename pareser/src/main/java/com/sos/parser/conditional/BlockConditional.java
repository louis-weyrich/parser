package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.Block;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;
import com.sos.parser.node.Token;
import com.sos.parser.utils.MatchedTokenSet;
import com.sos.parser.utils.MatchedTokenSet.MatchedToken;

public class BlockConditional implements Conditional {

	public BlockConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject parserObject)
	throws ParserException 
	{
		NodeContainer container = listener.getStack().peekTop();
		MatchedTokenSet tokens = context.getMatchedTokens();
		
		if(tokens.containsStart(parserObject.getContent()))
		{
			listener.startNestedBlock(parserObject);
			return true;
		}
		else if(container.getType() == NodeType.BLOCK)
		{
			Block block = (Block)container;
			
			if(block.getChildren().size() > 0)
			{
				NodeContainer node = block.getChildren().get(0);
				
				if(node.getType() == NodeType.TOKEN)
				{
					Token token = (Token)node;
					MatchedToken matched = tokens.getByStart(token.getContent());
					if(matched.getEnd().equals(parserObject.getContent().toString()))
					{
						listener.endNestedBlock(parserObject);					
						return true;
					}
				}
			}
		}
		return false;
	}

}
