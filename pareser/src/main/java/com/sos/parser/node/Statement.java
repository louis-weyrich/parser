package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Statement extends ChildContainer{
	
	
	public Statement() {
		super();
	}
	
	@Override
	public NodeType getType() {
		return NodeType.STATEMENT;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("statement");
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
