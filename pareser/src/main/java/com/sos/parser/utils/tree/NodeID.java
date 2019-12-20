package com.sos.parser.utils.tree;

public interface NodeID <ID>
{
	public ID getID();
	public boolean isID(NodeID <ID> id);
	public int compareID(NodeID <ID> id);
}
