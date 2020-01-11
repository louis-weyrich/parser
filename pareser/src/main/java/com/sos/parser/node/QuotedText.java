package com.sos.parser.node;

import java.util.List;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class QuotedText extends ChildContainer {
	
	public QuotedText() {
		super();
	}

	@Override
	public NodeType getType() {
		return NodeType.QUOTED_TEXT;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("quoted-text");
		if(validator != null)
		{
			validator.validate(context);
		}
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for(NodeContainer node : this.children)
		{
			if(node.getType() != NodeType.TOKEN)
			{
				builder.append(node.toString());
			}
		}
		
		return builder.toString();
	}

}
