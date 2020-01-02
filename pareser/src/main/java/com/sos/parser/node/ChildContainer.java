package com.sos.parser.node;

import java.util.ArrayList;
import java.util.List;

public abstract class ChildContainer extends ContentContainer 
{

	protected List <NodeContainer> children;
	
	public ChildContainer() {
		super();
		children = new ArrayList <NodeContainer> ();
	}

	public List<NodeContainer> getChildren()
	{
		return children;
	}
	
	@Override
	public void addContent(Object content)
	{
		if(content instanceof String) {
			super.addContent(content);
		}else {
			children.add((NodeContainer)content);
		}
	}

	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		if(super.content != null) builder.append(content.toString());
				
		for(NodeContainer container : children) {
			builder.append(container.toString());
		}
				
		return builder.toString();
	}
}
