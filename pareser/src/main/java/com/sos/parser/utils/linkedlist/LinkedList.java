package com.sos.parser.utils.linkedlist;

import java.util.Iterator;


/**
 * 
 * @author louisweyrich
 *
 * @param <E>
 */
public class LinkedList<E> {
	
	private LinkedNode<E> first;
	private LinkedNode<E> end;
	private int size = 0;

	/**
	 * 
	 */
	public LinkedList() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 
	 * @param object
	 */
	public void pushTop(E object)
	{
		if(first == null)
		{
			first = new LinkedNode <E> (object);
			end = first;
		}
		else
		{
			LinkedNode <E> tempNode = first;
			first = new LinkedNode <E> (object);
			first.addLinkedNode(tempNode);
		}
		size++;
	}
	
	public void clear()
	{
		int tempSize = size;
		
		for(int index = 0; index < tempSize; index++)
		{
			LinkedNode <E> node = first;
			first = node.popChildNode();
			node.clear();
			size--;
		}
		
		end.clear();
		end = null;
	}
	
	/**
	 * 
	 * @param object
	 */
	public void pushEnd(E object)
	{
		if(end == null)
		{
			end = new LinkedNode <E> (object);
			first = end;
		}
		else
		{
			LinkedNode <E> tempNode = end;
			end = new LinkedNode <E> (object);
			tempNode.addLinkedNode(end);
		}
		size++;
	}
	
	/**
	 * 
	 * @return
	 */
	public E peekEnd()
	{
		return (end != null)? end.getObject() : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public E peekTop()
	{
		return (first != null)? first.getObject() : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public E popTop()
	{
		if(first != null)
		{
			LinkedNode <E> tempNode = first;
			first = first.popChildNode();
			E object = tempNode.getObject();
			tempNode.clear();
			size--;
			if(first == null)
			{
				end = null;
			}
			return object;
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean hasMoreNodes()
	{
		return size > 0;
	}
	
	/**
	 * 
	 * @return
	 */
	public Iterator <E> iterator(){
		return new Iterator <E> () {

			public boolean hasNext() {
				return hasMoreNodes();
			}

			public E next() {
				return popTop();
			}
			
		};
	}
	
	/**
	 * 
	 * @return
	 */
	public int size()
	{
		return size;
	}

}
