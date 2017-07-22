/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;

/**
 *
 * @author ABC
 */
public class DataType {
    
    String name;
    
    public DataType(String name)
    {
        setName(name);
    }
    
    public void setName(String n)
    {
       /* char c = n.charAt(0);
        if(((c >= 'A') && (c <= 'Z')) ||((c >= 'a') && (c <= 'z')))
        {
            name = n;
        }
        else
        {
            name = null;
        }*/
        if (n.matches("^[a-z A-Z].*$"))
        {
            name = n;
        }
        else
        {  name = null;
        //System.out.println("invalid variable name!");
        
        }
    }
    
    public String getName()
    {
        return name;
    }
    
}
