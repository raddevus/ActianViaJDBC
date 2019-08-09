import java.sql.*;
import static java.lang.System.out;

public class DeleteOne {
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
            
            String insertQuery = "Insert into Student (ID, MAJOR, TUITION_ID) values ('777', 'Music', 1)";
            boolean retVal = stmt.execute(insertQuery);

            String deleteQuery = "delete from student where id = 777";
            stmt.execute(deleteQuery);

            // ### Attempts to select the record to determine if it has been deleted.
            String selectQuery = String.format("SELECT * from Student where id = 777",tableName);
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
            {
                out.println("The student record with an ID of 777 has been deleted.");
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