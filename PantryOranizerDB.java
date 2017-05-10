import java.sql.*;
import java.util.Scanner;

class PantryOrganizerDB {
    public final static String ITEM_COLUMN = "Item";
    public final static String ITEM_QUANTITY = "Quantity";
    public final static String PANTRY_TABLE_NAME = "Pantry Stock";
    public final static String SHOPPING_TABLE_NAME = "Shopping List";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/pantry";//Connection string – where's the database?
    static final String USER = "PamFields";   //username selected on MySQL
    static final String PASSWORD = System.getenv("SQL_Test");   //Set as environmental variable
    protected static PantryOrganizerModel pantryorganizerModel;
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    public PantryOrganizerDB() throws SQLException {
    }

    public static ResultSet loadAllItems() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
        String fetchAllDataSQL = "SELECT * FROM " + PANTRY_TABLE_NAME;
        ResultSet rs = statement.executeQuery(fetchAllDataSQL);
        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + PANTRY_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            //Or, if one already exists, update its ResultSet
            /*if (cubeSolverModel == null) {
                //If no current CubeSolverModel, then make one
                cubeSolverModel = new CubeSolverModel(rs);
            } else {CubeSolverModel.updateResultSet(rs);}*/

            return rs;

        } catch (Exception e) {
            System.out.println("Error loading or reloading solvers");
            System.out.println(e);
            e.printStackTrace();
            return null;
        }

    }
    public static ResultSet loadShoppingList() throws SQLException{
        Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
        String fetchAllDataSQL = "SELECT * FROM " + SHOPPING_TABLE_NAME;
        ResultSet rs = statement.executeQuery(fetchAllDataSQL);
        try {

            if (rs != null) {
                rs.close();
            }

            String getAllData = "SELECT * FROM " + SHOPPING_TABLE_NAME;
            rs = statement.executeQuery(getAllData);

            //Or, if one already exists, update its ResultSet
            /*if (cubeSolverModel == null) {
                //If no current CubeSolverModel, then make one
                cubeSolverModel = new CubeSolverModel(rs);
            } else {CubeSolverModel.updateResultSet(rs);}*/

            return rs;

        } catch (Exception e) {
            System.out.println("Error loading or reloading solvers");
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }

    public static void shutdown() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
        Statement statement = conn.createStatement();
        String fetchAllDataSQL = "SELECT * FROM " + PANTRY_TABLE_NAME;
        ResultSet rs = statement.executeQuery(fetchAllDataSQL);
        try {
            if (rs != null) {
                rs.close();
                System.out.println("Result set closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        try {
            if (statement != null) {
                statement.close();
                System.out.println("Statement closed");
            }
        } catch (SQLException se) {
            //Closing the connection could throw an exception too
            se.printStackTrace();
        }

        try {
            if (conn != null) {
                conn.close();
                System.out.println("Database connection closed");
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }
}
