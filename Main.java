import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {
    public final static String ITEM_COLUMN = "Item";
    public final static String ITEM_QUANTITY = "Quantity";
    public final static String PANTRY_TABLE_NAME = "Pantry Stock";
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/pantry";//Connection string – where's the database?
    static final String USER = "PamFields";   //username selected on MySQL
    static final String PASSWORD = System.getenv("SQL_Test");   //Set as environmental variable
    protected static PantryOrganizerModel pantryorganizerModel;

    public static void main(String[] args) throws SQLException {

        //shopping lists created from lack of items on pantry list
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Can't instantiate driver class; check you have driver and classpath configured correctly");
            cnfe.printStackTrace();
            System.exit(-1);
        }
        ResultSet rs = PantryOrganizerDB.loadAllItems();
        pantryorganizerModel = new PantryOrganizerModel(rs);
        PantryOrganizerGUI tableGUI = new PantryOrganizerGUI(pantryorganizerModel);
    }
}


