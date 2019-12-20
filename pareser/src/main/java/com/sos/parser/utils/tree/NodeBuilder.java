package com.sos.parser.utils.tree;

public interface NodeBuilder <ID, T>
{
	public Node <ID, T> createNewNode(ID theID, T theValue);
}
