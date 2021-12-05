package Lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class MyListsPageObject extends MainPageObject {
    public MyListsPageObject(AppiumDriver driver) {
        super(driver);
    }

    public void openReadingList(String list_name) {
        this.waitForElementAndClick(By.xpath("//*[@text='" + list_name + "']"), "Cannot locate reading list with the name " + list_name, 15);
    }

    public void openArticleFromTheList(String article_name){
        this.waitForElementAndClick(By.xpath("//*[@text='"+ article_name +"']"), "Cannot locate article " + article_name, 5);
    }

    public void deleteArticleFromReadingList(String article_name){
        this.swipeElementToLeft(By.xpath("//*[@text='"+ article_name +"']"), "Cannot locate article with title " + article_name + " to delete it");
        this.waitForElementNotPresent(By.xpath("//*[@text='"+ article_name +"']"), "Deleted article is still present after swipe", 5);
    }
}

