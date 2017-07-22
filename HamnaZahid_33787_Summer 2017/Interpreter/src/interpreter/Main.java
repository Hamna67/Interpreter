/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author ABC
 */
public class Main {
 
     public static void main(String[] args) throws IOException{
        // TODO code application logic here
       
                
        String s = readFile("code1.txt");
        Interpreter i = new Interpreter();
        i.setInput(s);
        i.parseInput();
        i.display();
        i.interpret();
        
      
       /* String s2 = readFile("code5.txt");
        Interpreter i2 = new Interpreter();
        i2.setInput(s2);
        i2.parseInput();
        i2.display();
        i2.interpret();*/


        
    }
     
     //function for reading from a file and storing text in a string, returning the string
     public static String readFile (String fileName)throws IOException
     {
        StringBuilder str = new StringBuilder();
        BufferedReader file;  
         
        file = new BufferedReader(new FileReader(fileName));
        
        String line = file.readLine();
        try  
        {
            while (line != null) {
                str.append(line);
                str.append("\n");
                line = file.readLine();
            }

         }
         
         finally
         {
             file.close();
             
         }
        
        return str.toString();
     }
}
