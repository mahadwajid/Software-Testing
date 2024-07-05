package com.se.rolesbase;

import com.se.LoginAsUserTestBase;
import com.se.config.Constants;

public class StudentLoginBase extends LoginAsUserTestBase {

    public StudentLoginBase() {
        this(false);
    }

    public StudentLoginBase(boolean tearDownBrowserAfterEachTest) {
        super(Constants.STUDENT_LOGIN_DETAILS, tearDownBrowserAfterEachTest);
    }

}
