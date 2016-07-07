package com.mcac0006.siftscience.result.domain;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SiftScoreResponse {
    private Float score;

    public void setScore(Float score) {
        this.score = score;
    }

    public Float getScore() {
        return this.score;
    }
}
