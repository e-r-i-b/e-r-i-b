
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Запроса реквизитов ценных бумаг
 * 
 * <p>Java class for SecuritiesInfoInqRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesInfoInqRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="SPName" type="{}SPName_Type"/>
 *         &lt;element name="SystemId" type="{}SystemId_Type" minOccurs="0"/>
 *         &lt;element name="BankInfo" type="{}BankInfoESB_Type" minOccurs="0"/>
 *         &lt;element name="OperName" type="{}OperNameSec_Type"/>
 *         &lt;choice>
 *           &lt;element name="BlankInfo" type="{}BlankInfo_Type"/>
 *           &lt;element name="BlankPackage" type="{}BlankPackage_Type"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesInfoInqRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "systemId",
    "bankInfo",
    "operName",
    "blankPackage",
    "blankInfo"
})
@XmlRootElement(name = "SecuritiesInfoInqRq")
public class SecuritiesInfoInqRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "BankInfo")
    protected BankInfoESBType bankInfo;
    @XmlElement(name = "OperName", required = true)
    protected String operName;
    @XmlElement(name = "BlankPackage")
    protected BlankPackageType blankPackage;
    @XmlElement(name = "BlankInfo")
    protected BlankInfoType blankInfo;

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
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoESBType }
     *     
     */
    public BankInfoESBType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoESBType }
     *     
     */
    public void setBankInfo(BankInfoESBType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the operName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperName() {
        return operName;
    }

    /**
     * Sets the value of the operName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperName(String value) {
        this.operName = value;
    }

    /**
     * Gets the value of the blankPackage property.
     * 
     * @return
     *     possible object is
     *     {@link BlankPackageType }
     *     
     */
    public BlankPackageType getBlankPackage() {
        return blankPackage;
    }

    /**
     * Sets the value of the blankPackage property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlankPackageType }
     *     
     */
    public void setBlankPackage(BlankPackageType value) {
        this.blankPackage = value;
    }

    /**
     * Gets the value of the blankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BlankInfoType }
     *     
     */
    public BlankInfoType getBlankInfo() {
        return blankInfo;
    }

    /**
     * Sets the value of the blankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BlankInfoType }
     *     
     */
    public void setBlankInfo(BlankInfoType value) {
        this.blankInfo = value;
    }

}
