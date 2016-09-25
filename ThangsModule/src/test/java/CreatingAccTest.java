import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by thang on 24.09.2016.
 */
public class CreatingAccTest extends SeleniumTestBase {
    private WriterToFieldObject po;

    //@Before
    public void startFromInitialPage() {

        po = new WriterToFieldObject(getDriver());
        po.toStartingPage();
        assertTrue(po.isOnPage());
    }


    //@Test
    public void testIncr() {
        po.toStartingPage();
    }

}
