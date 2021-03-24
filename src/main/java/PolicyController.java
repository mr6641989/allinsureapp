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

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PolicyController {
    public static Route serveViewPolicyPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Manager.name()) == null && request.session().attribute(User.UserType.ClaimAdjuster.name()) == null && request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance,Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        String claimtype = request.params(":claimtype");
        Insurance.InsuranceType insuranceType = Insurance.InsuranceType.valueOf(claimtype.replace("Insurance", ""));
        int customerid = Integer.parseInt(request.params(":customerid"));
        Insurance insurance = insuranceDao.queryBuilder().where().eq("insurancetype", insuranceType).and().eq("customerid", customerid).queryForFirst();
        if(insurance.getInsurancetype() == Insurance.InsuranceType.Car){
            Dao<CarInsurance,Integer> carInsuranceDao = DaoManager.createDao(connectionSource, CarInsurance.class);
            CarInsurance carInsurance = carInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
            model.put("insurancedetails", carInsurance);
        } else if(insurance.getInsurancetype() == Insurance.InsuranceType.Home){
            Dao<HomeInsurance,Integer> homeInsuranceDao = DaoManager.createDao(connectionSource, HomeInsurance.class);
            HomeInsurance homeInsurance = homeInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
            model.put("insurancedetails", homeInsurance);
        } else if(insurance.getInsurancetype() == Insurance.InsuranceType.AccidentalDamage) {
            Dao<AccidentalDamageInsurance,Integer> accidentalDamageInsuranceDao = DaoManager.createDao(connectionSource, AccidentalDamageInsurance.class);
            AccidentalDamageInsurance accidentalDamageInsurance = accidentalDamageInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
            model.put("insurancedetails", accidentalDamageInsurance);
        }

        model.put("insurance", insurance);
        model.put("date", new DateTool());
        return Views.render(request, model, Paths.Template.VIEWPOLICY);
    };
}
