package com.sos.parser.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MyMap <K,V> implements Map<K, V> 
{
	public final static int DEFAULT_ARRAY_SIZE = 20; 
	
	private int size = 0, arraySize = DEFAULT_ARRAY_SIZE;
	private Comparator <K> keyComparitor;
	private ListNode [] data;
	

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MyMap() {
		this.arraySize = DEFAULT_ARRAY_SIZE;
		data = new MyMap.ListNode [DEFAULT_ARRAY_SIZE];
		this.keyComparitor = defaultComparator();
	}
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MyMap(int arraySize) {
		this.arraySize = arraySize;
		data = new MyMap.ListNode [arraySize];
		this.keyComparitor = defaultComparator();
	}

	/**
	 * 
	 * @param keyComparitor
	 */
	public MyMap(Comparator <K> keyComparitor) {
		this.keyComparitor = (keyComparitor != null)?keyComparitor:defaultComparator();
	}

	/**
	 * 
	 * @param arraySize
	 * @param keyComparitor
	 */
	public MyMap(int arraySize, Comparator <K> keyComparitor) {
		this(arraySize);
		this.keyComparitor = (keyComparitor != null)?keyComparitor:defaultComparator();
	}
	
	public void setKeyComparator(Comparator <K> comparator)
	{
		this.keyComparitor = comparator;
	}
	
	/**
	 * 
	 * @return
	 */
	protected Comparator<K> defaultComparator()
	{
		return new Comparator <K> ()
		{
			public int compare(K o1, K o2) {
				int value = 0;
				
				if (o1.hashCode() == o2.hashCode() && o1.equals(o2)) {
					value = 0;
				}
				else if(o1.hashCode() > o2.hashCode())
				{
					value = 1;
				}
				else if(o1.hashCode() < o2.hashCode())
				{
					value = -1;
				}
				return value;
			}
		};
	}

	/**
	 * 
	 */
	public int size() 
	{
		return size;
	}

	/**
	 * 
	 */
	public boolean isEmpty() 
	{
		return (size == 0);
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public boolean containsKey(Object key) 
	{
		if(key != null)
		{
			int hash = calculateHash(key);
			
			if(hash >= 0 && hash < arraySize)
			{
				ListNode tempNode = data[hash];
				while(tempNode != null)
				{
					if(this.keyComparitor.compare((K)key, tempNode.getEntry().getKey()) == 0)
					{
						return true;
					}
					else
					{
						tempNode = tempNode.getChild();
					}
				}
			}
		}
		
		return false;
	}

	/**
	 * 
	 */
	public boolean containsValue(Object value) 
	{
		for(ListNode node : data)
		{
			ListNode tempNode = node;
			while(tempNode != null)
			{
				if(tempNode.getEntry().getValue().equals(value))
				{
					return true;
				}
				else
				{
					tempNode = tempNode.getChild();
				}
			}
		}
		return false;
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public V get(Object key) 
	{
		if(key != null)
		{
			int hash = calculateHash(key);
			
			if(hash >= 0 && hash < arraySize)
			{
				ListNode node = data[hash];
				while(node != null)
				{
					if(keyComparitor.compare((K)key, node.entry.getKey()) == 0)
					{
						return node.getEntry().getValue();
					}
					else
					{
						node = node.getChild();
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	public V put(K key, V value) 
	{
		if(key != null)
		{
			int hash = this.calculateHash(key);
			ListNode tempNode = data[hash];
			
			if(tempNode != null && !this.containsKey(key))
			{
				ListNode newNode = new ListNode(createEntry(key,value), null, tempNode);
				data[hash] = newNode;
				size++;
				return value;
			}
			else if(hash >= 0 && hash < arraySize && tempNode == null && !this.containsKey(key))
			{
				ListNode node = new ListNode(createEntry(key, value), null, null);
				data[hash] = node;
				size++;
			}
			else
			{
				if(hash >= 0 && hash < arraySize && tempNode != null)
				{
					while(tempNode != null)
					{
						if(keyComparitor.compare((K)key, tempNode.entry.getKey()) == 0)
						{
							V returnValue =  tempNode.getEntry().getValue();
							tempNode.getEntry().setValue(value);
							return returnValue;
						}
						else
						{
							tempNode = tempNode.getChild();
						}
					}
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	public V remove(Object key) 
	{
		int hash = this.calculateHash(key);
		
		if(hash >= 0 && hash < arraySize)
		{
			ListNode tempNode = data[hash];
			
			while(tempNode != null)
			{
				if(tempNode.getEntry().getKey().equals(key))
				{
					V value = tempNode.getEntry().getValue();
					ListNode parent = tempNode.getParent();
					ListNode child = tempNode.getChild();
					
					tempNode.setChild(null);
					tempNode.setParent(null);
					tempNode.setEntry(null);
					
					if(parent != null)
					{
						parent.setChild(child);
					}
					else
					{
						data[hash] = child;
					}
					
					if(child != null)
					{
						child.setParent(parent);
					}
					else
					{
						if(parent != null)
						{
							parent.setChild(null);
						}
					}
					
					this.size--;
					return value;
				}
			}
		}
		
		return null;
	}

	/**
	 * 
	 */
	public void putAll(Map<? extends K, ? extends V> map) 
	{
		Iterator <? extends Entry> iterator = map.entrySet().iterator();
		while(iterator.hasNext())
		{
			Entry<K,V> entry = iterator.next();
			put(entry.getKey(),entry.getValue());
		}
	}

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void clear() 
	{
		this.size = 0;
		this.data = new MyMap.ListNode [DEFAULT_ARRAY_SIZE];
	}

	/**
	 * 
	 */
	public Set<K> keySet() 
	{
		Set <K> keys = new HashSet <K> ();
		for(ListNode node : data)
		{
			if(node != null)
			{
				ListNode tempNode = node;
				while(tempNode != null)
				{
					keys.add(tempNode.getEntry().getKey());
					tempNode = tempNode.getChild();
				}
			}
		}
		return keys;
	}

	/**
	 * 
	 */
	public Collection<V> values() {
		List <V> list = new ArrayList <V> (size);
		
		for(ListNode node : data)
		{
			ListNode tempNode = node;
			while(tempNode != null)
			{
				list.add(tempNode.getEntry().getValue());
				tempNode = tempNode.getChild();
			}
		}
		
		return list;
	}

	/**
	 * 
	 */
	public Set<Entry<K, V>> entrySet() 
	{
		Set <Entry<K,V>> set = new HashSet <Entry<K,V>> ();
		
		for(ListNode node : data)
		{
			ListNode tempNode = node;
			while(tempNode != null)
			{
				set.add(tempNode.getEntry());
				tempNode = tempNode.getChild();
			}
		}
		
		return set;
	}
	
	/**
	 * 
	 * @param src
	 * @return
	 */
	protected int calculateHash(Object src)
	{
		return Math.abs(src.hashCode()) % this.arraySize;
	}
	
	/**
	 * 
	 * @param k
	 * @param v
	 * @return
	 */
	private Entry <K,V> createEntry(final K k, final V v)
	{
		return new Entry <K,V> ()
		{
			private K key = k;
			private V value = v;
			
			public K getKey() {
				return this.key;
			}

			public V getValue() {
				return this.value;
			}

			public V setValue(V value) {
				V tempValue = this.value;
				this.value = value;
				return tempValue;
			}
			
			@Override
			public boolean equals(Object src)
			{
				return (key != null)?key.equals(src):false;
			}
			
			@Override
			public int hashCode() {
				return key.hashCode();
			}
			
		};
	}
	
	/**
	 * 
	 * @author louisweyrich
	 *
	 */
	protected class ListNode 
	{
		private Entry <K,V> entry;
		private ListNode child;
		private ListNode parent;
		
		public ListNode(Entry<K,V> entry, ListNode parent, ListNode child)
		{
			this.entry = entry;
			this.child = child;
			this.parent = parent;
		}
		
		public ListNode getChild() {
			return child;
		}

		public void setChild(ListNode child) {
			this.child = child;
		}

		public ListNode getParent() {
			return parent;
		}

		public void setParent(ListNode parent) {
			this.parent = parent;
		}

		public void setEntry(Entry<K, V> entry) {
			this.entry = entry;
		}

		public Entry<K,V> getEntry()
		{
			return this.entry;
		}
	}

}
