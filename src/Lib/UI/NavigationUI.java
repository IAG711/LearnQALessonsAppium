package Lib.UI;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;

public class NavigationUI extends MainPageObject{
    public NavigationUI(AppiumDriver driver) {
        super(driver);
    }

    By element_open_reading_list_screen_btn = By.xpath("//android.widget.FrameLayout[@content-desc='My lists']");
    By element_reading_lists_container = By.id("org.wikipedia:id/reading_list_list");

    public void openReadingListsScreen(){
        this.waitForElementAndClick(element_open_reading_list_screen_btn, "Cannot locate reading list button on main screen", 15);
        this.waitForElementPresent(element_reading_lists_container, "Cannot locate reading lists element", 15);
    }

}
