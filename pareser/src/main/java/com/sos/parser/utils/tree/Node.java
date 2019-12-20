package com.sos.parser.utils.tree;

public interface Node <ID, T> extends NodeID<ID>
{
	public Node <ID, T> getParent();
	public void setParent(Node <ID, T> parent);
	
	public Node <ID, T> getLeftChild();
	public Node <ID, T> getLeftMostChild();
	public void setLeftChild(Node <ID, T> child);
	public boolean isLeftChild();
	
	public Node <ID, T> getRightChild();
	public Node <ID, T> getRightMostChild();
	public void setRightChild(Node <ID, T> child);
	public boolean isRightChild();
	
	public boolean isLeaf();
	public boolean isRoot();
	public boolean hasRightChild();
	public boolean hasLeftChild();
	public boolean hasBothChildren();
	
	public T getValue();
	
	public String nodeInfo();
	public void detach();
	public NodeID <ID> getNodeID();
	
}
