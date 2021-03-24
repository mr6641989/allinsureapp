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

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClaimsAdjusterController {
    public static Route serveAdjustClaimsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.ClaimAdjuster.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        ClaimsAdjuster claimsAdjuster = (ClaimsAdjuster) request.session().attribute(User.UserType.ClaimAdjuster.name());
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        List<Claim> claims = claimDao.queryBuilder().orderBy("lastupdated", false).where().eq("claimadjusterid", claimsAdjuster.getClaimadjusterid()).query();
        model.put("claims", claims);
        model.put("user", claimsAdjuster.getUser());
        model.put("date", new DateTool());
        return Views.render(request, model, Paths.Template.ADJUSTCLAIMS);
    };

    public static Route serveAdjustClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.ClaimAdjuster.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        int claimid = Integer.parseInt(request.params(":claimid"));
        ClaimsAdjuster claimsAdjuster = (ClaimsAdjuster) request.session().attribute(User.UserType.ClaimAdjuster.name());
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        Claim claim = claimDao.queryBuilder().where().eq("claimadjusterid", claimsAdjuster.getClaimadjusterid()).and().eq("claimid", claimid).queryForFirst();
        model.put("claim", claim);
        model.put("user", claimsAdjuster.getUser());
        model.put("date", new DateTool());
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);
        List<SupportingDocument> supportingDocuments = supportingDocumentDao.queryBuilder().where().eq("claimid", claim.getClaimid()).query();
        model.put("supportingdocuments", supportingDocuments);
        model.put("statuses", Claim.ClaimStatus.values());
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
        return Views.render(request, model, Paths.Template.ADJUSTCLAIM);
    };

    public static Route serveAdjustClaimPagePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.ClaimAdjuster.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        ClaimsAdjuster claimsAdjuster = (ClaimsAdjuster) request.session().attribute(User.UserType.ClaimAdjuster.name());

        int claimid = Integer.parseInt(request.params(":claimid"));
        ConnectionSource connectionSource = Database.databaseConn();
        String notes = request.queryParams("notes");
        Claim.ClaimStatus status = Claim.ClaimStatus.valueOf(request.queryParams("status"));

        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        Claim claim = claimDao.queryBuilder().where().eq("claimadjusterid", claimsAdjuster.getClaimadjusterid()).and().eq("claimid", claimid).queryForFirst();
        claim.setNotes(notes);
        claim.setStatus(status);
        claim.setLastupdated(new Date());

        claimDao.update(claim);

        connectionSource.close();
        model.put("user", claimsAdjuster.getUser());
        model.put("refno", claim.getClaimid());
        return Views.render(request, model, Paths.Template.CLAIMUPDATED);
    };
}
