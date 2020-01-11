/**
 * 
 */
package com.sos.parser.conditional;

import com.sos.parser.ParserContext;
import com.sos.parser.ParserListener;
import com.sos.parser.ParserObject;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;

/**
 * @author louisweyrich
 *
 */
public class StatementConditional implements Conditional {

	/**
	 * 
	 */
	public StatementConditional() {}

	public boolean evaluate(ParserContext context, ParserListener listener, ParserObject object) throws ParserException {
		NodeContainer container = listener.getStack().peekTop();
		
		if(context.getStatementEnd().contains(object.getContent().charAt(0)) && container.getType() == NodeType.STATEMENT)
		{
			listener.endStatement(object);
			return true;
		}
		
		return false;
	}

}
