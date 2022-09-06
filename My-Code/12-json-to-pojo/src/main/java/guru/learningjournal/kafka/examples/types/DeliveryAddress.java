
package guru.learningjournal.kafka.examples.types;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "addressLine",
    "city",
    "state",
    "pinCode",
    "contactNumber"
})
@Generated("jsonschema2pojo")
public class DeliveryAddress {

    @JsonProperty("addressLine")
    private String addressLine;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("pinCode")
    private String pinCode;
    @JsonProperty("contactNumber")
    private String contactNumber;

    @JsonProperty("addressLine")
    public String getAddressLine() {
        return addressLine;
    }

    @JsonProperty("addressLine")
    public void setAddressLine(String addressLine) {
        this.addressLine = addressLine;
    }

    public DeliveryAddress withAddressLine(String addressLine) {
        this.addressLine = addressLine;
        return this;
    }

    @JsonProperty("city")
    public String getCity() {
        return city;
    }

    @JsonProperty("city")
    public void setCity(String city) {
        this.city = city;
    }

    public DeliveryAddress withCity(String city) {
        this.city = city;
        return this;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    public DeliveryAddress withState(String state) {
        this.state = state;
        return this;
    }

    @JsonProperty("pinCode")
    public String getPinCode() {
        return pinCode;
    }

    @JsonProperty("pinCode")
    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    public DeliveryAddress withPinCode(String pinCode) {
        this.pinCode = pinCode;
        return this;
    }

    @JsonProperty("contactNumber")
    public String getContactNumber() {
        return contactNumber;
    }

    @JsonProperty("contactNumber")
    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public DeliveryAddress withContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DeliveryAddress.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("addressLine");
        sb.append('=');
        sb.append(((this.addressLine == null)?"<null>":this.addressLine));
        sb.append(',');
        sb.append("city");
        sb.append('=');
        sb.append(((this.city == null)?"<null>":this.city));
        sb.append(',');
        sb.append("state");
        sb.append('=');
        sb.append(((this.state == null)?"<null>":this.state));
        sb.append(',');
        sb.append("pinCode");
        sb.append('=');
        sb.append(((this.pinCode == null)?"<null>":this.pinCode));
        sb.append(',');
        sb.append("contactNumber");
        sb.append('=');
        sb.append(((this.contactNumber == null)?"<null>":this.contactNumber));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
