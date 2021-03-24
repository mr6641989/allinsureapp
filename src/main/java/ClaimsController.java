import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import org.apache.velocity.tools.generic.DateTool;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.utils.IOUtils;
import utils.AeSimpleSHA1;
import utils.Database;
import utils.Paths;
import utils.Views;

import javax.servlet.MultipartConfigElement;
import javax.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class ClaimsController {
    public static Route serveLodgeClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        model.put("user", customer.getUser());

        model.put("hasHomeInsurance", customer.hasHomeInsurance());
        model.put("hasCarInsurance", customer.hasCarInsurance());
        model.put("hasAccidentalDamageInsurance", customer.hasAccidentalDamageInsurance());

        return Views.render(request, model, Paths.Template.LODGECLAIM);
    };

    public static Route serveLodgeCarInsuranceClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        model.put("user", customer.getUser());
        model.put("customer", customer);
        return Views.render(request, model, Paths.Template.LODGECARINSURANCECLAIM);
    };

    public static Route serveLodgeCarInsuranceClaimPagePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());

        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        Insurance insurance = insuranceDao.queryBuilder().where().eq("customerid", customer.getCustomerid()).and().eq("insurancetype", Insurance.InsuranceType.Car).queryForFirst();
        Dao<CarInsurance,Integer> carInsuranceDao = DaoManager.createDao(connectionSource, CarInsurance.class);
        CarInsurance carInsurance = carInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
        System.out.println(carInsurance.getCarinsuranceid());
        String typeofincident = request.queryParams("typeofincident");
        String dateofincident = request.queryParams("dateofincident");
        System.out.println(dateofincident);
        java.util.Date utildateofincident = new SimpleDateFormat("yyyy-mm-dd").parse(dateofincident);

        String timeofincident = request.queryParams("timeofincident");

        String location = request.queryParams("location");
        String files = request.queryParams("files");

        String description = request.queryParams("description");

        Claim claim = new Claim();
        claim.setStatus(Claim.ClaimStatus.Pending);
        claim.setDateofincident(utildateofincident);
        claim.setDescription(description);
        claim.setTimeofincident(timeofincident);
        claim.setDate(new Date());
        claim.setCustomer(customer);
        claim.setClaimType(Claim.ClaimType.CarInsurance);
        claim.setLastupdated(new Date());

        CarInsuranceClaim carInsuranceClaim = new CarInsuranceClaim();
        carInsuranceClaim.setClaim(claim);
        carInsuranceClaim.setLocation(location);
        carInsuranceClaim.setTypeofincident(CarInsuranceClaim.TypeOfIncident.valueOf(typeofincident));
        carInsuranceClaim.setCarinsurance(carInsurance);

        Dao<CarInsuranceClaim,Integer> carInsuranceClaimDao = DaoManager.createDao(connectionSource, CarInsuranceClaim.class);
        carInsuranceClaimDao.create(carInsuranceClaim);
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);

        for(String f : files.split(",")){
            SupportingDocument supportingDocument = supportingDocumentDao.queryBuilder().where().eq("documentid",  Integer.parseInt(f)).queryForFirst();
            supportingDocument.setClaim(claim);
            supportingDocumentDao.update(supportingDocument);
        }
        connectionSource.close();
        model.put("user", customer.getUser());
        model.put("refno", claim.getClaimid());
        return Views.render(request, model, Paths.Template.CARINSURANCECLAIMLODGED);
    };

    public static Route serveLodgeHomeInsuranceClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        model.put("user", customer.getUser());
        model.put("customer", customer);

        return Views.render(request, model, Paths.Template.LODGEHOMEINSURANCECLAIM);
    };

    public static Route serveLodgeHomeInsuranceClaimPagePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());

        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        Insurance insurance = insuranceDao.queryBuilder().where().eq("customerid", customer.getCustomerid()).and().eq("insurancetype", Insurance.InsuranceType.Home).queryForFirst();
        Dao<HomeInsurance,Integer> homeInsuranceDao = DaoManager.createDao(connectionSource, HomeInsurance.class);
        HomeInsurance homeInsurance = homeInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
        System.out.println(homeInsurance.getHomeinsuranceid());

        String typeofincident = request.queryParams("typeofincident");
        String dateofincident = request.queryParams("dateofincident");
        System.out.println(dateofincident);
        java.util.Date utildateofincident = new SimpleDateFormat("yyyy-mm-dd").parse(dateofincident);
        String files = request.queryParams("files");

        String timeofincident = request.queryParams("timeofincident");

        String description = request.queryParams("description");

        Claim claim = new Claim();
        claim.setStatus(Claim.ClaimStatus.Pending);
        claim.setDateofincident(utildateofincident);
        claim.setDescription(description);
        claim.setTimeofincident(timeofincident);
        claim.setDate(new Date());
        claim.setCustomer(customer);
        claim.setClaimType(Claim.ClaimType.HomeInsurance);
        claim.setLastupdated(new Date());

        HomeInsuranceClaim homeInsuranceClaim = new HomeInsuranceClaim();
        homeInsuranceClaim.setClaim(claim);
        homeInsuranceClaim.setTypeofincident(HomeInsuranceClaim.TypeOfIncident.valueOf(typeofincident));
        homeInsuranceClaim.setHomeInsurance(homeInsurance);

        Dao<HomeInsuranceClaim,Integer> homeInsuranceClaimDao = DaoManager.createDao(connectionSource, HomeInsuranceClaim.class);
        homeInsuranceClaimDao.create(homeInsuranceClaim);
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);

        for(String f : files.split(",")){
            SupportingDocument supportingDocument = supportingDocumentDao.queryBuilder().where().eq("documentid",  Integer.parseInt(f)).queryForFirst();
            supportingDocument.setClaim(claim);
            supportingDocumentDao.update(supportingDocument);
        }
        connectionSource.close();
        model.put("user", customer.getUser());
        model.put("refno", claim.getClaimid());
        return Views.render(request, model, Paths.Template.HOMEINSURANCECLAIMLODGED);
    };

    public static Route serveLodgeAccidentDamageClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        model.put("user", customer.getUser());
        model.put("customer", customer);
        return Views.render(request, model, Paths.Template.LODGEACCIDENTALDAMAGEINSURANCECLAIM);
    };

    public static Route serveLodgeAccidentDamageClaimPagePost = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());

        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Insurance, Integer> insuranceDao = DaoManager.createDao(connectionSource, Insurance.class);
        Insurance insurance = insuranceDao.queryBuilder().where().eq("customerid", customer.getCustomerid()).and().eq("insurancetype", Insurance.InsuranceType.AccidentalDamage).queryForFirst();
        Dao<AccidentalDamageInsurance,Integer> accidentalDamageInsuranceDao = DaoManager.createDao(connectionSource, AccidentalDamageInsurance.class);
        AccidentalDamageInsurance accidentalDamageInsurance = accidentalDamageInsuranceDao.queryBuilder().where().eq("insuranceid", insurance.getInsuranceid()).queryForFirst();
        System.out.println(accidentalDamageInsurance.getAccidentaldamageinsuranceid());

        String dateofincident = request.queryParams("dateofincident");
        System.out.println(dateofincident);
        java.util.Date utildateofincident = new SimpleDateFormat("yyyy-mm-dd").parse(dateofincident);

        String timeofincident = request.queryParams("timeofincident");
        String files = request.queryParams("files");

        String description = request.queryParams("description");

        Claim claim = new Claim();
        claim.setStatus(Claim.ClaimStatus.Pending);
        claim.setDateofincident(utildateofincident);
        claim.setDescription(description);
        claim.setTimeofincident(timeofincident);
        claim.setDate(new Date());
        claim.setCustomer(customer);
        claim.setClaimType(Claim.ClaimType.AccidentalDamageInsurance);
        claim.setLastupdated(new Date());

        AccidentalDamageInsuranceClaim accidentalDamageInsuranceClaim = new AccidentalDamageInsuranceClaim();
        accidentalDamageInsuranceClaim.setClaim(claim);
        accidentalDamageInsuranceClaim.setAccidentaldamageinsurance(accidentalDamageInsurance);

        Dao<AccidentalDamageInsuranceClaim,Integer> accidentalDamageInsuranceClaimDao = DaoManager.createDao(connectionSource, AccidentalDamageInsuranceClaim.class);
        accidentalDamageInsuranceClaimDao.create(accidentalDamageInsuranceClaim);
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);

        for(String f : files.split(",")){
            SupportingDocument supportingDocument = supportingDocumentDao.queryBuilder().where().eq("documentid",  Integer.parseInt(f)).queryForFirst();
            supportingDocument.setClaim(claim);
            supportingDocumentDao.update(supportingDocument);
        }

        connectionSource.close();
        model.put("user", customer.getUser());
        model.put("refno", claim.getClaimid());
        return Views.render(request, model, Paths.Template.ACCIDENTALDAMAGEINSURANCECLAIMLODGED);
    };

    public static Route serveViewClaimsPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        List<Claim> claims = claimDao.queryBuilder().orderBy("lastupdated", false).where().eq("customerid", customer.getCustomerid()).query();
        model.put("claims", claims);
        model.put("date", new DateTool());
        connectionSource.close();
        model.put("user", customer.getUser());
        return Views.render(request, model, Paths.Template.VIEWCLAIMS);
    };

    public static Route serveViewClaimPage = (Request request, Response response) -> {
        Map<String, Object> model = new HashMap<>();
        if(request.session().attribute(User.UserType.Customer.name()) == null){
            return Views.render(request, model, Paths.Template.NOTAUTH);
        }
        int claimid = Integer.parseInt(request.params(":claimid"));
        Customer customer = (Customer)request.session().attribute(User.UserType.Customer.name());
        ConnectionSource connectionSource = Database.databaseConn();
        Dao<Claim,Integer> claimDao = DaoManager.createDao(connectionSource, Claim.class);
        Claim claim = claimDao.queryBuilder().where().eq("customerid", customer.getCustomerid()).and().eq("claimid", claimid).queryForFirst();
        model.put("claim", claim);
        model.put("date", new DateTool());
        connectionSource.close();
        model.put("user", customer.getUser());
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
        return Views.render(request, model, Paths.Template.VIEWCLAIM);
    };
/*
    public static Route serveUploadSupportingDocument = (Request request, Response response) -> {
        Part part = request.raw().getPart("supporting_document");
        Dao<SupportingDocument,Integer> supportingDocumentDao = DaoManager.createDao(connectionSource, SupportingDocument.class);
        SupportingDocument supportingDocument = new SupportingDocument();
        supportingDocument.setClaim(claim);
        supportingDocument.setDocumentname(part.getName());
        supportingDocument.setFileBytes(IOUtils.toByteArray(part.getInputStream()));
        supportingDocumentDao.create(supportingDocument);
    }
    */
}
