package com.mcac0006.services.siftscience;

import com.mcac0006.siftscience.SiftScienceHelper;
import com.mcac0006.siftscience.result.domain.SiftScienceSyncResponse;
import com.mcac0006.siftscience.score.domain.SiftScienceScore;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Rodrigo on 21/01/2015.
 */
public class SiftScienceParseTest {

    private SiftScienceHelper helper;

    @Before
    public void setup() {
        helper = new SiftScienceHelper("testapikey");
    }

    @Test
    public void testParseScore() throws IOException {
        InputStream io = SiftScienceParseTest.class.getResourceAsStream("/score/sift_score_error.json");
        String json = IOUtils.toString(io);
        SiftScienceScore result = new SiftScienceHelper("123123").parseScore(json);
        Assert.assertNotNull(result);
    }
    @Test
    public void testSyncResponse() throws IOException {
        InputStream stream = this.getClass().getResourceAsStream("/score/$sift_score_sync_response.json");
        SiftScienceSyncResponse syncResponse = helper.getMapper().readValue(stream, SiftScienceSyncResponse.class);
        assertNotNull(syncResponse);
        SiftScienceScore scoreResponse = syncResponse.getScore_response();
        assertEquals("OK", syncResponse.getError_message());
        assertEquals("viniciuslopeslps@gmail.com", scoreResponse.getUserId());
        assertEquals(Float.valueOf("0.31721878"), scoreResponse.getScore());
    }
}
