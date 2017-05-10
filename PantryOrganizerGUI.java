import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PantryOrganizerGUI extends JFrame {
    PantryOrganizerModel pantryorganizerModel;
    private JPanel rootPanel;
    private JButton updateQuantityButton;
    private JButton removeButton;
    private JButton addItemButton;
    private JTextField updateTextField;
    private JTextField itemNameField;
    private JButton shoppingListGeneratorButton;
    private JTable pantryTable;
    private JButton quitButton;
    private JTable shoppingListTable;

    PantryOrganizerGUI(final PantryOrganizerModel pantryOrganizerTableModel) {

        setContentPane(rootPanel);
        pack();
        setTitle("Pantry Organizer Application");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pantryTable.setGridColor(Color.BLACK);
        pantryTable.setModel(pantryorganizerModel);

        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get item name, make sure it's not already used
                String itemName = itemNameField.getText();

                if (itemName == null || itemName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the name of the item");
                    return;
                }

                //Get quantity of the item
                double itemQuantity;

                itemQuantity = Integer.parseInt(updateTextField.getText());

                System.out.println("Adding " + itemName + " " + itemQuantity);
                boolean insertedRow = pantryorganizerModel.insertRow(itemName, itemQuantity);

                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new item");
                }
                // If insertedRow is true and the data was added, it should show up in the table, so no need for confirmation message.

            }

        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PantryOrganizerDB.shutdown();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);   //Should probably be a call back to Main class so all the System.exit(0) calls are in one place.
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = pantryTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose an item to remove");
                }
                boolean deleted = pantryorganizerModel.deleteRow(currentRow);
                if (deleted) {
                    try {
                        ResultSet rs = PantryOrganizerDB.loadAllItems();
                        pantryorganizerModel = new PantryOrganizerModel(rs);
                        shoppingListTable.setModel(pantryOrganizerTableModel);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error removing item");
                }
            }
        });

        updateQuantityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get item name, make sure it's not already used
                String itemName = itemNameField.getText();

                if (itemName == null || itemName.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter the name of the item");
                    return;
                }

                //Get quantity of the item
                double itemQuantity;

                itemQuantity = Integer.parseInt(updateTextField.getText());

                System.out.println("Updating" + itemName + " " + itemQuantity);
                boolean updateItemSet = pantryorganizerModel.updateItemSet(itemName, itemQuantity);

                if (!updateItemSet) {
                    JOptionPane.showMessageDialog(rootPane, "Error updating item");
                }
                // If insertedRow is true and the data was added, it should show up in the table, so no need for confirmation message.

            }

        });

        shoppingListGeneratorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ResultSet rs = PantryOrganizerDB.loadShoppingList();
                    pantryorganizerModel = new PantryOrganizerModel(rs);
                    pantryTable.setModel(pantryOrganizerTableModel);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

    }
}
