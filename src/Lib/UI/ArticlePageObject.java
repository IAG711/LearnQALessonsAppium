package Lib.UI;

import io.appium.java_client.AppiumDriver;
import org.junit.Assert;
import org.openqa.selenium.By;

public class ArticlePageObject extends MainPageObject{
    public ArticlePageObject(AppiumDriver driver) {
        super(driver);
    }

    By element_article_title = By.id("org.wikipedia:id/view_page_title_text");
    By element_add_to_reading_list = By.xpath("//android.widget.ImageView[@content-desc='Add this article to a reading list']");
    By element_reading_list_onboarding_btn = By.id("org.wikipedia:id/onboarding_button");
    By element_reading_list_name_input = By.id("org.wikipedia:id/text_input");
    By element_reading_list_ok_btn = By.xpath("//*[@text='OK']");
    By element_article_screen_search_btn = By.id("org.wikipedia:id/menu_page_search");
    By element_cross_btn = By.xpath("*//android.widget.ImageButton[@content-desc='Navigate up']");

    public void waitForArticleTitleToAppear(){
        this.waitForElementPresent(element_article_title, "Cannot locate article title element", 15);
    }

    public void articleAddToReadingListClick(){
        this.waitForElementAndClick(element_add_to_reading_list, "Cannot locate more options button", 15);
    }

    public void articleSkipOnboardingToAddToList(){
        this.waitForElementAndClick(element_reading_list_onboarding_btn, "Cannot locate onboarding button to create new reading list", 5);
    }

    public void articleEnterReadingListName(String folder_name){
        this.waitForElementAndClear(element_reading_list_name_input, "Cannot locate text input for reading folder to clear default name", 5);
        this.waitForElementAndSendKeys(element_reading_list_name_input, folder_name, "Cannot locate text input for reading folder name", 5);
    }

    public void selectExistingReadingList(String list_name){
        this.waitForElementAndClick(By.xpath("//*[@text='"+ list_name +"']"), "Cannot locate existing reading folder with the name " + list_name, 15);
    }

    public void articleReadingListModalOkBtnClick(){
        this.waitForElementAndClick(element_reading_list_ok_btn, "Cannot locate OK button to add to reading list", 5);
    }

    public void articleSearchBtnClick(){
        this.waitForElementAndClick(element_article_screen_search_btn, "Cannot locate Looking glass button to click", 5);
    }

    public void createReadingListAndAddArticle(String folder_name){
        articleAddToReadingListClick();
        articleSkipOnboardingToAddToList();
        articleEnterReadingListName(folder_name);
        articleReadingListModalOkBtnClick();
    }

    public void addArticleIntoReadingList (String list_name){
        articleAddToReadingListClick();
        selectExistingReadingList(list_name);
    }

    public void closeArticle(){
        this.waitForElementAndClick(element_cross_btn, "Cannot locate cross button on article page", 5);
    }

    public void assertThatArticleHaveCorrectTitle(String expected_title){
        Assert.assertEquals("Article title is not equal to " + expected_title, expected_title, driver.findElement(element_article_title).getAttribute("text"));
    }

    public void assertTitleElementPresent(){
        Assert.assertFalse("Cannot locate presence of element", driver.findElements(element_article_title).isEmpty());
    }


}
