package com.PamFields;

import javax.swing.*;

/**
 * Created by Pam on 4/26/2017.
 */
public class PantryOrganizerForm extends JFrame implements WindowListener {
    private JPanel rootPanel;
    private JButton updateQuantityButton;
    private JButton removeButton;
    private JButton itemNameButton;
    private JTextField updateTextField;
    private JTextField itemNameField;
    private JButton recipeGeneratorButton;
    private JTable pantryTable;
    private JScrollPane recipeScrollPane;
    private JButton quitButton;

    PantryOrganizerForm(final pantryOrganizerModel pantryOrganizerTableModel){

        setContentPane(rootPanel);
        pack();
        setTitle("Pantry Organizer Application");
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pantryTable.setGridColor(Color.BLACK);
        pantryTable.setModel(pantryOrganizerModel);

        addSolverButton.addActionListener(new ActionListener() {
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

                solvedTime = Integer.parseInt(updateTextField.getText());

                System.out.println("Adding " + itemName + " " + itemQuantity);
                boolean insertedRow = pantryOrganizerModel.insertRow(itemName, itemQuantity);

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
                    pantryOrganizerDB.shutdown();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                System.exit(0);   //Should probably be a call back to Main class so all the System.exit(0) calls are in one place.
            }
        });

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = resultsTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose an item to remove");
                }
                boolean deleted = pantryOrganizerModel.deleteRow(currentRow);
                if (deleted) {
                    try {
                        ResultSet rs = pantryOrganizerDB.loadAllSolvers();
                        pantryOrganizerModel = new pantryOrganizerModel(rs);
                        pantryTable.setModel(pantryOrganizerModel);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error removing item");
                }
            }
        });
    }
    @Override
    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        try {
            cubesolverDB.shutdown();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {}
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
