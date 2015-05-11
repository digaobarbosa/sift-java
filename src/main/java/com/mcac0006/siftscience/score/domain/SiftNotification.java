package com.mcac0006.siftscience.score.domain;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Rodrigo on 11/05/2015.
 * Class to parse Sift notifications made on your website.
 * As specified in https://siftscience.com/resources/references/score-api Labels Api
 */
@Data
public class SiftNotification {
    @JsonProperty(value="latest_version")
    private String latestVersion;

    private Map<String,SiftScienceScore> versions = new TreeMap<String, SiftScienceScore>();
}
