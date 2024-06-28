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

    @Step("Clicking on the Due Exame button.")
    public static void clickDueExameButton() {
        clickOnElement(Constants.DueExame.BY_dueExameButton);
    }

    @Step("Clicking on the quiz title link.")
    public static void clickQuizTitleLink() {
        clickOnElement(Constants.DueExame.BY_examTitleLink);
    }

    @Step("Clicking on the Exam with Solution button.")
    public static void clickExamWithSolutionButton() {
        clickOnElement(Constants.DueExame.BY_ExamWithSolution);
    }

    @Step("Clicking on Skip button.")
    public static void clickonskipbutton() {
        clickOnElement(Constants.DueExame.BY_Skipbutton);
    }

    @Step("Clicking on the Performance analytics button.")
    public static void clickPerformanceAnalyticsButton() {
        clickOnElement(Constants.DueExame.BY_PerformanceAnalyticsButton);
    }



}