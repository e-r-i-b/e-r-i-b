
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запрос интерфейса TBP_PAY проводки билингового платежа
 * 
 * <p>Java class for BillingPayExecRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BillingPayExecRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RqUID"/>
 *         &lt;element ref="{}RqTm"/>
 *         &lt;element ref="{}OperUID"/>
 *         &lt;element ref="{}SPName"/>
 *         &lt;element name="SBOLUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SBOLTm" type="{}DateTime"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}SystemId"/>
 *         &lt;element ref="{}CardAcctId"/>
 *         &lt;element ref="{}RecipientRec"/>
 *         &lt;element name="Commission" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="CommissionCur" type="{}AcctCur_Type"/>
 *         &lt;element name="MadeOperationId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AuthorizationCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AuthorizationDtTm" type="{}DateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillingPayExecRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "sboluid",
    "sbolTm",
    "bankInfo",
    "systemId",
    "cardAcctId",
    "recipientRec",
    "commission",
    "commissionCur",
    "madeOperationId",
    "authorizationCode",
    "authorizationDtTm"
})
@XmlRootElement(name = "BillingPayExecRq")
public class BillingPayExecRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SBOLUID", required = true)
    protected String sboluid;
    @XmlElement(name = "SBOLTm", required = true)
    protected String sbolTm;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "CardAcctId", required = true)
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "RecipientRec", required = true)
    protected RecipientRec recipientRec;
    @XmlElement(name = "Commission", required = true)
    protected BigDecimal commission;
    @XmlElement(name = "CommissionCur", required = true)
    protected String commissionCur;
    @XmlElement(name = "MadeOperationId", required = true)
    protected String madeOperationId;
    @XmlElement(name = "AuthorizationCode")
    protected Long authorizationCode;
    @XmlElement(name = "AuthorizationDtTm")
    protected String authorizationDtTm;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(String value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the operUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperUID() {
        return operUID;
    }

    /**
     * Sets the value of the operUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperUID(String value) {
        this.operUID = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the sboluid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBOLUID() {
        return sboluid;
    }

    /**
     * Sets the value of the sboluid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBOLUID(String value) {
        this.sboluid = value;
    }

    /**
     * Gets the value of the sbolTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSBOLTm() {
        return sbolTm;
    }

    /**
     * Sets the value of the sbolTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSBOLTm(String value) {
        this.sbolTm = value;
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
     * Gets the value of the systemId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemId() {
        return systemId;
    }

    /**
     * Sets the value of the systemId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemId(String value) {
        this.systemId = value;
    }

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
     * Gets the value of the commission property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getCommission() {
        return commission;
    }

    /**
     * Sets the value of the commission property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setCommission(BigDecimal value) {
        this.commission = value;
    }

    /**
     * Gets the value of the commissionCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCommissionCur() {
        return commissionCur;
    }

    /**
     * Sets the value of the commissionCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCommissionCur(String value) {
        this.commissionCur = value;
    }

    /**
     * Gets the value of the madeOperationId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMadeOperationId() {
        return madeOperationId;
    }

    /**
     * Sets the value of the madeOperationId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMadeOperationId(String value) {
        this.madeOperationId = value;
    }

    /**
     * Gets the value of the authorizationCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAuthorizationCode() {
        return authorizationCode;
    }

    /**
     * Sets the value of the authorizationCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAuthorizationCode(Long value) {
        this.authorizationCode = value;
    }

    /**
     * Gets the value of the authorizationDtTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizationDtTm() {
        return authorizationDtTm;
    }

    /**
     * Sets the value of the authorizationDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizationDtTm(String value) {
        this.authorizationDtTm = value;
    }

}
