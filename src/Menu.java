import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class MenuGUI {

    private JFrame frame;
    private JComboBox<String> categoryDropdown;
    private JPanel itemsPanel;
    private JTextArea cartArea;
    private ArrayList<MenuItem> cart;
    private double totalPrice;

    public MenuGUI() {
        // Initialize cart and total price
        cart = new ArrayList<>();
        totalPrice = 0.0;

        // Frame setup
        frame = new JFrame("McDonald's Ordering System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout());

        // Category dropdown
        String[] categories = {"Select Category", "Burgers", "Chicken", "Sides", "Drinks", "Combos"};
        categoryDropdown = new JComboBox<>(categories);
        categoryDropdown.addActionListener(e -> showItems((String) categoryDropdown.getSelectedItem()));
        frame.add(categoryDropdown, BorderLayout.NORTH);

        // Items panel
        itemsPanel = new JPanel();
        itemsPanel.setLayout(new GridLayout(0, 1));
        frame.add(new JScrollPane(itemsPanel), BorderLayout.CENTER);

        // Cart area
        cartArea = new JTextArea(10, 30);
        cartArea.setEditable(false);
        frame.add(new JScrollPane(cartArea), BorderLayout.EAST);

        // Show frame
        frame.setVisible(true);
    }

    private void showItems(String category) {
        itemsPanel.removeAll();

        MenuItem[] items = getItemsByCategory(category);
        if (items != null) {
            for (MenuItem item : items) {
                JButton itemButton = new JButton(item.getName() + " - RM" + item.getPrice());
                itemButton.addActionListener(e -> addToCart(item));
                itemsPanel.add(itemButton);
            }
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private MenuItem[] getItemsByCategory(String category) {
        switch (category) {
            case "Burgers":
                return new MenuItem[]{
                    new MenuItem("Classic Cheeseburger", 9.90, "Burger"),
                    new MenuItem("BBQ Smokehouse Burger", 12.90, "Burger"),
                    new MenuItem("Grilled Chicken Burger", 11.90, "Burger")
                };
            case "Chicken":
                return new MenuItem[]{
                    new MenuItem("Fried Chicken (2 pcs)", 8.90, "Chicken"),
                    new MenuItem("Chicken Wings (6 pcs)", 12.90, "Chicken"),
                    new MenuItem("Chicken Tenders (4 pcs)", 9.90, "Chicken")
                };
            case "Sides":
                return new MenuItem[]{
                    new MenuItem("French Fries (Regular)", 4.50, "Side"),
                    new MenuItem("Curly Fries", 5.50, "Side"),
                    new MenuItem("Sweet Potato Fries", 6.50, "Side")
                };
            case "Drinks":
                return new MenuItem[]{
                    new MenuItem("Soft Drinks", 3.50, "Drink"),
                    new MenuItem("Iced Tea", 4.50, "Drink"),
                    new MenuItem("Milkshakes", 8.90, "Drink")
                };
            case "Combos":
                return new MenuItem[]{
                    new MenuItem("Classic Burger Combo", 14.90, "Combo"),
                    new MenuItem("Chicken Lover's Combo", 16.90, "Combo")
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
    }

    private void updateCartArea() {
        StringBuilder cartContent = new StringBuilder("Your Cart:\n");
        for (MenuItem item : cart) {
            cartContent.append(item.getName()).append(" (x").append(item.getQuantity()).append(") - RM").append(item.getPrice() * item.getQuantity()).append("\n");
        }
        cartContent.append("\nTotal: RM").append(totalPrice);
        cartArea.setText(cartContent.toString());
    }

    public static void main(String[] args) {
        new MenuGUI();
    }
}

