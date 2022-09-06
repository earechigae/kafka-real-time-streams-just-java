
package guru.learningjournal.kafka.examples.types;

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "StoreID",
    "PosID",
    "CustomerType",
    "DeliveryType",
    "DeliveryAddress"
})
@Generated("jsonschema2pojo")
public class PosInvoice
    extends Invoice
{

    @JsonProperty("StoreID")
    private String storeID;
    @JsonProperty("PosID")
    private String posID;
    @JsonProperty("CustomerType")
    private String customerType;
    @JsonProperty("DeliveryType")
    private String deliveryType;
    @JsonProperty("DeliveryAddress")
    private DeliveryAddress deliveryAddress;

    @JsonProperty("StoreID")
    public String getStoreID() {
        return storeID;
    }

    @JsonProperty("StoreID")
    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public PosInvoice withStoreID(String storeID) {
        this.storeID = storeID;
        return this;
    }

    @JsonProperty("PosID")
    public String getPosID() {
        return posID;
    }

    @JsonProperty("PosID")
    public void setPosID(String posID) {
        this.posID = posID;
    }

    public PosInvoice withPosID(String posID) {
        this.posID = posID;
        return this;
    }

    @JsonProperty("CustomerType")
    public String getCustomerType() {
        return customerType;
    }

    @JsonProperty("CustomerType")
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public PosInvoice withCustomerType(String customerType) {
        this.customerType = customerType;
        return this;
    }

    @JsonProperty("DeliveryType")
    public String getDeliveryType() {
        return deliveryType;
    }

    @JsonProperty("DeliveryType")
    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public PosInvoice withDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    @JsonProperty("DeliveryAddress")
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    @JsonProperty("DeliveryAddress")
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
    public PosInvoice withCreatedTime(CreatedTime createdTime) {
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
        sb.append("storeID");
        sb.append('=');
        sb.append(((this.storeID == null)?"<null>":this.storeID));
        sb.append(',');
        sb.append("posID");
        sb.append('=');
        sb.append(((this.posID == null)?"<null>":this.posID));
        sb.append(',');
        sb.append("customerType");
        sb.append('=');
        sb.append(((this.customerType == null)?"<null>":this.customerType));
        sb.append(',');
        sb.append("deliveryType");
        sb.append('=');
        sb.append(((this.deliveryType == null)?"<null>":this.deliveryType));
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
