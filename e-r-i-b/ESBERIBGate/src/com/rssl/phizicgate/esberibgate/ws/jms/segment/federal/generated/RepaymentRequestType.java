
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RepaymentRequest_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepaymentRequest_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TerminationRequestId" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="ERIBRequestID" type="{}String" minOccurs="0"/>
 *         &lt;element name="ProdId" type="{}AgreemtNum_Type"/>
 *         &lt;element name="PayAccount" type="{}AcctIdType"/>
 *         &lt;element name="Type" type="{}String"/>
 *         &lt;element name="RepaymentDate" type="{}Date"/>
 *         &lt;element name="Amount" type="{}Amt_Type"/>
 *         &lt;element name="Currency" type="{}AcctCur_Type"/>
 *         &lt;element name="FullRepayment" type="{}Long" minOccurs="0"/>
 *         &lt;element name="DateOfSign" type="{}Date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepaymentRequest_Type", propOrder = {
    "terminationRequestId",
    "eribRequestID",
    "prodId",
    "payAccount",
    "type",
    "repaymentDate",
    "amount",
    "currency",
    "fullRepayment",
    "dateOfSign"
})
public class RepaymentRequestType {

    @XmlElement(name = "TerminationRequestId")
    protected Long terminationRequestId;
    @XmlElement(name = "ERIBRequestID")
    protected String eribRequestID;
    @XmlElement(name = "ProdId", required = true)
    protected String prodId;
    @XmlElement(name = "PayAccount", required = true)
    protected String payAccount;
    @XmlElement(name = "Type", required = true)
    protected String type;
    @XmlElement(name = "RepaymentDate", required = true)
    protected String repaymentDate;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "Currency", required = true)
    protected String currency;
    @XmlElement(name = "FullRepayment")
    protected Long fullRepayment;
    @XmlElement(name = "DateOfSign", required = true)
    protected String dateOfSign;

    /**
     * Gets the value of the terminationRequestId property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getTerminationRequestId() {
        return terminationRequestId;
    }

    /**
     * Sets the value of the terminationRequestId property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setTerminationRequestId(Long value) {
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

    /**
     * Gets the value of the prodId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdId() {
        return prodId;
    }

    /**
     * Sets the value of the prodId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdId(String value) {
        this.prodId = value;
    }

    /**
     * Gets the value of the payAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayAccount() {
        return payAccount;
    }

    /**
     * Sets the value of the payAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayAccount(String value) {
        this.payAccount = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the repaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepaymentDate() {
        return repaymentDate;
    }

    /**
     * Sets the value of the repaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepaymentDate(String value) {
        this.repaymentDate = value;
    }

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
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

    /**
     * Gets the value of the fullRepayment property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getFullRepayment() {
        return fullRepayment;
    }

    /**
     * Sets the value of the fullRepayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setFullRepayment(Long value) {
        this.fullRepayment = value;
    }

    /**
     * Gets the value of the dateOfSign property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDateOfSign() {
        return dateOfSign;
    }

    /**
     * Sets the value of the dateOfSign property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDateOfSign(String value) {
        this.dateOfSign = value;
    }

}
