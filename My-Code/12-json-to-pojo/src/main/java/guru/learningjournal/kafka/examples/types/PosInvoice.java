
package guru.learningjournal.kafka.examples.types;

import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "storeId",
    "posId",
    "customerType",
    "deliveryType",
    "contactNumber",
    "deliveryAddress"
})
@Generated("jsonschema2pojo")
public class PosInvoice
    extends Invoice
{

    @JsonProperty("storeId")
    private String storeId;
    @JsonProperty("posId")
    private String posId;
    @JsonProperty("customerType")
    private String customerType;
    @JsonProperty("deliveryType")
    private String deliveryType;
    @JsonProperty("contactNumber")
    private String contactNumber;
    @JsonProperty("deliveryAddress")
    private DeliveryAddress deliveryAddress;

    @JsonProperty("storeId")
    public String getStoreId() {
        return storeId;
    }

    @JsonProperty("storeId")
    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public PosInvoice withStoreId(String storeId) {
        this.storeId = storeId;
        return this;
    }

    @JsonProperty("posId")
    public String getPosId() {
        return posId;
    }

    @JsonProperty("posId")
    public void setPosId(String posId) {
        this.posId = posId;
    }

    public PosInvoice withPosId(String posId) {
        this.posId = posId;
        return this;
    }

    @JsonProperty("customerType")
    public String getCustomerType() {
        return customerType;
    }

    @JsonProperty("customerType")
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public PosInvoice withCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    @JsonProperty("deliveryType")
    public String getDeliveryType() {
        return deliveryType;
    }

    @JsonProperty("deliveryType")
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PosInvoice withDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
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

    public PosInvoice withContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
        return this;
    }

    @JsonProperty("deliveryAddress")
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    @JsonProperty("deliveryAddress")
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public PosInvoice withDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    @Override
    public PosInvoice withInvoiceNumber(String invoiceNumber) {
        super.withInvoiceNumber(invoiceNumber);
        return this;
    }

    @Override
    public PosInvoice withCreatedTime(Long createdTime) {
        super.withCreatedTime(createdTime);
        return this;
    }

    @Override
    public PosInvoice withCustomerCardNo(String customerCardNo) {
        super.withCustomerCardNo(customerCardNo);
        return this;
    }

    @Override
    public PosInvoice withTotalAmount(Double totalAmount) {
        super.withTotalAmount(totalAmount);
        return this;
    }

    @Override
    public PosInvoice withNumberOfItem(Integer numberOfItem) {
        super.withNumberOfItem(numberOfItem);
        return this;
    }

    @Override
    public PosInvoice withPaymentMethod(String paymentMethod) {
        super.withPaymentMethod(paymentMethod);
        return this;
    }

    @Override
    public PosInvoice withTaxableAmount(Double taxableAmount) {
        super.withTaxableAmount(taxableAmount);
        return this;
    }

    @Override
    public PosInvoice withCgst(Double cgst) {
        super.withCgst(cgst);
        return this;
    }

    @Override
    public PosInvoice withSgct(Double sgct) {
        super.withSgct(sgct);
        return this;
    }

    @Override
    public PosInvoice withCess(Double cess) {
        super.withCess(cess);
        return this;
    }

    @Override
    public PosInvoice withInvoiceLineItems(List<LineItem> invoiceLineItems) {
        super.withInvoiceLineItems(invoiceLineItems);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(PosInvoice.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        int baseLength = sb.length();
        String superString = super.toString();
        if (superString!= null) {
            int contentStart = superString.indexOf('[');
            int contentEnd = superString.lastIndexOf(']');
            if ((contentStart >= 0)&&(contentEnd >contentStart)) {
                sb.append(superString, (contentStart + 1), contentEnd);
            } else {
                sb.append(superString);
            }
        }
        if (sb.length()>baseLength) {
            sb.append(',');
        }
        sb.append("storeId");
        sb.append('=');
        sb.append(((this.storeId == null)?"<null>":this.storeId));
        sb.append(',');
        sb.append("posId");
        sb.append('=');
        sb.append(((this.posId == null)?"<null>":this.posId));
        sb.append(',');
        sb.append("customerType");
        sb.append('=');
        sb.append(((this.customerType == null)?"<null>":this.customerType));
        sb.append(',');
        sb.append("deliveryType");
        sb.append('=');
        sb.append(((this.deliveryType == null)?"<null>":this.deliveryType));
        sb.append(',');
        sb.append("contactNumber");
        sb.append('=');
        sb.append(((this.contactNumber == null)?"<null>":this.contactNumber));
        sb.append(',');
        sb.append("deliveryAddress");
        sb.append('=');
        sb.append(((this.deliveryAddress == null)?"<null>":this.deliveryAddress));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
