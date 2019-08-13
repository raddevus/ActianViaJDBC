import pervasive.database.*;

import static java.lang.System.out;

public class insertRow{
    public static void main(String[] args) {
        Session session = null;
        Database db = null;
        try{
            session = Driver.establishSession();
            out.println("got valid session");
            String dbName = "demodata";
            String user = "";
            String host = "localhost";
            String pwd = "";
            String dbUrl = String.format("btrv://%s@%s/%s?pwd=%s",user,host,dbName, pwd);
            //db = session.connectToDatabase("btrv:///demodata");
            out.println(dbUrl);
            db = session.connectToDatabase(dbUrl);
            out.println(String.format("got database (%s) via %s",dbName,host));
            
            String tableName = "course";
            Table table = db.getTable(tableName);
            TableMetaData tmd = table.getTableMetaData();
            out.println(String.format("There are %s columns in %s.", tmd.getColumnCount(),tableName.toUpperCase()));
          
            RowSet rowset = table.createRowSet();
            try{
                Row newRow = rowset.createRow();
                newRow.setString(0, "JAV 101");
                newRow.setString(1, "CIS JAVA PROG I");
                newRow.setInt(2, 3);
                newRow.setString(3, "Computer Science");
                rowset.insertRow(newRow);
                
                newRow = rowset.createRow();
                newRow.setString(0, "JAV 102");
                newRow.setString(1, "CIS JAVA PROG II");
                newRow.setInt(2, 3);
                newRow.setString(3, "Computer Science");
                rowset.insertRow(newRow);
            }
            catch (Exception ex) {
                // if you run this code after the records
                // have already been inserted it will
                // throw an exception since the COURSE NAME
                // must be unique.
                // we assume exception thrown because data
                // was already added (ignore the exception)
            }

            RowSetMetaData rsmd = rowset.getRowSetMetaData();
            
            for (int x = 0; x < tmd.getColumnCount();x++){
                ColumnDef cdef = rsmd.getColumnDef(x);;
                out.print(cdef.getColumnName().toUpperCase() + " |\t");
            }
            out.println("\n");
            rsmd.setDirection(Consts.BTR_FORWARDS);
            rowset.reset();
            ColumnDef cmd = rsmd.getColumnDef(0);
            rsmd.addFirstTerm(cmd, Consts.BTR_GR_OR_EQ, "JAV 101");

            Row row = rowset.getNext();
            int rowCounter = 0;
            while (row != null && rowCounter < 2){
                for (int x = 0; x < tmd.getColumnCount();x++){
                    out.print(row.getString(x) + "  | ");
                }
                out.println("");
                row = rowset.getNext();
                rowCounter++;
            }
        }
        catch (PsqlException psqlEx){
            out.println("Couldn't connect : " + psqlEx);
        }
        finally{
            if (session != null){
                session.close();
            }
            if (db != null){
                db.close();
            }
        }
    }
}