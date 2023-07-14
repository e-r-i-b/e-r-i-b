
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись с информацией об автоплатеже
 * 
 * <p>Java class for AutoPaymentRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AutoPaymentRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AutoPaymentId" type="{}AutoPaymentId_Type"/>
 *         &lt;element name="AutoSubscriptionInfo" type="{}AutoSubscriptionInfo_Type" minOccurs="0"/>
 *         &lt;element name="AutoPaymentInfo" type="{}AutoPaymentInfo_Type"/>
 *         &lt;element ref="{}RecipientRec" minOccurs="0"/>
 *         &lt;element ref="{}BankAcctRec" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element name="CardAcctTo" type="{}CardAcctId_Type" minOccurs="0"/>
 *           &lt;element name="DepAcctIdTo" type="{}CardAcctId_Type" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element ref="{}CardAuthorization" minOccurs="0"/>
 *         &lt;element name="AutoSubscriptionId" type="{}AutoSubscriptionId_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AutoPaymentRec_Type", propOrder = {
    "autoPaymentId",
    "autoSubscriptionInfo",
    "autoPaymentInfo",
    "recipientRec",
    "bankAcctRecs",
    "depAcctIdTo",
    "cardAcctTo",
    "cardAuthorization",
    "autoSubscriptionId"
})
public class AutoPaymentRecType {

    @XmlElement(name = "AutoPaymentId", required = true)
    protected AutoPaymentIdType autoPaymentId;
    @XmlElement(name = "AutoSubscriptionInfo")
    protected AutoSubscriptionInfoType autoSubscriptionInfo;
    @XmlElement(name = "AutoPaymentInfo", required = true)
    protected AutoPaymentInfoType autoPaymentInfo;
    @XmlElement(name = "RecipientRec")
    protected RecipientRec recipientRec;
    @XmlElement(name = "BankAcctRec")
    protected List<BankAcctRecType> bankAcctRecs;
    @XmlElement(name = "DepAcctIdTo")
    protected CardAcctIdType depAcctIdTo;
    @XmlElement(name = "CardAcctTo")
    protected CardAcctIdType cardAcctTo;
    @XmlElement(name = "CardAuthorization")
    protected CardAuthorization cardAuthorization;
    @XmlElement(name = "AutoSubscriptionId")
    protected AutoSubscriptionIdType autoSubscriptionId;

    /**
     * Gets the value of the autoPaymentId property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public AutoPaymentIdType getAutoPaymentId() {
        return autoPaymentId;
    }

    /**
     * Sets the value of the autoPaymentId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPaymentIdType }
     *     
     */
    public void setAutoPaymentId(AutoPaymentIdType value) {
        this.autoPaymentId = value;
    }

    /**
     * Gets the value of the autoSubscriptionInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionInfoType }
     *     
     */
    public AutoSubscriptionInfoType getAutoSubscriptionInfo() {
        return autoSubscriptionInfo;
    }

    /**
     * Sets the value of the autoSubscriptionInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionInfoType }
     *     
     */
    public void setAutoSubscriptionInfo(AutoSubscriptionInfoType value) {
        this.autoSubscriptionInfo = value;
    }

    /**
     * Gets the value of the autoPaymentInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AutoPaymentInfoType }
     *     
     */
    public AutoPaymentInfoType getAutoPaymentInfo() {
        return autoPaymentInfo;
    }

    /**
     * Sets the value of the autoPaymentInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoPaymentInfoType }
     *     
     */
    public void setAutoPaymentInfo(AutoPaymentInfoType value) {
        this.autoPaymentInfo = value;
    }

    /**
     * Gets the value of the recipientRec property.
     * 
     * @return
     *     possible object is
     *     {@link RecipientRec }
     *     
     */
    public RecipientRec getRecipientRec() {
        return recipientRec;
    }

    /**
     * Sets the value of the recipientRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link RecipientRec }
     *     
     */
    public void setRecipientRec(RecipientRec value) {
        this.recipientRec = value;
    }

    /**
     * Информация о платёжном средстве, с которого производилась оплата Gets the value of the bankAcctRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bankAcctRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBankAcctRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BankAcctRecType }
     * 
     * 
     */
    public List<BankAcctRecType> getBankAcctRecs() {
        if (bankAcctRecs == null) {
            bankAcctRecs = new ArrayList<BankAcctRecType>();
        }
        return this.bankAcctRecs;
    }

    /**
     * Gets the value of the depAcctIdTo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getDepAcctIdTo() {
        return depAcctIdTo;
    }

    /**
     * Sets the value of the depAcctIdTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setDepAcctIdTo(CardAcctIdType value) {
        this.depAcctIdTo = value;
    }

    /**
     * Gets the value of the cardAcctTo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctTo() {
        return cardAcctTo;
    }

    /**
     * Sets the value of the cardAcctTo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctTo(CardAcctIdType value) {
        this.cardAcctTo = value;
    }

    /**
     * Информация об авторизации карты в Way4, если операция прошла успешно
     * 
     * @return
     *     possible object is
     *     {@link CardAuthorization }
     *     
     */
    public CardAuthorization getCardAuthorization() {
        return cardAuthorization;
    }

    /**
     * Sets the value of the cardAuthorization property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAuthorization }
     *     
     */
    public void setCardAuthorization(CardAuthorization value) {
        this.cardAuthorization = value;
    }

    /**
     * Gets the value of the autoSubscriptionId property.
     * 
     * @return
     *     possible object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public AutoSubscriptionIdType getAutoSubscriptionId() {
        return autoSubscriptionId;
    }

    /**
     * Sets the value of the autoSubscriptionId property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutoSubscriptionIdType }
     *     
     */
    public void setAutoSubscriptionId(AutoSubscriptionIdType value) {
        this.autoSubscriptionId = value;
    }

}
