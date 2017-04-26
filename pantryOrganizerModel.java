package com.PamFields;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pam on 4/26/2017.
 */
public class pantryOrganizerModel extends AbstractTableModel {

        private int rowCount = 0;
        private int colCount = 0;
        ResultSet resultSet;

        public pantryOrganizerModel(ResultSet rs){
            this.resultSet= rs;
            setup();
        }
        private void setup(){

            countRows();

            try{
                colCount = resultSet.getMetaData().getColumnCount();

            } catch (SQLException se) {
                System.out.println("Error counting columns" + se);
            }

        }


        public void updateResultSet(ResultSet newRS){
            resultSet = newRS;
            setup();
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
        public int getColumnCount(){
            return colCount;
        }

        @Override
        public Object getValueAt(int row, int col){
            try{
                //  System.out.println("get value at, row = " +row);
                resultSet.absolute(row+1);
                Object o = resultSet.getObject(col+1);
                return o.toString();
            }catch (SQLException se) {
                System.out.println(se);
                //se.printStackTrace();
                return se.toString();

            }
        }

        @Override
        //We only want user to be able to edit column 2 - the rating column.
        //If this method always returns true, the whole table will be editable.

        //TODO how can we avoid using a magic number (if col==3) ) here? This code depends on column 3 being the rating.
        //This might change if we were to add more data to our table, for example storing names of people who created the review.
        //TODO To fix: look into table column models, and generate the number columns based on the columns found in the ResultSet.
        public boolean isCellEditable(int row, int col){
            if (col == 3) {
                return true;
            }
            return false;
        }

        //Delete row, return true if successful, false otherwise
        public boolean deleteRow(int row){
            try {
                resultSet.absolute(row + 1);
                resultSet.deleteRow();
                //Tell table to redraw itself
                fireTableDataChanged();
                return true;
            }catch (SQLException se) {
                System.out.println("Delete row error " + se);
                return false;
            }
        }

        //returns true if successful, false if error occurs
        public boolean insertRow(String item, double quantity) {

            try {
                //Move to insert row, insert the appropriate data in each column, insert the row, move cursor back to where it was before we started
                resultSet.moveToInsertRow();
                resultSet.updateString(pantryOrganizerDB.ITEM_COLUMN, item);
                resultSet.updateDouble(pantryOrganizerDB.ITEM_QUANTITY, quantity);
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
        public String getColumnName(int col){
            //Get from ResultSet metadata, which contains the database column names
            //TODO translate DB column names into something nicer for display, so "YEAR_RELEASED" becomes "Year Released"
            try {
                return resultSet.getMetaData().getColumnName(col + 1);
            } catch (SQLException se) {
                System.out.println("Error fetching column names" + se);
                return "?";
            }
        }

}