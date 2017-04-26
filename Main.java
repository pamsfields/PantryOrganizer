package com.PamFields;

import java.sql.*;
import java.util.Scanner;

public class pantryOrganizerDB {
    static Scanner stringScanner = new Scanner(System.in);
    static Scanner numberScanner = new Scanner(System.in);

    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";//Configure the driver needed
    static final String DB_CONNECTION_URL = "jdbc:mysql://localhost:3306/pantry";//Connection string – where's the database?

    static final String USER = "PamFields";   //username selected on MySQL
    static final String PASSWORD = System.getenv("SQL_Test");   //Set as environmental variable

    public final static String ITEM_COLUMN = "Item";
    public final static String ITEM_QUANTITY = "Quantity";
    public final static String PANTRY_TABLE_NAME = "Pantry Stock";
    protected static pantryOrganizerModel pantryorganizerModel;

    public pantryOrganizerDB() throws SQLException {
    }

    public class Main {

        public void main(String[] args) {
            // write your code here
            //pantry list from https://www.realsimple.com/food-recipes/shopping-storing/food/pantry-staples-checklist
            // recipe lists linked from allrecipes.com
            //shopping lists created from lack of items on pantry list
            //TODO create database of pantry items
            //TODO link database to application
            //TODO create search using website
            try {
                Class.forName(JDBC_DRIVER);
            } catch (ClassNotFoundException cnfe) {
                System.out.println("Can't instantiate driver class; check you have driver and classpath configured correctly");
                cnfe.printStackTrace();
                System.exit(-1);
            }
            ResultSet rs = pantryOrganizerDB.loadAllItems();
            pantryorganizerModel = new pantryOrganizerModel(rs);
            PantryOrganizer tableGUI = new PantryOrganizer(pantryOganizerModel);
        }
    }

        public static ResultSet loadAllItems() throws SQLException {
            Connection conn = DriverManager.getConnection(DB_CONNECTION_URL, USER, PASSWORD);
            Statement statement = conn.createStatement();
            String fetchAllDataSQL = "SELECT * FROM " + SOLVER_TABLE_NAME;
            ResultSet rs = statement.executeQuery(fetchAllDataSQL);
            try {

                if (rs != null) {
                    rs.close();
                }

                String getAllData = "SELECT * FROM " + SOLVER_TABLE_NAME;
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
    }