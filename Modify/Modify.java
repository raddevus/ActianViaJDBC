import java.sql.*;
import static java.lang.System.out;

public class Modify {
    static final String COMPUTERNAME = "localhost";
    static final int PORT = 1583;

    static final String DB_URL = String.format("jdbc:pervasive://%s:%s/DEMODATA",COMPUTERNAME, PORT);
    
    //  Database credentials
    static final String USER = "";
    static final String PWD = "";
   
    public static void main(String[] args) {
        try{
            Connection conn = null;
            String tableName = "";
            

            try {
                tableName = args[0];
            }
            catch (ArrayIndexOutOfBoundsException noArgsEx){
                tableName = "student";
            }

            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            Statement stmt = conn.createStatement();
            
            String updateQuery = String.format("UPDATE Student set scholarship_amount = 1000 where cumulative_GPA >= 3.5 and major = 'Music' and scholarship_amount = 0" , tableName);
            int recordsUpdated = stmt.executeUpdate(updateQuery);
            out.println(String.format("recordsUpdated : %s",recordsUpdated));
            if (recordsUpdated > 0){
                out.println("########### TABLE INFO ############");
                out.println(String.format("Updated %s rows in the %s table.", recordsUpdated, tableName));
            }
            
            String selectQuery = String.format("SELECT * FROM %s",tableName);
            ResultSet rs = stmt.executeQuery(selectQuery);
            
            if (rs.next()){
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnCount = rsmd.getColumnCount();
                out.println(String.format("The %s table has %s columns.", tableName.toUpperCase(), columnCount));
                out.println("###################################\n");
                
                for (int i = 1; i <= columnCount;i++){
                    String columnName = rsmd.getColumnName(i);
                    out.println(String.format("%s : %s", columnName, rs.getString(columnName)));
                }
            }
            rs.close();
            stmt.close();
            out.println("Closing connection to DB.");
            conn.close();
        }
        catch ( SQLException sqlex){
            out.println(sqlex);
        }
    }//end main
}//end QueryBasics