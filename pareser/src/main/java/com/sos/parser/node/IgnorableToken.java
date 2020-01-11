package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class IgnorableToken extends ContentContainer{
	
	public IgnorableToken(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.IGNORABLE_TOKEN;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("ignorable-token");
		if(validator != null)
		{
			validator.validate(context);
		}
	}


	@Override
	public String toString()
	{
		return this.getContent();
	}

}
