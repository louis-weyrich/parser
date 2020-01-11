package com.sos.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sos.parser.conditional.BlockConditional;
import com.sos.parser.conditional.Conditional;
import com.sos.parser.conditional.IgnorableTokenCondition;
import com.sos.parser.conditional.KeywordConditional;
import com.sos.parser.conditional.NotAllowedConditional;
import com.sos.parser.conditional.QuoteConditional;
import com.sos.parser.conditional.StatementConditional;
import com.sos.parser.conditional.TokenConditional;
//import com.sos.parser.conditional.TokenSetConditional;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;
import com.sos.parser.validation.BlockValidator;
import com.sos.parser.validation.DocumentValidator;
import com.sos.parser.validation.IgnorableValidator;
import com.sos.parser.validation.KeywordValidator;
import com.sos.parser.validation.NotAllowedValidator;
import com.sos.parser.validation.QuotedTextValidator;
import com.sos.parser.validation.StatementValidator;
import com.sos.parser.validation.TextValidator;
import com.sos.parser.validation.TokenValidator;
import com.sos.parser.validation.Validator;

public class LanguageParser {
	
	private Parser parser;
	private ParserContext context;
	private ParserListener listener;
	private List <Conditional> conditionals;

	@SuppressWarnings("unchecked")
	public LanguageParser(String parserConfig, ParserListener listener) throws FileNotFoundException, IOException, ParseException, ParserException {
		this.context = new ParserContext();

		JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(parserConfig));
		
		try {
			Object bufferSize = jsonObject.get("bufferSize");
			if(bufferSize != null && !bufferSize.equals("")) {
				int intBufferSize = Integer.parseInt(bufferSize.toString());
				context.setBufferSize(intBufferSize);
			}else {
				context.setBufferSize(256);
			}
		}catch(NumberFormatException e) {
			throw new ParserException("Could not parse bufferSize property.");
		}
		
		try {
			Object initialBufferSize = jsonObject.get("initialBufferSize");
			if(initialBufferSize != null && !initialBufferSize.equals("")) {
				int intInitialBufferSize = Integer.parseInt(initialBufferSize.toString());
				context.setInitialBufferSize(intInitialBufferSize);
			}else {
				context.setInitialBufferSize(20);
			}
		}catch(NumberFormatException e) {
			throw new ParserException("Could not parse initialBufferSize property.");
		}
		
		try {
			Object bufferGrowthSize = jsonObject.get("bufferGrowthSize");
			if(bufferGrowthSize != null && !bufferGrowthSize.equals("")) {
				int intBufferGrowthSize = Integer.parseInt(bufferGrowthSize.toString());
				context.setBufferGrowthSize(intBufferGrowthSize);
			}else {
				context.setBufferGrowthSize(256);
			}
		}catch(NumberFormatException e) {
			throw new ParserException("Could not parse bufferGrowthSize property.");
		}
		
		try
		{
			Object debug = jsonObject.get("debug");
			if(debug != null)
			{
				boolean db = debug.toString().equals(Boolean.TRUE.toString());
				context.setDebug(db);
			}
			else
			{
				context.setDebug(false);
			}
		}
		catch(Exception e)
		{
			
		}
		
		try 
		{
			JSONArray statementEndArray = (JSONArray)jsonObject.get("statementEnd");
			if(statementEndArray != null)
			{
				Iterator <String> iterator = statementEndArray.iterator();
				while(iterator.hasNext())
				{
					String value = iterator.next();
					if(value != null && value.length() == 1)
					{
						context.getStatementEnd().add(Character.valueOf(value.charAt(0)));
					}
				}
			}
			else
			{
				context.getStatementEnd().add(';');
			}
		}
		catch(NullPointerException e)
		{
			throw new ParserException(e.getMessage());
		}
		
		try
		{
			Object caseSensitive = jsonObject.get("caseSensitive");
			if(caseSensitive != null && !caseSensitive.equals("")) {
				context.setCaseSensitive(Boolean.parseBoolean(caseSensitive.toString()));
			}else {
				context.setCaseSensitive(true);
			}
		}
		catch(Exception e)
		{
			throw new ParserException("Could not parse caseSensitive property.");
		}
		
		Object matchQuotes = jsonObject.get("matchQuotes");
		if(matchQuotes != null && !matchQuotes.equals("")) {
			context.setMatchQuotes(Boolean.parseBoolean(matchQuotes.toString()));
		}else {
			context.setMatchQuotes(true);
		}
		
		JSONArray quoteArray = (JSONArray)jsonObject.get("quoteTokens");
		if(quoteArray != null)
		{
			Iterator <String> iterator = quoteArray.iterator();
			while(iterator.hasNext())
			{
				String quote = iterator.next();
				if(quote.length() == 1)
				{
					context.getQuoteTokens().add(quote.charAt(0));
				}
			}
		}
		else
		{
			context.getQuoteTokens().add(new Character('\"'));
			context.getQuoteTokens().add(new Character('\''));
		}

		Object autoMarkBuffer = jsonObject.get("autoMarkBuffer");
		if(autoMarkBuffer != null && !autoMarkBuffer.equals("")) {
			context.setAutoMarkBuffer(Boolean.parseBoolean(autoMarkBuffer.toString()));
		}else {
			context.setAutoMarkBuffer(true);
		}
		
		JSONArray tokensArray = (JSONArray)jsonObject.get("tokens");
		if(tokensArray != null)
		{
			Iterator <String> tokenIterator = tokensArray.iterator();
			while(tokenIterator.hasNext()) {
				String tokenString = tokenIterator.next();
				if(tokenString.equals("\n")) {
					context.getParsableTokens().add(Character.valueOf('\n'));
				}else if(tokenString.equals("\r")) {
					context.getParsableTokens().add(Character.valueOf('\r'));
				}else if(tokenString.equals("\t")) {
					context.getParsableTokens().add(Character.valueOf('\t'));
				}else {
					if(tokenString.length() == 1) {
						context.getParsableTokens().add(Character.valueOf(tokenString.charAt(0)));
					}else {
						throw new ParserException("ignorableTokes can only be single characters.");
					}
				}
			}
		}
		
		
		JSONArray ignorablesArray = (JSONArray)jsonObject.get("ignorableTokens");
		if(ignorablesArray != null)
		{
			Iterator <String> ignorablesArrayIterator = ignorablesArray.iterator();
			while(ignorablesArrayIterator.hasNext()) {
				String ignorableText = ignorablesArrayIterator.next();
				if(ignorableText.equals("\n")) {
					context.getIgnorableSet().add(Character.valueOf('\n'));
				}else if(ignorableText.equals("\r")) {
					context.getIgnorableSet().add(Character.valueOf('\r'));
				}else if(ignorableText.equals("\t")) {
					context.getIgnorableSet().add(Character.valueOf('\t'));
				}else {
					if(ignorableText.length() == 1) {
						context.getIgnorableSet().add(Character.valueOf(ignorableText.charAt(0)));
					}else {
						throw new ParserException("ignorableTokes can only be single characters.");
					}
				}
			}
		}
		
		Object parseIgnorable = jsonObject.get("showIgnorable");
		if(parseIgnorable != null)
		{
			context.setShowIgnorables("true".equalsIgnoreCase((parseIgnorable.toString().trim())));
		}

		JSONArray tokenSetJsonArray = (JSONArray)jsonObject.get("tokenSets");
		if(tokenSetJsonArray != null)
		{
			Iterator <String> tokenSetIterator = tokenSetJsonArray.iterator();
			while(tokenSetIterator.hasNext()) {
				 context.getTokenTree().addTerm(tokenSetIterator.next());
			}
		}

		JSONArray tokensNotAllowedArray = (JSONArray)jsonObject.get("tokensNotAllowed");
		if(tokensNotAllowedArray != null)
		{
			Iterator <String> tokensNotAllowedIterator = tokensNotAllowedArray.iterator();
			while(tokensNotAllowedIterator.hasNext()) {
				String value = tokensNotAllowedIterator.next();
				if(value.length() == 1) {
					context.getTokensNotAllowed().add(Character.valueOf(value.charAt(0)));
				}
			}
		}
		
		//Matched Tokens
		JSONArray matchedTokensArray = (JSONArray)jsonObject.get("matchedTokens");
		if(matchedTokensArray != null)
		{
			Iterator <JSONObject> matchedTokenesIterator =  matchedTokensArray.iterator();
			context.getMatchedTokens().setSize(matchedTokensArray.size());
			while(matchedTokenesIterator.hasNext()) {
				JSONObject matchTokenObject = matchedTokenesIterator.next();
				JSONArray matchArray = (JSONArray)matchTokenObject.get("match");
				if(matchArray.size() == 2) {
					String startValue = matchArray.get(0).toString();
					String endValue = matchArray.get(1).toString();
					context.getMatchedTokens().add(startValue, endValue);
				}
			}
		}
		
		JSONArray keywords = (JSONArray)jsonObject.get("keywords");
		if(keywords != null)
		{
			Iterator <String> keywordIterator =  keywords.iterator();
			while(keywordIterator.hasNext())
			{
				String keywordObject = keywordIterator.next();
				context.getKeywords().add(
					(context.isCaseSensitive())?keywordObject:keywordObject.toLowerCase());
			}
		}
		
		JSONArray validators = (JSONArray)jsonObject.get("validators");
		if(validators != null)
		{
			configureDefaultValidators();
			configureValidators(validators);
		}
		else
		{
			configureDefaultValidators();
		}
				
		this.listener = (listener != null)?listener: new DefaultParserListener();
		this.listener.setParserContext(context);
		this.parser = new ParserImpl(context);
		this.context.setParser(parser);
		
		conditionals = new ArrayList <Conditional> (10);
		conditionals.add(new IgnorableTokenCondition());
		conditionals.add(new QuoteConditional());
		conditionals.add(new KeywordConditional());
		conditionals.add(new NotAllowedConditional());
//		conditionals.add(new TokenSetConditional());
		conditionals.add(new BlockConditional());
		conditionals.add(new StatementConditional());
		conditionals.add(new TokenConditional());
		
	}
	
	public NodeContainer parse(String content) throws IOException, ParserException {
		parser.parse(content);
		return execute();
	}
	
	public NodeContainer parse(Reader contentReader) throws IOException, ParserException {
		parser.parse(contentReader);
		return execute();
	}
	
	public NodeContainer parse(InputStream contentStream) throws IOException, ParserException {
		parser.parse(contentStream);
		return execute();
	}
	
	public NodeContainer parse(char [] content) throws IOException, ParserException {
		parser.parse(content);
		return execute();
	}
	
	/**
	 * 
	 * @throws IOException
	 * @throws ParserException
	 */
	private NodeContainer execute() throws IOException, ParserException 
	{
		if (parser.hasNextParserObject()) {
			listener.setParserContext(context);
			listener.startDocument();
			try 
			{
				while (parser.hasNextParserObject()) 
				{
					try 
					{
						ParserObject object = parser.getNextParserObject();
						if(context.isDebug())
						{
							System.out.print(object.getContent());
						}
						
						for(Conditional conditional : conditionals)
						{
							if(conditional.evaluate(context, listener, object))
							{
								break;
							}
						}
						
					}
					catch(Exception e) {
						listener.exceptions(e);
					}
				}
			}
			catch(Exception e) {
				listener.exceptions(e);
			}
		}
		
		if(listener.getStack().peekTop().getType() != NodeType.DOCUMENT)
		{
			switch(listener.getStack().peekTop().getType())
			{
				case STATEMENT :
				{
					listener.endStatement(new ParserObject("", 0,0,null));
					break;
				}
				case BLOCK :
				{
					listener.endNestedBlock(new ParserObject("", 0,0,null));
					break;
				}
				case QUOTED_TEXT :
				{
					listener.endQuotedText(new ParserObject("", 0,0,null));
					break;
				}
				default :
				{
					
				}
			}
		}
		
		this.parser.close();
		listener.endDocument(new ParserObject("",0,0,null));
		return listener.getStack().popTop();
	}
	
	protected void configureValidators(JSONArray validatorArray) throws ParserException
	{
		Set <String> keys = new HashSet <String> ();
		keys.add("block");keys.add("document");keys.add("ignorable-token");
		keys.add("keyword");keys.add("not-allowed");keys.add("quoted-text");
		keys.add("statement");keys.add("text");keys.add("token");
		
		Iterator <JSONObject> iterator = validatorArray.iterator();
		while(iterator.hasNext())
		{
			JSONObject json = iterator.next();
			Set <String> keySet = json.keySet();
			Iterator <String> keySetIterator = keySet.iterator();
			while(keySetIterator.hasNext())
			{
				String key = keySetIterator.next();
				if(keys.contains(key))
				{
					Object object = json.get(key);
					ClassLoader loader = this.getClass().getClassLoader();
					try
					{
						Class <Validator> validator = (Class<Validator>)loader.loadClass(object.toString());
						try
						{
							context.getValidators().put(key, (Validator)validator.newInstance());
						} 
						catch (InstantiationException e)
						{
							throw new ParserException(e);
						} 
						catch (IllegalAccessException e)
						{
							throw new ParserException(e);
						}
					} 
					catch (ClassNotFoundException e)
					{
						throw new ParserException(e);
					}
				}
				else
				{
					throw new ParserException("validator key ("+key+") is not defined.");
				}
			}
		}
	}
	
	
	protected void configureDefaultValidators()
	{
		Map <String, Validator> validators = new HashMap <String , Validator> ();
		validators.put("block", new BlockValidator());
		validators.put("document", new DocumentValidator());
		validators.put("ignorable-token", new IgnorableValidator());
		validators.put("keyword", new KeywordValidator());
		validators.put("not-allowed", new NotAllowedValidator());
		validators.put("quoted-text", new QuotedTextValidator());
		validators.put("statement", new StatementValidator());
		validators.put("text", new TextValidator());
		validators.put("token", new TokenValidator());
		context.setValidators(validators);
	}
	
	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException 
	{
		parser.close();
	}

}
