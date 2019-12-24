package com.sos.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
			Object statementEnd = jsonObject.get("statementEnd");
			if(statementEnd == null || statementEnd.equals("") || !(statementEnd.toString().length() > 1))
			{
				context.setStatementEnd(Character.valueOf(statementEnd.toString().charAt(0)));
			}
			else
			{
				context.setStatementEnd(';');
			}
		}
		catch(NullPointerException e)
		{
			
		}
		
		Object matchQuotes = jsonObject.get("matchQuotes");
		if(matchQuotes != null && !matchQuotes.equals("")) {
			context.setMatchQuotes(Boolean.parseBoolean(matchQuotes.toString()));
		}else {
			context.setMatchQuotes(true);
		}

		Object autoMarkBuffer = jsonObject.get("autoMarkBuffer");
		if(autoMarkBuffer != null && !autoMarkBuffer.equals("")) {
			context.setAutoMarkBuffer(Boolean.parseBoolean(autoMarkBuffer.toString()));
		}else {
			context.setAutoMarkBuffer(true);
		}
		
		JSONArray tokensArray = (JSONArray)jsonObject.get("tokens");
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
		
		
		JSONArray ignorablesArray = (JSONArray)jsonObject.get("ignorableTokens");
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
		
		Object parseIgnorable = jsonObject.get("showIgnorable");
		if(parseIgnorable != null)
		{
			context.setShowIgnorables(Boolean.getBoolean(parseIgnorable.toString()));
		}

		JSONArray tokenSetJsonArray = (JSONArray)jsonObject.get("tokenSets");
		Iterator <String> tokenSetIterator = tokenSetJsonArray.iterator();
		while(tokenSetIterator.hasNext()) {
			 context.getTokenTree().addTerm(tokenSetIterator.next());
		}

		JSONArray tokensNotAllowedArray = (JSONArray)jsonObject.get("tokensNotAllowed");
		Iterator <String> tokensNotAllowedIterator = tokensNotAllowedArray.iterator();
		while(tokensNotAllowedIterator.hasNext()) {
			String value = tokensNotAllowedIterator.next();
			if(value.length() == 1) {
				context.getTokensNotAllowed().add(Character.valueOf(value.charAt(0)));
			}
		}
		
		//Matched Tokens
		JSONArray matchedTokensArray = (JSONArray)jsonObject.get("matchedTokens");
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
		
		JSONArray keywords = (JSONArray)jsonObject.get("keywords");
		Iterator <String> keywordIterator =  keywords.iterator();
		while(keywordIterator.hasNext())
		{
			String keywordObject = keywordIterator.next();
			context.getKeywords().add(keywordObject);
		}
				
		this.listener = listener;
		listener.setParserContext(context);
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
	private NodeContainer execute() throws IOException, ParserException {
		
		if (parser.hasNextParserObject()) {
			listener.setParserContext(context);
			listener.startDocument();
			listener.startStatement();
			try {
				while (parser.hasNextParserObject()) {
					try 
					{
						ParserObject object = parser.getNextParserObject();
						
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
		
		this.parser.close();
		listener.endDocument(new ParserObject("",0,0,null));
		return listener.getStack().popTop();
	}
	
	
	/**
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		parser.close();
	}

}
