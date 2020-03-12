package project;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

//Requires: directory customerFiles and files in it
//Modifies: ArrayList users
//Effects: Create an empty ArrayList populates it with Customers from the files in folder customerFiles.

//Abstract Function:
//
//AF() = users.get(i).name for all i between 0 and users.size()
//
//
//Rep Invariant:
//RI(account) = true if users.get(i).name not equal to uesrs.get(j).name for every i and j (where j doesn't equal i)
//			  AND users.get(i).balance is >= 0 for all i > 0 (not including 0, which is the manager)
//			  
//			= false otherwise
//

public class Accounts {
    ArrayList<User> users;
    
    public Accounts() throws IOException{
        users = new ArrayList();
        users.add(new Manager("admin","admin"));
        if (!(new File("customerFiles").exists())){
            new File("customerFiles").mkdir();
        }
        else{
            File dir = new File("customerFiles");
            File[] directoryListing = dir.listFiles();
            for (File child : directoryListing) {
                BufferedReader reader;
                reader = new BufferedReader(new FileReader(child));
                String name = reader.readLine();
                String password = reader.readLine();
                String balance = reader.readLine();
                double b = Double.parseDouble(balance);
                
                users.add(new Customer(name, password, b));
            }
        }
      
    }
        
        
        
    
    
    public int addCustomer(String n, String p, double b) throws IOException{
        boolean exists = false;
        for (int i = 0; i < users.size(); i++){  
            if (users.get(i).getName().equals(n)){
                exists = true;
            }
        }
        if (!exists){
            users.add(new Customer(n, p, b));
            File file = new File("customerFiles//" + n + ".txt");
            file.createNewFile();
            FileWriter fileWriter = new FileWriter("customerFiles//" + n + ".txt");
            try (PrintWriter printWriter = new PrintWriter(fileWriter)) {
                printWriter.print(n + "\n");
                printWriter.print(p + "\n");
                printWriter.print(b + "\n");
            }
            
            
            
            
            return 1;
        }
        else {
            return -1;
        }
        
    }
    public void makeManager(String r, String t){
        users.add(new Manager(r, t));
        
    }
    public int removeCustomer(String n){
        for (int i = 1; i < users.size(); i++){  //set i to 1 so that manager (always in pos. 0) cannot be deleted
            if (users.get(i).getName().equals(n)){
                users.remove(i);
                File file = new File("customerFiles//" + n + ".txt");
                file.delete();
                return i;
            }
        }
        return -1;
    }
    public int returnUser(String n, String p){
        //System.out.println(users.size());
        for (int i = 0 ; i <  users.size(); i++){
            //System.out.println(i);
            if (users.get(i).getName().equals(n)){
                if (users.get(i).getPassword().equals(p)){
                    //System.out.println("i=" + i);
                    return i;
                }
            }
        }
        return -1;
    }
    
    public String toString(){
        String a = "";
        for (int i = 0; i < users.size(); i++){
            a = (a + users.get(i).name + "\n");
        }
    }
}


