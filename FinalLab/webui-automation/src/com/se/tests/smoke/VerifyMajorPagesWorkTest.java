package com.se.tests.smoke;
import com.github.javafaker.Faker;
import com.se.TestBase;
import com.se.data.DataGenerator;
import com.se.utils.NavigationUtil;
import com.se.config.Constants;
import com.se.utils.UtilsSet;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.se.utils.UtilsSet.*;

// SE001
public class VerifyMajorPagesWorkTest extends TestBase {

    @BeforeMethod
    public void CheckTestStatus(){
        System.out.println("Ensure internet is working for remote testing ...");

    }
    @Test
    public void validateWelcomeTabTest(){
        UtilsSet.launchBrowserAndUrl("https://demo.subexpert.com/", Constants.Login.BY_LOGIN_BUTTON);
    }
    @Test
    public void varify_title(){
        UtilsSet.goToUrl("https://demo.subexpert.com/");
        // Get the actual title of the web page
        String actualTitle = UtilsSet.getPageTitle();
        // Expected title
        String expectedTitle = "Subject Expert a learning and evaluation platform";
        // Assert that the actual title is equal to the expected title
        Assert.assertEquals(actualTitle, expectedTitle, "Page title mismatch");
    }
    @Test
    public void Signup(){
        UtilsSet.goToUrl("https://demo.subexpert.com/");
        //clicking on element with link text
        UtilsSet.clickOnElement(Constants.Signup.BY_Signup_button);
        var sampleUser = DataGenerator.getSampleUser();
        clearAndSetElementText((Constants.Signup.BY_firstname),sampleUser.firstName);
        clearAndSetElementText((Constants.Signup.BY_lastname),sampleUser.lastName);
        clearAndSetElementText((Constants.Signup.BY_username),sampleUser.username);
        clearAndSetElementText((Constants.Signup.BY_password),sampleUser.password);
        clearAndSetElementText((Constants.Signup.BY_confirmpassword),sampleUser.confirmPassword);
        clearAndSetElementText((Constants.Signup.BY_email),sampleUser.email);
        UtilsSet.clickOnElement(Constants.Signup.By_Check_Button);
        UtilsSet.clickOnElement(Constants.Signup.By_submit_Button);
        clearAndSetElementText((Constants.Signup.BY_firstname),sampleUser.firstName);
        //Assert.assertEquals(Actual_text, expected_text, "Element is mismatch mismatch");
    }
//    @Test
//    public void click_DB_System(){
//        //click on element with link text
//        UtilsSet.clickOnElement(Constants.DB_System.BY_DB_System_link);
//        //Getting actual text of element
//        String Actual_text=UtilsSet.getElementText(Constants.actual_txt.BY_Element_text);
//        //Expected text
//        String expected_text=Constants.DB_System.BY_Expected_text;
//        // Assert that the actual text is equal to expected text of element.
//        Assert.assertEquals(Actual_text, expected_text, "Element is mismatch mismatch");
//    }
@Test
public void gmail() throws InterruptedException {
        
    goToUrl("https://mail.google.com/");
    clearAndSetElementText(Constants.gmail.BY_email,Constants.gmail.useremail);
    clickOnElement(Constants.gmail.BY_unext_button);
    clearAndSetElementText(Constants.gmail.BY_password, Constants.gmail.userpassword);
    clickOnElement(Constants.gmail.BY_pnext_button);
    clickOnElement(Constants.gmail.BY_mailmessage);
    clickOnElement(Constants.gmail.BY_verfication_link);
    UtilsSet.switchToNextWindow();
    WebElement element = findElement(Constants.Login.BY_login);
    Assert.assertTrue(element.isDisplayed());

}
}
