package com.mcac0006.siftscience.score.domain;

import com.mcac0006.siftscience.types.deserializer.DateDeserializer;
import com.mcac0006.siftscience.types.serializer.DateSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rodrigo on 21/07/2014.
 */
@Data
@EqualsAndHashCode(of={"isBad","time"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScoreLabel {

    @JsonProperty(value="is_bad")
    private Boolean isBad;
    private String description;
    @JsonProperty(value="time")
    @JsonSerialize(using=DateSerializer.class)
    @JsonDeserialize(using=DateDeserializer.class)
    private Date time;
    private List<String> reasons = new ArrayList<String>();
}
