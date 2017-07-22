/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interpreter;
import java.util.*;


//class reponsible for interpreting the input
public class Interpreter {

    int count; //variable for keeping the count of strings in input
    int line; //varible for counting the number of lines
    String input;  //variable for storing the entire input
    String[] commands; //array for storing each command
    String[] iden; //array for storing identifiers in a single command
    HashMap<String, Runnable> map;//map for storing identifiers and the related functins
    HashMap variables; //map for storing the variables in the program
   
    
//constructor
    Interpreter()
    {
        count = 0;
        line = 1;
        input = new String();
        map = new HashMap<String,Runnable>();
        variables = new HashMap();
        //putting values in map
        map.put("Let",new Runnable()
        {
            public void run() {
               createDataType();
                
            }
        });
        
        map.put("print",new Runnable()
        {
            public void run()
            {
                print();
            }
        });
        
        map.put("=",new Runnable()
        {
            public void run()
            {
                assign();
            }
        });
        
        map.put("+", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
        
         map.put("*", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
        
         map.put("/", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
         
         map.put("==", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
         
         map.put(">", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
         
         map.put("<", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
         
         map.put("><", new Runnable()
        {
            public void run()
            {
                binary();
            }
        });
        
        map.put("and", new Runnable()
        {
            public void run()
            {
                boolOperation();
            }
        });
        
        map.put("or", new Runnable()
        {
            public void run()
            {
                boolOperation();
            }
        });
        
        map.put("^", new Runnable()
        {
            public void run()
            {
                callFunction();
            }
        });
        
        map.put("-", new Runnable()
        {
            public void run()
            {
                uniary();
            }
        });
        
        map.put("not", new Runnable()
        {
            public void run()
            {
                uniary();
            }
        });
    }
    
   
    //assignment funtion
    public void assign()
    {
        if( count == 0) //if there is not a first operator
        {
            System.out.println("Invalid assignment expression on line :" + line);
            System.exit(0);
            return ;
        }
        
        String op1 = iden[count - 1];
 
        if(count + 1== (iden.length - 1)) //if there is only one variable after =
        {
            String op2 = iden[count + 1];
            
            if(variables.get(op2) != null) //if the second operand is another vairable
            {
                String varClass = variables.get(op2).getClass().getName();
                
                if(varClass.matches("interpreter.Int"))
                { 
                    variables.put(op1,((Int)variables.get(op2)));
                }

                else if(varClass.matches("interpreter.Bool"))
                {
                    Bool temp = new Bool(op1);
                    temp.setBool(((Bool)variables.get(op2)).getBool());
                    variables.put(op1,temp);
                }
            }
            
            else if(isInteger(op2)) //checking if value assigned is an integer
            {
                Int i = new Int(op1); 
                i.setInt(Integer.parseInt(op2));
              
                variables.put(op1,i);
             
            }
            
            else if(op2.matches("ff") || op2.matches("tt"))
            {
                Bool b = new Bool(op1);
                b.setBool(op2);
                variables.put(op1,b);
            }
            
            else
            {
                System.out.println("Incompatible assignment operands on line: " + line);
                System.exit(0);
            }
        }
        
        else if(count + 2 == (iden.length - 1))// if there are at most 2 more char after =
        {
            if(map.containsKey(iden[count + 1]))
            {
                count++;
                map.get(iden[count]).run(); //running the uniary operator
            }
            else
            { 
                System.out.println("Invalid operation used on line: " + line);
                System.exit(0);
            
            }
        }
        
        else if(count + 3 == (iden.length - 1))//if there are at most 3 more characters after =
        {
            if(map.containsKey(iden[count + 2]))
                map.get(iden[count + 2]).run(); //running the binary operator
            else
            {
                System.out.println("Invalid binary operator used on line: " + line);
                System.exit(0);
            }
        }
        
        else
        {
            System.out.println("Invalid assignment expression on line: " + line);
            System.exit(0);
        }
        
        count = count + 3; //moving count to the end of expression
    }
    
    //function for checking boolean value
    public boolean checkbool(String b)
    {
        if(b.matches("tt"))
            return true;
        
        return false;
    }
    
    //function for getting string from booean
    public String getBoolString(boolean b)
    {
        if(b == true)
        {
            return "tt";
        }
        
        return "ff";
            
    }
    
    //function to check if a string is integer  or not
    public static boolean isInteger(String str) 
    {
        if (str == null) 
        {
            return false;
        }
        
        int l = str.length();
        if (l == 0) 
            return false;

        int i = 0;
        if (str.charAt(0) == '-') 
        {
            if (l == 1) 
            {
                return false;
            }
            i = 1;
        }

        for (; i < l; i++) 
        {
            char c = str.charAt(i);
            if (c < '0' || c > '9') 
            {
                return false;
            }
        }
        return true;
}
    
    //function for determining wether to call boolOperation or to call bianry()
    public void callFunction()
    {
        String op1 = iden[count + 1];
        String op2 = iden[count + 3];

        String class1 = new String();
        String class2 = new String();

        if (variables.containsKey(op1))
        {class1 = variables.get(op1).getClass().getName();}

        if (variables.containsKey(op2))
        {class2 = variables.get(op2).getClass().getName();}


        if(class1.matches("interpreter.Int") || class2.matches("interpreter.Int"))
        {
           binary();
        }


        //checking if both are integers
        else if(op1.matches("\\d+") && op2.matches("\\d+"))
        {
           binary();
        }

        else if(class1.matches("interpreter.Bool") && class2.matches("interpreter.Bool"))
        {
            boolOperation();
        }
            
        
        else
        {
            System.out.println("Invalid operands for ^ function on line: " + line);
            System.exit(0);
        }
    }
    
    //function for uniary operator
    public void uniary()
    {
      
        if(iden[count].matches("\\-") || iden[count].matches("not"))//checking the uniary operator
        {
            String operand1 = iden[count + 1];
            if(variables.containsKey(operand1))//if the operand already exists
            {
            
                //if there is an assignment operator before it
                if(count > 0 && iden[count-1].matches("="))
                {
                    if((count - 2) >= 0) //getting the operand which is being assigned a value
                    {
                        //System.out.println("Inside uniary" + iden[count - 2]);
                        String operand2 = iden[count - 2];
                        if(variables.containsKey(operand2))
                        {
                            //check if the operand2 is int or bool and assign value
                            if(variables.get(operand1).getClass().getName().matches("interpreter.Bool"))
                            {
                                
                                Bool temp = new Bool(operand1);                   
                                Bool op1 = (Bool)(variables.get(operand1));
                                temp.setBool(op1.getBool());

                                if(temp.getBool().matches("tt"))
                                {
                                  temp.setBool("ff");
                                }
                                else if(temp.getBool().matches("ff"))
                                {
                                    temp.setBool("tt");
                                }
                                Bool op2 = new Bool(operand2);
                                op2.setBool(temp.getBool());
                                variables.put(operand2,op2);
                            }
                            
                            else if(variables.get(operand1).getClass().getName().matches("interpreter.Int"))
                            {
                                Int tempInt = new Int(operand1);
                                Int op1 = (Int)(variables.get(operand1));
                                tempInt.setInt(op1.getInt());
                                
                                long temp = tempInt.getInt();
                                temp *= -1;
                                
                                tempInt.setInt(temp);
                                Int op2 = new Int(operand2);
                              
                                op2.setInt(tempInt.getInt());
                                variables.put(operand2,op2);
                            }
                            
                            else
                            {
                                System.out.println("Type of variable is invalid on line: " + line);
                                System.exit(0);
                            }
                        }
                        else
                        {
                            System.out.println("No such variable exists: " + operand2 + " on line: " + line);
                            System.exit(0);
                        }
                    }
                    
                    else
                    {
                        System.out.println("Invalid assignment operation on line: " + line);
                        System.exit(0);
                    }
                }
                
                
                
            }
            else
            {
                System.out.println("Invaild operand on line: " + line);
                System.exit(0);
            }
        }
    }
    
    //function for two bool operands
    public void boolOperation()
    {
        boolean operand1 = false;
        boolean operand2 = false;
        
        boolean boolResult = false;
        
        String operator = iden[count + 2];
        
        String op1 = iden[count + 1];
        String op2 = iden[count + 3];
        String op3 = iden[count - 1];

        //checking if the variable beign assigned is a datatype
        if(variables.containsKey(op3)!= false)
        {
            //checking if either is DataType
            if(variables.containsKey(op1)!= false && variables.containsKey(op2)!= false)
            {
                String class1 = variables.get(op1).getClass().getName();             
                String class2 = variables.get(op2).getClass().getName();  
                String class3 = variables.get(op3).getClass().getName();

                if(class1.matches("interpreter.Bool") && class2.matches("interpreter.Bool"))
                {
                    operand1 = checkbool(((Bool)variables.get(op1)).getBool()) ;
                    operand2 = checkbool(((Bool)variables.get(op2)).getBool()) ;         
                }
            }
            
            else
            {
                System.out.println("Invalid operation on line : " + line);
                System.exit(0);
            }
          
           if(operator.matches("and"))
          {
              boolResult = (operand1 & operand2);           
          }
           
          else if(operator.matches("or"))
          {
              boolResult = operand1 | operand2;     
          }
          
                     
          else if(operator.matches("\\^"))
          {
              boolResult = operand1 ^ operand2;
              
          }
          
           Bool temp = new Bool(op3); 
           temp.setBool(getBoolString(boolResult));
           variables.put(op3,temp);
        }
        
        else
        {
            System.out.println("Invalid operation on line: " + line);
            System.exit(0);
        }
        
    }
    
    //function for  two int operands
    public void binary()
    {
        long operand1 = 0;
        long operand2 = 0;
        long intResult = 0; //variable for storing int result of two int operands
        
        boolean boolResult = false; //variable for storing bool result of two int operands
        
        boolean integer = false;
        boolean b = false;
        
        if (!(iden[count].matches("=")))
        {
            System.out.println("Invalid binary expression on line: " + line);
            System.exit(0);
        }
        String operator = iden[count + 2];
        
        String op1 = iden[count + 1];
        String op2 = iden[count + 3];
        String op3 = iden[count - 1];

        //checking if the variable beign assigned is a datatype
        if(variables.containsKey(op3)!= false)
        {
            //checking if either is DataType
            if(variables.containsKey(op1)!= false || variables.containsKey(op2)!= false)
            {
                String class1 = new String();
                String class2 = new String();
                
                if (variables.containsKey(op1))
                {class1 = variables.get(op1).getClass().getName();}
                
                if (variables.containsKey(op2))
                {class2 = variables.get(op2).getClass().getName();}
                
                
                String class3 = variables.get(op3).getClass().getName();

                if(class1.matches("interpreter.Int") && class2.matches("interpreter.Int"))
                {
                    operand1 = ((Int)variables.get(op1)).getInt() ;
                    operand2 = ((Int)variables.get(op2)).getInt() ;
                  
                }

                    //checking if first is Int and second is int
               else if(class1.matches("interpreter.Int") && op2.matches("\\d+"))
               {
                  operand1 = ((Int)variables.get(op1)).getInt();
                  operand2 = Integer.parseInt(op2);

               }
            
                //checking if first is int and second is Int
                 else if(isInteger(op1) && class2.matches("interpreter.Int"))
                {
                    operand1 = Integer.parseInt(op1);
                    operand2 = ((Int)variables.get(op2)).getInt();
                }
                
                else
                 {
                    System.out.println("Incompatible operands for binary expression on line: " + line);
                    System.exit(0);
                 }
            }
            //checking if both are integers
            else if(isInteger(op1) && isInteger(op2))
            {
             
                operand1 = Integer.parseInt(op1);
                operand2 = Integer.parseInt(op2);
    
            }
            
          if(operator.matches("\\+"))
          {
              intResult = operand1 + operand2;
              integer = true;
          }
 
          else if(operator.matches("\\*"))
          {
              intResult = operand1 * operand2;
              integer = true;
          }
          
          else if(operator.matches("/"))
          {
              intResult = operand1 - operand2;
              integer = true;
          }
          
          else if(operator.matches("=="))
          {
              boolResult = operand1 == operand2;
              b = true;
          }
          
          else if(operator.matches("><"))
          {
              boolResult = operand1 != operand2;
              b = true;
          }
          
          else if(operator.matches(">"))
          {
              boolResult = operand1 > operand2;
              b = true;
          }
          
          else if(operator.matches("<"))
          {
              boolResult = operand1 < operand2;
              b = true;
          }
          
          else if(operator.matches("\\^"))
          {
              intResult = (int)Math.pow(operand1, operand2);
              integer = true;
              
          }
          
          if(integer == true)
          {
              Int temp = new Int(op3); 
              temp.setInt(intResult);
              variables.put(op3,temp);
          }
          
          if(b == true)
          {
              Bool temp = new Bool(op3);
              if (boolResult)
                temp.setBool("tt");
              else
                temp.setBool("ff");
              
              variables.put(op3,temp);
              
          }
        }
        else
        {
            System.out.println("Variable '" + op3 + "' has not been declared on line: " + line);
            System.exit(0);
        }

    }
  
    //function for print 
    public void print()
    {
        count++;
        String next = iden[count];
        
        if(variables.get(next) != null)
        {
            
            if(variables.get(next).getClass().getName().matches("interpreter.Int"))
            {
                Int temp = (Int)variables.get(next);
                System.out.println(temp.getInt());
            }
            
            else if(variables.get(next).getClass().getName().matches("interpreter.Bool"))
            {
                Bool temp = (Bool)variables.get(next);
                System.out.println(temp.getBool());
            }
            else
            {
                System.out.println("Invalid Expression to print on line: " + line);
                System.exit(0);
            }
        }
        
        else
        {
            System.out.println("No such variable exists: " + iden[count] + " on line: " + line);
            System.exit(0);
        }
        
        
    }
    
    //function for making a DataType
    public void createDataType()
    {
        count++; //getting to the next string in the command
        String next = iden[count];

        if(count < iden.length && !(next.matches(" "))) //checking if there is a name after Let
        {
            String name = iden[count]; 
            DataType datatype = new DataType(name);
            if(datatype.getName() != null)
            {

            //putting the variable created into the hashmap
            variables.put(name,datatype);

            //System.out.println("Datatype created with name: " + name);
            }
            else
            {
                System.out.println("Invalid name: " + name + " on line: " + line);
                System.exit(0);
            }
           
        }
        else 
        {
            System.out.println("Nothing initialized after Let on line: " + line);
            System.exit(0);
        }
    }
    
    //setter function for setting the input
    public void setInput(String i)
    {
        input = i;
    }
    
    //getter function for getting the input
    public String getInput()
    {
        return input;
    }
    
    //function for parsing the input into commands by ; or by new line and storing in commands array
    public void parseInput()
    {
        input = input.replaceAll(";\n", ";");
        String delims = "[\n;]";
        commands = input.split(delims);     
    }
    
    //function for displaying input
    public void display()
    {
        System.out.println("Input:  ");
        for(String s: commands)
        {
            System.out.println(s);
        }
        System.out.println("Input End!\n\n  ");
    }
    
    //function for interpreting the commands
    public void interpret()
    {
        for(String s: commands)
        {
            s = s.replaceAll("(^\\s+|\\s+$)", "");
           
            //String delims = "[ ]";
            String delims = "\\s+";
            iden = s.split(delims);
            for(count = 0; count < iden.length ; count++)
            {
                
                if(map.containsKey(iden[count]) != false)//if the expression starts with let or print
                    map.get(iden[count]).run();

            }
            line++;
        }
    }
    
   
    
}
