import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PantryOrganizerModel extends AbstractTableModel {

    ResultSet resultSet;
    private int rowCount = 0;
    private int colCount = 0;

    public PantryOrganizerModel(ResultSet rs) {
        this.resultSet = rs;
        setup();
    }

    private void setup() {

        countRows();

        try {
            colCount = resultSet.getMetaData().getColumnCount();

        } catch (SQLException se) {
            System.out.println("Error counting columns" + se);
        }

    }


    public boolean updateItemSet(String item, double quantity) {
        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            while (resultSet.next()) {
                resultSet.updateDouble(PantryOrganizerDB.ITEM_QUANTITY, quantity);
                resultSet.updateRow();
                fireTableDataChanged();
                if (quantity == 0){
                    resultSet.moveToInsertRow();
                    resultSet.updateString(PantryOrganizerDB.ITEM_COLUMN, item);
                    resultSet.updateDouble(PantryOrganizerDB.ITEM_QUANTITY, quantity);
                    resultSet.insertRow();
                    resultSet.moveToCurrentRow();
                    fireTableDataChanged();
                    return true;
                }
            }
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    private void countRows() {
        rowCount = 0;
        try {
            //Move cursor to the start...
            resultSet.beforeFirst();
            // next() method moves the cursor forward one row and returns true if there is another row ahead
            while (resultSet.next()) {
                rowCount++;

            }
            resultSet.beforeFirst();

        } catch (SQLException se) {
            System.out.println("Error counting rows " + se);
        }

    }

    @Override
    public int getRowCount() {
        countRows();
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return colCount;
    }

    @Override
    public Object getValueAt(int row, int col) {
        try {
            //  System.out.println("get value at, row = " +row);
            resultSet.absolute(row + 1);
            Object o = resultSet.getObject(col + 1);
            return o.toString();
        } catch (SQLException se) {
            System.out.println(se);
            //se.printStackTrace();
            return se.toString();

        }
    }

    @Override
    public boolean isCellEditable(int row, int col) {
        if (col == 3) {
            return true;
        }
        return false;
    }

    //Delete row, return true if successful, false otherwise
    public boolean deleteRow(int row) {
        try {
            resultSet.absolute(row + 1);
            resultSet.deleteRow();
            //Tell table to redraw itself
            fireTableDataChanged();
            return true;
        } catch (SQLException se) {
            System.out.println("Delete row error " + se);
            return false;
        }
    }

    //returns true if successful, false if error occurs
    public boolean insertRow(String item, double quantity) {

        try {
            //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
            resultSet.moveToInsertRow();
            resultSet.updateString(PantryOrganizerDB.ITEM_COLUMN, item);
            resultSet.updateDouble(PantryOrganizerDB.ITEM_QUANTITY, quantity);
            resultSet.insertRow();
            resultSet.moveToCurrentRow();
            fireTableDataChanged();
            return true;

        } catch (SQLException e) {
            System.out.println("Error adding row");
            System.out.println(e);
            return false;
        }

    }

    @Override
    public String getColumnName(int col) {
        //Get from ResultSet metadata, which contains the database column names
        try {
            return resultSet.getMetaData().getColumnName(col + 1);
        } catch (SQLException se) {
            System.out.println("Error fetching column names" + se);
            return "?";
        }
    }

}