package com.sos.parser.io;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.sos.parser.exception.ArrayOverflowException;

/**
 * 
 * @author rr913763
 *
 */
public class CharacterBuffer implements ArrayBuffer 
{
	
	protected CharacterHolder[] buffer;
	protected int bufferSize;
	protected int growthSize;
	protected int charSize;
	protected int size = 0;
	protected int mark = 0;
	protected boolean autoMark = false;

	/**
	 * 
	 * @param bufferSize
	 */
	public CharacterBuffer(int bufferSize)
	{
		this(bufferSize, 10, 10);
	}
	
	/**
	 * 
	 * @param bufferSize
	 * @param initialSize
	 * @param growthSize
	 */
	public CharacterBuffer(int bufferSize, int initialSize, int growthSize)
	{
		this(bufferSize, initialSize, growthSize, false);
	}

	/**
	 * 
	 * @param bufferSize
	 * @param initialSize
	 * @param growthSize
	 * @param autoMark
	 */
	public CharacterBuffer(int bufferSize, int initialSize, int growthSize, boolean autoMark)
	{
		this.bufferSize = bufferSize;
		this.growthSize = growthSize;
		this.buffer = new CharacterHolder[initialSize];
		this.autoMark = autoMark;
	}
	
	/**
	 * 
	 */
	public void clear()
	{
		buffer = new CharacterHolder[growthSize];
		size = 0;
		charSize = 0;
		mark = 0;
	}
	
	/**
	 * 
	 * @param index
	 */
	public void mark(int index)
	{
		// the remaining character 
		int remaining = ((index - mark) / bufferSize);
		
		if(remaining > 0)
		{
			CharacterHolder [] temp = new CharacterHolder [buffer.length - remaining];
			System.arraycopy(buffer, remaining, temp, 0, temp.length);
			mark += remaining * bufferSize;
			buffer = temp;
		}
	}
	
	/**
	 * 
	 */
	public int getBufferSize()
	{
		return charSize;
	}

	/**
	 * 
	 * @param testSize
	 */
	protected void checkAvailablilty(int testSize)
	{
		if(buffer.length + testSize > Integer.MAX_VALUE)
		{
			throw new ArrayOverflowException(
				"CharacterBuffer has reached the maximum size ("+
				buffer.length + testSize+") max size ("+Integer.MAX_VALUE+")");
		}
		
		try
		{
			int available = buffer.length - size;
			int growth = (testSize > growthSize)? testSize:growthSize;
			
			if(available <= testSize)
			{
				CharacterHolder temp [] = new CharacterHolder[buffer.length + growth];
				System.arraycopy(buffer, 0, temp, 0, size);
				buffer = temp;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param characters
	 * @param available
	 * @throws IOException
	 */
	public void append(char [] characters, int available) throws IOException
	{
		if(available > characters.length)
		{
			throw new ArrayIndexOutOfBoundsException("Available is larger than the array size.");
		}
		
		if(characters.length != bufferSize && characters.length != available)
		{
			throw new ArrayIndexOutOfBoundsException("Array size does not match the maximum buffer size;.");
		}
		
		checkAvailablilty(1);
		buffer[size++] = new CharacterHolder(characters, available);
		charSize += (available < bufferSize)? available : bufferSize;
	}
	
	/**
	 * 
	 * @param characters
	 * @param available
	 * @throws IOException
	 */
	public void append(byte [] bytes, int available) throws IOException
	{
		if(available > bytes.length)
		{
			throw new ArrayIndexOutOfBoundsException("Available is larger than the array size.");
		}
		
		if(bytes.length != bufferSize && bytes.length != available)
		{
			throw new ArrayIndexOutOfBoundsException("Array size does not match the maximum buffer size;.");
		}
		
		checkAvailablilty(1);
		buffer[size++] = new CharacterHolder(convertBytesToCharArray(bytes), available);
		charSize += (available < bufferSize)? available : bufferSize;
	}
	
	private char [] convertBytesToCharArray(byte [] bytes) throws UnsupportedEncodingException {
		String text = new String(bytes, "UTF-8");
		char[] chars = text.toCharArray();
		return chars;
	}

	/**
	 * 
	 * @param value
	 * @throws IOException
	 */
	public void append(String value) throws IOException
	{
		int size = value.length();
		int batchSize = size/bufferSize;
		int remaining = size % bufferSize;
		int totalChars = 0;
		
		for(int index = 0; index < batchSize + ((remaining > 0)?1:0); index++)
		{
			int endIndex = (bufferSize+totalChars < size)? bufferSize+totalChars : (remaining+totalChars);
			String subString = value.substring(totalChars, endIndex);
			totalChars += subString.length();
			append(subString.toCharArray(), subString.length());
		}
	}
	
	/**
	 * 
	 * @param chr
	 */
	public void append(char chr)
	{
		CharacterHolder current = buffer[size - 1];
		
		if(current.getAvailable() >= bufferSize)
		{
			checkAvailablilty(1);
			buffer[size++] = new CharacterHolder(bufferSize);
		}
		
		current = buffer[size - 1];
		
		current.put(chr);
		charSize++;
	}
	
	/**
	 * 
	 * @param startIndex
	 * @param length
	 * @return
	 */
	public String getSubsetAsString(int startIndex, int length) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append(subset(startIndex, length));
		
		return buffer.toString();
	}
	
	/**
	 * 
	 */
	public char [] subset(int startIndex, int length) 
	throws ArrayIndexOutOfBoundsException
	{
		if(startIndex + length > charSize)
			throw new ArrayIndexOutOfBoundsException(
				"StartIndex plus length exceeds the array boundary.");
		
		if(autoMark && startIndex < mark)
			throw new ArrayIndexOutOfBoundsException(
				"Array has been marked.  startIndex preceeds the marked buffer index. index ="+startIndex+" : mark="+mark);
		
		char [] subset = new char [length];
		int bufferIndex = (startIndex - mark > 0)?(int)(startIndex - mark)/bufferSize : 0;
		int offset = (bufferIndex > 0)? (startIndex - mark) % (bufferIndex*bufferSize) : startIndex - mark;
		int newLength = (length < bufferSize - offset)? length : bufferSize - offset;
		int remaining = 0;
		
		while(bufferIndex < buffer.length && remaining < length)
		{
			CharacterHolder ch = buffer[bufferIndex++];
			char [] characters = ch.getCharacters();
			System.arraycopy(characters, offset, subset, remaining, newLength);
			remaining += (bufferSize - offset);
			newLength = (bufferSize > length - remaining)? (length - remaining) : bufferSize;
			offset = (ch.getAvailable() < bufferSize)? ch.getAvailable() : 0;
		}
		
		if(autoMark && ( (startIndex - mark) >= bufferSize) )
		{
			if(((startIndex - mark) / bufferSize) >= 1)
			{
				mark(startIndex+length);
			}
		}
		
		return subset;
	}
	
	/**
	 * 
	 */
	public char chatAt(int index)
	{
		return subset(index, 1)[0];
	}
	
	/**
	 * 
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		
		for(CharacterHolder holder : buffer)
		{
			if(holder != null)
			{
				builder.append(holder.getCharacters());
			}
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 */
	public boolean exceedsMaxBufferSize(int testSize)
	{
		return (buffer.length + testSize > Integer.MAX_VALUE);
	}

	/**
	 * 
	 */
	public int available()
	{
		return ((charSize > bufferSize)?bufferSize: charSize);
	}
	
	/**
	 * 
	 * @author Louis Weyrich
	 *
	 */
	protected class CharacterHolder
	{
		protected char [] characters;
		protected int available;
		
		/**
		 * 
		 * @param size
		 */
		public CharacterHolder(int size)
		{
			characters = new char [size];
		}
		
		/**
		 * 
		 * @param characters
		 * @param available
		 */
		public CharacterHolder(char [] characters, int available)
		{
			this.available = available;
			this.characters = characters;
		}
		
		/**
		 * 
		 * @param chr
		 */
		public void put(char chr)
		{
			if(available >= characters.length)
			{
				throw new ArrayIndexOutOfBoundsException("CharacterHolder is full: has("+available+")");
			}
			
			characters[available++] = chr;
		}
		
		/**
		 * 
		 * @return
		 */
		public char [] getCharacters()
		{
			return characters;
		}
		
		/**
		 * 
		 * @return
		 */
		public int getAvailable()
		{
			return this.available;
		}
		
		/*
		 * 
		 */
		public int length()
		{
			return characters.length;
		}
	}

	
}
