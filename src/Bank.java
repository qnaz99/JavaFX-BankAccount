package project;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class Bank extends Application{
    Accounts a;
    public static void main (String []args){
        Application.launch(args);
    }

    public Bank() throws IOException {
        this.a = new Accounts();
    }
    
    public void start(Stage primaryStage) throws Exception {
        
        
        Stage stage = new Stage();
        stage.setTitle("KMoney Bank");
        
        Text welcome = new Text("Welcome to QIBC bank, the #1 bank for students"); 
        welcome.setFont(Font.font("Monospaced", 18));
        welcome.setX(34);
        welcome.setY(30); 
        
        Text instruc = new Text("Please enter your username and password to login.");
        instruc.setX(160);
        instruc.setY(100);
        
        
        TextField user = new TextField();
        user.setPromptText("Username");
        user.setLayoutX(160);
        user.setLayoutY(120);
        user.setPrefWidth(280);
        
        
        PasswordField  pass = new PasswordField();
        pass.setPromptText("Password");
        pass.setLayoutX(160);
        pass.setLayoutY(150);
        pass.setPrefWidth(280);
        
        Button quit = new Button("Quit");
        quit.setLayoutX(350);
        quit.setLayoutY(185);
        quit.setOnAction(e -> {
            stage.close();
        });
        
        
        Button log = new Button("Login");
        log.setLayoutX(395);
        log.setLayoutY(185);
        log.setOnAction(e -> {
            String username = user.getText();
            String password = pass.getText();
            int find = a.returnUser(username,password);
            if (find == 0){
                try {
                    manager(new Stage());
                } catch (Exception ex) {
                    Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if (find == -1){
                Alert alert = new Alert(AlertType.ERROR, "Invalid username and/or password. Please try again.", ButtonType.OK);
                alert.showAndWait();
            }
            else{
                try {
                    customer(new Stage(), find);
                } catch (Exception ex) {
                    Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
        });
        
        Group first = new Group(welcome, instruc, log, user, pass, quit);

        stage.setWidth(600);
        stage.setHeight(300);
        Scene scene1 = new Scene(first);
        stage.setScene(scene1);
        stage.show();
        
    }
    
    public void manager(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Manager Tools");
        stage.setWidth(600);
        stage.setHeight(250);
        
        Text welcome = new Text("Welcome, manager. What would you like to do?"); 
        welcome.setFont(Font.font("Monospaced", 15));
        welcome.setX(100);
        welcome.setY(30); 
        
        
        Button make = new Button("Create Customer");
        make.setLayoutX(240);
        make.setLayoutY(70);
        make.setOnAction(e -> {
            try {
                create(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button delete = new Button("Delete Customer");
        delete.setLayoutX(240);
        delete.setLayoutY(100);
        delete.setOnAction(e -> {
            try {
                delete(new Stage());
            } catch (Exception ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        Button logout = new Button("Logout");
        logout.setLayoutX(240);
        logout.setLayoutY(130);
        logout.setPrefWidth(105);
        logout.setOnAction(e -> {
            stage.close();

        });
        Group group = new Group (welcome, make, delete, logout);
        Scene scene = new Scene(group);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void delete(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Delete Customer");
        stage.setWidth(600);
        stage.setHeight(200);
        
        Text instruc = new Text("Enter username of customer account to delete");
        instruc.setX(175);
        instruc.setY(50);
        
        TextField user = new TextField();
        user.setPromptText("Username");
        user.setLayoutX(175);
        user.setLayoutY(60);
        user.setPrefWidth(245);
        
        Button del = new Button("Delete");
        del.setLayoutX(370);
        del.setLayoutY(90);
        del.setOnAction(e -> {
            String deleteUsername = user.getText();
            if (deleteUsername.equals("admin")){
                Alert alert = new Alert(AlertType.ERROR, "You are not authorized to delete managers.", ButtonType.OK);
                alert.showAndWait();    
            }
            else{
                int j = a.removeCustomer(deleteUsername);
                if (j != -1){
                    Alert success = new Alert(AlertType.NONE, "Successfully deleted customer account.", ButtonType.OK);
                    success.showAndWait();
                    stage.close();
                }
                else {
                    Alert fail = new Alert(AlertType.ERROR, "Customer not found. Please try again.", ButtonType.OK);
                    fail.showAndWait();
                    stage.close();
                }
            }
        });
        
        Button can = new Button("Cancel");
        can.setLayoutX(315);
        can.setLayoutY(90);
        can.setOnAction(e -> {
            stage.close();
        });
        
        Group group = new Group(instruc, user, del, can);
        Scene scene1 = new Scene(group);
        
        stage.setScene(scene1);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        
        
    }
    
    public void create(Stage primaryStage) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Create Customer");
        stage.setWidth(600);
        stage.setHeight(200);
        
        Text instruc = new Text("Enter username, password and balance of customer account to create");
        instruc.setX(112);
        instruc.setY(30);
        
        TextField user = new TextField();
        user.setPromptText("Username");
        user.setLayoutX(175);
        user.setLayoutY(40);
        user.setPrefWidth(245);
        
        TextField pass = new TextField();
        pass.setPromptText("Password");
        pass.setLayoutX(175);
        pass.setLayoutY(70);
        pass.setPrefWidth(245);

        
        Button back = new Button("Back");
        back.setLayoutX(325);
        back.setLayoutY(130);
        back.setOnAction(e -> {
            stage.close();
        });
        
        Button create = new Button("Create");
        create.setLayoutX(370);
        create.setLayoutY(130);
        create.setOnAction(e -> {
            boolean valid = true;
            String username = user.getText();
            String password = pass.getText();
            
            try {
                if(a.addCustomer(username, password, 100) == 1){
                    Alert success = new Alert(AlertType.NONE, "Successfully created customer.", ButtonType.OK);
                    success.showAndWait();
                }
                else{
                    Alert already = new Alert(AlertType.ERROR, "A user with that username already exists. Please try again.", ButtonType.OK);
                    already.showAndWait();
                }
            } catch (IOException ex) {
                //System.out.println("lmao");
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        
        Group group = new Group(instruc, user, pass, back, create);
        Scene scene = new Scene(group);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void customer(Stage primaryStage, int i) throws Exception {
        String username = a.users.get(i).getName();
        
        
        Stage stage = new Stage();
        stage.setTitle(username + "'s Account");
        stage.setWidth(600);
        stage.setHeight(250);
        
        
        
        Text welcome = new Text("Welcome, " + username + ". What would you like to do?"); 
        welcome.setFont(Font.font("Monospaced", 15));
        welcome.setX(5);
        welcome.setY(15);
        
        Button viewBal = new Button("View Balance/Tier");
        viewBal.setLayoutX(245);
        viewBal.setLayoutY(60);
        viewBal.setOnAction(e -> {
            String t;
            char tier = a.users.get(i).getTier();
            double balance = a.users.get(i).getBalance();
            if (tier == 's'){
                t = ("Silver");
            }
            else if (tier == 'g'){
                t = ("Gold");
            }
            else {
                t = ("Platinum");
            }
            Alert bbox = new Alert(AlertType.NONE, "Your account balance is: $" + String.format("%.2f", balance) + "\nYour tier is " + t , ButtonType.OK);
            bbox.showAndWait();
        });
        
        
        Button dep = new Button ("Deposit Money");
        dep.setLayoutX(245);
        dep.setLayoutY(90);
        dep.setPrefWidth(110);
        dep.setOnAction(e -> {
            try {
                add(new Stage(), i);
            } catch (Exception ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        Button wit = new Button ("Withdraw Money");
        wit.setLayoutX(245);
        wit.setLayoutY(120);
        wit.setPrefWidth(110);
        wit.setOnAction(e -> {
            try { 
                remove(new Stage(), i);
            } catch (Exception ex) {
                Logger.getLogger(Bank.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        
        Button shop = new Button ("Online Shopping");
        shop.setLayoutX(245);
        shop.setLayoutY(150);
        shop.setPrefWidth(110);
        shop.setOnAction(e -> {
            Stage stageS = new Stage();
            stageS.setTitle("Online Shopping");
            stageS.setHeight(200);
            stageS.setWidth(400);
            
            TextField amount = new TextField(); 
            amount.setLayoutX(20);
            amount.setLayoutY(40);
        
            Text instruc = new Text("How much would you like to spend?");
            instruc.setX(20);
            instruc.setY(30);
            
            char tier = a.users.get(i).getTier();
            String t;
            double f;
            if (tier == 's'){
                t = ("Silver");
                f = 20.0;
            }
            else if (tier == 'g'){
                t = ("Gold");
                f = 10.0;
            }
            else {
                t = ("Platinum");
                f = 0;
            }
            String s;
            if (tier != 'p'){
                s = ("Since you are a " +  t + " member, there will be a $" + f + "0 fee.");
            }
            else {
                s = ("Since you are a Platinum member, there will be no fee.");
            }
            Text fee = new Text(s);
            fee.setX(20);
            fee.setY(120);
        
            Button buy = new Button("Buy");
            buy.setLayoutX(135);
            buy.setLayoutY(70);
            buy.setOnAction(k -> {
                boolean valid = true;
                try {
                    Double.parseDouble(amount.getText());
                }
                catch(NumberFormatException j){
                    Alert alert = new Alert(AlertType.ERROR, "Invalid amount. Please try again.", ButtonType.OK);
                    alert.showAndWait();
                    valid = false;
                }
                if (valid){
                    double b = Double.parseDouble(amount.getText());
                    if (b >= 50){
                        if ((b+f) < a.users.get(i).getBalance()){
                            a.users.get(i).withdrawMoney(b+f);
                            Alert alert = new Alert(AlertType.NONE, "Successfully purchased.", ButtonType.OK);
                            alert.showAndWait();
                            stageS.close();
                        }
                        else {
                            Alert alert = new Alert(AlertType.ERROR, "Insufficient balance. Please try again.", ButtonType.OK);
                            alert.showAndWait();
                        }
                    }
                    else{
                        Alert alert = new Alert(AlertType.ERROR, "Minimum $50 purchase. Please try again.", ButtonType.OK);
                        alert.showAndWait();
                    }
                    
                }
                
            });
            Group group3 = new Group(amount, instruc, fee, buy);
            Scene scene3 = new Scene(group3);
            stageS.setScene(scene3);
            stageS.initModality(Modality.APPLICATION_MODAL);
            stageS.show();
            
            
        });
        
        Button logout = new Button ("Logout");
        logout.setLayoutX(245);
        logout.setLayoutY(180);
        logout.setPrefWidth(110);
        logout.setOnAction(e -> {
            stage.close();
        });
        
        
        
        Group group = new Group(welcome, viewBal, dep, wit, shop, logout);
        Scene scene = new Scene(group);
        
        
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();
        
        
        
    }
    
    public void add(Stage primaryStage, int i) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Deposit Money");
        stage.setWidth(250);
        stage.setHeight(150);
        
        
        
        TextField amount = new TextField();
        amount.setLayoutX(20);
        amount.setLayoutY(40);
        
        Text instruc = new Text("Enter amount to deposit.");
        instruc.setX(20);
        instruc.setY(30);
        
        Button deposit = new Button("Deposit");
        deposit.setLayoutX(112);
        deposit.setLayoutY(70);
        deposit.setOnAction(e -> {
            boolean valid = true;
            try {
                Double.parseDouble(amount.getText());
            }
            catch(NumberFormatException f){
                Alert alert = new Alert(AlertType.ERROR, "Invalid amount. Please try again.", ButtonType.OK);
                alert.showAndWait();
                valid = false;
            }
            if (valid){
                double b = Double.parseDouble(amount.getText());
                if (b > 0){
                    a.users.get(i).depositMoney(b);
                    Alert alert = new Alert(AlertType.NONE, "Successfully deposited $" + b + ".", ButtonType.OK);
                    alert.showAndWait();
                    stage.close();
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid amount. Please try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            
        });
        
        
        Group group = new Group(deposit, amount, instruc);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
        
    }
        
    public void remove(Stage primaryStage, int i) throws Exception {
        Stage stage = new Stage();
        stage.setTitle("Withdraw Money");
        stage.setWidth(250);
        stage.setHeight(150);
        
        
        
        TextField amount = new TextField();
        amount.setLayoutX(20);
        amount.setLayoutY(40);
        
        Text instruc = new Text("Enter amount to withdraw.");
        instruc.setX(20);
        instruc.setY(30);
        
        Button withdraw = new Button("Withdraw");
        withdraw.setLayoutX(112);
        withdraw.setLayoutY(70);
        withdraw.setOnAction(e -> {
            boolean valid = true;
            try {
                Double.parseDouble(amount.getText());
            }
            catch(NumberFormatException f){
                Alert alert = new Alert(AlertType.ERROR, "Invalid amount. Please try again.", ButtonType.OK);
                alert.showAndWait();
                valid = false;
            }
            if (valid){
                double b = Double.parseDouble(amount.getText());
                if(b > a.users.get(i).getBalance()){
                    Alert alert = new Alert(AlertType.ERROR, "Insufficient account balance. Please try again.", ButtonType.OK);
                    alert.showAndWait();
                }
                else if (b > 0){
                    a.users.get(i).withdrawMoney(b);
                    Alert alert = new Alert(AlertType.NONE, "Successfully withdrew $" + b + ".", ButtonType.OK);
                    alert.showAndWait();
                    stage.close();
                }
                else {
                    Alert alert = new Alert(AlertType.ERROR, "Invalid amount. Please try again.", ButtonType.OK);
                    alert.showAndWait();
                }
            }
            
        });
        
        
        Group group = new Group(withdraw, amount, instruc);
        Scene scene = new Scene(group);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        
    }
}
