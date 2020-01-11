package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Block extends ChildContainer{
	
	public Block(Object content) {
		super();
		this.addContent(content);
		this.content = content.toString();
	}

	@Override
	public NodeType getType() {
		return NodeType.BLOCK;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("block");
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
			builder.append(node.toString());
		}
		
		return builder.toString();
	}

}
