package com.sos.parser.node;

public abstract class ContentContainer implements NodeContainer {
	
	protected String content;

	public ContentContainer() {}

	public void addContent(Object content) 
	{
		if(content instanceof String)
		{
			this.content = content.toString();
		}
	}

	public String getContent() 
	{
		return content;
	}

	public abstract  NodeType getType();

	
	@Override
	public String toString() {
		return this.getContent();
	}
}
