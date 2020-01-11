package com.sos.parser.validation;

import com.sos.parser.ParserContext;
import com.sos.parser.exception.ParserException;

public interface Validator
{
	public void validate(ParserContext context) throws ParserException;
}
