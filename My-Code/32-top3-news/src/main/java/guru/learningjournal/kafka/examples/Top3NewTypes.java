package guru.learningjournal.kafka.examples;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.learningjournal.kafka.examples.types.ClicksByNewsType;

import java.io.IOError;
import java.io.IOException;
import java.util.TreeSet;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "top3Sorted"
})
public class Top3NewTypes {
    private ObjectMapper mapper = new ObjectMapper();

    @JsonProperty("top3Sorted")
    private final TreeSet<ClicksByNewsType> top3Sorted = new TreeSet<>((o1, o2) -> {
        final int result = o2.getClicks().compareTo(o1.getClicks());
        if(result != 0){
            return result;
        }else{
            return o1.getNewsType().compareToIgnoreCase(o2.getNewsType());
        }
    });

    public void add(ClicksByNewsType newValue){
        top3Sorted.add(newValue);
        if(top3Sorted.size() > 3){
            top3Sorted.remove(top3Sorted.last());
        }
    }

    public void remove(ClicksByNewsType oldValue){
        top3Sorted.remove(oldValue);
    }

    @JsonProperty("top3Sorted")
    public String getTop3Sorted() throws JsonProcessingException {
        return mapper.writeValueAsString(top3Sorted);
    }

    @JsonProperty("top3Sorted")
    public void setTop3Sorted(String toeString) throws IOException {
        ClicksByNewsType[] top3 = mapper.readValue(toeString, ClicksByNewsType[].class);
        for(ClicksByNewsType i:top3){
            add(i);
        }
    }
}
