import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.commons.lang.ObjectUtils;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginController {
    public static Route serverLoginPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        User user = UserController.authenticate(request.queryParams("username"), request.queryParams("password"));
        if (user == null) {
            return Views.render(request, model, Paths.Template.LOGINFAILED);
        }
        request.session(true);
        ConnectionSource connectionSource = Database.databaseConn();
        if(user.getUsertype().equals(User.UserType.Customer)) {
            Dao<Customer, Integer> customerDao = DaoManager.createDao(connectionSource, Customer.class);
            Customer customer = customerDao.queryBuilder().where().eq("userid", user.getUserid()).query().get(0);
            request.session().attribute(User.UserType.Customer.name(), customer);
            response.redirect(Routes.LODGECLAIM);
        } else if(user.getUsertype().equals(User.UserType.ClaimAdjuster)){
            Dao<ClaimsAdjuster, Integer> claimAdjusterDao = DaoManager.createDao(connectionSource, ClaimsAdjuster.class);
            ClaimsAdjuster claimsAdjuster = claimAdjusterDao.queryBuilder().where().eq("userid", user.getUserid()).query().get(0);
            request.session().attribute(User.UserType.ClaimAdjuster.name(), claimsAdjuster);
            response.redirect(Routes.ADJUSTCLAIMS);
        } else if(user.getUsertype().equals(User.UserType.Manager)){
            Dao<Manager, Integer> managerDao = DaoManager.createDao(connectionSource, Manager.class);
            Manager manager = managerDao.queryBuilder().where().eq("userid", user.getUserid()).query().get(0);
            request.session().attribute(User.UserType.Manager.name(), manager);
            response.redirect(Routes.MANAGECLAIMS);
        }
        connectionSource.close();
        return Views.render(request, model, Paths.Template.LOGINFAILED);
    };

    public static Route serveLogoutPage = (Request request, Response response) -> {
        request.session().removeAttribute(User.UserType.Customer.name());
        request.session().removeAttribute(User.UserType.ClaimAdjuster.name());
        request.session().removeAttribute(User.UserType.Manager.name());
        Map<String, Object> model = new HashMap<>();
        response.redirect(Routes.INDEX);
        return Views.render(request, model, Paths.Template.INDEX);
    };
}
