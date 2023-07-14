
package com.rssl.phizgate.basket.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Список задолженностей по АП
 * 
 * <p>Java class for Payment_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Payment_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PaymentId" type="{}PaymentId_Type"/>
 *         &lt;element name="PaymentInfo" type="{}PaymentInfo_Type"/>
 *         &lt;element name="RecipientRec" type="{}RecipientRec_Type"/>
 *         &lt;element name="BankAcctRec" type="{}BankAcctRec_Type"/>
 *         &lt;element name="CardAuthorization" type="{}CardAuthorization_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Payment_Type", propOrder = {
    "paymentId",
    "paymentInfo",
    "recipientRec",
    "bankAcctRec",
    "cardAuthorization"
})
public class PaymentType {

    @XmlElement(name = "PaymentId", required = true)
    protected PaymentIdType paymentId;
    @XmlElement(name = "PaymentInfo", required = true)
    protected PaymentInfoType paymentInfo;
    @XmlElement(name = "RecipientRec", required = true)
    protected RecipientRecType recipientRec;
    @XmlElement(name = "BankAcctRec", required = true)
    protected BankAcctRecType bankAcctRec;
    @XmlElement(name = "CardAuthorization")
    protected CardAuthorizationType cardAuthorization;

    /**
     * Gets the value of the paymentId property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdType }
     *     
     */
    public PaymentIdType getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the value of the paymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdType }
     *     
     */
    public void setPaymentId(PaymentIdType value) {
        this.paymentId = value;
    }

    /**
     * Gets the value of the paymentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentInfoType }
     *     
     */
    public PaymentInfoType getPaymentInfo() {
        return paymentInfo;
    }

    /**
     * Sets the value of the paymentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentInfoType }
     *     
     */
    public void setPaymentInfo(PaymentInfoType value) {
        this.paymentInfo = value;
    }

    /**
     * Gets the value of the recipientRec property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRecType }
     *     
     */
    public RecipientRecType getRecipientRec() {
        return recipientRec;
    }

    /**
     * Sets the value of the recipientRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRecType }
     *     
     */
    public void setRecipientRec(RecipientRecType value) {
        this.recipientRec = value;
    }

    /**
     * Gets the value of the bankAcctRec property.
     * 
     * @return
     *     possible object is
     *     {@link BankAcctRecType }
     *     
     */
    public BankAcctRecType getBankAcctRec() {
        return bankAcctRec;
    }

    /**
     * Sets the value of the bankAcctRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankAcctRecType }
     *     
     */
    public void setBankAcctRec(BankAcctRecType value) {
        this.bankAcctRec = value;
    }

    /**
     * Gets the value of the cardAuthorization property.
     * 
     * @return
     *     possible object is
     *     {@link CardAuthorizationType }
     *     
     */
    public CardAuthorizationType getCardAuthorization() {
        return cardAuthorization;
    }

    /**
     * Sets the value of the cardAuthorization property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAuthorizationType }
     *     
     */
    public void setCardAuthorization(CardAuthorizationType value) {
        this.cardAuthorization = value;
    }

}
