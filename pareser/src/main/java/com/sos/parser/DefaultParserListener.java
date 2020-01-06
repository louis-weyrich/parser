/**
 * 
 */
package com.sos.parser;

import java.util.ArrayList;
import java.util.List;

import com.sos.parser.exception.ParserException;
import com.sos.parser.node.Block;
import com.sos.parser.node.Document;
import com.sos.parser.node.IgnorableToken;
import com.sos.parser.node.Keyword;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeException;
import com.sos.parser.node.NodeType;
import com.sos.parser.node.NotAllowed;
import com.sos.parser.node.QuotedText;
import com.sos.parser.node.Statement;
import com.sos.parser.node.Text;
import com.sos.parser.node.Token;
import com.sos.parser.utils.linkedlist.LinkedList;

/**
 * @author louisweyrich
 *
 */ 
public class DefaultParserListener implements ParserListener 
{

	private ParserContext context;
	private LinkedList <NodeContainer> nodeStack;
	private List <Exception> exceptions;
	
	/**
	 * 
	 */
	public DefaultParserListener() 
	{
		nodeStack = new LinkedList <NodeContainer> ();
		exceptions = new ArrayList <Exception>();
	}

	/**
	 * @see com.sos.parser.ParserListener#startDocument()
	 */
	public void startDocument() 
	{
		nodeStack.pushTop(new Document());
	}

	/**
	 * @see com.sos.parser.ParserListener#endDocument()
	 */
	public void endDocument(ParserObject parserObject) throws ParserException 
	{
		NodeContainer container = nodeStack.peekTop();
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * @see com.sos.parser.ParserListener#startStatement(com.sos.parser.ParserObject)
	 */
	public void startStatement() 
	{
		nodeStack.pushTop(new Statement());;
	}

	/**
	 * @see com.sos.parser.ParserListener#endStatement(com.sos.parser.ParserObject)
	 */
	public void endStatement(ParserObject parserObject) throws ParserException 
	{
		NodeContainer container = nodeStack.popTop();
		container.addContent(new Token(parserObject.getContent()));
		
		try
		{
			container.validate(this.context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			NodeContainer nextContainer = nodeStack.peekTop();
			nextContainer.addContent(container);
		}
		else
		{
			ParserException e = new ParserException("Unable to end statement.");
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * @see com.sos.parser.ParserListener#parsedToken(com.sos.parser.ParserObject)
	 */
	public void parsedToken(ParserObject parserObject) throws ParserException 
	{
		Token token = new Token(parserObject.getContent());
		NodeContainer container = nodeStack.peekTop();
		
		
		try
		{
			token.validate(this.context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		container.addContent(token);
	}


	/**
	 * @see com.sos.parser.ParserListener#startNestedBlock(com.sos.parser.ParserObject)
	 */
	public void startNestedBlock(ParserObject parserObject) 
	{
		nodeStack.pushTop(new Block(new Token(parserObject.getContent())));
	}

	/**
	 * @see com.sos.parser.ParserListener#endNestedBlock(com.sos.parser.ParserObject)
	 */
	public void endNestedBlock(ParserObject parserObject) throws ParserException 
	{
		NodeContainer container = nodeStack.popTop();
		container.addContent(new Token(parserObject.getContent()));
		
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			NodeContainer nextContainer = nodeStack.peekTop();
			nextContainer.addContent(container);
			
//			if(nextContainer.getType() == NodeType.STATEMENT)
//			{
//				endStatement(
//					new ParserObject("",parserObject.getStartIndex(),parserObject.getEndIndex(), null));
//			}
		}
		else
		{
			ParserException e = new ParserException("Unable to end nested block.");
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * @see com.sos.parser.ParserListener#parsedIgnorableTokens(com.sos.parser.ParserObject)
	 */
	public void parsedIgnorableToken(ParserObject parserObject) throws ParserException 
	{
		IgnorableToken it = new IgnorableToken(parserObject.getContent());
		
		try
		{
			it.validate(context);
		}
		catch(ParserException e)
		{
			it.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			nodeStack.peekTop().addContent(it);
		}
		else
		{
			ParserException e = new ParserException("Unable to add ignorable token.");
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * @see com.sos.parser.ParserListener#setParserContext(com.sos.parser.ParserContext)
	 */
	public void setParserContext(ParserContext context) 
	{
		this.context = context; 
	}

	/**
	 * 
	 */
	public void tokenNotAllowed(ParserObject parserObject) throws ParserException 
	{
		NotAllowed notAllowed = new NotAllowed(parserObject.getContent());
		
		try
		{
			notAllowed.validate(context);
		}
		catch(ParserException e)
		{
			notAllowed.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			nodeStack.peekTop().addContent(notAllowed);
		}
		else
		{
			ParserException e = new ParserException("Unable to add token not allowed.");
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}
	
	/**
	 * 
	 */
	public void exceptions(Exception exception) 
	{
		exceptions.add(exception);
	}

	/**
	 * 
	 */
	public void startQuotedText(ParserObject parserObject) 
	{
		QuotedText text = new QuotedText();
		text.addContent(new Token(parserObject.getContent()));
		
		try
		{
			text.validate(context);
		}
		catch(ParserException e)
		{
			text.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			nodeStack.pushTop(text);
		}
		else
		{
			ParserException e = new ParserException("Unable to start quoted text.");
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}
	
	/**
	 * 
	 */
	public void addText(ParserObject parserObject)
	{
		if(nodeStack.peekTop().getType() != NodeType.QUOTED_TEXT)
		{
			nodeStack.peekTop().addContent(new Text(parserObject.getContent()));
		}
		else
		{
			ParserException e = new ParserException("Unable to add text("+parserObject.getContent()+")");
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * 
	 */
	public void endQuotedText(ParserObject parserObject) throws ParserException 
	{
		NodeContainer container = nodeStack.popTop();
		container.addContent(new Token(parserObject.getContent()));
		
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
		
		if(nodeStack.peekTop() != null)
		{
			nodeStack.peekTop().addContent(container);
		}
		else
		{
			ParserException e = new ParserException("Unable to end quoted text("+parserObject.getContent()+")");
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
			exceptions.add(e);
		}
	}

	/**
	 * 
	 */
	public void parsedKeyword(ParserObject parserObject) throws ParserException
	{
		if(nodeStack.peekTop() != null)
		{
			nodeStack.peekTop().addContent(new Keyword(parserObject.getContent()));
		}
		else
		{
			ParserException e = new ParserException("unable to add keyword("+parserObject.getContent()+")");
			exceptions.add(e);
			nodeStack.peekTop().addContent(new NodeException(e, parserObject));
		}
	}
	
	/**
	 * 
	 */
	public LinkedList <NodeContainer> getStack()
	{
		return nodeStack;
	}

	public void parsedTokenSet(ParserObject parserObject) throws ParserException {
//		NodeContainer node = nodeStack.peekTop();
//		node.addContent(new TokenSet(parserObject.getContent()));
//		
//		try
//		{
//			node.validate(context);
//		}
//		catch(ParserException e)
//		{
//			node.addContent(new NodeException(e));
//			exceptions.add(e);
//			
//		}
	}
	
}
