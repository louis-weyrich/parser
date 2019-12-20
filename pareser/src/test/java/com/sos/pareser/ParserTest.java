package com.sos.pareser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.sos.parser.DefaultParserListener;
import com.sos.parser.LanguageParser;
import com.sos.parser.ParserListener;
import com.sos.parser.exception.ParserException;
import com.sos.parser.node.Document;
import com.sos.parser.node.NodeContainer;
import com.sos.parser.node.NodeType;
import com.sos.parser.utils.Stack;

public class ParserTest {

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
		ParserListener listener = new DefaultParserListener();
		
		try {
			LanguageParser parser = new LanguageParser("./src/main/resource/json_parser.json", listener);
			NodeContainer node = parser.parse(new FileInputStream(new File("./src/main/resource/initialize.json")));
			
			assertTrue(node.getType() == NodeType.DOCUMENT);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
