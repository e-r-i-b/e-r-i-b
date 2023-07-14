
package com.rssl.phizgate.basket.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о платёжном средстве, с которого производилась оплата
 * 
 * <p>Java class for BankAcctRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CardAcctId" type="{}CardAcctId_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctRec_Type", propOrder = {
    "cardAcctId"
})
public class BankAcctRecType {

    @XmlElement(name = "CardAcctId", required = true)
    protected CardAcctIdType cardAcctId;

    /**
     * Gets the value of the cardAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctIdType }
     *     
     */
    public CardAcctIdType getCardAcctId() {
        return cardAcctId;
    }

    /**
     * Sets the value of the cardAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctIdType }
     *     
     */
    public void setCardAcctId(CardAcctIdType value) {
        this.cardAcctId = value;
    }

}
