package com.se.config;

import org.openqa.selenium.By;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Constants {
    public static final String API_ROOT = ConfigHelper.getInstance().getApiRoot();
    public  static class actual_txt{
        public static By BY_Element_text=By.xpath("//*[@id=\"body\"]/div[2]/div[1]/h2");

    }
    public static class gmail {
        public static By BY_email= By.id("identifierId");
        public static String useremail = "SIGNUPAUTOMATION1@gmail.com";
        public static By BY_unext_button= By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d");
        public static By BY_password= By.xpath("//input[@type='password']");
        public static String userpassword = "Subexpert1@signup";
        public static By BY_pnext_button= By.cssSelector(".VfPpkd-LgbsSe-OWXEXe-k8QpJ > .VfPpkd-vQzf8d");
        public static By BY_mailmessage= By.cssSelector("#\\3A 2a .yP");
        public static By BY_verfication_link= By.xpath("//a[contains(text(),'Activate My Account')]");

    }


    public static final LoginParameters STUDENT_LOGIN_DETAILS = new LoginParameters.Builder()
            .role(Role.STUDENT)
            .getUrl(ConfigHelper.getInstance().getSubjectExpertUrl())
            .password(ConfigHelper.getInstance().getStudentPassword())
            .username(ConfigHelper.getInstance().getStudentUsername())
            .build();







    public static class Signup {

        public static By BY_Signup_button= By.xpath("//*[@id=\"header\"]/div[2]/div//div[2]/div[1]/span[2]");
        public static By BY_firstname= By.id("FirstName");
        public static By BY_lastname= By.id("LastName");
        public static By BY_username= By.id("UserName");
        public static By BY_password= By.id("Password");
        public static By BY_confirmpassword= By.id("ConfirmPassword");
        public static By BY_email= By.id("Email");
        public static By By_submit_Button= By.xpath("//span[contains(@class,'hRegister')]");
        public static By By_Check_Button= By.xpath("//*[@id=\"recaptcha-anchor\"]/div[1]");
    }


    public static class Tags {
        public static final By BY_BODY = By.tagName("body");
        public static final By BY_OPTION = By.tagName("option");
        public static final By BY_NG_OPTION = By.xpath("//div[contains(@class,'ng-option')]");
    }

    public static class Login {

        public static final By BY_USERNAME_FIELD = By.id("UserName");
        public static final By BY_PASSWORD_FIELD = By.id("Password");
        public static final By BY_LOGIN_BUTTON = By.xpath("//*[@id='body']/div/div/form/div[2]/input[2]");
        public static By BY_login= By.xpath("//*[@id='header']/div[2]/div/div/div[2]/div[1]/span[1]");
    }


    public static class DueExame {
        public static  final By BY_Skipbutton = By.xpath("/html/body/div[4]/div[3]/div[1]/div/p/input[2]");
        public static final By BY_HomepageText = By.xpath("/html/body/div[4]/div[3]/div[1]/div/header/h2[contains(text(),'Welcome! Mahad Wajid')]");
        public static final By BY_subjectButton = By.xpath("/html/body/div[4]/div[3]/div[1]/div/div[3]/div/div[2]/div/div[2]/div/div/div[2]/div[1]/div[1]");
        public static final By BY_subjectSection = By.xpath("/html/body/div[4]/div[3]/div[7]/div/div[1]/div/h4/span[contains(text(),'Course Objectives')]");
        public static final By BY_lectureButton = By.xpath("/html/body/div[4]/div[3]/div[5]/div/a[3]");
        public static final By BY_lectureSection = By.xpath("//*[@id=Introduction to OOSE][contains(text(),'Introduction to OOSE')]");

        public static final By BY_lectureSections = By.xpath("//*[@id=\"perfdiv\"]/div/div[4]/div/a[2]");

        public static final By BY_lectureTaskButton = By.xpath("/html/body/div[4]/div[3]/div[6]/div/div[2]/div[2]/div[2]/div[2]/div[2]/div[1]/div/div[4]/div/a[1]");
        public static final By BY_lectureTaskContent = By.xpath("/html/body/div[4]/div[3]/div[6]/div/div[2]/div[2]/div[2]/div[2]/div[1]/div[2]/div/div/div/div[4]/a[1]/div[contains(text(),'Lecture/Lab Tasks')]");

        public static final By BY_assignmentButton = By.xpath("/html/body/div[4]/div[3]/div[5]/div/a[5]");
        public static final By BY_assignmentSummary = By.xpath("//div[@class='assignment-summary']");

    }

    public static class Common {
        public static By generateByForIdPattern(String idPattern) {
            return By.xpath("//*[contains(@id,'" + idPattern + "')]");
        }
    }

    public class Text {
        public static final String OOSE_SUBJECT = "Object Oriented Software Engineering";
    }

    public static class LoginParameters {
        private final String _Url;
        private final Constants.Role _role;
        private Boolean _hasMoreThanOneRole = true;
        private final String _password;
        private final String _username;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            var that = (LoginParameters) o;
            return _role == that._role
                    && Objects.equals(_password, that._password)
                    && Objects.equals(_username, that._username);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_role, _password, _username);
        }

        public static class Builder {
            private String _Url;
            private Constants.Role _role;
            private String _password;
            private String _username;

            public Builder() {
            }

            public Builder(LoginParameters loginParameters) {
                _Url = loginParameters.getUrl();
                _role = loginParameters.getRole();
                _password = loginParameters.getPassword();
                _username = loginParameters.getUsername();
            }

            public LoginParameters build() {
                return new LoginParameters(this);
            }

            public Builder role(Constants.Role role) {
                _role = role;
                return this;
            }

            public Builder getUrl(String intelliTrackUrl) {
                _Url = intelliTrackUrl;
                return this;
            }

            public Builder password(String password) {
                _password = password;
                return this;
            }

            public Builder username(String username) {
                _username = username;
                return this;
            }
        }

        private LoginParameters(Builder builder) {
            _role = builder._role;
            _Url = builder._Url;
            _password = builder._password;
            _username = builder._username;
        }


        public Boolean getHasMoreThanOneRole() {
            return _hasMoreThanOneRole;
        }

        public String getApiRootUrl() {
            return ConfigHelper.getInstance().getApiRoot();
        }

        public Constants.Role getRole() {
            return _role;
        }
        public String getUrl() {
            return _Url;
        }

        public String getUsername() {
            return _username;
        }

        public String getPassword() {
            return _password;
        }


    }



    public enum Role {
        STUDENT(1, "Student", "dashboard"),
        TEACHER(2, "Teacher", "dashboard"),
        ADMIN(3, "Admin", "dashboard");

        private final int _roleId;
        private final String _roleName;
        private final String _landingPageUrlPart;

        Role(
                int roleId,
                String roleName,
                String landPage
        ) {
            _roleId = roleId;
            _roleName = roleName;
            _landingPageUrlPart = landPage;
        }

        public int getRoleId() {
            return _roleId;
        }

        public By getRoleBy() {
            return By.id("role-" + _roleId);
        }

        public String getRoleName() {
            return _roleName;
        }

        public String getLandingPageUrlPart() {
            return _landingPageUrlPart;
        }

        public static Optional<Role> getRoleById(int roleId) {
            return Arrays.stream(Role.values()).filter(role -> role.getRoleId() == roleId).findFirst();
        }

    }




    public static class Swagger {
        public static class Offender {
            public static final By BY_ADD_CASE_API = By.id("Offender_Offender_AddCase");
            public static final By BY_EXPAND_OFFENDER_APIS = By.xpath("//li[@id=\"resource_Offender\"]/div/ul/li[3]/a");

            public static By getByRequiredId(String apiName) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender"
                        + apiName
                        + "_content\"]/form/table/tbody/tr[1]/td[2]/input");
            }

            public static By getByFilterId(
                    String apiName
            ) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender"
                        + apiName
                        + "_content\"]/form/table/tbody/tr[3]/td[2]/input");
            }

            public static By getByTryItOut(String apiName) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender" + apiName + "_content\"]/form/div[2]/input");
            }

            public static By getByResponseCode(String apiName) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender" + apiName + "_content\"]/div[3]/div[4]/pre");
            }

            public static By getByResponseBody(String apiName) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender"
                        + apiName
                        + "_content\"]/div[3]/div[3]/pre/code");
            }

            public static By getByParameterId(
                    String apiName,
                    int index
            ) {
                return By.xpath("//*[@id=\"Offender_Offender_GetOffender"
                        + apiName
                        + "_content\"]/form/table/tbody/tr["
                        + index
                        + "]/td[2]/textarea");
            }
        }

        public static class ResponseCode {
            public static final String OK = "200";
        }

        public static class Login {
            public static final By BY_SWAGGER_LOGIN_BUTTON = By.id("login-button");
            public static final By BY_ENTERPRISE_URL = By.id("auto-login-enterprise-url");
            public static final By BY_ROLE_ID = By.id("auto-login-role-id");
            public static final By BY_USER_NAME = By.id("auto-login-username");
            public static final By BY_USER_PASSWORD = By.id("auto-login-password");
        }
    }
}
