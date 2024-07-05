package com.se.utils;

import com.se.config.Constants;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.yaml.snakeyaml.scanner.Constant;

import static com.se.utils.UtilsSet.clickOnElement;

public class NavigationUtil {
    @Step("Opening a subject base on {0}.")
    public static void openSubjectFromLearnMenu(String subName) {
        clickOnElement(By.linkText(subName));
    }

    @Step("Clicking on Skip button.")
    public static void clickonskipbutton() {
        clickOnElement(Constants.DueExame.BY_Skipbutton);
    }

    @Step("Clicking on the Subject button.")
    public static void clickonsubjectButton() {
        clickOnElement(Constants.DueExame.BY_subjectButton);
    }

    @Step("Clicking on the Lecture button.")
    public static void clickonLectureButton() {
        clickOnElement(Constants.DueExame.BY_lectureButton);
    }

    @Step("Opening Lecture Task")
    public static void clickonLectTaskButton() {
        clickOnElement(Constants.DueExame.BY_lectureTaskButton);
    }

    @Step("Opening Assignment Page")
    public static void clickonAssignmentButton() {
        clickOnElement(Constants.DueExame.BY_assignmentButton);
    }

    @Step("Opening Assignment Summary")
    public static void clickonAssignmentSummary() {
        clickOnElement(Constants.DueExame.BY_assignmentSummary);
    }

}
