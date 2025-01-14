public class MenuItem extends User {
    private String name;
    private double price;
    private String type; // Type of menu item (e.g., "Burger", "Chicken", "Side", "Drink", "Combo")
    private MenuItem[] comboItems; // For Combo items
    private int quantity; // Quantity of the menu item
    private String imageName; //image file name

    // Constructor for regular menu items
    public MenuItem(String nm, String em, String np, String add, String ps,String name, double price, String type, int quantity,String imageName) {
        super(nm,em,np,add,ps);
        this.name = name;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
        this.imageName=imageName;
        this.comboItems = new MenuItem[0]; // Initialize to an empty array for non-combo items
    }

    // Constructor for Combo items
    public MenuItem(String nm, String em, String np, String add, String ps,String name, double price, MenuItem[] comboItems, int quantity) {
        super(nm,em,np,add,ps);
        this.name = name;
        this.price = price;
        this.type = "Combo";
        this.comboItems = comboItems;
        this.quantity = quantity;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getImageName(){
        return imageName;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Calculate total price based on quantity
    public double calcPrice() {
        return price * quantity;
    }

    // Display method for showing the details of the item
    public void display() {
        if (type.equals("Combo")) {
            System.out.println("Combo: " + name + " - RM" + price + " (Quantity: " + quantity + ")");
            System.out.println("Includes:");
            for (MenuItem item : comboItems) {
                System.out.println("  - " + item.name + " - RM" + item.price);
            }
        } else {
            System.out.println(type + ": " + name + " - RM" + price + " (Quantity: " + quantity + ")");
        }
        System.out.println("Total Price: RM" + calcPrice());
    }
}