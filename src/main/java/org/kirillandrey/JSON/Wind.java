package org.kirillandrey.JSON;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "speed",
        "deg"
})
@Generated("jsonschema2pojo")
public class Wind {

    @JsonProperty("speed")
    private Double speed;
    @JsonProperty("deg")
    private Double deg;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonProperty("speed")
    public Double getSpeed() {
        return speed;
    }

    @JsonProperty("speed")
    public void setSpeed(Double speed) {
        this.speed = speed;
    }
    public String getDirection (double degrees) {
        if (degrees >= 337.5 || degrees < 22.5) {
            return "Северный";
        } else if (degrees >= 22.5 && degrees < 67.5) {
            return "Северо-восточный";
        } else if (degrees >= 67.5 && degrees < 112.5) {
            return "Восточный";
        } else if (degrees >= 112.5 && degrees < 157.5) {
            return "Юго-восточный";
        } else if (degrees >= 157.5 && degrees < 202.5) {
            return "Южный";
        } else if (degrees >= 202.5 && degrees < 247.5) {
            return "Юго-западный";
        } else if (degrees >= 247.5 && degrees < 292.5) {
            return "Западный";
        } else if (degrees >= 292.5 && degrees < 337.5) {
            return "Северо-западный";
        }
        return "Не определено";
    }
    @JsonProperty("deg")
    public Double getDeg() {
        return deg;
    }

    @JsonProperty("deg")
    public void setDeg(Double deg) {
        this.deg = deg;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}