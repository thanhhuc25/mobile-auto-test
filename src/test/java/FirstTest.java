import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSTouchAction;
import io.appium.java_client.remote.MobileCapabilityType;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static io.appium.java_client.ios.touch.IOSPressOptions.iosPressOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;


public class FirstTest {

    private IOSDriver<?> driver;
    public static String email = "tester@one.com";
    public static String password = "123456";
    public static String appPath = "/Users/user/ios/Futsal.app";

    @Before
    public void setUp() throws MalformedURLException {
        File app = new File(appPath);
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "11.4");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 6");
        desiredCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
        desiredCapabilities.setCapability("appiumVersion", "1.7.1");
        desiredCapabilities.setCapability("automationName", "XCUITest");

        driver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @Test
    public void loginTest() throws InterruptedException
    {
        //MobileElement btn = (MobileElement)driver.findElement(By.xpath("//XCUIElementTypeOther[@name='\uF2DB']"));
        MobileElement btn = (MobileElement)driver.findElement(By.xpath("//XCUIElementTypeOther[@name='Matches']"));
        int writeBtnCount = driver.findElements(By.xpath("//XCUIElementTypeOther[@name='\uF2DB']")).size();
        MobileElement btnPaint = null;
        if(writeBtnCount > 1){
            btnPaint = (MobileElement)driver.findElements(By.xpath("//XCUIElementTypeOther[@name='\uF2DB']")).get(1);
            new IOSTouchAction(driver)
                    .press(iosPressOptions()
                            .withElement(element(btnPaint))
                            .withPressure(1))
                    .release()
                    .perform();
            driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);

            List<MobileElement> emails = (List<MobileElement>)driver.findElements(By.xpath("//XCUIElementTypeOther[@name='Email']"));
            List<MobileElement> passwords = (List<MobileElement>)driver.findElements(By.xpath("//XCUIElementTypeOther[@name='Password']"));
            List<MobileElement> loginBtns = (List<MobileElement>)driver.findElements(By.xpath("//XCUIElementTypeOther[@name='Login']"));

            if(emails.size() > 1){
                emails.get(1).sendKeys(email);
            }
            if(passwords.size() > 1){
                passwords.get(1).sendKeys(password);
            }
            if(loginBtns.size() > 1){
                new IOSTouchAction(driver)
                        .press(iosPressOptions()
                                .withElement(element(loginBtns.get(1)))
                                .withPressure(1))
                        .release()
                        .perform();
            }
            Thread.sleep(3*1000);
            WebDriverWait wait = new WebDriverWait(driver, 10);
            // wait for alert push notification
            wait.until(ExpectedConditions.alertIsPresent());
            driver.switchTo().alert().accept();
            // stop
            Thread.sleep(2*1000);

        }

    }

    @After
    public void teardown(){
        driver.quit();
    }


}
