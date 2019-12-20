package com.sos.parser.utils.trie;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 * 
 * @author louisweyrich
 *
 */
public class TrieCharTree 
{
	private TrieCharNode rootNode;
	private int minimumLength = 0;

	/**
	 * 
	 * @param minimumLength
	 */
	public TrieCharTree(int minimumLength) 
	{
		this.rootNode = new TrieCharNode(' ', false);
		this.minimumLength = minimumLength;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMinimumLength() {
		return minimumLength;
	}

	/**
	 * 
	 * @param minimumLength
	 */
	public void setMinimumLength(int minimumLength) {
		this.minimumLength = minimumLength;
	}

	/**
	 * 
	 * @param term
	 */
	public void addTerm(String term)
	{
		
		if(term == null) return;
		
		if(containsText(term)) return;
		
		TrieCharNode node = rootNode;
		node.incrementCount();
		
		for(int index = 0; index < term.length(); index++)
		{
			Character c = Character.toLowerCase(term.charAt(index));
			if(node.containsChildNode(c))
			{
				node = node.getChild(c);
			}
			else
			{
				TrieCharNode child = new TrieCharNode(c, index + 1 == term.length());
				node.addChild(c, child);
				node = child;
			}
			
			if(term.length() == index +1)
			{
				node.setWordEnd(true);
			}
			
			node.incrementCount();
		}
	}
	
	/**
	 * 
	 * @param match
	 * @return
	 */
	public List <String> matchWords(String match)
	{
		if(match == null || match.length() < minimumLength) return new ArrayList <String> (0);
		
		List <String> list = new LinkedList <String> ();
		TrieCharNode node = this.rootNode;
		
		if(!match.equals(""))
		{
			for(int index = 0; index < match.length(); index++)
			{
				Character c = Character.toLowerCase(match.charAt(index));
				if(node.containsChildNode(c))
				{
					TrieCharNode childNode = node.getChild(c);				
					node = childNode;
				}
				else
				{
					return list;
				}
			}
		}
		
		if(!node.isLeaf())
		{
			list = matchWords(node, list);
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param node
	 * @param list
	 * @return
	 */
	private List <String> matchWords(TrieCharNode node, List <String> list)
	{
		if(node.isWord())
		{
			list.add(node.getWord());
		}
		
		if(!node.isLeaf())
		{
			for(TrieCharNode childNode : node.getChildren().values())
			{
				list = matchWords(childNode, list);
			}
		}
		
		return list;
	}
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public boolean containsText(String word)
	{
		TrieCharNode node = this.rootNode;
		
		for(int index = 0; index < word.length(); index++)
		{
			Character c = Character.toLowerCase(word.charAt(index));
			
			if(node.containsChildNode(c))
			{
				node = node.getChild(c);
			}
			else
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean containsCharacters(char [] characters) {
		
		TrieCharNode node = this.rootNode;
		
		for(int index = 0; index < characters.length; index++)
		{
			Character c = Character.toLowerCase(characters[index]);
			
			if(node.containsChildNode(c))
			{
				node = node.getChild(c);
			}
			else
			{
				return false;
			}
		}
		
		return true;
	}
	
	public boolean isWord(char [] characters) {
		TrieCharNode node = this.rootNode;
		
		for(int index = 0; index < characters.length; index++)
		{
			Character c = Character.toLowerCase(characters[index]);
			
			if(node.containsChildNode(c))
			{
				node = node.getChild(c);
			}
			else
			{
				return false;
			}
		}
		
		return node.isWord();
	}
	
	public boolean isWord(String word) {
		return isWord(word.toCharArray());
	}
	
	/**
	 * 
	 */
	public void clear()
	{
		this.rootNode.clear();
	}

	/**
	 * 
	 * @param term
	 * @return
	 */
	public TrieCharNode getNode(String term)
	{
		if(term.equals("")) return this.rootNode;
		
		TrieCharNode node = this.rootNode;
		
		for(int index = 0; index < term.length(); index++)
		{
			Character c = Character.toLowerCase(term.charAt(index));
			
			if(node.containsChildNode(c))
			{
				node = node.getChild(c);
			}
			else
			{
				return null;
			}
		}
		
		return node;
	}
}
