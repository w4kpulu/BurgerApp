import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MenuGUI {

    private JFrame frame;
    private JComboBox<String> categoryDropdown;
    private JPanel itemsPanel;
    private JTextArea cartArea;
    private ArrayList<MenuItem> cart;
    private double totalPrice;
    private JButton confirmButton;
    private JButton deleteItemButton;
    private JComboBox<String> deleteItemDropdown;

    public MenuGUI() {
        // Initialize cart and total price
        cart = new ArrayList<>();
        totalPrice = 0.0;

        // Setup the frame
        frame = createFrame();
        frame.add(createCategoryDropdown(), BorderLayout.NORTH);
        frame.add(createItemsPanel(), BorderLayout.CENTER);
        frame.add(createCartArea(), BorderLayout.EAST);
        frame.add(createBottomPanel(), BorderLayout.SOUTH);

        // Show the frame
        frame.setVisible(true);
    }

    private JFrame createFrame() {
        JFrame frame = new JFrame("Street Burger");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Increase the frame size to accommodate larger content
        frame.setLayout(new BorderLayout());
        return frame;
    }

    private JComboBox<String> createCategoryDropdown() {
        String[] categories = {"Select Category", "Burgers", "Chicken", "Sides", "Drinks", "Combos"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.addActionListener(e -> showItems((String) categoryDropdown.getSelectedItem()));
        return categoryDropdown;
    }

    private JPanel createItemsPanel() {
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(0, 2)); // Two columns for better item arrangement
        return itemsPanel;
    }

    private JScrollPane createCartArea() {
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        return new JScrollPane(cartArea);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());

        deleteItemDropdown = new JComboBox<>();
        bottomPanel.add(deleteItemDropdown);

        deleteItemButton = new JButton("Delete Item");
        deleteItemButton.addActionListener(e -> deleteItem());
        bottomPanel.add(deleteItemButton);

        confirmButton = new JButton("Confirm Selection");
        confirmButton.addActionListener(e -> confirmSelection());
        bottomPanel.add(confirmButton);

        return bottomPanel;
    }

    private void showItems(String category) {
        itemsPanel.removeAll();

        MenuItem[] items = getItemsByCategory(category);
        if (items != null) {
            for (MenuItem item : items) {
                JPanel itemPanel = createItemPanel(item);
                itemsPanel.add(itemPanel);
            }
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private JPanel createItemPanel(MenuItem item) {
        JPanel itemPanel = new JPanel();
        itemPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Load and scale image
        JLabel imageLabel = createItemImage(item);
        itemPanel.add(imageLabel);

        // Add button for the item
        JButton itemButton = createItemButton(item);
        itemPanel.add(itemButton);

        return itemPanel;
    }

    private JLabel createItemImage(MenuItem item) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/" + item.getImageName())); 
        Image img = icon.getImage().getScaledInstance(140, 140, Image.SCALE_SMOOTH); // Increase the size to 140px
        icon = new ImageIcon(img);
        return new JLabel(icon);
    }

    private JButton createItemButton(MenuItem item) {
        JButton itemButton = new JButton(item.getName() + " - RM" + item.getPrice());
        itemButton.setPreferredSize(new Dimension(200, 50)); // Increase the button size
        itemButton.addActionListener(e -> addToCart(item));
        return itemButton;
    }

    private MenuItem[] getItemsByCategory(String category) {
        switch (category) {
            case "Burgers":
                return new MenuItem[]{
                    new MenuItem("Classic Cheeseburger", 9.90, "Burger", "CheeseBurger.jpeg"),
                    new MenuItem("BBQ Smokehouse Burger", 12.90, "Burger", "BBQburger.jpeg"),
                    new MenuItem("Grilled Chicken Burger", 11.90, "Burger", "GrillChickBurger.jpeg")
                };
            case "Chicken":
                return new MenuItem[]{
                    new MenuItem("Fried Chicken (2 pcs)", 8.90, "Chicken", "Chicken2pcs.jpg"),
                    new MenuItem("Chicken Wings (6 pcs)", 12.90, "Chicken", "chickenWing.jpg"),
                    new MenuItem("Chicken Tenders (4 pcs)", 9.90, "Chicken", "ChickenTnder.jpg")
                };
            case "Sides":
                return new MenuItem[]{
                    new MenuItem("French Fries (Regular)", 4.50, "Side", "FrenchFries.jpg"),
                    new MenuItem("Curly Fries", 5.50, "Side", "CurlyFries.jpg"),
                    new MenuItem("Sweet Potato Fries", 6.50, "Side", "sweetPotatoFries.jpg")
                };
            case "Drinks":
                return new MenuItem[]{
                    new MenuItem("Soft Drinks", 3.50, "Drink", "SoftDrink.jpg"),
                    new MenuItem("Iced Tea", 4.50, "Drink", "IcedTea.jpg"),
                    new MenuItem("Milkshakes", 8.90, "Drink", "MilkShake.jpg")
                };
            case "Combos":
                return new MenuItem[]{
                    new MenuItem("Classic Burger Combo", 14.90, "Combo", "ClassicBurgerCombo.jpg"),
                    new MenuItem("Chicken Lover's Combo", 16.90, "Combo", "BurgerChickCombo.jpg")
                };
            default:
                return null;
        }
    }

    private void addToCart(MenuItem item) {
        boolean found = false;
        for (MenuItem cartItem : cart) {
            if (cartItem.getName().equals(item.getName())) {
                cartItem.increaseQuantity(1);
                totalPrice += item.getPrice();
                found = true;
                break;
            }
        }
        if (!found) {
            item.setQuantity(1);
            cart.add(item);
            totalPrice += item.getPrice();
        }
        updateCartArea();
        updateDeleteItemDropdown();
    }

    private void deleteItem() {
        String selectedItem = (String) deleteItemDropdown.getSelectedItem();
        if (selectedItem != null && !selectedItem.equals("Select Item to Delete")) {
            MenuItem toRemove = getCartItemByName(selectedItem);
            if (toRemove != null) {
                removeItemFromCart(toRemove);
            } else {
                JOptionPane.showMessageDialog(frame, "Item not found in the cart.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private MenuItem getCartItemByName(String name) {
        for (MenuItem item : cart) {
            if (item.getName().equalsIgnoreCase(name.trim())) {
                return item;
            }
        }
        return null;
    }

    private void removeItemFromCart(MenuItem item) {
        String quantityString = JOptionPane.showInputDialog(frame, "Enter quantity to delete for " + item.getName() + " (max: " + item.getQuantity() + "):");
        try {
            int quantityToDelete = Integer.parseInt(quantityString.trim());
            if (quantityToDelete <= 0 || quantityToDelete > item.getQuantity()) {
                JOptionPane.showMessageDialog(frame, "Invalid quantity.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            totalPrice -= item.getPrice() * quantityToDelete;
            item.decreaseQuantity(quantityToDelete);
            if (item.getQuantity() == 0) {
                cart.remove(item);
            }
            updateCartArea();
            updateDeleteItemDropdown();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Invalid quantity input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateDeleteItemDropdown() {
        deleteItemDropdown.removeAllItems();
        deleteItemDropdown.addItem("Select Item to Delete");
        for (MenuItem item : cart) {
            deleteItemDropdown.addItem(item.getName());
        }
    }

    private void updateCartArea() {
        StringBuilder cartContent = new StringBuilder("Your Cart:\n");
        for (MenuItem item : cart) {
            cartContent.append(item.getName())
                    .append(" (x").append(item.getQuantity())
                    .append(") - RM").append(item.getPrice() * item.getQuantity())
                    .append("\n");
        }
        cartContent.append("\nTotal: RM").append(totalPrice);
        cartArea.setText(cartContent.toString());
    }

    private void confirmSelection() {
        int confirmation = JOptionPane.showConfirmDialog(frame, "Do you want to proceed to payment?", "Confirm Selection", JOptionPane.YES_NO_OPTION);
        if (confirmation == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(frame, "Proceeding to payment...", "Payment", JOptionPane.INFORMATION_MESSAGE);
            // Here you can add the logic to open a payment form or process payment
        }
    }

    public static void main(String[] args) {
        new MenuGUI();
    }

    public void setVisible(boolean b) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setVisible'");
    }
}

class MenuItem {
    private String name;
    private double price;
    private String type;
    private int quantity;
    private String imageName;

    public MenuItem(String name, double price, String type, String imageName) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.imageName = imageName;
        this.quantity = 0;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getType() {
        return type;
    }

    public String getImageName() {
        return imageName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}