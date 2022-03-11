package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class BaseClass {
    public static ExtentReports reports;
    public static WebDriver driver;
   public static ExtentTest test;
    @BeforeSuite
    public void configBS(){
        //Extent report configuration
        ExtentSparkReporter sparkReporter=new ExtentSparkReporter("./ExtentReport.html");
        sparkReporter.config().setTheme(Theme.DARK);
        sparkReporter.config().setDocumentTitle("Vtiger Automation");
        sparkReporter.config().setReportName("Execution Report");
         reports=new ExtentReports();
        reports.attachReporter(sparkReporter);
        reports.setSystemInfo("OS","Windows10");
        reports.setSystemInfo("url","http://localhost:8888");
        reports.setSystemInfo("Reporter name","Nithesh");
    }
    @BeforeClass
    public void launchBrowser() throws Throwable {
        String browserName=FileUtility.getPropertyValue("browser");
        if(browserName.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            driver=new ChromeDriver();
        }else if(browserName.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
        }
        else{
            System.out.println("browser name is invalid: "+browserName);
        }
        driver.get(FileUtility.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }
    @BeforeMethod
    public void login(){
        driver.findElement(By.name("user_name")).sendKeys("admin");
        driver.findElement(By.name("user_password")).sendKeys("admin", Keys.ENTER);
    }

    @AfterMethod
    public void logOut(ITestResult result){
        int resultVar=result.getStatus();
        if(resultVar==ITestResult.SUCCESS){//1
            test.log(Status.PASS,result.getMethod().getMethodName()+"is pass");
        }else if(resultVar==ITestResult.FAILURE){//2
            test.log(Status.FAIL,result.getMethod().getMethodName()+"is failed");
            test.log(Status.FAIL,result.getThrowable());
        }else if(resultVar==ITestResult.SKIP){//3
            test.log(Status.SKIP,result.getMethod().getMethodName()+" is Skipped");
            test.log(Status.SKIP,result.getThrowable());
        }
       WebElement ele= driver.findElement(By.xpath("//img[contains(@src,'/images/user.PNG')]"));
        Actions actions=new Actions(driver);
        actions.moveToElement(ele).build().perform();
        driver.findElement(By.linkText("Sign Out")).click();

    }

    @AfterClass
    public void quitBrowser(){
        driver.quit();
    }

    @AfterSuite
    public void configAS(){
        reports.flush();
    }

}
