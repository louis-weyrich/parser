package com.sos.parser.utils.tree;

import java.util.ArrayList;
import java.util.List;


public class BinaryTree  <ID, T>{
	
	private Node <ID, T> root = null;
	private int nodeCount = 0;
	private List <Node <ID, T>> insertOrder;
	private NodeBuilder <ID, T> helper;

	public BinaryTree() 
	{
		this.insertOrder = new ArrayList <Node <ID, T>> ();
		this.helper = createDefaultHelper();
	}
	
	public BinaryTree(NodeBuilder <ID, T> helper) 
	{
		insertOrder = new ArrayList <Node <ID, T>> ();
		this.helper = helper;
	}
	
	private NodeBuilder <ID, T> createDefaultHelper()
	{
		return new NodeBuilder  <ID, T> ()
		{
			public Node <ID, T> createNewNode(ID theID, T theValue)
			{
				return new BinaryNode <ID, T> (theID, theValue);
			}
		};
	}

	public Node <ID, T> getRoot()
	{
		return root;
	}
	
	public int size()
	{
		return insertOrder.size();
	}
	
	public List <Node <ID, T>> getInsertOrder()
	{
		return insertOrder;
	}
	
	public boolean addNode(ID id, T value)
	{
		Node <ID, T> node = (Node<ID, T>)helper.createNewNode(id, value);
		
		if(insertOrder.contains(node))
		{
			return false;
		}
		
		insertOrder.add(node);
		
		if(root == null)
		{
			root = node;
		}
		else
		{
			Node <ID, T> focusNode = root;
			Node <ID, T> parentNode = null;
			
			while(true)
			{
				parentNode = focusNode;
				
				if(id.hashCode() < focusNode.getID().hashCode())
				{
					focusNode = focusNode.getLeftChild();
					
					if(focusNode == null)
					{
						parentNode.setLeftChild(node);
						break;
					}
				}
				else
				{
					focusNode = focusNode.getRightChild();
					
					if(focusNode == null)
					{
						parentNode.setRightChild(node);
						break;
					}
				}
			}
			
		}
		
		nodeCount++;
		return true;
	}
	
	public int getNodeCount()
	{
		return nodeCount;
	}
	
	public List <Node <ID, T>> inOrderTransversal()
	{
		return inOrderTransversal(getRoot(), new ArrayList <Node <ID, T>> ());
	}
	
	public List <Node <ID, T>> inOrderTransversal(Node <ID, T> node, List <Node <ID, T>> list)
	{
		if(node != null)
		{
			list = inOrderTransversal(node.getLeftChild(), list);
			list.add(node);
			list = inOrderTransversal(node.getRightChild(), list);
		}
		
		return list;
	}
	
	public List <Node <ID, T>> preOrderTransversal()
	{
		return preOrderTransversal(getRoot(), new ArrayList <Node <ID, T>> ());
	}
	
	public List <Node <ID, T>> preOrderTransversal(Node <ID, T> node, List <Node <ID, T>> list)
	{
		if(node != null)
		{
			list.add(node);
			
			if(node.hasLeftChild())
				list = preOrderTransversal(node.getLeftChild(), list);
			
			if(node.hasRightChild())
				list = preOrderTransversal(node.getRightChild(), list);
		}
		
		return list;
	}
	
	public List <Node <ID, T>> postOrderTransversal()
	{
		return postOrderTransversal(getRoot(), new ArrayList <Node <ID, T>> ());
	}

	public List <Node <ID, T>> postOrderTransversal(Node <ID, T> node, List <Node <ID, T>> list)
	{
		if(node != null)
		{
			list = postOrderTransversal(node.getLeftChild(), list);
			list = postOrderTransversal(node.getRightChild(), list);
			list.add(node);
		}
		
		return list;
	}
	
	public Node <ID, T> findCommonAncestor(Node <ID, T> node1, Node <ID, T> node2)
	{
		Node <ID, T> commonNode = null;
		List <Node <ID, T>> list1 = new ArrayList <Node <ID, T>> ();
		List <Node <ID, T>> list2 = new ArrayList <Node <ID, T>> ();
		
		while(node1 != null)
		{
			list1.add(node1);
			node1 = node1.getParent();
		}
		
		while(node2 != null)
		{
			list2.add(node2);
			node2 = node2.getParent();
		}
		
		for(Node <ID, T> n1 : list1)
		{
			for(Node <ID, T> n2 : list2)
			{
				if(n1.equals(n2))
				{
					return n2;
				}
			}
		}
		return commonNode;
	}
	
	public Node <ID, T> findCommonAncestor(ID key1, ID key2)
	{
		Node <ID, T> node1 = findNode(key1);
		Node <ID, T> node2 = findNode(key2);
		
		return findCommonAncestor(node1, node2);
	}
	
	public Node <ID, T> findNode(ID id)
	{
		Node <ID, T> focusNode = root;
		
		if(focusNode != null)
		{
			while(true)
			{
				if(focusNode.getID().equals(id))
				{
					return focusNode;
				}
				else if(focusNode.getID().hashCode() > id.hashCode())
				{
					focusNode = focusNode.getLeftChild();
				}
				else
				{
					focusNode = focusNode.getRightChild();
				}
				
				if(focusNode == null) 
				{
					return null;
				}
			}
		}
		
		return null;
	}
	
	public boolean removeNode(ID id)
	{
		Node <ID, T> node = findNode(id);
		boolean nodeRemoved = false;
		
		if(this.insertOrder.contains(node))
		{
			this.insertOrder.remove(node);
		}
		
		if(node != null)
		{
			if(node.isLeaf())
			{
				if(node.isRoot())
				{
					root = null;
				}
				else
				{					
					node.detach();
				}
				
				nodeRemoved = true;
			}
			else if(node.hasBothChildren())
			{
				Node <ID, T> leftChild 		= node.getLeftChild();
				Node <ID, T> rightChild 	= node.getRightChild();
				Node <ID, T> rightsLeftMost = (rightChild.hasLeftChild())?rightChild.getLeftMostChild():rightChild;
				Node <ID, T> parent 		= node.getParent();
				
				if(node.isRoot())
				{
					node.detach();
					rightsLeftMost.getParent().setLeftChild(null);
					root = rightsLeftMost;
					rightsLeftMost.setParent(null);
					if(!rightsLeftMost.equals(rightChild))
					{
						rightsLeftMost.setRightChild(rightChild);
					}
					rightsLeftMost.setLeftChild(leftChild);
				}
				else
				{
					if(node.isLeftChild())
					{
						node.detach();
						rightsLeftMost.getParent().setLeftChild(null);
						parent.setLeftChild(rightsLeftMost);
						if(!rightsLeftMost.equals(rightChild))
						{
							rightsLeftMost.setRightChild(rightChild);
						}
						rightsLeftMost.setLeftChild(leftChild);
					}
					else
					{
						node.detach();
						rightsLeftMost.getParent().setLeftChild(null);
						parent.setRightChild(rightsLeftMost);
						if(!rightsLeftMost.equals(rightChild))
						{
							rightsLeftMost.setRightChild(rightChild);
						}
						rightsLeftMost.setLeftChild(leftChild);
					}
				}
				
				nodeRemoved = true;
			}
			else if(node.hasLeftChild())
			{
				Node <ID, T> leftChild = node.getLeftChild();
				
				if(node.isRoot())
				{
					node.detach();
					root = leftChild;
					root.setParent(null);
				}
				else
				{
					Node <ID, T> parent = node.getParent();
					
					if(node.isLeftChild())
					{
						node.detach();
						parent.setLeftChild(leftChild);
					}
					else
					{
						node.detach();
						parent.setRightChild(leftChild);
					}
				}
				
				nodeRemoved = true;
				
			}
			else
			{
				Node <ID, T> rightChild = node.getRightChild();
				
				if(node.isRoot())
				{
					node.detach();
					root = rightChild;
					root.setParent(null);
				}
				else
				{
					Node <ID, T> parent = node.getParent();
					
					if(node.isLeftChild())
					{
						node.detach();
						parent.setLeftChild(rightChild);
					}
					else
					{
						node.detach();
						parent.setRightChild(rightChild);
					}
				}
				
				nodeRemoved = true;
			}
		}
		
		return nodeRemoved;
	}
	
}
