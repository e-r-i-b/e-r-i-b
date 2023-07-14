
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип информация об ОМС договоре
 * 
 * <p>Java class for IMAInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IMAInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}CustRec"/>
 *         &lt;element ref="{}AcctId" minOccurs="0"/>
 *         &lt;element ref="{}AcctCur"/>
 *         &lt;element name="AcctCode" type="{}Long"/>
 *         &lt;element name="AcctSubCode" type="{}Long"/>
 *         &lt;element name="BankInfo" type="{}MinBankInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IMAInfo_Type", propOrder = {
    "custRec",
    "acctId",
    "acctCur",
    "acctCode",
    "acctSubCode",
    "bankInfo"
})
@XmlRootElement(name = "IMAInfo")
public class IMAInfo {

    @XmlElement(name = "CustRec", required = true)
    protected CustRec custRec;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "AcctCur", required = true)
    protected String acctCur;
    @XmlElement(name = "AcctCode")
    protected long acctCode;
    @XmlElement(name = "AcctSubCode")
    protected long acctSubCode;
    @XmlElement(name = "BankInfo")
    protected MinBankInfoType bankInfo;

    /**
     * Gets the value of the custRec property.
     * 
     * @return
     *     possible object is
     *     {@link CustRec }
     *     
     */
    public CustRec getCustRec() {
        return custRec;
    }

    /**
     * Sets the value of the custRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustRec }
     *     
     */
    public void setCustRec(CustRec value) {
        this.custRec = value;
    }

    /**
     * Номер ОМС
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
     * Код металла
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
     * Gets the value of the acctCode property.
     * 
     */
    public long getAcctCode() {
        return acctCode;
    }

    /**
     * Sets the value of the acctCode property.
     * 
     */
    public void setAcctCode(long value) {
        this.acctCode = value;
    }

    /**
     * Gets the value of the acctSubCode property.
     * 
     */
    public long getAcctSubCode() {
        return acctSubCode;
    }

    /**
     * Sets the value of the acctSubCode property.
     * 
     */
    public void setAcctSubCode(long value) {
        this.acctSubCode = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link MinBankInfoType }
     *     
     */
    public MinBankInfoType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link MinBankInfoType }
     *     
     */
    public void setBankInfo(MinBankInfoType value) {
        this.bankInfo = value;
    }

}
