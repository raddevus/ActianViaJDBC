import java.sql.*;
import static java.lang.System.out;

public class AddDefault {
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
            
            String alterQuery = "ALTER TABLE Student MODIFY Probation tinyint DEFAULT 0";
            boolean retVal = stmt.execute(alterQuery);
            out.println(String.format("retVal : %s",retVal));
            out.println("########### TABLE INFO ############");
            
            String insertQuery = "Insert into Student (ID, MAJOR, TUITION_ID) values ('555', 'Music', 3)";
            retVal = stmt.execute(insertQuery);

            String selectQuery = String.format("SELECT * from Student where id = 555",tableName);
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
            String deleteQuery = "delete from student where id = 555";
            stmt.execute(deleteQuery);
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