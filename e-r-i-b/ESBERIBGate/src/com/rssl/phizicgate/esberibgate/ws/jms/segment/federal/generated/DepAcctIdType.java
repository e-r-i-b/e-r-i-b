
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Идентификатор депозитного счета
 * 
 * <p>Java class for DepAcctId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcctId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element name="BIC" type="{}String" minOccurs="0"/>
 *         &lt;element name="CorrAcctId" type="{}AcctIdType" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur" minOccurs="0"/>
 *         &lt;element name="AcctName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AcctCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="AcctSubCode" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="MaxSumWrite" type="{}Decimal" minOccurs="0"/>
 *         &lt;element name="OpenDate" type="{}Date" minOccurs="0"/>
 *         &lt;element name="Status" type="{}AccountStatusEnum_Type" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo" minOccurs="0"/>
 *         &lt;element name="VariantInterestPayment" type="{}VariantInterestPayment_Type" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepAcctId_Type", propOrder = {
    "systemId",
    "acctId",
    "bic",
    "corrAcctId",
    "acctCur",
    "acctName",
    "acctCode",
    "acctSubCode",
    "maxSumWrite",
    "openDate",
    "status",
    "custInfo",
    "variantInterestPayment",
    "bankInfo"
})
public class DepAcctIdType {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "BIC")
    protected String bic;
    @XmlElement(name = "CorrAcctId")
    protected String corrAcctId;
    @XmlElement(name = "AcctCur")
    protected String acctCur;
    @XmlElement(name = "AcctName")
    protected String acctName;
    @XmlElement(name = "AcctCode")
    protected Long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected Long acctSubCode;
    @XmlElement(name = "MaxSumWrite")
    protected BigDecimal maxSumWrite;
    @XmlElement(name = "OpenDate")
    protected String openDate;
    @XmlElement(name = "Status")
    protected AccountStatusEnumType status;
    @XmlElement(name = "CustInfo")
    protected CustInfoType custInfo;
    @XmlElement(name = "VariantInterestPayment")
    protected VariantInterestPaymentType variantInterestPayment;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;

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
     * Gets the value of the corrAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrAcctId() {
        return corrAcctId;
    }

    /**
     * Sets the value of the corrAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrAcctId(String value) {
        this.corrAcctId = value;
    }

    /**
     * Gets the value of the acctCur property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctCur() {
        return acctCur;
    }

    /**
     * Sets the value of the acctCur property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctCur(String value) {
        this.acctCur = value;
    }

    /**
     * Gets the value of the acctName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctName() {
        return acctName;
    }

    /**
     * Sets the value of the acctName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctName(String value) {
        this.acctName = value;
    }

    /**
     * Gets the value of the acctCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctCode() {
        return acctCode;
    }

    /**
     * Sets the value of the acctCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctCode(Long value) {
        this.acctCode = value;
    }

    /**
     * Gets the value of the acctSubCode property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAcctSubCode() {
        return acctSubCode;
    }

    /**
     * Sets the value of the acctSubCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAcctSubCode(Long value) {
        this.acctSubCode = value;
    }

    /**
     * Gets the value of the maxSumWrite property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMaxSumWrite() {
        return maxSumWrite;
    }

    /**
     * Sets the value of the maxSumWrite property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMaxSumWrite(BigDecimal value) {
        this.maxSumWrite = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpenDate(String value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link AccountStatusEnumType }
     *     
     */
    public AccountStatusEnumType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountStatusEnumType }
     *     
     */
    public void setStatus(AccountStatusEnumType value) {
        this.status = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfoType }
     *     
     */
    public CustInfoType getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfoType }
     *     
     */
    public void setCustInfo(CustInfoType value) {
        this.custInfo = value;
    }

    /**
     * Gets the value of the variantInterestPayment property.
     * 
     * @return
     *     possible object is
     *     {@link VariantInterestPaymentType }
     *     
     */
    public VariantInterestPaymentType getVariantInterestPayment() {
        return variantInterestPayment;
    }

    /**
     * Sets the value of the variantInterestPayment property.
     * 
     * @param value
     *     allowed object is
     *     {@link VariantInterestPaymentType }
     *     
     */
    public void setVariantInterestPayment(VariantInterestPaymentType value) {
        this.variantInterestPayment = value;
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

}
