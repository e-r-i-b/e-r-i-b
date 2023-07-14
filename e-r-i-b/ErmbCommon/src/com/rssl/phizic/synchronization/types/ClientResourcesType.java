
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Все продукты клиента
 * 
 * <p>Java class for ClientResourcesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientResourcesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cards" type="{}CardsType" minOccurs="0"/>
 *         &lt;element name="accounts" type="{}AccountsType" minOccurs="0"/>
 *         &lt;element name="loans" type="{}LoansType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientResourcesType", propOrder = {
    "cards",
    "accounts",
    "loans"
})
public class ClientResourcesType {

    protected CardsType cards;
    protected AccountsType accounts;
    protected LoansType loans;

    /**
     * Gets the value of the cards property.
     * 
     * @return
     *     possible object is
     *     {@link CardsType }
     *     
     */
    public CardsType getCards() {
        return cards;
    }

    /**
     * Sets the value of the cards property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardsType }
     *     
     */
    public void setCards(CardsType value) {
        this.cards = value;
    }

    /**
     * Gets the value of the accounts property.
     * 
     * @return
     *     possible object is
     *     {@link AccountsType }
     *     
     */
    public AccountsType getAccounts() {
        return accounts;
    }

    /**
     * Sets the value of the accounts property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountsType }
     *     
     */
    public void setAccounts(AccountsType value) {
        this.accounts = value;
    }

    /**
     * Gets the value of the loans property.
     * 
     * @return
     *     possible object is
     *     {@link LoansType }
     *     
     */
    public LoansType getLoans() {
        return loans;
    }

    /**
     * Sets the value of the loans property.
     * 
     * @param value
     *     allowed object is
     *     {@link LoansType }
     *     
     */
    public void setLoans(LoansType value) {
        this.loans = value;
    }

}
