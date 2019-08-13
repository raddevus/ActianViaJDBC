import pervasive.database.*;

import static java.lang.System.out;

public class getTableInfo{
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
            RowSetMetaData rsmd = rowset.getRowSetMetaData();
            
            for (int x = 0; x < tmd.getColumnCount();x++){
                ColumnDef cdef = rsmd.getColumnDef(x);;
                out.print(cdef.getColumnName().toUpperCase() + " |\t");
            }
            out.println("\n");
            rsmd.setDirection(Consts.BTR_FORWARDS);
            rowset.reset();
            Row row = rowset.getNext();
            while (row != null){
                for (int x = 0; x < tmd.getColumnCount();x++){
                    out.print(row.getString(x) + "  | ");
                }
                out.println("");
                row = rowset.getNext();
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