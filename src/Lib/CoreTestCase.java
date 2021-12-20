package Lib;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import junit.framework.TestCase;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.URL;

public class CoreTestCase extends TestCase {

    protected AppiumDriver driver;
    private static String AppiumURL = "http://127.0.0.1:4723/wd/hub";
    private static final String PLATFORM_IOS = "ios";
    private static final String PLATFORM_ANDROID = "android";



    @Override
    protected void setUp() throws Exception {
        super.setUp();
        driver = this.setDriver();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    private DesiredCapabilities getCapabilitiesByPlatformEnv() throws Exception {
        String platform = System.getenv("PLATFORM");
        DesiredCapabilities capabilities = new DesiredCapabilities();

        if (platform.equals(PLATFORM_ANDROID)) {
            capabilities.setCapability("platformName","Android");
            capabilities.setCapability("deviceName","AndroidTestDevice");
            capabilities.setCapability("platformVersion","8.0");
            capabilities.setCapability("automationName","Appium");
            capabilities.setCapability("appPackage","org.wikipedia");
            capabilities.setCapability("appActivity",".main.MainActivity");
            capabilities.setCapability("app","/Users/iag/AutoTests/LearnQALessonsAppium/apks/org.wikipedia.apk");
        } else if (platform.equals(PLATFORM_IOS)){
            capabilities.setCapability("platformName","iOS");
            capabilities.setCapability("deviceName","iPhone 11");
            capabilities.setCapability("platformVersion","15.0");
            capabilities.setCapability("app","/Users/iag/AutoTests/LearnQALessonsAppium/apks/Wikipedia.app");
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }
        return capabilities;
    }

    //Задание упоминалось в уроке: на основе примера с получением capabilities сделать получение драйвера
    //Думаю, что в целом getCapabilitiesByPlatformEnv можно было бы и убрать и вынести значения в две константы, которые бы просто забирались
    //в setDriver в зависимости от platform, но в итоге оставил пока так
    public AppiumDriver setDriver() throws Exception {
        String platform = System.getenv("PLATFORM");
        URL URL = new URL(AppiumURL);
        if (platform.equals(PLATFORM_ANDROID)) {
            return new AndroidDriver(URL, this.getCapabilitiesByPlatformEnv());
        } else if (platform.equals(PLATFORM_IOS)) {
            return new IOSDriver(URL, this.getCapabilitiesByPlatformEnv());
        } else {
            throw new Exception("Cannot get run platform from env variable. Platform value " + platform);
        }
    }

}
