package com.sos.parser.node;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;
import com.sos.parser.validation.Validator;

public class Document extends ChildContainer{

	
	public Document() {
		super();
	}

	@Override
	public NodeType getType() {
		return NodeType.DOCUMENT;
	}

	public void validate(ParserContext context) throws ParserException  {
		Validator validator = context.getValidators().get("document");
		if(validator != null)
		{
			validator.validate(context);
		}
	}

}
