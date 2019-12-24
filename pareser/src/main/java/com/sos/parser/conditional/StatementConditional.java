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
		if(object.getContent().equals(context.getStatementEnd().toString()) && container.getType() == NodeType.STATEMENT)
		{
			listener.endStatement(object);
			listener.startStatement();
			return true;
		}
		else if(container.getType() == NodeType.DOCUMENT || container.getType() == NodeType.BLOCK)
		{
			listener.startStatement();
			return true;
		}
		
		return false;
	}

}
