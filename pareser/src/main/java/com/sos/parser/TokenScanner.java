package com.sos.parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Collection;

import com.sos.parser.exception.ParserException;
import com.sos.parser.exception.TokenNotAllowedException;
//import com.sos.parser.io.ArrayBuffer;
import com.sos.parser.io.CharacterBuffer;
import com.sos.parser.utils.CharacterSet;
import com.sos.parser.utils.UtilQueue;

/**
 * 
 * @author Louis Weyrich
 *
 */
public class TokenScanner
{
	
	// The maximum size of the buffer to read.
	public static final int MAX_BUFFER_SIZE = 256;
	
	// This is the size of the token character set.
	public static final int INITIAL_CHARACTER_SET_SIZE = 8;

	protected UtilQueue <TokenIndexer> 		queue; 				 // The queue of tokens found.
	protected Reader						streamReader; 		 // The contents to parse.
	protected CharacterBuffer				buffer;				 // Hold the content once parsed.
	protected int							previousIndex 	= 0; // The index of the previous token found.
	protected ParserContext					context;			 // Contains parser setup
	
	private StringBuilder 					cachedTokenSet; 	 // 
	
	/**
	 * Creates a TokenScanner with the bufferSize set to the default MAX_BUFFER_SIZE.
	 */
	public TokenScanner()
	{
		this(MAX_BUFFER_SIZE);
	}

	/**
	 * Creates a TokenScanner with the bufferSize set to the bufferSize passed in.
	 * 
	 * @param bufferSize
	 */
	public TokenScanner(int bufferSize)
	{
		this.context = new ParserContext();
		this.context.setBufferSize(bufferSize);
		this.context.setAutoMarkBuffer(true);
		this.context.setInitialBufferSize(20);
		this.context.setBufferGrowthSize(20);
		this.cachedTokenSet  = new StringBuilder();
		initialize();
	}
	
	/**
	 * Creates a TokenScanner with the bufferSize set to the bufferSize passed in
	 * and the token characters.
	 * 
	 * @param bufferSize
	 * @param characterCollection
	 */
	public TokenScanner(ParserContext parserContext)
	{
		this.context = parserContext;
		this.cachedTokenSet  = new StringBuilder();
		initialize();
	}
	
	/**
	 * 
	 * @param characterArray
	 */
	public TokenScanner(char [] characterArray)
	{
		this(MAX_BUFFER_SIZE, characterArray);
	}
	
	/**
	 * 
	 * @param bufferSize
	 * @param characterArray
	 */
	public TokenScanner(int bufferSize, char [] characterArray)
	{
		initialize();
		this.context = new ParserContext();
		this.cachedTokenSet  = new StringBuilder();
		for(char c : characterArray) {
			this.context.getParsableTokens().add(c);
		}
	}
	
	/**
	 * 
	 * @param bufferSize
	 * @param characterArray
	 */
	public TokenScanner(int bufferSize, CharacterSet characterSet)
	{
		initialize();
		this.context = new ParserContext();
		this.context.setParsableTokens(characterSet);
		this.cachedTokenSet  = new StringBuilder();
	}

	
	public void initialize() {
		this.queue = new UtilQueue<TokenIndexer>();
	}

	/**
	 * 
	 * @param data
	 */
	public void parse(String data)
	{
		parse(new StringReader(data));
	}
	
	/**
	 * 
	 * @param reader
	 */
	public void parse(Reader reader)
	{
		this.streamReader = reader;
		this.buffer = new CharacterBuffer(context.getBufferSize(), context.getInitialBufferSize(), context.getBufferGrowthSize(), context.isAutoMarkBuffer());
	}

	/**
	 * 
	 * @param stream
	 */
	public void parse(InputStream stream)
	{
		streamReader = new InputStreamReader(stream);
		this.buffer = new CharacterBuffer(context.getBufferSize(), context.getInitialBufferSize(), context.getBufferGrowthSize(), context.isAutoMarkBuffer());
	}

	/**
	 * 
	 * @param character
	 * @return
	 */
	public boolean addToCharacterSet(char character)
	{
		return this.context.getParsableTokens().add(character);
	}

	/**
	 * 
	 * @param characterArray
	 * @return
	 */
	public boolean addToCharacterSet(char [] characterArray)
	{
		boolean addedAll = true;
		
		for(Character character : characterArray)
		{
			boolean added = this.context.getParsableTokens().add(character);
			if(!added)addedAll = false;
		}
		
		return addedAll;
	}

	/**
	 * 
	 * @param characterCollection
	 * @return
	 */
	public boolean addToCharacterSet(Collection <Character> characterCollection)
	{
		return this.context.getParsableTokens().addAll(characterCollection);
	}

	/**
	 * 
	 * @return
	 */
	public boolean hasNext()
	{
		boolean hasNext = true;
		
		try
		{
			char [] charsRead = new char [context.getBufferSize()];
			int available = streamReader.read(charsRead);
			
			if(available > 0)
			{
				int index = 0;
				
				buffer.append(charsRead, available);
				
				for(; index < available; index++)
				{
					char charRead = charsRead[index];
					
					if(this.context.getTokensNotAllowed().contains(Character.valueOf(charRead))) {
						TokenNotAllowedException exception = new TokenNotAllowedException("Token ("+charRead+") is not allowed.", charRead, previousIndex+index);
						createSingleTokenIndex(index, available, charsRead, true, exception);
					}
					// if TokenSets already exist
					else if(this.cachedTokenSet.length() > 0 && this.context.getTokenTree().containsText(this.cachedTokenSet.toString())) {
						if(this.context.getTokenTree().isWord(this.cachedTokenSet.toString())) {
							createSingleTokenIndex(index, available, charsRead, false, null);
							this.cachedTokenSet = new StringBuilder();
						}
						else{
							this.cachedTokenSet.append(charRead);
						}
						
						
						if(this.context.getParsableTokens().contains(charRead) )
						{
							createSingleTokenIndex(index, available, charsRead, true, null);
						}
						
					}
					// for finding TokenSets
					else if(this.context.getTokenTree().containsText(String.valueOf(charRead))) {
						this.cachedTokenSet = new StringBuilder(String.valueOf(charRead));
						char nextCharRead = charsRead[index+1];
						if(this.context.getTokenTree().containsText(String.valueOf(charRead)+ String.valueOf(nextCharRead))) {
							createSingleTokenIndex(index, available, charsRead, false, null);
						}
					}
					// for single tokens
					else if(this.context.getParsableTokens().contains(charRead) )
					{
						createSingleTokenIndex(index, available, charsRead, true, null);
						this.cachedTokenSet = new StringBuilder();
					}else if(this.cachedTokenSet.length() > 0){
						this.cachedTokenSet = new StringBuilder();
					}
				}
				
				previousIndex += index;
			}
			else
			{
				hasNext = !(queue.isEmpty());
				if(queue.size() == 1)
				{
					TokenIndexer index = queue.peek();
					if(index.getToken() != Character.MIN_VALUE)
					{
						TokenIndexer newIndex = new TokenIndexer(
							Character.MIN_VALUE,index.getStartIndex()+1,buffer, 
							null);
						queue.add(newIndex);
					}
					
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
			hasNext = false;
		}
		
		return hasNext;
	}
	
	private void createSingleTokenIndex(int index, int available, char [] charsRead, boolean createEnd, TokenNotAllowedException exception) {
		TokenIndexer indexer = 
			new TokenIndexer(charsRead[index], previousIndex+index, buffer, exception);
		queue.add(indexer);
		if(index < available -1 && createEnd) {
			char nextCharRead = charsRead[index+1];
			if(!this.context.getParsableTokens().contains(nextCharRead)) {
				queue.add(new TokenIndexer(nextCharRead, previousIndex+index+1, buffer, null));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public TokenIndexer next()
	{
		return queue.poll();
	}
	
	/**
	 * 
	 * @return
	 */
	public TokenIndexer peek()
	{
		return queue.peek();
	}

	/**
	 * 
	 * @return
	 */
	public TokenIndexer peekNext()
	{
		return queue.peekAtNext();
	}
	

	public void close() throws IOException {
		this.streamReader.close();
	}
}
