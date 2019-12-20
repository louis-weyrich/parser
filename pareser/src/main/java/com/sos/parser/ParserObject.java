package com.sos.parser;

import com.sos.parser.exception.TokenNotAllowedException;

public class ParserObject {
	
	private String content;
	long startIndex;
	long endIndex;
	private TokenNotAllowedException exception;

	public ParserObject(String content, long startIndex, long endIndex, TokenNotAllowedException exception) {
		this.content = content;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.exception = exception;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public long getStartIndex() {
		return startIndex;
	}

	public long getEndIndex() {
		return endIndex;
	}

	public TokenNotAllowedException getException() {
		return exception;
	}

	@Override
	public String toString() {
		return getContent();
	}
}
