/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

import static interpreter.Main.readFile;
import java.io.IOException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TestRule;
import static org.junit.Assert.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;

/**
 *
 * @author ABC
 */
public class InterpreterTest {
 
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    
    
    public InterpreterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        System.setOut(new PrintStream(outContent));
    }
    
    @After
    public void tearDown() {
        System.setOut(null);
    }

    
    /* Test for testing the result of the whole application reading files and testing output*/
    @Test
    public void testOutput1 () throws IOException
    {
 
        String s = readFile("code1.txt");
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "10\nff"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
        
    }
   
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    
    /* Test for testing the result of the whole application reading files and testing output*/
    @Test
    public void testOutput2 () throws IOException
    {
 
        exit.expectSystemExitWithStatus(0);
        String s = readFile("code2.txt");
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();           
    }
    
    /*Test for testing any type other than int and bool*/
    @Test
    public void testType()
    {
        exit.expectSystemExitWithStatus(0);
        
        String s = " Let x = \"a string\" ";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
    }
    
    /*Test for testing bool type can not be assigned other than tt or ff*/
    @Test
    public void testBool()
    {
        exit.expectSystemExitWithStatus(0);
        
        String s = " Let x = true ";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
    }
    
    /*Test for integer can not be other than Z*/
    @Test
    public void testInt()
    {
        exit.expectSystemExitWithStatus(0);
        
        String s = " Let x = 2.13 ";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
    }
    
    /*Test for variable name*/
    @Test
    public void testVarName()
    {
        exit.expectSystemExitWithStatus(0);
        
        String s = " Let 1alkdfj";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
    }
    
    /*Test for checking what an expression can  have*/
    @Test
    public void testExpression()
    {
        String s = "Let x = tt;Let y = ff; Let y = - x; Let z = x ^ y;print z;";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "tt"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
    }
    
    /*Test for checking a binary operation*/
    @Test
    public void testBinary()
    {
        String s = "Let x = 2;Let y = 3;Let z = x ^ y;print z;";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "8"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
    }
    
    /*Test for uniary operation*/
    @Test
    public void testUniary()
    {
        String s = "Let x = 2;Let y = not x;print y;";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "-2"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
    }
    
    /*Test for initialize a varible with another variable*/
    @Test
    public void testInitialize()
    {
        String s = "Let x = 2;Let y = x;print y;";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "2"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
    }
    
    /*Test for declaring a variable without any type*/
     /*Test for initialize a varible with another variable*/
    @Test
    public void testDeclare()
    {
        String s = "Let x = 2;print x;Let y = ff;print y;";
        
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.interpret();   
        
        
        String str = outContent.toString(); //string output by the interpreter
        str = str.replaceAll("\\s","");
        
        String result = "2\nff"; //the output should be
        result = result.replaceAll("\\s","");
        
        assertEquals(result, str);
    }
  
}
