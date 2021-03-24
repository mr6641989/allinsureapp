import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.Database;
import utils.Paths;
import utils.Routes;
import utils.Views;

import java.util.HashMap;
import java.util.Map;

public class IndexController {
    public static Route serverIndexPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) != null) {
            response.redirect(Routes.LODGECLAIM);
        } else if(request.session().attribute(User.UserType.ClaimAdjuster.name()) != null){
            response.redirect(Routes.ADJUSTCLAIMS);
        } else if(request.session().attribute(User.UserType.Manager.name()) != null){
            response.redirect(Routes.MANAGECLAIMS);
        }
        return Views.render(request, model, Paths.Template.INDEX);
    };

    public static Route serveNotFoundPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();

        return Views.render(request, model, Paths.Template.NOTFOUND);
    };
}
