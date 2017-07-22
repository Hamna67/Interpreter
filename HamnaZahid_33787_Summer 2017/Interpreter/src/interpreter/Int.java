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
//function for storing integer datatype
public class Int extends DataType{
    
    long value;
    
    public Int(String n)
    {
        super(n);
    }
    public void setInt(long v)
    {
        value = v;
    }
    
    public long getInt()
    {
        return value;
    }
}
