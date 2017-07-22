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
public class Bool extends DataType{
    boolean value;
    
    public Bool(String n)
    {
        super(n);
    }
    public void setBool(String b)
    {
       if (b.matches("tt"))
       {value = true;}
       else if(b.matches("ff"))
       {value = false;}
    }
    
    public String getBool()
    {  
        if (value == true)
            return "tt";
        if (value == false)
            return "ff";
        
        return "-1";
    }
}
