package report;

import io.qameta.allure.*;
import org.testng.annotations.Test;

public class AllureReport {

    @Epic("FP_01 Login Module")
    @Story("FP_10 user can login using phone number")
    @Severity(SeverityLevel.BLOCKER)
    @Description("As a valid user login with valid credentials")
    @Test
    public void loginToApplication(){
        System.out.println("loginToApplication");
    }

    @Epic("FP_02 Browsing for product")
    @Story("FP_11 User can browser Electronics goods")
    @Severity(SeverityLevel.MINOR)
    @Test
    public void SearchForProduct(){
        System.out.println("SearchForProduct");
    }
    @Epic("FP_03 Cart module")
    @Story("FP_13 User can remove and add product to cart")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void removeProductFromCart(){
        System.out.println("removeProductFromCart");
    }
}
