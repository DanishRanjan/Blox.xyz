package test;

import java.sql.*;
import java.util.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class bloxQuestion2 {
    public static void main(String[] args) throws SQLException, NoSuchAlgorithmException {
        // Connect to both databases
        Connection localDb = DriverManager.getConnection("jdbc:mysql://localhost:3306/localdb", "user", "password");
        Connection cloudDb = DriverManager.getConnection("jdbc:mysql://cloud:3306/clouddb", "user", "password");

        // Query data from both databases
        Statement stmtLocal = localDb.createStatement();
        Statement stmtCloud = cloudDb.createStatement();
        ResultSet rsLocal = stmtLocal.executeQuery("SELECT * FROM your_table");
        ResultSet rsCloud = stmtCloud.executeQuery("SELECT * FROM your_table");

        // Compare data
        while (rsLocal.next() && rsCloud.next()) {
            Map<String, String> localRow = getRowData(rsLocal);
            Map<String, String> cloudRow = getRowData(rsCloud);

            if (!localRow.equals(cloudRow)) {
                System.out.println("Data mismatch found!");
            }
        }
    }

    private static Map<String, String> getRowData(ResultSet rs) throws SQLException {
        Map<String, String> rowData = new HashMap<>();
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            rowData.put(metaData.getColumnName(i), rs.getString(i));
        }
        return rowData;
    }
}

