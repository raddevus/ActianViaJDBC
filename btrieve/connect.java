import pervasive.database.*;


import static java.lang.System.out;

public class connect{
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
            String [] names = db.getTableNames();
            for (String n : names){
                out.println(n);
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