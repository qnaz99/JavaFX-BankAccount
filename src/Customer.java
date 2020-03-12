package project;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




public class Customer extends User{
    double balance;
    char tier;
    
    public Customer (String n, String p, double b){
        name = n;
        password = p;
        role = false;
        balance = b;
        if (balance >= 20000)
            tier = ('p');
        else if (10000 <= balance)
            tier = ('g');
        else
            tier = ('s');
        
    }
    
    @Override
    public void depositMoney(double b){
        balance = balance + b;
        Path path = Paths.get("customerFiles//" + name + ".txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.set(2, ""+balance);
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (balance >= 20000)
            tier = ('p');
        else if (10000 <= balance)
            tier = ('g');
        else
            tier = ('s');
    }
    
    @Override
    public void withdrawMoney(double b) throws IllegalArgumentException{
        
        balance = balance - b;
        Path path = Paths.get("customerFiles//" + name + ".txt");
        List<String> lines;
        try {
            lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            lines.set(2, ""+balance);
            Files.write(path, lines, StandardCharsets.UTF_8);
        } catch (IOException ex) {
            Logger.getLogger(Customer.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        if (balance >= 20000){
            tier = ('p');
        }
        else if (balance >= 10000){
            tier = ('g');
        }
        else {
            tier = ('s');
        }
    }
    
    @Override
    public double getBalance(){
        return balance;
    }
    
    @Override
    public char getTier(){
        return tier;
    }
    
}
