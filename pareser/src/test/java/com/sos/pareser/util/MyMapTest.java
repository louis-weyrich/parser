/**
 * 
 */
package com.sos.pareser.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Test;

import com.sos.parser.utils.MyMap;

/**
 * @author louisweyrich
 *
 */
public class MyMapTest {
	

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#size()}.
	 */
	@Test
	public void testSize() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		int size = map.size();
		map.clear();
		map = null;
		assertTrue(size == 7);
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		map.clear();
		assertTrue(map.isEmpty() == true);
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#containsKey(java.lang.Object)}.
	 */
	@Test
	public void testContainsKey() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		assertTrue(map.containsKey(new Integer(1)));
		assertTrue(map.containsKey(new Integer(642)));
		assertTrue(map.containsKey(new Integer(1000)));
		assertTrue(map.containsKey(new Integer(2000)));
		assertTrue(map.containsKey(new Integer(100)));
		assertTrue(map.containsKey(new Integer(110)));
		assertFalse(map.containsKey(new Integer(101)));
	}
	

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#containsValue(java.lang.Object)}.
	 */
	@Test
	public void testContainsValue() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		assertTrue(map.containsValue("One"));
		assertTrue(map.containsValue("One Thousand"));
		assertTrue(map.containsValue("Two Thousand"));
		assertTrue(map.containsValue("Two"));
		assertTrue(map.containsValue("One hundred"));
		assertTrue(map.containsValue("One Hundred Ten"));
		assertTrue(map.containsValue("Six Hundred Fourty Two"));
		assertFalse(map.containsValue("Six Hundred Fourty"));
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#get(java.lang.Object)}.
	 */
	@Test
	public void testGet() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(523), "Five hundred  Tweenty Three");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		String value = map.get(new Integer(2000));
		assertTrue("Two Thousand".equals(value));
		
		value = map.get(new Integer(523));
		assertTrue("Five hundred  Tweenty Three".equals(value));
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#remove(java.lang.Object)}.
	 */
	@Test
	public void testRemove() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		String value = map.remove(new Integer(642));
		assertTrue("Six Hundred Fourty Two".equals(value));
		int size = map.size();
		assertTrue(size == 6);
		assertFalse(map.containsKey(new Integer(642)));
		assertNull(map.get(new Integer(642)));
		
		value = map.remove(new Integer(110));
		assertTrue("One Hundred Ten".equals(value));
		size = map.size();
		assertTrue(size == 5);
		assertFalse(map.containsKey(new Integer(110)));
		assertNull(map.get(new Integer(110)));
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#putAll(java.util.Map)}.
	 */
	@Test
	public void testPutAll() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "1");
		map.put(new Integer(2), "Two");
		
		assertTrue(map.size() == 2);
		
		MyMap <Integer, String> newmap = new MyMap <Integer, String> ();
		newmap.put(new Integer(1), "One");
		newmap.put(new Integer(2), "2");
		newmap.put(new Integer(1000), "One Thousand");
		newmap.put(new Integer(2000), "Two Thousand");
		newmap.put(new Integer(100), "One hundred");
		newmap.put(new Integer(110), "One Hundred Ten");
		newmap.put(new Integer(642), "Six Hundred Fourty Two");		map.put(new Integer(1), "One");
		
		map.putAll(newmap);
		
		assertTrue(map.size() == 7);
		assertTrue(map.containsKey(new Integer(1000)));
		
		String value = map.get(new Integer(1));
		assertFalse("1".equals(value));
		assertTrue("One".equals(value));
		
		value = map.get(new Integer(2));
		assertFalse("Two".equals(value));
		assertTrue("2".equals(value));
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#keySet()}.
	 */
	@Test
	public void testKeySet() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		Set <Integer> set = map.keySet();
		Iterator <Integer> iterator = set.iterator();
		while(iterator.hasNext())
		{
			Integer value = iterator.next();
			switch(value)
			{
				case 1:{break;}
				case 2:{break;}
				case 100:{break;}
				case 110:{break;}
				case 200:{break;}
				case 642:{break;}
				case 1000:{break;}
				case 2000:{break;}
				default:{fail("KeySet value is bad");}
			}
		}
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#values()}.
	 */
	@Test
	public void testValues() 
	{
		List <String> list = new ArrayList <String> ();
		list.add("One");
		list.add("Two");
		list.add("One Thousand");
		list.add("Two Thousand");
		list.add("One hundred");
		list.add("One Hundred Ten");
		list.add("Six Hundred Fourty Two");
		
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), list.get(0));
		map.put(new Integer(2), list.get(1));
		map.put(new Integer(1000), list.get(2));
		map.put(new Integer(2000), list.get(3));
		map.put(new Integer(100), list.get(4));
		map.put(new Integer(110), list.get(5));
		map.put(new Integer(642), list.get(6));
		
		int count = 0;
		Collection <String> values = map.values();
		Iterator <String> iterator = values.iterator();
		while(iterator.hasNext())
		{
			String value = iterator.next();
			if(list.contains(value))
			{
				count++;
			}
		}
		
		assertTrue(count == 7);
	}

	/**
	 * Test method for {@link com.sos.parser.utils.MyMap#entrySet()}.
	 */
	@Test
	public void testEntrySet() {
		MyMap <Integer, String> map = new MyMap <Integer, String> ();
		map.put(new Integer(1), "One");
		map.put(new Integer(2), "Two");
		map.put(new Integer(1000), "One Thousand");
		map.put(new Integer(2000), "Two Thousand");
		map.put(new Integer(100), "One hundred");
		map.put(new Integer(110), "One Hundred Ten");
		map.put(new Integer(642), "Six Hundred Fourty Two");
		
		Set <Entry<Integer, String>> set = map.entrySet();
		Iterator <Entry<Integer, String>> iterator = set.iterator();
		
		int count = 0;
		
		while(iterator.hasNext())
		{
			Entry<Integer, String> entry = iterator.next();
			Integer key = entry.getKey();
			
			switch(key)
			{
				case 1:{count++;break;}
				case 2:{count++;break;}
				case 100:{count++;break;}
				case 110:{count++;break;}
				case 200:{count++;break;}
				case 642:{count++;break;}
				case 1000:{count++;break;}
				case 2000:{count++;break;}
				default:{count--;fail("KeySet value is bad");}
			}
		}
		
		assertTrue(count == 7);
	}

}
