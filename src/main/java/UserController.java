import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import utils.AeSimpleSHA1;
import utils.Database;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.List;

public class UserController {
    public static User authenticate(String username, String password) throws ClassNotFoundException, SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<User,Integer> userDao = DaoManager.createDao(connectionSource, User.class);
        String passwordHash = AeSimpleSHA1.SHA1(password);
        List<User> users = userDao.queryBuilder().where().eq("username", username).and().eq("password", passwordHash).query();
        connectionSource.close();
        if(users.size() > 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
