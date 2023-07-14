
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Запись о выписке
 * 
 * <p>Java class for DepAcctStmtRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcctStmtRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}EffDate"/>
 *         &lt;element name="DocumentNumber" type="{}String" minOccurs="0"/>
 *         &lt;element name="Name" type="{}String" minOccurs="0"/>
 *         &lt;element name="Aspect" type="{}String"/>
 *         &lt;element name="IsDebit" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Amount" type="{}Decimal"/>
 *         &lt;element name="AmountCur" type="{}AcctCur_Type"/>
 *         &lt;element name="Correspondent" type="{}String" minOccurs="0"/>
 *         &lt;element name="Balance" type="{}Decimal"/>
 *         &lt;element name="BalanceCur" type="{}AcctCur_Type"/>
 *         &lt;element name="Recipient" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecipientAccount" type="{}String" minOccurs="0"/>
 *         &lt;element name="RecipientBIC" type="{}String" minOccurs="0"/>
 *         &lt;element name="Destination" type="{}String" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAcctStmtRec_Type", propOrder = {
    "effDate",
    "documentNumber",
    "name",
    "aspect",
    "isDebit",
    "amount",
    "amountCur",
    "correspondent",
    "balance",
    "balanceCur",
    "recipient",
    "recipientAccount",
    "recipientBIC",
    "destination"
})
@XmlRootElement(name = "DepAcctStmtRec")
public class DepAcctStmtRec {

    @XmlElement(name = "EffDate", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar effDate;
    @XmlElement(name = "DocumentNumber")
    protected String documentNumber;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Aspect", required = true)
    protected String aspect;
    @XmlElement(name = "IsDebit")
    protected Boolean isDebit;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "AmountCur", required = true)
    protected String amountCur;
    @XmlElement(name = "Correspondent")
    protected String correspondent;
    @XmlElement(name = "Balance", required = true)
    protected BigDecimal balance;
    @XmlElement(name = "BalanceCur", required = true)
    protected String balanceCur;
    @XmlElement(name = "Recipient")
    protected String recipient;
    @XmlElement(name = "RecipientAccount")
    protected String recipientAccount;
    @XmlElement(name = "RecipientBIC")
    protected String recipientBIC;
    @XmlElement(name = "Destination")
    protected String destination;

    /**
     * Gets the value of the effDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDate() {
        return effDate;
    }

    /**
     * Sets the value of the effDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDate(Calendar value) {
        this.effDate = value;
    }

    /**
     * Gets the value of the documentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDocumentNumber() {
        return documentNumber;
    }

    /**
     * Sets the value of the documentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDocumentNumber(String value) {
        this.documentNumber = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the aspect property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAspect() {
        return aspect;
    }

    /**
     * Sets the value of the aspect property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAspect(String value) {
        this.aspect = value;
    }

    /**
     * Gets the value of the isDebit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsDebit() {
        return isDebit;
    }

    /**
     * Sets the value of the isDebit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDebit(Boolean value) {
        this.isDebit = value;
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
     * Gets the value of the amountCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmountCur() {
        return amountCur;
    }

    /**
     * Sets the value of the amountCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmountCur(String value) {
        this.amountCur = value;
    }

    /**
     * Gets the value of the correspondent property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrespondent() {
        return correspondent;
    }

    /**
     * Sets the value of the correspondent property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrespondent(String value) {
        this.correspondent = value;
    }

    /**
     * Gets the value of the balance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Sets the value of the balance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBalance(BigDecimal value) {
        this.balance = value;
    }

    /**
     * Gets the value of the balanceCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBalanceCur() {
        return balanceCur;
    }

    /**
     * Sets the value of the balanceCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBalanceCur(String value) {
        this.balanceCur = value;
    }

    /**
     * Gets the value of the recipient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipient() {
        return recipient;
    }

    /**
     * Sets the value of the recipient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipient(String value) {
        this.recipient = value;
    }

    /**
     * Gets the value of the recipientAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientAccount() {
        return recipientAccount;
    }

    /**
     * Sets the value of the recipientAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientAccount(String value) {
        this.recipientAccount = value;
    }

    /**
     * Gets the value of the recipientBIC property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecipientBIC() {
        return recipientBIC;
    }

    /**
     * Sets the value of the recipientBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecipientBIC(String value) {
        this.recipientBIC = value;
    }

    /**
     * Gets the value of the destination property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Sets the value of the destination property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDestination(String value) {
        this.destination = value;
    }

}
