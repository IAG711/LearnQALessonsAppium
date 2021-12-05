package Tests;

import Lib.CoreTestCase;
import Lib.UI.ArticlePageObject;
import Lib.UI.MyListsPageObject;
import Lib.UI.NavigationUI;
import Lib.UI.SearchPageObject;
import org.junit.Test;

public class ArticleTests extends CoreTestCase {

    //EX.5
    @Test
    public void testSavingTwoArticles(){
        String reading_folder_name = "Reading list";
        String search_query = "Russia";
        String first_article = "Russia";
        String second_article = "Russian language";

        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);
        NavigationUI NavigationUI = new NavigationUI(driver);
        MyListsPageObject MyListsPageObject = new MyListsPageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.insertSearchQuery(search_query);
        SearchPageObject.openArticle(first_article);
        ArticlePageObject.waitForArticleTitleToAppear();
        ArticlePageObject.createReadingListAndAddArticle(reading_folder_name);
        ArticlePageObject.articleSearchBtnClick();
        SearchPageObject.insertSearchQuery(search_query);
        SearchPageObject.openArticle(second_article);
        ArticlePageObject.waitForArticleTitleToAppear();
        ArticlePageObject.addArticleIntoReadingList(reading_folder_name);
        ArticlePageObject.closeArticle();
        NavigationUI.openReadingListsScreen();
        MyListsPageObject.openReadingList(reading_folder_name);
        MyListsPageObject.deleteArticleFromReadingList(second_article);
        MyListsPageObject.openArticleFromTheList(first_article);
        ArticlePageObject.waitForArticleTitleToAppear();
        ArticlePageObject.assertThatArticleHaveCorrectTitle(first_article);
    }

    //EX.6
    @Test
    public void testAssertThatElementIsPresent(){
        SearchPageObject SearchPageObject = new SearchPageObject(driver);
        ArticlePageObject ArticlePageObject = new ArticlePageObject(driver);

        SearchPageObject.initSearchInput();
        SearchPageObject.insertSearchQuery("Russia");
        SearchPageObject.openArticle("Russia");
        ArticlePageObject.assertTitleElementPresent();
    }

}
