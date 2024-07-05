package com.se.api;

import kong.unirest.Unirest;
import com.se.config.Constants;

public class ApiHelper {

    private static final String AUTHENTICATION_URL = Constants.API_ROOT + "/v1/authentication/token";
    private static final String AUTHORIZATION_URL = Constants.API_ROOT + "/v1/authorization/token";
    private static final String TEST_DATA_CONTROLLER_URL = Constants.API_ROOT + "/integration/simulator";

    private static boolean _isDatabaseSetupPerformed;
    public static void ensureDatabaseSetupPerformed() {
        if (_isDatabaseSetupPerformed) {
            return;
        }

        Unirest
                .post(TEST_DATA_CONTROLLER_URL + "/setupdatabase")
                .asEmpty();
        _isDatabaseSetupPerformed = true;
    }
}
