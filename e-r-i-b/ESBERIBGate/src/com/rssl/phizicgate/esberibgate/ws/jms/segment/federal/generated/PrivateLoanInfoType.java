
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PrivateLoanInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrivateLoanInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProdId" type="{}AgreemtNum_Type"/>
 *         &lt;element name="RecPayment" type="{}Amt_Type"/>
 *         &lt;element name="PaymentDate" type="{}Date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrivateLoanInfo_Type", propOrder = {
    "prodId",
    "recPayment",
    "paymentDate"
})
public class PrivateLoanInfoType {

    @XmlElement(name = "ProdId", required = true)
    protected String prodId;
    @XmlElement(name = "RecPayment", required = true)
    protected BigDecimal recPayment;
    @XmlElement(name = "PaymentDate", required = true)
    protected String paymentDate;

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
     * Gets the value of the recPayment property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRecPayment() {
        return recPayment;
    }

    /**
     * Sets the value of the recPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRecPayment(BigDecimal value) {
        this.recPayment = value;
    }

    /**
     * Gets the value of the paymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentDate() {
        return paymentDate;
    }

    /**
     * Sets the value of the paymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentDate(String value) {
        this.paymentDate = value;
    }

}
