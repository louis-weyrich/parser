package com.sos.parser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sos.parser.exception.ParserException;

public class ParserImpl implements Parser {
	
	private ParserContext context;
	
	private TokenScanner scanner;
	private TokenIndexer previousIndexer = new TokenIndexer('-', 0, null, null);
	
	@SuppressWarnings("unchecked")
	public ParserImpl(String parserConfig) throws FileNotFoundException, IOException, ParseException, ParserException {
		this.context = new ParserContext();
		JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(parserConfig));
		JSONArray tokensArray = (JSONArray)jsonObject.get("tokens");
		Iterator <String> tokenIterator = tokensArray.iterator();
		while(tokenIterator.hasNext()) {
			String tokenString = tokenIterator.next();
			if(tokenString.equals("\n")) {
				this.context.getParsableTokens().add(Character.valueOf('\n'));
			}else if(tokenString.equals("\r")) {
				this.context.getParsableTokens().add(Character.valueOf('\r'));
			}else if(tokenString.equals("\t")) {
				this.context.getParsableTokens().add(Character.valueOf('\t'));
			}else {
				if(tokenString.length() == 1) {
					this.context.getParsableTokens().add(Character.valueOf(tokenString.charAt(0)));
				}else {
					throw new ParserException("ignorableTokes can only be single characters.");
				}
			}
		}
		scanner = new TokenScanner(this.context);
	}

	public ParserImpl(ParserContext context) {
		this.context = context;
		scanner = new TokenScanner(context);
	}

	public void parse(String content) {
		scanner.parse(content);
		
	}

	public void parse(Reader contentReader) {
		scanner.parse(contentReader);
	}


	public void parse(InputStream contentStream) {
		scanner.parse(contentStream);
	}

	public void parse(char[] content) {
		this.parse(new String(content));
	}


	public ParserObject getNextParserObject() {
		if(scanner.hasNext()) {
			TokenIndexer indexer = scanner.next();
			
			int previousIndex = previousIndexer.getStartIndex();
			
			String content = indexer.getBuffer().getSubsetAsString(previousIndex, indexer.getStartIndex() - previousIndex);
			this.previousIndexer = indexer;
			
			return new ParserObject(content, previousIndex, indexer.getStartIndex(), indexer.getException());
		}
		return null;
	}

	public boolean hasNextParserObject() {
		return scanner.hasNext();
	}

	public ParserContext getParserContext() {
		return this.context;
	}

	public TokenScanner getScanner() {
		return scanner;
	}

	public void setScanner(TokenScanner scanner) {
		this.scanner = scanner;
	}
	
	public void close() throws IOException {
		scanner.close();
	}

}
