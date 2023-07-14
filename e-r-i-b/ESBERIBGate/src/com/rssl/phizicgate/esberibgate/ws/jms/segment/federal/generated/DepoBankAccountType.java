
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информации о рублевом счете
 * 
 * <p>Java class for DepoBankAccount_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoBankAccount_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CardType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CardId" type="{}CardNumType" minOccurs="0"/>
 *         &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BIC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CorAccId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CorBankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Dest" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoBankAccount_Type", propOrder = {
    "accId",
    "cardType",
    "cardId",
    "bankName",
    "bic",
    "corAccId",
    "corBankName",
    "dest"
})
public class DepoBankAccountType {

    @XmlElement(name = "AccId", required = true)
    protected String accId;
    @XmlElement(name = "CardType")
    protected String cardType;
    @XmlElement(name = "CardId")
    protected String cardId;
    @XmlElement(name = "BankName", required = true)
    protected String bankName;
    @XmlElement(name = "BIC", required = true)
    protected String bic;
    @XmlElement(name = "CorAccId", required = true)
    protected String corAccId;
    @XmlElement(name = "CorBankName", required = true)
    protected String corBankName;
    @XmlElement(name = "Dest", required = true)
    protected String dest;

    /**
     * Gets the value of the accId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccId() {
        return accId;
    }

    /**
     * Sets the value of the accId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccId(String value) {
        this.accId = value;
    }

    /**
     * Gets the value of the cardType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardType() {
        return cardType;
    }

    /**
     * Sets the value of the cardType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardType(String value) {
        this.cardType = value;
    }

    /**
     * Gets the value of the cardId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * Sets the value of the cardId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardId(String value) {
        this.cardId = value;
    }

    /**
     * Gets the value of the bankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * Sets the value of the bankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankName(String value) {
        this.bankName = value;
    }

    /**
     * Gets the value of the bic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBIC() {
        return bic;
    }

    /**
     * Sets the value of the bic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBIC(String value) {
        this.bic = value;
    }

    /**
     * Gets the value of the corAccId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorAccId() {
        return corAccId;
    }

    /**
     * Sets the value of the corAccId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorAccId(String value) {
        this.corAccId = value;
    }

    /**
     * Gets the value of the corBankName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorBankName() {
        return corBankName;
    }

    /**
     * Sets the value of the corBankName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorBankName(String value) {
        this.corBankName = value;
    }

    /**
     * Gets the value of the dest property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDest() {
        return dest;
    }

    /**
     * Sets the value of the dest property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDest(String value) {
        this.dest = value;
    }

}
