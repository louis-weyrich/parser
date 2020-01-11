package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Keyword extends ContentContainer {

	public Keyword(String keyword) {
		super();
		this.addContent(keyword);
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("keyword");
		if(validator != null)
		{
			validator.validate(context);
		}
	}


	@Override
	public NodeType getType() {
		return NodeType.KEYWORD;
	}

	@Override
	public String toString()
	{
		return this.getContent();
	}

}
