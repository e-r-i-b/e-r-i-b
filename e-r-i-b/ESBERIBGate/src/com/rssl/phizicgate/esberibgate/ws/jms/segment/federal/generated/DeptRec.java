
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Реквизиты для оплаты задолженности ДЕПО
 * 
 * <p>Java class for DeptRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeptRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BIC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BankName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CorBankAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecipientName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TaxId" type="{}TaxId_Type"/>
 *         &lt;element name="KPP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecipientAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeptRec_Type", propOrder = {
    "bic",
    "bankName",
    "corBankAccount",
    "recipientName",
    "taxId",
    "kpp",
    "recipientAccount"
})
@XmlRootElement(name = "DeptRec")
public class DeptRec {

    @XmlElement(name = "BIC", required = true)
    protected String bic;
    @XmlElement(name = "BankName", required = true)
    protected String bankName;
    @XmlElement(name = "CorBankAccount", required = true)
    protected String corBankAccount;
    @XmlElement(name = "RecipientName", required = true)
    protected String recipientName;
    @XmlElement(name = "TaxId", required = true)
    protected String taxId;
    @XmlElement(name = "KPP", required = true)
    protected String kpp;
    @XmlElement(name = "RecipientAccount", required = true)
    protected String recipientAccount;

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
     * Gets the value of the corBankAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorBankAccount() {
        return corBankAccount;
    }

    /**
     * Sets the value of the corBankAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorBankAccount(String value) {
        this.corBankAccount = value;
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
     * Gets the value of the taxId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Gets the value of the kpp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKPP() {
        return kpp;
    }

    /**
     * Sets the value of the kpp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKPP(String value) {
        this.kpp = value;
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

}
