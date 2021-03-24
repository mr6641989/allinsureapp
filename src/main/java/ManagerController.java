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
import java.util.List;
import java.util.Map;

public class ManagerController {
    public static Route serveManageClaimsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Manager.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Manager manager = (Manager) request.session().attribute(User.UserType.Manager.name());
        model.put("user", manager.getUser());

        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        List<Claim> claims = claimDao.queryBuilder().orderBy("lastupdated", false).query();
        model.put("claims", claims);
        model.put("date", new DateTool());
        connectionSource.close();

        return Views.render(request, model, Paths.Template.MANAGECLAIMS);
    };

    public static Route serveSetClaimAdjusterPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Manager.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Manager manager = (Manager) request.session().attribute(User.UserType.Manager.name());
        model.put("user", manager.getUser());

        int claimid = Integer.parseInt(request.params(":claimid"));
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        Claim claim = claimDao.queryBuilder().where().eq("claimid", claimid).queryForFirst();
        Dao<ClaimsAdjuster,Integer> claimAdjusterDao = DaoManager.createDao(connectionSource, ClaimsAdjuster.class);
        List<ClaimsAdjuster> claimsAdjusters = claimAdjusterDao.queryForAll();
        model.put("claim", claim);
        model.put("claimadjusters", claimsAdjusters);
        model.put("date", new DateTool());
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);
        List<SupportingDocument> supportingDocuments = supportingDocumentDao.queryBuilder().where().eq("claimid", claim.getClaimid()).query();
        model.put("supportingdocuments", supportingDocuments);
        if(claim.getClaimType() == Claim.ClaimType.AccidentalDamageInsurance){
            Dao<AccidentalDamageInsuranceClaim,Integer> accidentalDamageInsuranceClaimDao = DaoManager.createDao(connectionSource, AccidentalDamageInsuranceClaim.class);
            AccidentalDamageInsuranceClaim accidentalDamageInsuranceClaim = accidentalDamageInsuranceClaimDao.queryBuilder().where().eq("claimid", claimid).queryForFirst();
            model.put("claiminfo", accidentalDamageInsuranceClaim);
        } else if(claim.getClaimType() == Claim.ClaimType.HomeInsurance) {
            Dao<HomeInsuranceClaim,Integer> homeInsuranceClaimDao = DaoManager.createDao(connectionSource, HomeInsuranceClaim.class);
            HomeInsuranceClaim homeInsuranceClaim = homeInsuranceClaimDao.queryBuilder().where().eq("claimid", claimid).queryForFirst();
            model.put("claiminfo", homeInsuranceClaim);
        } else if(claim.getClaimType() == Claim.ClaimType.CarInsurance) {
            Dao<CarInsuranceClaim,Integer> carInsuranceClaimDao = DaoManager.createDao(connectionSource, CarInsuranceClaim.class);
            CarInsuranceClaim carInsuranceClaim = carInsuranceClaimDao.queryBuilder().where().eq("claimid", claimid).queryForFirst();
            model.put("claiminfo", carInsuranceClaim);
        }
        connectionSource.close();


        return Views.render(request, model, Paths.Template.SETCLAIMADJUSTER);
    };

    public static Route serveSetClaimAdjusterPagePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Manager.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Manager manager = (Manager) request.session().attribute(User.UserType.Manager.name());
        model.put("user", manager.getUser());
        int claimid = Integer.parseInt(request.params(":claimid"));

        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);

        Claim claim = claimDao.queryBuilder().where().eq("claimid", claimid).queryForFirst();

        int  claimadjusterid = Integer.parseInt(request.queryParams("claimadjusterid"));

        Dao<ClaimsAdjuster,Integer> claimAdjusterDao = DaoManager.createDao(connectionSource, ClaimsAdjuster.class);

        ClaimsAdjuster claimsAdjuster = claimAdjusterDao.queryBuilder().where().eq("claimadjusterid", claimadjusterid).queryForFirst();

        claim.setClaimadjuster(claimsAdjuster);

        claimDao.update(claim);

        return "Claim adjuster set to " + claimsAdjuster.getUser().getFullName() + " for claim id " + claimid;
    };
}
