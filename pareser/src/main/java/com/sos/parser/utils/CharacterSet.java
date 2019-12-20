/**
 * 
 */
package com.sos.parser.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;

/**
 * @author louis.weyrich
 *
 */
public class CharacterSet implements Set<Character>
{

	public static final int DEFAULT_INITIAL_SIZE = 10;
	
	private Character [] array = new Character[0];
	private int size = 0;
	private int loadFactor;
	
	/**
	 * 
	 */
	public CharacterSet()
	{
		this(DEFAULT_INITIAL_SIZE, DEFAULT_INITIAL_SIZE);
	}
	
	
	public CharacterSet(int initialSize)
	{
		this(initialSize, DEFAULT_INITIAL_SIZE);
	}

	public CharacterSet(int initialSize, int loadFactor)
	{
		this.loadFactor = loadFactor;
		checkSize(initialSize);
	}
	
	
	public CharacterSet(char [] array) {
		this(array.length);
		for(char c : array) {
			this.add(c);
		}
	}
	
	
	protected void checkSize(int increaseSize)
	{
		if(size + increaseSize >= array.length)
		{
			Character [] tempArray = new Character[size+increaseSize+loadFactor];
			if(size > 0)
			{
				System.arraycopy(array, 0, tempArray, 0, size);
			}
			
			array = tempArray;
		}
	}

	protected void addToIndex(Character element, int index)
	{
		checkSize(1);
		if(index == 0){
			System.arraycopy(array, 0, array, 1, size);
			array[0] = element;
			size++;
		}
		else
		{
			if(size > 0)
			{
				System.arraycopy(array, index, array, index+1, size-index);
			}
			array[index] = element;
			size++;
		}
	}

	public int indexOfSorted(Character object)
	{
		int first = 0;
		int upto = size;
		
		 while (first < upto && object != null) 
		 {
		        int mid = (first + upto) / 2;  // Compute mid point.
		        Character e = array[mid];
		        int value = -(getComparator().compare(object, e));
		        if (value == 1) 
		        {
		            upto = mid;     // repeat search in bottom half.
		        } 
		        else if(value == -1) 
		        {
		            first = mid + 1;  // Repeat search in top half.
		        } 
		        else 
		        {
		            return mid;     // Found it. return position
		        }
		    }
		 
		    return (first);    // Failed to find key
	}

	/**
	 * @see java.util.Set#size()
	 */
	public int size()
	{
		return size;
	}

	/**
	 * @see java.util.Set#isEmpty()
	 */
	public boolean isEmpty()
	{
		return size == 0;
	}

	public int getLoadFactor()
	{
		return loadFactor;
	}


	public void setLoadFactor(int loadFactor)
	{
		this.loadFactor = loadFactor;
	}


	/**
	 * @see java.util.Set#contains(java.lang.Object)
	 */
	public boolean contains(Object object)
	{
		if(object instanceof Character)
		{
			Character c = (Character)object;
			int index = indexOfSorted(c);
			return ((index > -1)?(getComparator().compare(array[index], c) == 0):false);
		}
		else if(object instanceof String && object.toString().length() == 1)
		{
			Character c = ((String)object).charAt(0);
			int index = indexOfSorted(c);
			return ((index > -1)?(getComparator().compare(array[index], c) == 0):false);
		}
		
		return false;
	}

	/**
	 * @see java.util.Set#iterator()
	 */

	public Iterator<Character> iterator()
	{
		return new Iterator <Character> ()
		{
			private int currentIndex = 0;
			
			public boolean hasNext()
			{
				return currentIndex < size;
			}

			public Character next()
			{
				if(currentIndex >= size)
				{
					currentIndex = size -1;
				}
				
				return array[currentIndex++];
			}
			
		};
	}

	/**
	 * @see java.util.Set#toArray()
	 */
	public Character[] toArray()
	{
		return array;
	}
	
	public char [] toCharArray() {
		char [] charArray = new char[size];
		
		if(size > 0) {
			for(int index = 0; index < size; index++) {
				charArray[index++] = array[index];
			}
		}
		
		return charArray;
	}

	/**
	 * @see java.util.Set#toArray(java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] elementArray)
	{
		if (elementArray.length < size)
			elementArray = (T[]) Array.newInstance(array.getClass().getComponentType(), size);
		else if (elementArray.length > size)
			elementArray[size] = null;
		
		System.arraycopy(array, 0, elementArray, 0, size);
		return elementArray;
	}

	/**
	 * @see java.util.Set#add(java.lang.Object)
	 */
	public boolean add(Character c)
	{
		if(!contains(c))
		{
			boolean added = false;
			int index = -1;
			
			if(c != null)
			{
				index = indexOfSorted(c);
				addToIndex(c, index);
				if(index > -1) added = true;
			}
			
			return added;
		}
		
		return false;
	}

	public boolean add(char c)
	{
		Character newCharacter = Character.valueOf(c);
		if(!contains(newCharacter))
		{
			boolean added = false;
			int index = -1;
			
			if(newCharacter != null)
			{
				index = indexOfSorted(newCharacter);
				addToIndex(newCharacter, index);
				
				if(index > -1) added = true;
			}
			
			return added;
		}
		
		return false;
	}
	
	public Character [] sort()
	{
		boolean finished = false;
		int currentIndex = size;
		int largestIndex = currentIndex;
		Character current = null;
		Character largest = null;
		
		while(currentIndex > 0)
		{
			current = array[--currentIndex];
			largest = current;
			finished = true;
			
			for(int index = currentIndex; index > -1; index--)
			{
				Character e = array[index];
				if(e != null && current != null && largest != null)
				{
					if(getComparator().compare(e,current) > 0 && 
					getComparator().compare(e, largest) > 0)
					{
						largest = e;
						largestIndex = index;
						finished = false;
					}
				}
			}
			
			if(!finished)
			{
				array[largestIndex] = current;
				array[currentIndex] = largest;
			}
			
		}
		
		return array;
	}

	public Comparator <Character> getComparator()
	{
		return new Comparator <Character> ()
		{

			public int compare(Character ch1, Character ch2)
			{
				
				if(ch1 == null)
				{
					return -1;
				}
				else if(ch2 == null)
				{
					return 1;
				}
				
				int hashcode1 = ch1.hashCode();
				int hashcode2 = ch2.hashCode();
				
				if(hashcode1 == hashcode2)
				{
					return 0;
				}
				else if(hashcode1 > hashcode2)
				{
					return 1;
				}
				else if(hashcode1 < hashcode2)
				{
					return -1;
				}
				
				return 0;
			}
	
		};
	}
	
	
	/**
	 * @see java.util.Set#remove(java.lang.Object)
	 */

	public boolean remove(Object element) 
	{
		boolean removed = false;
		
		if(element instanceof Character)
		{
			Character c = (Character)element;
			if(contains(c))
			{
				int index = indexOfSorted(c);
				if(index > -1)
				{
					removed = (remove(index) != null)?true:removed;
				}
			}
		}
		
		return removed;
	}

	public Character remove(int index) 
	{
		Character e = null;
		if(index > -1 && index < size)
		{
			e = array[index];
			Character [] tempArray = new Character[array.length - 1];
			if(index == 0)
			{
				System.arraycopy(array, 1, tempArray, 0, size -1);
			}
			else
			{
				System.arraycopy(array, 0, tempArray, 0, index);
				System.arraycopy(array, index+1, tempArray, index, size-index);
			}
			array = tempArray;
			size--;
		}
		
		return e;
	}

	/**
	 * @see java.util.Set#containsAll(java.util.Collection)
	 */

	public boolean containsAll(Collection<?> c)
	{
		for(Object element : c)
		{
			if(!contains(element)) return false;
		}
		
		return true;
	}

	/**
	 * @see java.util.Set#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection<? extends Character> collection)
	{
		boolean complete = false;
		
		if(collection != null){
			complete = true;
			//Remove null entries
			Iterator <?> iterator = collection.iterator();
			while(iterator.hasNext())
			{
				Object o = iterator.next();
				if(o == null)
				{
					iterator.remove();
					complete = false;
				}
			}
			
			//Check if array is large enough
			checkSize(collection.size());
			
			//Add all elements
			for(Character e : collection)
			{
				if(e != null){
					boolean added = add(e);
					if(complete && !added) complete = added;
				}
				else
				{
					complete = false;
				}
			}
		}
		
		return complete;
	}


	/**
	 * @see java.util.Set#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection<?> collection) 
	{
		boolean success = false;
		
		if(collection != null)
		{
			success = true;
			for(Object o : collection)
			{
				boolean contains = contains(o);
				success = (contains)?success:false;
			}
			
			for(int index = 0; index < size; index++)
			{
				Character e = array[index];
				boolean contains = collection.contains(e);
				
				if(!contains)
				{
					remove(e);
					index--;
				}
			}
		}
		
		return success;
	}

	/**
	 * @see java.util.Set#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection<?> collection) 
	{
		boolean complete = false;
		
		if(collection != null)
		{
			complete = true;
			for(Object o : collection)
			{
				if(o != null)
				{
					if(!remove(o))
					{
						complete = false;
					}
				}
				else
				{
					complete = false;
				}
			}
		}
		
		return complete;
	}

	/**
	 * @see java.util.Set#clear()
	 */
	public void clear()
	{
		array = new Character[0];
		size = 0;
	}

}
