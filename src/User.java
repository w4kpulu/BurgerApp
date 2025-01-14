import java.io.*;
import java.util.*;

public abstract class User {
    private String name;
    private String email;
    private String noPhone;
    private String address;
    private String pass;

    public User() {
        name = "";
        email = "";
        noPhone = "";
        address = "";
        pass = "";
    }

    public User(String nm, String em, String np, String add, String ps) {
        name = nm;
        email = em;
        noPhone = np;
        address = add;
        pass = ps;
    }
    public void setName(String nm){
        name=nm;
    }

    public String getName() {
        return name;
    }

    public abstract double calcPrice();

    public boolean validateCredentials(String inputEmail, String inputPass) {
        return this.email.equals(inputEmail) && this.pass.equals(inputPass);
    }

    public static List<User> loadUsers(String filePath) throws IOException {
        List<User> users = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length == 5) {
                users.add(new BasicUser(parts[0], parts[1], parts[2], parts[3], parts[4]));
            }
        }
        reader.close();
        return users;
    }

    @Override
    public String toString() {
        return "\nName : " + name +
               "\nEmail : " + email +
               "\nNo Phone : " + noPhone +
               "\nAddress : " + address +
               "\nPassword : " + pass;
    }
}