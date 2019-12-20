package com.sos.parser;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.sos.parser.utils.CharacterSet;
import com.sos.parser.utils.MatchedTokenSet;
import com.sos.parser.utils.trie.TrieCharTree;

public class ParserContext {
	
	private TrieCharTree tokenTree;
	private CharacterSet parsableTokens;
	private CharacterSet tokensNotAllowed;
	private Set <Character> ignorableSet;
	private Set <String> keywords;
	private Character statementEnd;
	private MatchedTokenSet matchedTokens;

	
	private Map <String, String> operatorMap;
	private Parser parser;
	private int bufferSize;
	private boolean autoMarkBuffer = true;
	private boolean matchQuotes = true;
	private boolean quoted = false;
	private boolean showIgnorables = false;
	private int initialBufferSize = 20;
	private int bufferGrowthSize = 20;


	public ParserContext() {
		// Do Nothing
	}
	

	public TrieCharTree getTokenTree() {
		if(tokenTree == null) {
			this.tokenTree = new TrieCharTree(2);
		}
		return tokenTree;
	}


	public void setTokenTree(TrieCharTree tokenTree) {
		this.tokenTree = tokenTree;
	}


	public Set<Character> getIgnorableSet() {
		if(ignorableSet == null) {
			this.ignorableSet = new HashSet <Character> ();
		}
		return ignorableSet;
	}


	public void setIgnorableSet(Set<Character> ignorableSet) {
		this.ignorableSet = ignorableSet;
	}



	public Map<String, String> getOperatorMap() {
		if(this.operatorMap == null) {
			this.operatorMap = new HashMap <String,String> ();
		}
		return operatorMap;
	}


	public void setOperatorMap(Map<String, String> operatorMap) {
		this.operatorMap = operatorMap;
	}


	public CharacterSet getParsableTokens() {
		if(this.parsableTokens == null) {
			this.parsableTokens = new CharacterSet();
		}
		return parsableTokens;
	}


	public void setParsableTokens(CharacterSet parsableTokens) {
		this.parsableTokens = parsableTokens;
	}


	public Parser getParser() {
		return parser;
	}


	public void setParser(Parser parser) {
		this.parser = parser;
	}

	public boolean isAutoMarkBuffer() {
		return autoMarkBuffer;
	}


	public void setAutoMarkBuffer(boolean autoMarkBuffer) {
		this.autoMarkBuffer = autoMarkBuffer;
	}


	public int getBufferSize() {
		return bufferSize;
	}


	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}


	public int getInitialBufferSize() {
		return initialBufferSize;
	}


	public void setInitialBufferSize(int initialBufferSize) {
		this.initialBufferSize = initialBufferSize;
	}


	public int getBufferGrowthSize() {
		return bufferGrowthSize;
	}


	public void setBufferGrowthSize(int bufferGrowthSize) {
		this.bufferGrowthSize = bufferGrowthSize;
	}


	public CharacterSet getTokensNotAllowed() {
		if(tokensNotAllowed == null) {
			tokensNotAllowed = new CharacterSet();
		}
		
		return tokensNotAllowed;
	}


	public void setTokensNotAllowed(CharacterSet tokensNotAllowed) {
		this.tokensNotAllowed = tokensNotAllowed;
	}


	public Set<String> getKeywords() {
		if(keywords == null) {
			this.keywords = new HashSet <String> ();
		}
		return keywords;
	}


	public void setKeywords(Set<String> keywords) {
		this.keywords = keywords;
	}


	public Character getStatementEnd() {
		return statementEnd;
	}


	public void setStatementEnd(Character statementEnd) {
		this.statementEnd = statementEnd;
	}


	public MatchedTokenSet getMatchedTokens() {
		if(matchedTokens == null) {
			matchedTokens = new MatchedTokenSet();
		}
		return matchedTokens;
	}


	public void setMatchedTokens(MatchedTokenSet matchedTokens) {
		this.matchedTokens = matchedTokens;
	}


	public boolean isMatchQuotes() {
		return matchQuotes;
	}


	public void setMatchQuotes(boolean matchQuotes) {
		this.matchQuotes = matchQuotes;
	}


	public boolean isQuoted() {
		return quoted;
	}


	public void setQuoted(boolean quoted) {
		this.quoted = quoted;
	}
	

	public boolean isShowIgnorables() {
		return showIgnorables;
	}


	public void setShowIgnorables(boolean parseIgnorables) {
		this.showIgnorables = parseIgnorables;
	}


	public void switchQuoted() {
		this.quoted = !quoted;
	}
}
