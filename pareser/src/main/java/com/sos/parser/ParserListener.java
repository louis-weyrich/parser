package com.sos.parser;

import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.utils.Stack;

public interface ParserListener {
	
	/**
	 * 
	 */
	public void startDocument();
	
	/**
	 * 
	 */
	public void endDocument() throws ParserException;
	
	/**
	 * 
	 */
	public void startStatement();
	
	/**
	 * 
	 */
	public void endStatement(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param parserObject
	 * @param context
	 */
	public void parsedToken(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param parserObject
	 */
	public void startQuotedText(ParserObject parserObject);
	
	/**
	 * 
	 * @param parserObject
	 */
	public void addText(ParserObject parserObject);
	
	/**
	 * 
	 * @param parserObject
	 */
	public void endQuotedText(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 */
	public void startNestedBlock(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 */
	public void endNestedBlock(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param token
	 */
	public void parsedIgnorableToken(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param context
	 */
	public void setParserContext(ParserContext context) throws ParserException;
	
	/**
	 * 
	 * @param paeredObject
	 */
	public void tokenNotAllowed(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param parserObject
	 */
	public void parsedKeyword(ParserObject parserObject) throws ParserException;
	
	/**
	 * 
	 * @param exception
	 */
	public void exceptions(Exception object);
	
	/**
	 * 
	 * @return
	 */
	public Stack <NodeContainer> getStack();
}
