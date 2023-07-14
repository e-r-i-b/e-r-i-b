
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for EarlyRepayment_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EarlyRepayment_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Amount" type="{}Decimal"/>
 *         &lt;element name="Date" type="{}Date"/>
 *         &lt;element name="Status" type="{}String"/>
 *         &lt;element name="Account" type="{}AcctIdType"/>
 *         &lt;element name="RepaymentChannel" type="{}String"/>
 *         &lt;element name="TerminationRequestId" type="{}String"/>
 *         &lt;element name="ERIBRequestID" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EarlyRepayment_Type", propOrder = {
    "amount",
    "date",
    "status",
    "account",
    "repaymentChannel",
    "terminationRequestId",
    "eribRequestID"
})
public class EarlyRepaymentType {

    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "Date", required = true)
    protected String date;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "Account", required = true)
    protected String account;
    @XmlElement(name = "RepaymentChannel", required = true)
    protected String repaymentChannel;
    @XmlElement(name = "TerminationRequestId", required = true)
    protected String terminationRequestId;
    @XmlElement(name = "ERIBRequestID")
    protected String eribRequestID;

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAmount(BigDecimal value) {
        this.amount = value;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDate(String value) {
        this.date = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * Gets the value of the repaymentChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepaymentChannel() {
        return repaymentChannel;
    }

    /**
     * Sets the value of the repaymentChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepaymentChannel(String value) {
        this.repaymentChannel = value;
    }

    /**
     * Gets the value of the terminationRequestId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerminationRequestId() {
        return terminationRequestId;
    }

    /**
     * Sets the value of the terminationRequestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerminationRequestId(String value) {
        this.terminationRequestId = value;
    }

    /**
     * Gets the value of the eribRequestID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERIBRequestID() {
        return eribRequestID;
    }

    /**
     * Sets the value of the eribRequestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERIBRequestID(String value) {
        this.eribRequestID = value;
    }

}
