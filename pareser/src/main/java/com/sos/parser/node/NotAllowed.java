package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class NotAllowed extends ContentContainer {
	
	public NotAllowed(String token) {
		super();
		this.addContent(token);
	}

	@Override
	public NodeType getType() {
		return NodeType.NOT_ALLOWED;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("not-allowed");
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
