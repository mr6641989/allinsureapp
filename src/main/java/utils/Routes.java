package utils;

public class Routes {
        public static final String INDEX = "/";
        public static final String LOGIN = "/login";
        public static final String LODGECLAIM = "/lodge_claim";
        public static final String LODGEACCIDENTALDAMAGEINSURANCECLAIM = "/lodge_accidental_damage_claim";
        public static final String LODGECARINSURANCECLAIM = "/lodge_car_insurance_claim";
        public static final String LODGEHOMEINSURANCECLAIM = "/lodge_home_insurance_claim";
        public static final String VIEWCLAIM = "/view_claim/:claimid";
        public static final String VIEWCLAIMS = "/view_claims";
        public static final String ADJUSTCLAIMS = "/adjust_claims";
        public static final String ADJUSTCLAIM = "/adjust_claim/:claimid";
        public static final String MANAGECLAIMS = "/manage_claims";
        public static final String SETCLAIMDADJUSTER = "/set_claim_adjuster/:claimid";
        public static final String VIEWPOLICY = "/view_policy/:claimtype/:customerid";
        public static final String VIEWCUSTOMER = "/view_customer/:customerid";
        public static final String UPLOADFILE = "/upload_file/:customerid";
        public static final String DOWNLOADFILE = "/download_file/:fileid";
        public static final String LOGOUT = "/logout";
}
