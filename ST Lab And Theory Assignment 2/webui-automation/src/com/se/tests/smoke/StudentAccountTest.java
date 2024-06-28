package com.se.tests.smoke;

import com.se.rolesbase.StudentLoginBase;
import org.testng.annotations.Test;
import com.se.utils.NavigationUtil;
import com.se.utils.UtilsSet;
import org.testng.Assert;
import com.se.config.Constants;

public class StudentAccountTest extends StudentLoginBase {

    @Test
    public void verifyStudentIsLoggedIn(){
        System.out.println("A Student is now logged in");
    }

    @Test
    public void verifyWelcomeToTrainStudent(){
        // Placeholder for another test case
    }



    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage"})
    public void verifyDueExameButtonIsClicked() {
        System.out.println("Starting verifyDueExamButtonIsClicked test");
        NavigationUtil.clickDueExameButton();
        System.out.println("Due Exam button is clicked");

        try {
            int timeoutSeconds = 27;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_dueExameSection, timeoutSeconds);
            String dueExameSectionText = UtilsSet.getElementText(Constants.DueExame.BY_dueExameSection);
            System.out.println("Due Exame section is visible with text: " + dueExameSectionText);

            Assert.assertFalse(dueExameSectionText.isEmpty(), "dueExameSectionText should not be empty");
            Assert.assertEquals(dueExameSectionText, "Exams", "Mismatch text when the Due Exame button is clicked");
            System.out.println("verifyDueExameButtonIsClicked test completed successfully");
        } catch (Exception e) {
            System.err.println("The dueExameSectionElement was not found or did not behave as expected.");
            Assert.fail("The dueExameSectionElement was not found or did not behave as expected.", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn"})
    public void verifySkipButtonAndNavigateToHomePage() {
        System.out.println("Starting verifySkipButtonAndNavigateToHomePage test");

        try {
            // Click the Skip Button
            NavigationUtil.clickonskipbutton();
            System.out.println("Skip button clicked");

            // Wait for the home page to load
            int timeoutSeconds = 15;
            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_HomepageText, timeoutSeconds);

            // Verify that we're on the home page
            String homePageText = UtilsSet.getElementText(Constants.DueExame.BY_HomepageText);
            System.out.println("Home page text: " + homePageText);

            Assert.assertFalse(homePageText.isEmpty(), "Home page text should not be empty");
            Assert.assertEquals(homePageText, "Welcome! Mahad Wajid", "Home page text should be 'Homepage'");

            System.out.println("verifySkipButtonAndNavigateToHomePage test completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to click Skip button or navigate to Home Page");
            Assert.fail("Failed to click Skip button or navigate to Home Page", e);
        }
    }



    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifyDueExameButtonIsClicked"})
    public void verifyQuizTitlesDisplayed() {
        System.out.println("Starting verifyQuizTitlesDisplayed test");

        try {
            int timeoutSeconds = 27;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_quizTitlesSection, timeoutSeconds);
            String quizTitlesSectionText = UtilsSet.getElementText(Constants.DueExame.BY_quizTitlesSection);
            System.out.println("Quiz Titles section is visible with text: " + quizTitlesSectionText);

            Assert.assertFalse(quizTitlesSectionText.isEmpty(), "quizTitlesSectionText should not be empty");
            Assert.assertTrue(quizTitlesSectionText.contains("Quiz"), "The page should display a list of quiz titles available for the student");
            System.out.println("verifyQuizTitlesDisplayed test completed successfully");
        } catch (Exception e) {
            System.err.println("The quizTitlesSectionElement was not found or did not behave as expected.");
            Assert.fail("The quizTitlesSectionElement was not found or did not behave as expected.", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifyDueExameButtonIsClicked"})
    public void verifyExamTitleLinkIsClicked() {
        System.out.println("Starting verifyExameTitleLinkIsClicked test");
        NavigationUtil.clickQuizTitleLink();
        System.out.println("The quiz title link is clicked");

        try{
            int timeoutSeconds = 15;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_examSummarySection, timeoutSeconds);

            // Scroll to the Exam Summary section
            UtilsSet.scrollToElement(Constants.DueExame.BY_examSummarySection);

            String examSummarySectionText = UtilsSet.getElementText(Constants.DueExame.BY_examSummarySection);
            System.out.println("Exam Summary section is visible with text: " + examSummarySectionText);
            Assert.assertFalse(examSummarySectionText.isEmpty(), "examSummarySection should not be empty");
            Assert.assertEquals(examSummarySectionText, "Exam Summary", "Mismatch text when the exam title link is clicked");
            System.out.println("verifyExamTitleLinkIsClicked test completed successfully");
        } catch (Exception e) {
            System.err.println("The verifyExamTitleLink was not found or did not behave as expected.");
            Assert.fail("The verifyExamTitleLink was not found or did not behave as expected.", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifyDueExameButtonIsClicked", "verifyExamTitleLinkIsClicked"})
    public void verifyExamSolutionDetails() {
        System.out.println("Starting verifyExamSolutionDetails test");
        NavigationUtil.clickExamWithSolutionButton();

        try {
            int timeoutSeconds = 15;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_examSummarySection, timeoutSeconds);
            String examSummaryText = UtilsSet.getElementText(Constants.DueExame.BY_examSummarySection);
            System.out.println("Exam Summary is visible with text: " + examSummaryText);

            Assert.assertFalse(examSummaryText.isEmpty(), "Exam summary should not be empty");
            Assert.assertTrue(examSummaryText.contains("Exam Summary"), "Exam summary should contain 'Exam Summary'");

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_quizDetailsSection, timeoutSeconds);
            String quizDetailsText = UtilsSet.getElementText(Constants.DueExame.BY_quizDetailsSection);
            System.out.println("Quiz details are visible with text: " + quizDetailsText);

            Assert.assertFalse(quizDetailsText.isEmpty(), "Quiz details should not be empty");

            String expectedUsername = Constants.STUDENT_LOGIN_DETAILS.getUsername();
            String formattedUsername = expectedUsername.replace("-", "").toLowerCase();

            System.out.println("Expected username: " + expectedUsername);
            System.out.println("Formatted username: " + formattedUsername);
            System.out.println("Full quiz details text: " + quizDetailsText);

            Assert.assertTrue(
                    quizDetailsText.toLowerCase().contains(expectedUsername.toLowerCase()) ||
                            quizDetailsText.toLowerCase().contains(formattedUsername) ||
                            quizDetailsText.toLowerCase().contains("user:" + formattedUsername) ||
                            quizDetailsText.toLowerCase().contains("username:" + formattedUsername),
                    "Quiz details should contain the logged-in username or a variation: " + expectedUsername
            );

            System.out.println("verifyExamSolutionDetails test completed successfully");
        } catch (Exception e) {
            System.err.println("The examSolutionDetails section was not found or did not behave as expected.");
            Assert.fail("The examSolutionDetails section was not found or did not behave as expected.", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage", "verifyDueExameButtonIsClicked", "verifyExamTitleLinkIsClicked"})
    public void verifyPerformanceAnalyticsAvailability() {
        System.out.println("Starting verifyPerformanceAnalyticsAvailability test");

        try {
            // Click on Performance analytics button
            NavigationUtil.clickPerformanceAnalyticsButton();
            System.out.println("Performance analytics button clicked");

            // Wait for analytics page to load
            int timeoutSeconds = 15;
            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_AnalyticsSection, timeoutSeconds);

            // Verify analytics section is visible
            String analyticsSectionText = UtilsSet.getElementText(Constants.DueExame.BY_AnalyticsSection);
            System.out.println("Analytics section is visible with text: " + analyticsSectionText);

            Assert.assertFalse(analyticsSectionText.isEmpty(), "Analytics section should not be empty");
            Assert.assertEquals(analyticsSectionText, "Exam Performance of Student and Class", "Analytics section should contain performance metrics");

            System.out.println("verifyPerformanceAnalyticsAvailability test completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to click Performance analytics button or navigate to Analytics page");
            Assert.fail("Failed to click Performance analytics button or navigate to Analytics page", e);
        }
    }

}
