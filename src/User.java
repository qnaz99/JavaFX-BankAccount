package project;


public abstract class User {
    String name;
    String password;
    Boolean role;
    
    public User(String n, String p){
        name = n;
        password = p;    
    }
    public User(){  
    }
    
    
    public String getName(){
        return name;
    }
    
    public void setName(String n){
        name =n;        
    }
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String p){
        password =p;        
    }
        
    public boolean getRole(){
        return role;
    }
    
    public void setRole(boolean r){
        role =r;        
    }
    public double getBalance(){
        
    }   
    public char getTier(){
        
    }
    public void depositMoney(double b){
    
    }
    public void withdrawMoney(double b) throws IllegalArgumentException{
        
    }
    
}
