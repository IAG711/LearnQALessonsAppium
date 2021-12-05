package Tests;

import Lib.CoreTestCase;
import Lib.UI.SearchPageObject;
import org.junit.Test;

public class SearchTests extends CoreTestCase {
    SearchPageObject SearchPageObject = new SearchPageObject(driver);

    //Ex.3
    @Test
    public void testCheckIfSearchResultsPresent(){
        SearchPageObject.initSearchInput();
        SearchPageObject.insertSearchQuery("Russia");
        SearchPageObject.assertSearchResultsAreShown();
        SearchPageObject.closeSearchResults();
    }





}
