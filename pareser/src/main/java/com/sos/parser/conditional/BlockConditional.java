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

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object)
	throws ParserException 
	{
		NodeContainer container = listener.getStack().peekTop();
		MatchedTokenSet tokens = context.getMatchedTokens();
		
		if(tokens.containsStart(object.getContent()) && container.getType() == NodeType.DOCUMENT)
		{
			listener.startStatement();
			return true;
		}
		else if(tokens.containsStart(object.getContent()))
		{
			listener.startNestedBlock(object);
			return true;
		}
		else if(context.getMatchedTokens().containsEnd(object.getContent()))
		{
			if(container.getType() == NodeType.STATEMENT)
			{
				listener.endStatement(
					new ParserObject("",object.getStartIndex(),object.getEndIndex(),null));
				container = listener.getStack().peekTop();
			}
			
			if(container.getType() == NodeType.BLOCK)
			{
				Block block = (Block)container;
				
				if(block.getChildren().size() > 0)
				{
					NodeContainer node = block.getChildren().get(0);
					
					if(node.getType() == NodeType.TOKEN)
					{
						Token token = (Token)node;
						MatchedToken matched = tokens.getByStart(token.getContent());
						if(matched.getEnd().equals(object.getContent().toString()))
						{
							listener.endNestedBlock(object);					
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
