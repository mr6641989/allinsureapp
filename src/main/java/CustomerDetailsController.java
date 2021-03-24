import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.velocity.tools.generic.DateTool;
import spark.Request;
import spark.Response;
import spark.Route;
import utils.Database;
import utils.Paths;
import utils.Views;

import java.util.HashMap;
import java.util.Map;

public class CustomerDetailsController {
    public static Route serveViewCustomerDetailsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Manager.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Manager manager = (Manager) request.session().attribute(User.UserType.Manager.name());
        model.put("user", manager.getUser());
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Customer,Integer> customerDao = DaoManager.createDao(connectionSource, Customer.class);
        int customerid = Integer.parseInt(request.params(":customerid"));
        Customer customer = customerDao.queryBuilder().where().eq("customerid", customerid).queryForFirst();
        model.put("customer", customer);
        model.put("date", new DateTool());
        return Views.render(request, model, Paths.Template.VIEWCUSTOMERDETAILS);
    };
}
