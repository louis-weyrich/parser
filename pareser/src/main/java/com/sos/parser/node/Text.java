package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Text extends ContentContainer {

	public Text(String text) 
	{
		addContent(text);
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("text");
		if(validator != null)
		{
			validator.validate(context);
		}
	}

	@Override
	public NodeType getType() {
		return NodeType.TEXT;
	}
	
	@Override
	public String toString()
	{
		return this.getContent();
	}

}
