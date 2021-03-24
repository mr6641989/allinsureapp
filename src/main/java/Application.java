import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.apache.log4j.BasicConfigurator;
import spark.Spark;
import utils.Database;
import utils.Routes;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.SQLException;

import static spark.Spark.*;

public class Application {
    public static void createTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.createTable(connectionSource, User.class);
        TableUtils.createTable(connectionSource, Customer.class);
        TableUtils.createTable(connectionSource, AccidentalDamageInsurance.class);
        TableUtils.createTable(connectionSource, AccidentalDamageInsuranceClaim.class);
        TableUtils.createTable(connectionSource, CarInsurance.class);
        TableUtils.createTable(connectionSource, CarInsuranceClaim.class);
        TableUtils.createTable(connectionSource, Claim.class);
        TableUtils.createTable(connectionSource, ClaimsAdjuster.class);
        TableUtils.createTable(connectionSource, Insurance.class);
        TableUtils.createTable(connectionSource, HomeInsurance.class);
        TableUtils.createTable(connectionSource, HomeInsuranceClaim.class);
        TableUtils.createTable(connectionSource, Manager.class);
        TableUtils.createTable(connectionSource, SupportingDocument.class);
    }
    public static void clearTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.clearTable(connectionSource, User.class);
        TableUtils.clearTable(connectionSource, Customer.class);
        TableUtils.clearTable(connectionSource, AccidentalDamageInsurance.class);
        TableUtils.clearTable(connectionSource, AccidentalDamageInsuranceClaim.class);
        TableUtils.clearTable(connectionSource, CarInsurance.class);
        TableUtils.clearTable(connectionSource, CarInsuranceClaim.class);
        TableUtils.clearTable(connectionSource, Claim.class);
        TableUtils.clearTable(connectionSource, ClaimsAdjuster.class);
        TableUtils.clearTable(connectionSource, Insurance.class);
        TableUtils.clearTable(connectionSource, HomeInsurance.class);
        TableUtils.clearTable(connectionSource, HomeInsuranceClaim.class);
        TableUtils.clearTable(connectionSource, Manager.class);
        TableUtils.clearTable(connectionSource, SupportingDocument.class);
    }
    public static void dropTables(ConnectionSource connectionSource) throws SQLException {
        TableUtils.dropTable(connectionSource, User.class, true);
        TableUtils.dropTable(connectionSource, Customer.class, true);
        TableUtils.dropTable(connectionSource, AccidentalDamageInsurance.class, true);
        TableUtils.dropTable(connectionSource, AccidentalDamageInsuranceClaim.class, true);
        TableUtils.dropTable(connectionSource, CarInsurance.class, true);
        TableUtils.dropTable(connectionSource, CarInsuranceClaim.class, true);
        TableUtils.dropTable(connectionSource, Claim.class, true);
        TableUtils.dropTable(connectionSource, ClaimsAdjuster.class, true);
        TableUtils.dropTable(connectionSource, Insurance.class, true);
        TableUtils.dropTable(connectionSource, HomeInsurance.class, true);
        TableUtils.dropTable(connectionSource, HomeInsuranceClaim.class, true);
        TableUtils.dropTable(connectionSource, Manager.class, true);
        TableUtils.dropTable(connectionSource, SupportingDocument.class, true);
    }

    public static void loadTables(ConnectionSource connectionSource) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        Dao<User,Integer> userDao = DaoManager.createDao(connectionSource, User.class);

        User user1 = new User();
        user1.setFname("Bob");
        user1.setLname("Jones");
        user1.setUsername("customer1");
        user1.setPassword("customer1");
        user1.setIsadmin(false);

        Dao<Customer,Integer> customerDao = DaoManager.createDao(connectionSource, Customer.class);
        Customer customer1 = new Customer();
        customer1.setAddress("test");
        customer1.setPhone("1234837438");
        customer1.setEmail("bob@email.com");
        customer1.setDatejoined(Date.valueOf("2009-12-12"));
        customer1.setDateofbirth(Date.valueOf("1988-10-10"));
        customer1.setUser(user1);
        customerDao.create(customer1);

        User user2 = new User();
        user2.setFname("Billy");
        user2.setLname("Jones");
        user2.setUsername("manager1");
        user2.setPassword("manager1");
        user2.setIsadmin(false);
        user2.setUsertype(User.UserType.Manager);

        Dao<Manager,Integer> managerDao = DaoManager.createDao(connectionSource, Manager.class);
        Manager manager1 = new Manager();
        manager1.setUser(user2);
        managerDao.create(manager1);

        User user3 = new User();
        user3.setFname("Max");
        user3.setLname("Jones");
        user3.setUsername("claimadjuster1");
        user3.setPassword("claimadjuster1");
        user3.setIsadmin(false);
        user3.setUsertype(User.UserType.ClaimAdjuster);

        Dao<ClaimsAdjuster,Integer> claimAdjusterDao = DaoManager.createDao(connectionSource, ClaimsAdjuster.class);
        ClaimsAdjuster claimsAdjuster1 = new ClaimsAdjuster();
        claimsAdjuster1.setUser(user3);
        claimAdjusterDao.create(claimsAdjuster1);

        User user4 = new User();
        user4.setFname("Simon");
        user4.setLname("Jones");
        user4.setUsername("customer2");
        user4.setPassword("customer2");
        user4.setIsadmin(false);

        Customer customer2 = new Customer();
        customer2.setAddress("test");
        customer2.setPhone("1234337438");
        customer2.setEmail("simon@email.com");
        customer2.setDatejoined(Date.valueOf("2009-12-12"));
        customer2.setDateofbirth(Date.valueOf("1978-06-06"));
        customer2.setUser(user4);
        customerDao.create(customer2);

        User user5 = new User();
        user5.setFname("Billy");
        user5.setLname("Jones");
        user5.setUsername("manager2");
        user5.setPassword("manager2");
        user5.setIsadmin(false);
        user5.setUsertype(User.UserType.Manager);

        Manager manager2 = new Manager();
        manager2.setUser(user5);
        managerDao.create(manager2);

        User user6 = new User();
        user6.setFname("Bobby");
        user6.setLname("Jones");
        user6.setUsername("claimadjuster2");
        user6.setPassword("claimadjuster2");
        user6.setIsadmin(false);
        user6.setUsertype(User.UserType.ClaimAdjuster);

        ClaimsAdjuster claimsAdjuster2 = new ClaimsAdjuster();
        claimsAdjuster2.setUser(user6);
        claimAdjusterDao.create(claimsAdjuster2);

        Dao<CarInsurance,Integer> carInsuranceDao = DaoManager.createDao(connectionSource, CarInsurance.class);
        Insurance insurance1 = new Insurance();
        insurance1.setCoverageamount(30000);
        insurance1.setEnddate(Date.valueOf("2019-12-11"));
        insurance1.setExcess(500);
        insurance1.setPremium(123);
        insurance1.setStartdate(Date.valueOf("2018-12-11"));
        insurance1.setInsurancetype(Insurance.InsuranceType.Car);
        insurance1.setCustomer(customer1);

        CarInsurance carInsurance = new CarInsurance();
        carInsurance.setCarvalue(20000);
        carInsurance.setDriverage(30);
        carInsurance.setInsurance(insurance1);
        carInsurance.setMake("Holden");
        carInsurance.setModel("Commodore");
        carInsurance.setRegistration("HDHD-2323");
        carInsurance.setYear(2009);
        carInsuranceDao.create(carInsurance);

        Dao<HomeInsurance,Integer> homeInsuranceDao = DaoManager.createDao(connectionSource, HomeInsurance.class);
        Insurance insurance2 = new Insurance();
        insurance2.setCoverageamount(450000);
        insurance2.setEnddate(Date.valueOf("2020-05-05"));
        insurance2.setExcess(1000);
        insurance2.setPremium(230);
        insurance2.setStartdate(Date.valueOf("2019-05-05"));
        insurance2.setInsurancetype(Insurance.InsuranceType.Home);
        insurance2.setCustomer(customer1);

        HomeInsurance homeInsurance = new HomeInsurance();
        homeInsurance.setAddress("111 murray road");
        homeInsurance.setHomevalue(500000);
        homeInsurance.setInsurance(insurance2);

        homeInsuranceDao.create(homeInsurance);

        Dao<AccidentalDamageInsurance,Integer> accidentalDamageInsuranceDao = DaoManager.createDao(connectionSource, AccidentalDamageInsurance.class);
        Insurance insurance3 = new Insurance();
        insurance3.setCoverageamount(2000);
        insurance3.setEnddate(Date.valueOf("2020-08-11"));
        insurance3.setExcess(250);
        insurance3.setPremium(20);
        insurance3.setStartdate(Date.valueOf("2019-08-11"));
        insurance3.setInsurancetype(Insurance.InsuranceType.AccidentalDamage);
        insurance3.setCustomer(customer1);

        AccidentalDamageInsurance accidentalDamageInsurance = new AccidentalDamageInsurance();
        accidentalDamageInsurance.setNameofitem("Laptop");
        accidentalDamageInsurance.setSerialno("13232-223323");
        accidentalDamageInsurance.setValue(1000);
        accidentalDamageInsurance.setTypeofitem(AccidentalDamageInsurance.TypeOfItem.Electrical);
        accidentalDamageInsurance.setInsurance(insurance3);
        accidentalDamageInsuranceDao.create(accidentalDamageInsurance);

        Insurance insurance4 = new Insurance();
        insurance4.setCoverageamount(30000);
        insurance4.setEnddate(Date.valueOf("2019-12-11"));
        insurance4.setExcess(500);
        insurance4.setPremium(123);
        insurance4.setStartdate(Date.valueOf("2018-12-11"));
        insurance4.setInsurancetype(Insurance.InsuranceType.Car);
        insurance4.setCustomer(customer2);

        CarInsurance carInsurance2 = new CarInsurance();
        carInsurance2.setCarvalue(20000);
        carInsurance2.setDriverage(30);
        carInsurance2.setInsurance(insurance4);
        carInsurance2.setMake("Honda");
        carInsurance2.setModel("Jazz");
        carInsurance2.setRegistration("ABCD-1234");
        carInsurance2.setYear(2009);
        carInsuranceDao.create(carInsurance2);

        Insurance insurance5 = new Insurance();
        insurance5.setCoverageamount(450000);
        insurance5.setEnddate(Date.valueOf("2020-05-05"));
        insurance5.setExcess(1000);
        insurance5.setPremium(230);
        insurance5.setStartdate(Date.valueOf("2019-05-05"));
        insurance5.setInsurancetype(Insurance.InsuranceType.Home);
        insurance5.setCustomer(customer2);

        HomeInsurance homeInsurance2 = new HomeInsurance();
        homeInsurance2.setAddress("111 murray road");
        homeInsurance2.setHomevalue(500000);
        homeInsurance2.setInsurance(insurance5);

        homeInsuranceDao.create(homeInsurance2);

        Insurance insurance6 = new Insurance();
        insurance6.setCoverageamount(3000);
        insurance6.setEnddate(Date.valueOf("2020-08-11"));
        insurance6.setExcess(250);
        insurance6.setPremium(20);
        insurance6.setStartdate(Date.valueOf("2019-08-11"));
        insurance6.setInsurancetype(Insurance.InsuranceType.AccidentalDamage);
        insurance6.setCustomer(customer2);

        AccidentalDamageInsurance accidentalDamageInsurance2 = new AccidentalDamageInsurance();
        accidentalDamageInsurance2.setNameofitem("Lounge");
        accidentalDamageInsurance2.setSerialno("13232-223323");
        accidentalDamageInsurance2.setValue(3000);
        accidentalDamageInsurance2.setTypeofitem(AccidentalDamageInsurance.TypeOfItem.Furniture);
        accidentalDamageInsurance2.setInsurance(insurance6);
        accidentalDamageInsuranceDao.create(accidentalDamageInsurance2);
    }

    public static void main(String[] args) throws SQLException, UnsupportedEncodingException, NoSuchAlgorithmException {
        BasicConfigurator.configure();

        //initialise database
        ConnectionSource connectionSource = Database.databaseConn();
        //dropTables(connectionSource);
        //createTables(connectionSource);
        //clearTables(connectionSource);
        //loadTables(connectionSource);
        connectionSource.close();
        Spark.staticFiles.location("/public");
        port(getHerokuAssignedPort());
        get(Routes.INDEX, IndexController.serverIndexPage);
        post(Routes.LOGIN, LoginController.serverLoginPage);
        get(Routes.LODGECLAIM, ClaimsController.serveLodgeClaimPage);
        get(Routes.LODGEACCIDENTALDAMAGEINSURANCECLAIM, ClaimsController.serveLodgeAccidentDamageClaimPage);
        post(Routes.LODGEACCIDENTALDAMAGEINSURANCECLAIM, ClaimsController.serveLodgeAccidentDamageClaimPagePost);
        get(Routes.LODGECARINSURANCECLAIM, ClaimsController.serveLodgeCarInsuranceClaimPage);
        post(Routes.LODGECARINSURANCECLAIM, ClaimsController.serveLodgeCarInsuranceClaimPagePost);
        get(Routes.LODGEHOMEINSURANCECLAIM, ClaimsController.serveLodgeHomeInsuranceClaimPage);
        post(Routes.LODGEHOMEINSURANCECLAIM, ClaimsController.serveLodgeHomeInsuranceClaimPagePost);
        get(Routes.VIEWCLAIMS, ClaimsController.serveViewClaimsPage);
        get(Routes.VIEWCLAIM, ClaimsController.serveViewClaimPage);
        get(Routes.ADJUSTCLAIMS, ClaimsAdjusterController.serveAdjustClaimsPage);
        get(Routes.ADJUSTCLAIM, ClaimsAdjusterController.serveAdjustClaimPage);
        post(Routes.ADJUSTCLAIM, ClaimsAdjusterController.serveAdjustClaimPagePost);
        get(Routes.MANAGECLAIMS, ManagerController.serveManageClaimsPage);
        get(Routes.SETCLAIMDADJUSTER, ManagerController.serveSetClaimAdjusterPage);
        post(Routes.SETCLAIMDADJUSTER, ManagerController.serveSetClaimAdjusterPagePost);
        get(Routes.VIEWPOLICY, PolicyController.serveViewPolicyPage);
        get(Routes.VIEWCUSTOMER, CustomerDetailsController.serveViewCustomerDetailsPage);
        get(Routes.LOGOUT, LoginController.serveLogoutPage);
        post(Routes.UPLOADFILE, FileController.serverFileUpload);
        get(Routes.DOWNLOADFILE, FileController.serverFileDownload);
        get("*", IndexController.serveNotFoundPage);
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567;
    }

}
