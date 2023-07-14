
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Реквизиты операции в ВСП
 * 
 * <p>Java class for VSPOperation_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VSPOperation_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ClientName" type="{}FullName_Type" minOccurs="0"/>
 *         &lt;element name="ClientAccount" type="{}AcctIdType" minOccurs="0"/>
 *         &lt;element name="IsDebit" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Amount" type="{}Decimal"/>
 *         &lt;element name="PayDate" type="{}Date"/>
 *         &lt;element name="AuthorizationCode" type="{}Long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VSPOperation_Type", propOrder = {
    "clientName",
    "clientAccount",
    "isDebit",
    "amount",
    "payDate",
    "authorizationCode"
})
public class VSPOperationType {

    @XmlElement(name = "ClientName")
    protected String clientName;
    @XmlElement(name = "ClientAccount")
    protected String clientAccount;
    @XmlElement(name = "IsDebit")
    protected boolean isDebit;
    @XmlElement(name = "Amount", required = true)
    protected BigDecimal amount;
    @XmlElement(name = "PayDate", required = true)
    protected String payDate;
    @XmlElement(name = "AuthorizationCode")
    protected long authorizationCode;

    /**
     * Gets the value of the clientName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientName() {
        return clientName;
    }

    /**
     * Sets the value of the clientName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientName(String value) {
        this.clientName = value;
    }

    /**
     * Gets the value of the clientAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientAccount() {
        return clientAccount;
    }

    /**
     * Sets the value of the clientAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientAccount(String value) {
        this.clientAccount = value;
    }

    /**
     * Gets the value of the isDebit property.
     * 
     */
    public boolean isIsDebit() {
        return isDebit;
    }

    /**
     * Sets the value of the isDebit property.
     * 
     */
    public void setIsDebit(boolean value) {
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
     * Gets the value of the payDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayDate() {
        return payDate;
    }

    /**
     * Sets the value of the payDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayDate(String value) {
        this.payDate = value;
    }

    /**
     * Gets the value of the authorizationCode property.
     * 
     */
    public long getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Sets the value of the authorizationCode property.
     * 
     */
    public void setAuthorizationCode(long value) {
        this.authorizationCode = value;
    }

}
