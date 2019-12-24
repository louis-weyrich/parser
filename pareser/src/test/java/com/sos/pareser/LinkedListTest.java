package com.sos.pareser;

import java.util.Iterator;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sos.parser.utils.linkedlist.LinkedList;

public class LinkedListTest  {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		LinkedList <Integer> list = new LinkedList <Integer> ();
		for(int index = 0; index < 10; index++)
		{
			list.pushTop(new Integer(index));
		}
		
		for(int index = 20; index > 10; index--)
		{
			list.pushEnd((new Integer(index)));
		}
		
		Iterator <Integer> iterator = list.iterator();
		while(iterator.hasNext())
		{
			System.out.println(iterator.next());
		}
	}

}
