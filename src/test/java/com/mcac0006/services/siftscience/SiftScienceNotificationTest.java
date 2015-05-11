package com.mcac0006.services.siftscience;

import com.mcac0006.siftscience.SiftScienceHelper;
import com.mcac0006.siftscience.score.domain.SiftNotification;
import com.mcac0006.siftscience.score.domain.SiftScienceScore;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Rodrigo on 11/05/2015.
 */
public class SiftScienceNotificationTest {

    private SiftScienceHelper helper;

    @Before
    public void setup() {
        helper = new SiftScienceHelper("testapikey");
    }

    @Test
    public void testParsingHttpNotification() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/score/$sift_score_notification.json");
        SiftNotification notification = helper.getMapper().readValue(stream, SiftNotification.class);
        assertEquals(notification.getLatestVersion(), "203");
        assertTrue(notification.getVersions().size() == 1);
        SiftScienceScore first = notification.getVersions().get("203");
        assertEquals(first.getUserId(),"billy_jones_301");
        assertEquals(first.getScore(),0.93,0.01);
        assertNull(first.getLatestLabel());
    }

}
