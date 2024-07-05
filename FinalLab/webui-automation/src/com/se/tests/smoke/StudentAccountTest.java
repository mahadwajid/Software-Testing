package com.se.tests.smoke;

import com.se.config.Constants;
import com.se.rolesbase.StudentLoginBase;
import com.se.utils.NavigationUtil;
import com.se.utils.UtilsSet;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StudentAccountTest extends StudentLoginBase {


    @Test
    public void verifyStudentIsLoggedIn(){


        System.out.println("A Student is now logged in");
    }

    @Test
    public void verifyWelcomeToTrainStudent(){

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

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage"})
    public void verifySubjectButtonIsClicked() {
        System.out.println("Starting verifySubjectButtonIsClicked test");
        NavigationUtil.clickonsubjectButton();
        System.out.println("Subject Button is clicked");

        try {
            int timeoutSeconds = 27;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_subjectSection, timeoutSeconds);
            String subjectbuttonSectionText = UtilsSet.getElementText(Constants.DueExame.BY_subjectSection);
            System.out.println("Subject Section is visible with Text: " + subjectbuttonSectionText);

            Assert.assertFalse(subjectbuttonSectionText.isEmpty(), "Subject SectionText should not be empty");
            Assert.assertEquals(subjectbuttonSectionText, "Course Objectives", "Mismatch text when the Subject Section button is clicked");
            System.out.println("verifySubjectButtonIsClicked test completed successfully");
        } catch (Exception e) {
            System.err.println("The subjectSectionElement was not found or did not behave as expected.");
            Assert.fail("The subjectSectionElement was not found or did not behave as expected.", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage" ,"verifySubjectButtonIsClicked"})
    public void verifyLectureSectionIsClicked() {
        System.out.println("Starting verifyLectureSectionIsClicked test");
        NavigationUtil.clickonLectureButton();
        System.out.println("Lecture Button is clicked");

        try {
            int timeoutSeconds = 27;

            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_lectureSection, timeoutSeconds);
            String lectureSectionText = UtilsSet.getElementText(Constants.DueExame.BY_lectureSection);
            System.out.println("Lecture Section is visible with Text: " + lectureSectionText);

            Assert.assertFalse(lectureSectionText.isEmpty(), "Lecture Section text should not be empty");
            Assert.assertEquals(lectureSectionText, "Introduction to OOSE", "Mismatch text when the Lecture Section button is clicked");
            System.out.println("verifyLectureSectionIsClicked test completed successfully");
        } catch (Exception e) {
            System.err.println("The lectureSectionElement was not found or did not behave as expected.");
            Assert.fail("The lectureSectionElement was not found or did not behave as expected.", e);
        }
    }


    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage" , "verifyLectureSectionIsClicked"})
    public void verifyLecturePerformance() {
        System.out.println("Starting verifyLecturePerformance test");

        try {
            // Simulate accessing lecture content and measure performance
            long startTime = System.currentTimeMillis();

            // Replace this with actual actions to access lecture content
            NavigationUtil.clickonLectureButton();
            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_lectureSections, 10);

            long endTime = System.currentTimeMillis();
            long durationMs = endTime - startTime;
            double durationSeconds = durationMs / 1000.0;

            System.out.println("Lecture accessed in: " + durationSeconds + " seconds");

            // Add assertions or logging to validate performance requirements
            Assert.assertTrue(durationSeconds < 5.0, "Lecture access should take less than 5 seconds");
            System.out.println("verifyLecturePerformance test completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to verify lecture performance");
            Assert.fail("Failed to verify lecture performance", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage" ,"verifyLecturePerformance" , "verifyLectureSectionIsClicked"})
    public void verifyLectTaskAndVerify() {
        System.out.println("Starting verifyLectTaskAndVerify test");

        try {
            // Click the Lecture Task Button
            NavigationUtil.clickonLectTaskButton();
            System.out.println("Lecture Task Button is clicked");

            // Wait for the Lecture Task content to load
            int timeoutSeconds = 20;
            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_lectureTaskContent, timeoutSeconds);

            // Verify Lecture Task content
            String lectureTaskContent = UtilsSet.getElementText(Constants.DueExame.BY_lectureTaskContent);
            System.out.println("Lecture Task content: " + lectureTaskContent);

            Assert.assertFalse(lectureTaskContent.isEmpty(), "Lecture Task content should not be empty");
            Assert.assertTrue(lectureTaskContent.contains("Lecture/Lab Tasks"), "Lecture Task should contain 'Task Description'");
            System.out.println("verifyLectTaskAndVerify test completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to verify Lecture Task content");
            Assert.fail("Failed to verify Lecture Task content", e);
        }
    }

    @Test(dependsOnMethods = {"verifyStudentIsLoggedIn", "verifySkipButtonAndNavigateToHomePage"})
    public void verifyAssignmentSummary() {
        System.out.println("Starting verifyAssignmentSummary test");

        try {
            // Navigate to Assignment Page
            NavigationUtil.clickonAssignmentButton();
            System.out.println("Assignment Button is clicked");

            // Wait for Assignment summary to load
            int timeoutSeconds = 15;
            UtilsSet.waitForElementToBeVisible(Constants.DueExame.BY_assignmentSummary, timeoutSeconds);

            // Verify Assignment Summary
            String assignmentSummaryText = UtilsSet.getElementText(Constants.DueExame.BY_assignmentSummary);
            System.out.println("Assignment Summary: " + assignmentSummaryText);

            Assert.assertFalse(assignmentSummaryText.isEmpty(), "Assignment Summary should not be empty");
            Assert.assertTrue(assignmentSummaryText.contains("Due Date"), "Assignment Summary should contain 'Due Date'");

            System.out.println("verifyAssignmentSummary test completed successfully");
        } catch (Exception e) {
            System.err.println("Failed to verify Assignment Summary");
            Assert.fail("Failed to verify Assignment Summary", e);
        }
    }

}
