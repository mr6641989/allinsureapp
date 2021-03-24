package utils;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

public class Database {
   public final static String databaseUrl = "jdbc:sqlite:C:\\Users\\matth\\IdeaProjects\\allinsureapp\\allinsure.db";
   //public final static String databaseUrl = "jdbc:mysql://us-cdbr-iron-east-05.cleardb.net/heroku_e4ec4ac28a51ba7?reconnect=true";

    public static ConnectionSource databaseConn() throws SQLException {
        // create a connection source to our database
        ConnectionSource connectionSource =
                new JdbcConnectionSource(databaseUrl);
                //new JdbcConnectionSource(databaseUrl, "b3941767734297", "2ea901a2");
        return connectionSource;
    }
}
