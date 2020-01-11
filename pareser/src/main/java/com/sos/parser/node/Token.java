package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Token extends ContentContainer {

	public Token(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.TOKEN;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("token");
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
