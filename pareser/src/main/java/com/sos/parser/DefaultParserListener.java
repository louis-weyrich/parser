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
import com.sos.parser.utils.Stack;
import com.sos.parser.utils.UtilStack;

/**
 * @author louisweyrich
 *
 */
public class DefaultParserListener implements ParserListener {

	private ParserContext context;
	private Stack <NodeContainer> nodeStack;
	private List <Exception> exceptions;
	
	/**
	 * 
	 */
	public DefaultParserListener() {
		nodeStack = new UtilStack <NodeContainer> ();
		exceptions = new ArrayList <Exception>();
	}

	/**
	 * @see com.sos.parser.ParserListener#startDocument()
	 */
	public void startDocument() {
		nodeStack.push(new Document());
	}

	/**
	 * @see com.sos.parser.ParserListener#endDocument()
	 */
	public void endDocument() throws ParserException {
		NodeContainer container = nodeStack.pop();
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		if(nodeStack.peek() != null)
		{
			nodeStack.peek().addContent(container);
		}
	}

	/**
	 * @see com.sos.parser.ParserListener#startStatement(com.sos.parser.ParserObject)
	 */
	public void startStatement() {
		nodeStack.push(new Statement());;
	}

	/**
	 * @see com.sos.parser.ParserListener#endStatement(com.sos.parser.ParserObject)
	 */
	public void endStatement(ParserObject parserObject) throws ParserException {
		NodeContainer container = nodeStack.pop();
		container.addContent(new Token(parserObject.getContent()));
		
		try
		{
			container.validate(this.context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		nodeStack.peek().addContent(container);
	}

	/**
	 * @see com.sos.parser.ParserListener#parsedToken(com.sos.parser.ParserObject)
	 */
	public void parsedToken(ParserObject parserObject) throws ParserException {
		Token token = new Token(parserObject.getContent());
		NodeContainer container = nodeStack.peek();
		container.addContent(token);
		
		try
		{
			token.validate(this.context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
	}


	/**
	 * @see com.sos.parser.ParserListener#startNestedBlock(com.sos.parser.ParserObject)
	 */
	public void startNestedBlock(ParserObject parserObject) {
		NodeContainer node = nodeStack.peek();
		if(node.getType() == NodeType.STATEMENT)
		{
			Statement statement = (Statement)node;
			if(statement.getChildren().size() == 0)
			{
				nodeStack.pop();
			}
		}
		
		nodeStack.push(new Block(new Token(parserObject.getContent())));
		startStatement();
	}

	/**
	 * @see com.sos.parser.ParserListener#endNestedBlock(com.sos.parser.ParserObject)
	 */
	public void endNestedBlock(ParserObject parserObject) throws ParserException {
		NodeContainer container = nodeStack.pop();
		
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		nodeStack.peek().addContent(container);
	}

	/**
	 * @see com.sos.parser.ParserListener#parsedIgnorableTokens(com.sos.parser.ParserObject)
	 */
	public void parsedIgnorableToken(ParserObject parserObject) throws ParserException {
		IgnorableToken it = new IgnorableToken(parserObject.getContent());
		
		try
		{
			it.validate(context);
		}
		catch(ParserException e)
		{
			it.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		nodeStack.peek().addContent(it);
	}

	/**
	 * @see com.sos.parser.ParserListener#setParserContext(com.sos.parser.ParserContext)
	 */
	public void setParserContext(ParserContext context) {
		this.context = context; 

	}

	public void tokenNotAllowed(ParserObject parsedObject) throws ParserException {
		NotAllowed notAllowed = new NotAllowed(parsedObject.getContent());
		
		try
		{
			notAllowed.validate(context);
		}
		catch(ParserException e)
		{
			notAllowed.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		nodeStack.peek().addContent(notAllowed);;
	}
	
	public void exceptions(Exception exception) {
		nodeStack.peek().addContent(new NodeException(exception));
		exceptions.add(exception);
	}

	public void startQuotedText(ParserObject parserObject) {
		QuotedText text = new QuotedText();
		text.addContent(new Token(parserObject.getContent()));
		nodeStack.push(text);
	}
	
	public void addText(ParserObject parserObject)
	{
		nodeStack.peek().addContent(new Text(parserObject.getContent()));
	}

	public void endQuotedText(ParserObject parserObject) throws ParserException {
		NodeContainer container = nodeStack.pop();
		container.addContent(new Token(parserObject.getContent()));
		
		try
		{
			container.validate(context);
		}
		catch(ParserException e)
		{
			container.addContent(new NodeException(e));
			exceptions.add(e);
		}
		
		nodeStack.peek().addContent(container);
	}

	public void parsedKeyword(ParserObject parserObject) throws ParserException
	{
		nodeStack.peek().addContent(new Keyword(parserObject.getContent()));
	}
	
	public Stack <NodeContainer> getStack()
	{
		return nodeStack;
	}
}
