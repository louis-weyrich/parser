/**
 * 
 */
package com.sos.parser.utils;

import java.util.Iterator;

import com.sos.parser.exception.FullException;

/**
 * @author lweyrich
 *
 */
public class UtilStack <E> implements Stack<E> 
{
	
	public static final int DEFAULT_GROWTH_SIZE = 10;
	
	@SuppressWarnings("unchecked")
	protected E [] elements = (E[]) new Object[DEFAULT_GROWTH_SIZE];
	protected int size = 0;
	protected int growthSize = DEFAULT_GROWTH_SIZE;
	protected int maxSize = Integer.MAX_VALUE;

	
	/**
	 * 
	 */
	public UtilStack() 
	{
		this(DEFAULT_GROWTH_SIZE);
	}
	
	public UtilStack(int growthSize) 
	{
		setGrowthSize(growthSize);
		test(1);
	}

	
	public UtilStack(int growthSize, int maxSize) 
	{
		setGrowthSize(growthSize);
		setMaxSize(maxSize);
		test(1);
	}
	
	public UtilStack(E [] e)
	{
		this(e, DEFAULT_GROWTH_SIZE, -1);
	}
	
	public UtilStack(E [] elements, int growthSize, int maxGrowth)
	{
		this(growthSize, maxGrowth);
		size = elements.length;
		this.elements = elements;
	}

	@SuppressWarnings("unchecked")
	private void test(int newSize)
	{
		if((size + newSize) >= elements.length){
			E [] tempElements = (E[]) new Object [size + newSize + growthSize];
			System.arraycopy(elements, 0, tempElements, 0, size);
			elements = tempElements;
		}
	}


	@SuppressWarnings("unchecked")
	public void clear() 
	{
		elements = (E[]) new Object[0];
		size = 0;
	}


	public E peek() 
	{
		if(size > 0)
			return elements[size - 1];
		else
			return null;
	}


	public E peekAt(int index) 
	{
		if(size > index)
		{
			return elements[index];
		}
		
		return null;
	}


	@SuppressWarnings("unchecked")
	public E pop() 
	{
		if(size > 0)
		{
			E element = elements[size - 1];
			E [] tempElements = (E[])new Object[elements.length - 1];
			System.arraycopy(elements, 0, tempElements, 0, size - 1);
			size--;
			return element;
		}
		else
		{
			return null;
		}
	}


	public void push(E e) 
	{
		if(size <= maxSize || maxSize == -1)
		{
			test(1);
			elements[size++] = e;
		}
		else
		{
			throw new FullException(
				"Stack has reached the max size("+maxSize+")");
		}
		
	}


	public int getIndex(E element) 
	{
		if(!isEmpty())
		{
			for(int index = 0; index < size; index++)
			{
				E e = elements[index];
				if(e != null)
				{
					if(e.equals(element)) return index;
				}
			}
		}
		
		return -1;
	}


	public int size() 
	{
		return size;
	}


	public boolean isEmpty() 
	{
		return !(size > 0);
	}
	
	public void setMaxSize(int maxSize)
	{
		this.maxSize = maxSize;
	}
	
	public void setGrowthSize(int growthSize)
	{
		this.growthSize = growthSize;
	}


	public Iterator<E> iterator() 
	{
		return new Iterator <E> ()
		{
			
			public int currentIndex = size -1;


			public boolean hasNext() 
			{
				return (currentIndex > 0);
			}
			

			public E next() 
			{
				return elements[currentIndex--];
			}


			public void remove() 
			{
				throw new UnsupportedOperationException();
			}
			
		};
	}


	@SuppressWarnings("unchecked")
	public E[] toArray() 
	{
		E [] tempElements = (E []) new Object[size];
		System.arraycopy(elements, 0, tempElements, 0, size);
		elements = tempElements;
		return elements;
	}


	@SuppressWarnings("unchecked")
	public E[] toArray(E[] array) 
	{
		if((size+array.length) <= maxSize)
		{
			test(array.length);
			
			for( E e : array)
			{
				push(e);
			}
			
			E [] tempElements = (E []) new Object[size];
			System.arraycopy(elements, 0, tempElements, 0, size);
			return tempElements;
		}
		else
		{
			throw new FullException(
				"Stack has reached the max size("+maxSize+") = "+(size+array.length));
		}
	}


	public int search(E e)
	{
		if(e != null)
		{
			for(int index = size -1; index > 0; --index)
			{
				if(elements[index].equals(e))
				{
					return index;
				}
			}
		}
		return 0;
	}

}
