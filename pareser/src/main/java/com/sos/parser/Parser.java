package com.sos.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public interface Parser {

	public void parse(String content);
	public void parse(Reader contentReader);
	public void parse(InputStream contentStream);
	public void parse(char [] content);
	
	public ParserObject getNextParserObject();
	public boolean hasNextParserObject();
	
	public ParserContext getParserContext();
	public TokenScanner getScanner();
	
	public void close() throws IOException;
}
