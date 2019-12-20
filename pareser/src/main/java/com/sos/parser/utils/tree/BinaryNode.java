package com.sos.parser.utils.tree;

public class BinaryNode <ID, T> implements Node<ID, T> {
	
	private T value;
	private NodeID <ID> nodeId;
	
	private Node <ID, T> parentNode;
	private Node <ID, T> leftChild;
	private Node <ID, T> rightChild;

	public BinaryNode(ID id, T value) throws IllegalArgumentException
	{
		if(id == null)
		{
			throw new IllegalArgumentException("ID can not be null.");
		}
		
		if(value == null)
		{
			throw new IllegalArgumentException("Parameter Value can not be null for id("+id.toString()+").");
		}
		
		this.value = value;
		this.nodeId = createNodeID(id);
	}
	
	private NodeID <ID> createNodeID(final ID theID)
	{
		return new NodeID <ID> ()
		{
			private ID id = theID;
			
			public ID getID() {
				return this.id;
			}

			public boolean isID(NodeID<ID> id) 
			{
				if(this.id != null)
				{
					return this.id.equals(id.getID());
				}
				
				return false;
			}

			public int compareID(NodeID<ID> id) {
				ID thatId = id.getID();
				ID thisId = this.getID();
				
				if(thisId.equals(thatId))
				{
					return 0;
				}
				else if(thisId.hashCode() < thatId.hashCode())
				{
					return -1;
				}
				else
				{
					return 1;
				}
			}
	
			@Override
			public boolean equals(Object src)
			{
				if(src != null && src instanceof NodeID)
				{
					@SuppressWarnings("unchecked")
					NodeID <ID> nodeId = (NodeID <ID>)src;
					return isID(nodeId);
				}
				return false;
			}
			
			@Override
			public int hashCode()
			{
				return id.hashCode();
			}
			
			@Override
			public String toString()
			{
				return this.id.toString();
			}
		};
	}

	public ID getID() 
	{
		if(this.nodeId != null)
		{
			return this.nodeId.getID();
		}
		else
		{
			return null;
		}
	}

	public boolean isID(NodeID<ID> id) 
	{
		if(this.nodeId != null)
		{
			return this.nodeId.isID(id);
		}
		
		return false;
	}

	public int compareID(NodeID<ID> id) 
	{
		if(this.nodeId != null)
		{
			return this.nodeId.compareID(id);
		}
		return 0;
	}

	public Node<ID, T> getParent() 
	{
		return this.parentNode;
	}

	public void setParent(Node<ID, T> parent) 
	{
		this.parentNode = parent;
	}

	public Node<ID, T> getLeftChild() 
	{
		return this.leftChild;
	}

	public Node<ID, T> getLeftMostChild() 
	{
		Node <ID, T> lefttMost = (hasLeftChild())?getLeftChild(): this;
		
		while(lefttMost.getLeftChild() != null)
		{
			lefttMost = lefttMost.getLeftChild();
		}
		
		return lefttMost;
	}

	public void setLeftChild(Node<ID, T> child) 
	{
		this.leftChild = child;
		
		if(child != null)
		{
			child.setParent(this);
		}
	}

	public boolean isLeftChild() {
		if(this.parentNode != null)
		{
			return equals(parentNode.getLeftChild());
		}
		return false;
	}

	public Node<ID, T> getRightChild() {
		return this.rightChild;
	}
	
	public Node<ID, T> getRightMostChild() {
		Node <ID, T> rightMost = getRightChild();
		
		while(rightMost.getRightChild() != null)
		{
			rightMost = rightMost.getRightChild();
		}
		
		return rightMost;
	}

	public void setRightChild(Node<ID, T> child) 
	{
		this.rightChild = child;
		
		if(child != null)
		{
			child.setParent(this);
		}
	}

	public boolean isRightChild() {
		if(this.parentNode != null)
		{
			return equals(parentNode.getRightChild());
		}
		return false;
	}

	public boolean isLeaf() {
		return (this.leftChild == null && this.rightChild == null);
	}
	
	public boolean isRoot() {
		return (this.parentNode == null);
	}

	public boolean hasRightChild() 
	{
		return (this.rightChild != null);
	}

	public boolean hasLeftChild() 
	{
		return (this.leftChild != null);
	}

	public boolean hasBothChildren() 
	{
		return (this.leftChild != null && this.rightChild != null);
	}

	public T getValue() 
	{
		return this.value;
	}

	public String nodeInfo() 
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append(this.nodeId);
		builder.append(" : ");
		
		if(this.parentNode != null)
		{
			if(isLeftChild()) builder.append("L ");
			else builder.append("R ");
			
			builder.append(getParent().getID());
		}
		else
		{
			builder.append("Root Node");
		}
		
		if(value != null)
		{
			builder.append(" value[ ");
			builder.append(value);
			builder.append(" ]");
		}
		
		return builder.toString();
	}

	public void detach() 
	{
		this.leftChild = null;
		this.rightChild = null;
		this.value = null;
		
		
		if(isLeftChild())
		{
			if(this.getParent() != null)
			{
				this.getParent().setLeftChild(null);
			}			
		}
		else
		{
			if(this.getParent() != null) 
			{
				this.getParent().setRightChild(null);
			}
		}
		
		this.parentNode = null;
		this.nodeId = null;
	}

	public NodeID<ID> getNodeID() 
	{
		return nodeId;
	}
	
	@Override
	public boolean equals(Object src)
	{
		if(src instanceof BinaryNode)
		{
			@SuppressWarnings("unchecked")
			BinaryNode <ID, T> node = (BinaryNode<ID, T>)src;
			return getID().equals(node.getID());
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return getID().hashCode();
	}
	
	@Override
	public String toString()
	{
		return nodeInfo();
	}

}
