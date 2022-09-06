
package guru.learningjournal.kafka.examples.types;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "invoiceNumber",
    "createdTime",
    "customerCardNo",
    "totalAmount",
    "numberOfItem",
    "paymentMethod",
    "taxableAmount",
    "CGST",
    "SGCT",
    "CESS",
    "invoiceLineItems"
})
@Generated("jsonschema2pojo")
public class Invoice {

    @JsonProperty("invoiceNumber")
    private String invoiceNumber;
    @JsonProperty("createdTime")
    private Long createdTime;
    @JsonProperty("customerCardNo")
    private String customerCardNo;
    @JsonProperty("totalAmount")
    private Double totalAmount;
    @JsonProperty("numberOfItem")
    private Integer numberOfItem;
    @JsonProperty("paymentMethod")
    private String paymentMethod;
    @JsonProperty("taxableAmount")
    private Double taxableAmount;
    @JsonProperty("CGST")
    private Double cgst;
    @JsonProperty("SGCT")
    private Double sgct;
    @JsonProperty("CESS")
    private Double cess;
    @JsonProperty("invoiceLineItems")
    private List<LineItem> invoiceLineItems = new ArrayList<LineItem>();

    @JsonProperty("invoiceNumber")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    @JsonProperty("invoiceNumber")
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Invoice withInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
        return this;
    }

    @JsonProperty("createdTime")
    public Long getCreatedTime() {
        return createdTime;
    }

    @JsonProperty("createdTime")
    public void setCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
    }

    public Invoice withCreatedTime(Long createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    @JsonProperty("customerCardNo")
    public String getCustomerCardNo() {
        return customerCardNo;
    }

    @JsonProperty("customerCardNo")
    public void setCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo;
    }

    public Invoice withCustomerCardNo(String customerCardNo) {
        this.customerCardNo = customerCardNo;
        return this;
    }

    @JsonProperty("totalAmount")
    public Double getTotalAmount() {
        return totalAmount;
    }

    @JsonProperty("totalAmount")
    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Invoice withTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
        return this;
    }

    @JsonProperty("numberOfItem")
    public Integer getNumberOfItem() {
        return numberOfItem;
    }

    @JsonProperty("numberOfItem")
    public void setNumberOfItem(Integer numberOfItem) {
        this.numberOfItem = numberOfItem;
    }

    public Invoice withNumberOfItem(Integer numberOfItem) {
        this.numberOfItem = numberOfItem;
        return this;
    }

    @JsonProperty("paymentMethod")
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @JsonProperty("paymentMethod")
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Invoice withPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    @JsonProperty("taxableAmount")
    public Double getTaxableAmount() {
        return taxableAmount;
    }

    @JsonProperty("taxableAmount")
    public void setTaxableAmount(Double taxableAmount) {
        this.taxableAmount = taxableAmount;
    }

    public Invoice withTaxableAmount(Double taxableAmount) {
        this.taxableAmount = taxableAmount;
        return this;
    }

    @JsonProperty("CGST")
    public Double getCgst() {
        return cgst;
    }

    @JsonProperty("CGST")
    public void setCgst(Double cgst) {
        this.cgst = cgst;
    }

    public Invoice withCgst(Double cgst) {
        this.cgst = cgst;
        return this;
    }

    @JsonProperty("SGCT")
    public Double getSgct() {
        return sgct;
    }

    @JsonProperty("SGCT")
    public void setSgct(Double sgct) {
        this.sgct = sgct;
    }

    public Invoice withSgct(Double sgct) {
        this.sgct = sgct;
        return this;
    }

    @JsonProperty("CESS")
    public Double getCess() {
        return cess;
    }

    @JsonProperty("CESS")
    public void setCess(Double cess) {
        this.cess = cess;
    }

    public Invoice withCess(Double cess) {
        this.cess = cess;
        return this;
    }

    @JsonProperty("invoiceLineItems")
    public List<LineItem> getInvoiceLineItems() {
        return invoiceLineItems;
    }

    @JsonProperty("invoiceLineItems")
    public void setInvoiceLineItems(List<LineItem> invoiceLineItems) {
        this.invoiceLineItems = invoiceLineItems;
    }

    public Invoice withInvoiceLineItems(List<LineItem> invoiceLineItems) {
        this.invoiceLineItems = invoiceLineItems;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Invoice.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("invoiceNumber");
        sb.append('=');
        sb.append(((this.invoiceNumber == null)?"<null>":this.invoiceNumber));
        sb.append(',');
        sb.append("createdTime");
        sb.append('=');
        sb.append(((this.createdTime == null)?"<null>":this.createdTime));
        sb.append(',');
        sb.append("customerCardNo");
        sb.append('=');
        sb.append(((this.customerCardNo == null)?"<null>":this.customerCardNo));
        sb.append(',');
        sb.append("totalAmount");
        sb.append('=');
        sb.append(((this.totalAmount == null)?"<null>":this.totalAmount));
        sb.append(',');
        sb.append("numberOfItem");
        sb.append('=');
        sb.append(((this.numberOfItem == null)?"<null>":this.numberOfItem));
        sb.append(',');
        sb.append("paymentMethod");
        sb.append('=');
        sb.append(((this.paymentMethod == null)?"<null>":this.paymentMethod));
        sb.append(',');
        sb.append("taxableAmount");
        sb.append('=');
        sb.append(((this.taxableAmount == null)?"<null>":this.taxableAmount));
        sb.append(',');
        sb.append("cgst");
        sb.append('=');
        sb.append(((this.cgst == null)?"<null>":this.cgst));
        sb.append(',');
        sb.append("sgct");
        sb.append('=');
        sb.append(((this.sgct == null)?"<null>":this.sgct));
        sb.append(',');
        sb.append("cess");
        sb.append('=');
        sb.append(((this.cess == null)?"<null>":this.cess));
        sb.append(',');
        sb.append("invoiceLineItems");
        sb.append('=');
        sb.append(((this.invoiceLineItems == null)?"<null>":this.invoiceLineItems));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
