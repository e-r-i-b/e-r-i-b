
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о поставщике
 * 
 * <p>Java class for RecipientRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RecipientRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CodeRecipientBS" type="{}NC" minOccurs="0"/>
 *         &lt;element name="Name" type="{}C" minOccurs="0"/>
 *         &lt;element name="GroupService" type="{}C" minOccurs="0"/>
 *         &lt;element name="CodeService" type="{}NC" minOccurs="0"/>
 *         &lt;element name="NameService" type="{}C" minOccurs="0"/>
 *         &lt;element name="TaxId" type="{}TaxId_Type" minOccurs="0"/>
 *         &lt;element name="CorrAccount" type="{}AcctIdType" minOccurs="0"/>
 *         &lt;element name="KPP" type="{}C" minOccurs="0"/>
 *         &lt;element name="BIC" type="{}String" minOccurs="0"/>
 *         &lt;element name="AcctId" type="{}AcctIdType" minOccurs="0"/>
 *         &lt;element name="NameOnBill" type="{}C" minOccurs="0"/>
 *         &lt;element name="NotVisibleBankDetails" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PhoneToClient" type="{}C" minOccurs="0"/>
 *         &lt;element name="IsAutoPayAccessible" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AutoPayDetails" type="{}AutopayDetails_Type" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="Requisites" type="{}Requisites_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RecipientRec_Type", propOrder = {
    "codeRecipientBS",
    "name",
    "groupService",
    "codeService",
    "nameService",
    "taxId",
    "corrAccount",
    "kpp",
    "bic",
    "acctId",
    "nameOnBill",
    "notVisibleBankDetails",
    "phoneToClient",
    "isAutoPayAccessible",
    "autoPayDetails",
    "bankInfo",
    "requisites"
})
@XmlRootElement(name = "RecipientRec")
public class RecipientRec {

    @XmlElement(name = "CodeRecipientBS")
    protected String codeRecipientBS;
    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "GroupService")
    protected String groupService;
    @XmlElement(name = "CodeService")
    protected String codeService;
    @XmlElement(name = "NameService")
    protected String nameService;
    @XmlElement(name = "TaxId")
    protected String taxId;
    @XmlElement(name = "CorrAccount")
    protected String corrAccount;
    @XmlElement(name = "KPP")
    protected String kpp;
    @XmlElement(name = "BIC")
    protected String bic;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "NameOnBill")
    protected String nameOnBill;
    @XmlElement(name = "NotVisibleBankDetails")
    protected Boolean notVisibleBankDetails;
    @XmlElement(name = "PhoneToClient")
    protected String phoneToClient;
    @XmlElement(name = "IsAutoPayAccessible")
    protected Boolean isAutoPayAccessible;
    @XmlElement(name = "AutoPayDetails")
    protected AutopayDetailsType autoPayDetails;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;
    @XmlElement(name = "Requisites")
    protected RequisitesType requisites;

    /**
     * Gets the value of the codeRecipientBS property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeRecipientBS() {
        return codeRecipientBS;
    }

    /**
     * Sets the value of the codeRecipientBS property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeRecipientBS(String value) {
        this.codeRecipientBS = value;
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
     * Gets the value of the groupService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupService() {
        return groupService;
    }

    /**
     * Sets the value of the groupService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupService(String value) {
        this.groupService = value;
    }

    /**
     * Gets the value of the codeService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeService() {
        return codeService;
    }

    /**
     * Sets the value of the codeService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeService(String value) {
        this.codeService = value;
    }

    /**
     * Gets the value of the nameService property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameService() {
        return nameService;
    }

    /**
     * Sets the value of the nameService property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameService(String value) {
        this.nameService = value;
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
     * Gets the value of the corrAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrAccount() {
        return corrAccount;
    }

    /**
     * Sets the value of the corrAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrAccount(String value) {
        this.corrAccount = value;
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
     * Gets the value of the acctId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the nameOnBill property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNameOnBill() {
        return nameOnBill;
    }

    /**
     * Sets the value of the nameOnBill property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNameOnBill(String value) {
        this.nameOnBill = value;
    }

    /**
     * Gets the value of the notVisibleBankDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getNotVisibleBankDetails() {
        return notVisibleBankDetails;
    }

    /**
     * Sets the value of the notVisibleBankDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setNotVisibleBankDetails(Boolean value) {
        this.notVisibleBankDetails = value;
    }

    /**
     * Gets the value of the phoneToClient property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneToClient() {
        return phoneToClient;
    }

    /**
     * Sets the value of the phoneToClient property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneToClient(String value) {
        this.phoneToClient = value;
    }

    /**
     * Gets the value of the isAutoPayAccessible property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsAutoPayAccessible() {
        return isAutoPayAccessible;
    }

    /**
     * Sets the value of the isAutoPayAccessible property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsAutoPayAccessible(Boolean value) {
        this.isAutoPayAccessible = value;
    }

    /**
     * Gets the value of the autoPayDetails property.
     * 
     * @return
     *     possible object is
     *     {@link AutopayDetailsType }
     *     
     */
    public AutopayDetailsType getAutoPayDetails() {
        return autoPayDetails;
    }

    /**
     * Sets the value of the autoPayDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link AutopayDetailsType }
     *     
     */
    public void setAutoPayDetails(AutopayDetailsType value) {
        this.autoPayDetails = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoType }
     *     
     */
    public BankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoType }
     *     
     */
    public void setBankInfo(BankInfoType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the requisites property.
     * 
     * @return
     *     possible object is
     *     {@link RequisitesType }
     *     
     */
    public RequisitesType getRequisites() {
        return requisites;
    }

    /**
     * Sets the value of the requisites property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequisitesType }
     *     
     */
    public void setRequisites(RequisitesType value) {
        this.requisites = value;
    }

}
