package com.sos.parser.utils;

import java.util.Iterator;

public interface Stack <E> {

	public void clear();
	public E peek();
	public E peekAt(int index);
	public E pop();
	public void push(E e);
	public int getIndex(E e);
	public Iterator <E> iterator();
	public boolean isEmpty();
	public int size();
	public E [] toArray();
	public E [] toArray(E [] e);
	public int search(E e);
}
