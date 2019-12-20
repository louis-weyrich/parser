/**
 * 
 */
package com.sos.parser.utils;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

/**
 * @author lweyrich
 *
 */
public class UtilQueue <E> implements Queue<E> 
{
	
	public static final int DEFAULT_GROWTH_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	protected E [] elements = (E[]) new Object[DEFAULT_GROWTH_SIZE];
	protected int size = 0;
	protected int growthSize = DEFAULT_GROWTH_SIZE;
	protected int maxSize = -1;

	/**
	 * 
	 */
	public UtilQueue() 
	{
		this(DEFAULT_GROWTH_SIZE);
	}
	
	public UtilQueue(int growthSize) 
	{
		this(growthSize, -1);
	}

	
	public UtilQueue(int growthSize, int maxSize) 
	{
		this.growthSize = growthSize;
		this.maxSize = maxSize;
		test(growthSize);
	}

	@SuppressWarnings("unchecked")
	protected void test(int newSize)
	{
		if((size + newSize) >= elements.length)
		{
			E [] tempElements = (E[]) new Object [size + newSize + growthSize];
			System.arraycopy(elements, 0, tempElements, 0, size);
			elements = tempElements;
		}
	}


	@SuppressWarnings("unchecked")
	public boolean addAll(Collection <? extends E> collection) 
	{
		boolean added = true;
		
		if((maxSize == -1)||(size+collection.size()) < maxSize)
		{
			test(collection.size());
			
			Iterator <?> iterator = collection.iterator();
			while(iterator.hasNext())
			{
				try
				{
					add((E)iterator.next());
				}
				catch(IllegalStateException e)
				{
					added = false;
				}
			}
			
		}
		else
		{
			added = false;
		}
		
		return added;
	}


	@SuppressWarnings("unchecked")
	public void clear() 
	{
		elements = (E[]) new Object[0];
		size = 0;
	}


	public boolean contains(Object testElement) 
	{
		for(E e : elements)
		{
			if(e != null)
			{
				if(e.equals(testElement))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	protected int elementIndex(Object testElement)
	{
		for(int i = 0; i < size; i++)
		{
			E e = elements[i];
			if(e != null)
			{
				if(e.equals(testElement))
				{
					return i;
				}
			}
		}
		
		return -1;
	}


	public boolean containsAll(Collection<?> collection) 
	{
		Iterator <?> iterator = collection.iterator();
		while(iterator.hasNext())
		{
			if(!contains(iterator.next()))
			{
				return false;
			}
		}
		return true;
	}


	public boolean isEmpty() 
	{
		return (size == 0);
	}


	public Iterator<E> iterator() 
	{
		return new Iterator<E>(){
			
			private int currentIndex = 0;


			public boolean hasNext() 
			{
				return (currentIndex < size);
			}


			public E next() 
			{
				return elements[currentIndex++];
			}


			@SuppressWarnings("unchecked")
			public void remove() 
			{
				E [] tempElements = (E []) new Object[elements.length-1];
				int index = currentIndex - 1;
				if(index == 0)
				{
					System.arraycopy(elements, 1, tempElements, 0, size-1);
				}
				else
				{
					System.arraycopy(elements, 0, tempElements, 0, index);
					System.arraycopy(elements, index+1, tempElements, index, size-1);
				}
				currentIndex = index;
				elements = tempElements;
				size--;
			}
			
		};
	}


	@SuppressWarnings("unchecked")
	public boolean remove(Object value) 
	{
		int index = elementIndex(value);
		if(index > -1)
		{
			E [] tempElements = (E[]) new Object [elements.length -1];
			if(index == 0)
			{
				System.arraycopy(elements, 1, tempElements, 0, size-1);
			}
			else
			{
				System.arraycopy(elements, 0, tempElements, 0, index);
				System.arraycopy(elements, index+1, tempElements, index, size-(1+index));
			}
			elements = tempElements;
			size--;
			return true;
		}
		return false;
	}


	public boolean removeAll(Collection<?> collection) 
	{
		boolean hasAll = true;
		Iterator <?> iterator = collection.iterator();
		
		while(iterator.hasNext())
		{
			Object object = iterator.next();
			if(contains(object))
			{
				remove(object);
			}
			else
			{
				hasAll = false;
			}
		}
		
		return hasAll;
	}


	public boolean retainAll(Collection<?> collection) 
	{
		boolean hasAll = false;
		Iterator <?> iterator = iterator();
		
		while(iterator.hasNext())
		{
			Object object = iterator.next();
			if(!collection.contains(object))
			{
				iterator.remove();
			}
			else
			{
				hasAll = true;
			}
		}
		
		return hasAll;
	}


	public int size() 
	{
		return size;
	}


	public Object[] toArray() {
		return (Object [])elements;
	}


	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] newElements) 
	{
		for(T t : newElements)
		{
			add((E)t);
		}
		
		return (T[])this.elements;
	}


	public boolean add(E element) 
	{
		
		if(maxSize == -1 || (size < maxSize))
		{
			test(1);
			elements[size] = element;
			size++;
			return true;
		}
		else
		{
			throw new IllegalStateException("maxsize is set to ("+maxSize+") no more elements can be added.");
		}
	}


	public E element() 
	{
		if(size > 0)
			return elements[0];
		else
			return null;
	}


	public boolean offer(E element) 
	{
		boolean added = true;
		try
		{
			added = add(element);
		}
		catch(IllegalStateException e)
		{
			added = false;
		}
		
		return added;
	}
	
	public E peekLastAdded()
	{
		return (size > 0)? elements[size-1] : null;
	}


	public E peek() 
	{
		return element();
	}
	
	public E peekAtNext()
	{
		if(elements.length < 2)
		{
			return null;
		}
		
		return elements[1];
	}
	
	public E peekNext(E element) {
		int index = indexOf(element);
		return elements[index+1];
	}


	public E poll() 
	{
		return remove();
	}


	@SuppressWarnings("unchecked")
	public E remove() 
	{
		if(size > 0)
		{
			E element = elements[0];
		
			E [] tempElements = (E[]) new Object [elements.length -1];
			System.arraycopy(elements, 1, tempElements, 0, size -1);
			elements = tempElements;
			size--;
		
			return element;
		}
		else
		{
			return null;
		}
	}

	public boolean reachedMaxSize()
	{
		return !(size < maxSize);
	}
	
	public int indexOf(E element) {
		int index = -1;
		
		for(int i = 0; i < elements.length; i++) {
			if(elements[i].equals(element)) {
				index = i;
				break;
			}
		}
		
		return index;
	}
}
