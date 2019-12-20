package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;

public interface Conditional {
	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object) throws ParserException;
}
