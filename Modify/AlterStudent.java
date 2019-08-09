import java.sql.*;
import static java.lang.System.out;

public class AlterStudent {
    static final String COMPUTERNAME = "localhost";
    static final int PORT = 1583;

    static final String DB_URL = String.format("jdbc:pervasive://%s:%s/DEMODATA",COMPUTERNAME, PORT);
    
    //  Database credentials
    static final String USER = "";
    static final String PWD = "";
   
    public static void main(String[] args) {
        try{
            Connection conn = null;
            String tableName = "student";

            conn = DriverManager.getConnection(DB_URL, USER, PWD);
            Statement stmt = conn.createStatement();
            
            String alterQuery = "ALTER TABLE Student ADD Probation tinyint ";
            boolean retVal = stmt.execute(alterQuery);
            out.println(String.format("retVal : %s",retVal));
            out.println("########### TABLE INFO ############");
            // if (recordsUpdated > 0){
            //     out.println(String.format("Updated %s rows in the %s table.", recordsUpdated, tableName));
            // }
            
            String selectQuery = String.format("SELECT top(1)* FROM %s where cumulative_GPA >= 3.5 and major = 'Music' and scholarship_amount = 1000",tableName);
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