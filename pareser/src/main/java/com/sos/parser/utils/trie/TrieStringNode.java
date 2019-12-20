package com.sos.parser.utils.trie;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class TrieStringNode 
{
	
	private TrieStringNode parent;
	private List <TrieStringNode> children;
	private String word;

	/**
	 * 
	 * @param c
	 */
	public TrieStringNode(String word) 
	{
		this.word = word;
	}


	/**
	 * 
	 * @return
	 */
	public TrieStringNode getParent() 
	{
		return parent;
	}

	/**
	 * 
	 * @param parent
	 */
	public void setParent(TrieStringNode parent) 
	{
		this.parent = parent;
	}

	/**
	 * 
	 * @return
	 */
	public List<TrieStringNode> getChildren() 
	{
		if(this.children == null)
		{
			this.children = new ArrayList <TrieStringNode> ();
		}
		
		return children;
	}
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public TrieStringNode getChild(int index)
	{
		return (this.children != null)? this.children.get(index) : null;
	}
	
	/**
	 * 
	 * @return
	 */
	public int childCount()
	{
		return (this.children != null)? this.children.size() : 0;
	}
	
	/**
	 * 
	 * @param c
	 * @param child
	 */
	public void addChild(TrieStringNode child)
	{
		child.setParent(this);
		getChildren().add(child);
	}

	/**
	 * 
	 * @param children
	 */
	public void setChildren(List<TrieStringNode> children) 
	{
		this.children = children;
	}

	/**
	 * 
	 * @return
	 */
	public String getWord() 
	{
		return word;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRoot()
	{
		return (this.parent == null);
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isLeaf()
	{
		return (this.children == null);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getPhrase()
	{
		Stack <TrieStringNode> stack = new Stack <TrieStringNode> ();
		TrieStringNode parentNode = this;
		
		while(!parentNode.isRoot())
		{
			stack.push(parentNode);
			parentNode = parentNode.getParent();
		}
		
		StringBuilder builder = new StringBuilder(stack.size());
		
		while(!stack.isEmpty())
		{
			TrieStringNode node = stack.pop();
			builder.append(node.getWord());
		}
		
		return builder.toString();
	}
	
	/**
	 * 
	 */
	@Override
	public int hashCode()
	{
		return getWord().hashCode();
	}
	
	/**
	 * 
	 */
	@Override
	public boolean equals(Object src)
	{
		if(src instanceof TrieCharNode)
		{
			TrieCharNode node = (TrieCharNode)src;
			return (getWord().equals(node.getWord()));
		}
		
		return false;
	}
	
	/**
	 * 
	 */
	public void clear()
	{
		if(this.children != null)
		{
			for(TrieStringNode node : this.children)
			{
				node.clear();
			}
			
			this.children.clear();
		}
	}

}
