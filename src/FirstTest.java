import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {

    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();

        capabilities.setCapability("platformName","Android");
        capabilities.setCapability("deviceName","AndroidTestDevice");
        capabilities.setCapability("platformVersion","8.0");
        capabilities.setCapability("automationName","Appium");
        capabilities.setCapability("appPackage","org.wikipedia");
        capabilities.setCapability("appActivity",".main.MainActivity");
        capabilities.setCapability("app","/Users/ilya/Desktop/LearnQALessonsAppium/apks/org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown(){
        driver.quit();
    }

    By element_search_open = By.id("org.wikipedia:id/search_container");
    By element_search_input = By.id("org.wikipedia:id/search_src_text");
    By element_search_results_list = By.id("org.wikipedia:id/search_results_list");
    By element_add_to_reading_list = By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']");
    By element_create_new_reading_list = By.id("org.wikipedia:id/create_button");
    By element_reading_list_onboarding_btn = By.id("org.wikipedia:id/onboarding_button");
    By element_article_screen_more_options = By.xpath("//android.widget.ImageView[@content-desc='More options']");
    By element_add_reading_list = By.xpath("//*[@text='Add to reading list']");
    By element_reading_list_name_input = By.id("org.wikipedia:id/text_input");
    By element_reading_list_ok_btn = By.xpath("//*[@text='OK']");
    By element_article_screen_search_btn = By.id("org.wikipedia:id/menu_page_search");
    By element_article_title = By.id("org.wikipedia:id/view_page_title_text");
    By element_cross_btn = By.xpath("*//android.widget.ImageButton[@content-desc='Navigate up']");
    By element_open_reading_list_screen_btn = By.xpath("//android.widget.FrameLayout[@content-desc='My lists']");
    By element_reading_lists_container = By.id("org.wikipedia:id/reading_list_list");

    String reading_folder_name = "Reading list";
    String search_query = "Russia";

    @Test
    public void testSavingTwoArticles(){
        String first_article = "Russia";
        String second_article = "Russian language";

        searchForArticleFromMainScreen(search_query);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ first_article +"']"),
                "Cannot locate search result with title " + first_article,
                5
        );

        //Столкнулся со странным поведением, когда иногда клик происходил не по кнопке добавления статьи, а по произвольному месту в МП
        //предположил, что проблема в том, что в эмуляторе не все прогружалось до конца и поставил ассерт на элемент с заголовком
        //проблема решилась, клики теперь всегда идут по нужному элементу
        waitForElementPresent(
                element_article_title,
                "Cannot locate article title element",
                15
        );

        createReadingListAndAddArticle(reading_folder_name);

        searchForArticleFromArticleScreen(search_query);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ second_article +"']"),
                "Cannot locate search result with title " + second_article,
                5
        );

        waitForElementPresent(
                element_article_title,
                "Cannot locate article title element",
                15
        );

        addArticleToReadingList(reading_folder_name);

        waitForElementAndClick(
                element_cross_btn,
                "Cannot locate cross button on article page",
                5
        );

        openReadingListFromMainScreen(reading_folder_name);

        swipeElementToLeft(
                By.xpath("//*[@text='"+ second_article +"']"),
                "Cannot locate article with title " + second_article + " to delete it"
        );

        waitForElementNotPresent(
                By.xpath("//*[@text='"+ second_article +"']"),
                "Deleted article is still present",
                5
        );

        //сделал метод assertElementIsPresent для проверки пункта "Убеждается, что вторая осталась", но не стал использовать
        //по сути переход в оставшуюся статью равен этой проверке тк тест упадет, если мы не сможем в нее зайти
        waitForElementAndClick(
                By.xpath("//*[@text='"+ first_article +"']"),
                "Cannot locate article " + first_article,
                5
        );

        waitForElementPresent(
                element_article_title,
                "Cannot locate article title element",
                15
        );

        Assert.assertEquals(
                "Article title is not equal to " + first_article,
                first_article,
                driver.findElement(element_article_title).getAttribute("text")
        );

    }

    @Test
    public void testAssertThatElementIsPresent(){
        String first_article = "Russia";

        searchForArticleFromMainScreen(search_query);

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='"+ first_article +"']"),
                "Cannot locate search result with title " + first_article,
                5
        );

        assertElementPresent(element_article_title);
    }



    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message){
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }

    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds){
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message){
        WebElement element = waitForElementPresent(by, "Cannot locate element with text", 5);
        String element_value = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_text, element_value);
    }

    private void assertSearchResultsAreShown(){
        List get_elements = driver.findElements(By.id("org.wikipedia:id/search_results_list"));
        int element_count = get_elements.size();
        Assert.assertTrue("Search list is empty",element_count > 0);
    }

    protected void swipeUp(int timeOfSwipe){
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.press(x, start_y)
                .waitAction(timeOfSwipe)
                .moveTo(x, end_y)
                .release()
                .perform();
    }

    protected void swipeUpQuick(){
        swipeUp(200);
    }

    protected void swipeUpToFindElement(By by, String error_message, int max_swipes){
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0){
            if (already_swiped > max_swipes){
                waitForElementPresent(by, "Cannot find element by swiping up. \n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            ++already_swiped;
        }
    }

    protected void swipeElementToLeft(By by, String error_message){
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;

        TouchAction action = new TouchAction(driver);
        action
                .press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by){
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void assertElementNotPresent(By by, String error_message){
        int get_amount_of_elements = getAmountOfElements(by);
        if (get_amount_of_elements > 0){
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInSeconds){
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        return element.getAttribute(attribute);
    }

    public void searchForArticleFromMainScreen(String search_text){
        waitForElementAndClick(element_search_open, "Cannot locate element to click", 5);
        waitForElementAndSendKeys(element_search_input, search_text, "Cannot locate element to send keys to", 5);
        waitForElementPresent(element_search_results_list, "Cannot locate search results list", 20);
    }

    public void searchForArticleFromArticleScreen(String search_text){
        waitForElementAndClick(element_article_screen_search_btn, "Cannot locate Looking glass button to click", 5);
        waitForElementAndSendKeys(element_search_input, search_text, "Cannot locate element to send keys to", 5);
        waitForElementPresent(element_search_results_list, "Cannot locate search results list", 20);
    }

    public void createReadingListAndAddArticle(String folder_name){
        waitForElementAndClick(element_add_to_reading_list, "Cannot locate more options button", 15);
        waitForElementAndClick(element_reading_list_onboarding_btn, "Cannot locate onboarding button to create new reading list", 5);
        waitForElementAndClear(element_reading_list_name_input, "Cannot locate text input for reading folder to clear default name", 5);
        waitForElementAndSendKeys(element_reading_list_name_input, folder_name, "Cannot locate text input for reading folder name", 5);
        waitForElementAndClick(element_reading_list_ok_btn, "Cannot locate OK button to add to reading list", 5);
    }

    public void addArticleToReadingList(String list_name){
        waitForElementAndClick(element_add_to_reading_list, "Cannot locate more options button", 15);
        waitForElementAndClick(By.xpath("//*[@text='"+ list_name +"']"), "Cannot locate existing reading folder with the name " + list_name, 15);
    }

    public void openReadingListFromMainScreen(String list_name){
        waitForElementAndClick(element_open_reading_list_screen_btn, "Cannot locate reading list button on main screen", 15);
        waitForElementPresent(element_reading_lists_container, "Cannot locate reading lists element", 15);
        waitForElementAndClick(By.xpath("//*[@text='"+ list_name +"']"), "Cannot locate reading list with the name " + list_name, 15);
    }

    private void assertElementIsPresent(By by){
        WebElement element = driver.findElement(by);
        Assert.assertTrue("Element " + by + " is not located on the page", element.isDisplayed());
    }

    private void assertElementPresent(By by){
        Assert.assertFalse("Cannot locate presence of element", driver.findElements(by).isEmpty());
    }

}
