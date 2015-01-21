package com.mcac0006.services.siftscience;

import com.mcac0006.siftscience.SiftScienceHelper;
import com.mcac0006.siftscience.score.domain.SiftScienceScore;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Rodrigo on 21/01/2015.
 */
public class SiftScienceParseTest {


    @Test
    public void testParseScore() throws IOException {
        InputStream io = SiftScienceParseTest.class.getResourceAsStream("/score/sift_score_error.json");
        String json = IOUtils.toString(io);
        SiftScienceScore result = new SiftScienceHelper("123123").parseScore(json);
        Assert.assertNotNull(result);
    }
}
