
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Элементы для передачи информации о порядке уплаты процентов
 * 
 * <p>Java class for VariantInterestPayment_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VariantInterestPayment_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsInterestToCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardNumber" type="{}CardNumType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VariantInterestPayment_Type", propOrder = {
    "isInterestToCard",
    "cardNumber"
})
public class VariantInterestPaymentType {

    @XmlElement(name = "IsInterestToCard", required = true)
    protected String isInterestToCard;
    @XmlElement(name = "CardNumber")
    protected String cardNumber;

    /**
     * Gets the value of the isInterestToCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInterestToCard() {
        return isInterestToCard;
    }

    /**
     * Sets the value of the isInterestToCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInterestToCard(String value) {
        this.isInterestToCard = value;
    }

    /**
     * Gets the value of the cardNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNumber() {
        return cardNumber;
    }

    /**
     * Sets the value of the cardNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNumber(String value) {
        this.cardNumber = value;
    }

}
