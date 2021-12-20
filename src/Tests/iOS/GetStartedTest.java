package Tests.iOS;

import Lib.CoreTestCase;
import Lib.UI.WelcomePageObject;
import org.junit.Test;

public class GetStartedTest extends CoreTestCase {

    @Test
    public void testIosWelcomeScreen() {

        WelcomePageObject WelcomePageObject = new WelcomePageObject(driver);

        WelcomePageObject.waitForLearningMoreLink();
        WelcomePageObject.nextButtonClick();

        WelcomePageObject.waitForNewWayToExploreText();
        WelcomePageObject.nextButtonClick();

        WelcomePageObject.waitForAddOrEditPrefLang();
        WelcomePageObject.nextButtonClick();

        WelcomePageObject.waitForLearnAboutDataCollection();
        WelcomePageObject.getStartedButtonClick();

    }


}
