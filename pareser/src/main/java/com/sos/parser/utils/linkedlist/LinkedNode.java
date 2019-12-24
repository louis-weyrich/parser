package com.sos.parser.utils.linkedlist;

public class LinkedNode<E> {
	
	private E object;
	private LinkedNode <E> child;

	public LinkedNode(E object) {
		this.object = object;
	}
	
	public E getObject()
	{
		return object;
	}
	
	public void addLinkedNode(LinkedNode <E> node)
	{
		this.child = node;
	}
	
	public LinkedNode <E> popChildNode()
	{
		LinkedNode <E> tempNode = child;
		child =  null;
		return tempNode;
	}
	
	public LinkedNode <E> peekChildNode()
	{
		return child;
	}

}
