package com.se.utils;

import com.github.javafaker.Faker;
import com.se.TestDriver;
import com.se.config.Constants;
import io.qameta.allure.Step;

import java.util.AbstractMap;
import java.util.stream.Stream;

import static com.se.utils.UtilsSet.*;

public class LoginUtil {
    public static void login(Constants.LoginParameters loginParameters) {
        var roleToSelect = loginParameters.getHasMoreThanOneRole()
                ? loginParameters.getRole()
                : null;

        login(
                loginParameters.getUsername(),
                loginParameters.getPassword(),
                roleToSelect
        );
    }

    public static void login(
            String username,
            String password
    ) {
        login(username, password, null);
    }

    public static void login(
            String username,
            String password,
            Constants.Role role
    ) {
        clickOnElement(Constants.Login.BY_login);
        Stream.of(
                        new AbstractMap.SimpleEntry<>(Constants.Login.BY_USERNAME_FIELD, username),
                        new AbstractMap.SimpleEntry<>(Constants.Login.BY_PASSWORD_FIELD, password)
                )
                .forEach(entry -> {
                    if (entry.getValue() != null) {
                        clearAndSetElementText(entry.getKey(), entry.getValue());
                        return;
                    }

                    // Trigger the "field required" message
                    sendTextToElement(
                            entry.getKey(),
                            Faker.instance().lorem().characters()
                    );
                    clearTextArea(entry.getKey());
                });

        clickOnElement(Constants.Login.BY_LOGIN_BUTTON);

    }

    @Step
    public static void logout() {
        //UtilityNavUtil.openUtilityNav(UtilityNavUtil.Entry.LOGOUT);
       // waitForElementToBeVisible(Constants.Login.BY_LOGIN_BUTTON);
    }

    @Step
    public static void goToLoginPage(Constants.LoginParameters loginParameters) {
        goToUrl(loginParameters.getUrl());
    }

    @Step
    public static void logoutAndLoginToDifferentUser(
            Constants.LoginParameters enterprise,
            String username,
            String password
    ) {
        logout();
        TestDriver.tearDown();
        login(username, password);
    }
    @Step
    public static void selectRole(Constants.Role role) {
       // waitForElementToBeVisible(role.getRoleName());
        clickOnElement(role.getRoleBy(), "Failed to find the role popup buttons, if the user only" +
                " has one role change the test to login without selecting a role.");
      //  waitForLoadingSpinnerToComplete();
    }

    public static void selectRoleWithoutWaitingForLoadingSpinner(Constants.Role role) {
        waitForElementToBeClickable(role.getRoleBy());
        clickOnElement(role.getRoleBy());
    }

    public static void loginInSwagger(Constants.LoginParameters loginParameters) {
        clearAndSetElementText(Constants.Swagger.Login.BY_ROLE_ID, "" + loginParameters.getRole().getRoleId());
        clearAndSetElementText(Constants.Swagger.Login.BY_USER_NAME, loginParameters.getUsername());
        clearAndSetElementText(Constants.Swagger.Login.BY_USER_PASSWORD, loginParameters.getPassword());
        clickOnElement(Constants.Swagger.Login.BY_SWAGGER_LOGIN_BUTTON);
    }

    @Step
    public static void logoutAndLoginWithDifferentEnterprise(Constants.LoginParameters loginParameters) {
        LoginUtil.logout();
        waitForElementToBeVisible(Constants.Login.BY_USERNAME_FIELD,5);
        LoginUtil.login(loginParameters);
    }
}
