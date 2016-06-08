package home.prijimackytester;

import org.hsqldb.Server;

public class SpustacDatabazy {
    
    public static void  execute(){
        Server server =new Server();
        server.setDatabaseName(0,"prijimackydb");
        server.setDatabasePath(0 ,"db/prijimackydb");
        server.setPort(12345);
        server.start();
          
    }
    public static void main(String[] args) {
        Server server =new Server();
        server.setDatabaseName(0,"prijimackydb");
        server.setDatabasePath(0 ,"db/prijimackydb");
        server.setPort(12345);
        server.start();
    }
}
