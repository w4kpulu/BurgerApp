public class BasicUser extends User {
    public BasicUser(String name, String email, String noPhone, String address, String pass) {
        super(name, email, noPhone, address, pass);
    }

    @Override
    public double calcPrice() {
        return 0; // You can implement actual logic if needed
    }
}
