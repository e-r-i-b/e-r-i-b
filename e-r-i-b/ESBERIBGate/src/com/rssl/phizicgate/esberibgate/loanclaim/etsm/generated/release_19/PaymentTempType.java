
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о шаблоне платежа
 * 
 * <p>Java class for PaymentTemp_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentTemp_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RecipientID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecipientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PaymentsID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTemp_Type", propOrder = {
    "recipientID",
    "recipientName",
    "paymentsID"
})
public class PaymentTempType {

    @XmlElement(name = "RecipientID", required = true)
    protected String recipientID;
    @XmlElement(name = "RecipientName", required = true)
    protected String recipientName;
    @XmlElement(name = "PaymentsID", required = true)
    protected String paymentsID;

    /**
     * Gets the value of the recipientID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientID() {
        return recipientID;
    }

    /**
     * Sets the value of the recipientID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientID(String value) {
        this.recipientID = value;
    }

    /**
     * Gets the value of the recipientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientName() {
        return recipientName;
    }

    /**
     * Sets the value of the recipientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientName(String value) {
        this.recipientName = value;
    }

    /**
     * Gets the value of the paymentsID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaymentsID() {
        return paymentsID;
    }

    /**
     * Sets the value of the paymentsID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaymentsID(String value) {
        this.paymentsID = value;
    }

}
