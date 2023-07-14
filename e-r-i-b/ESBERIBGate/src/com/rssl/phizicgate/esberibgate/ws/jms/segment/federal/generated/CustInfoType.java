
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о потребителе <CustInfo>
 * 
 * <p>Java class for CustInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustId" type="{}EribCustId_Type" minOccurs="0"/>
 *         &lt;element name="EffDt" type="{}DateTime" minOccurs="0"/>
 *         &lt;element ref="{}PersonInfo" minOccurs="0"/>
 *         &lt;element ref="{}BankInfo" minOccurs="0"/>
 *         &lt;element name="SPDefField" type="{}SPDefField_Type" minOccurs="0"/>
 *         &lt;element name="IntegrationInfo" type="{}IntegrationInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustInfo_Type", propOrder = {
    "custId",
    "effDt",
    "personInfo",
    "bankInfo",
    "spDefField",
    "integrationInfo"
})
public class CustInfoType {

    @XmlElement(name = "CustId")
    protected String custId;
    @XmlElement(name = "EffDt")
    protected String effDt;
    @XmlElement(name = "PersonInfo")
    protected PersonInfoType personInfo;
    @XmlElement(name = "BankInfo")
    protected BankInfoType bankInfo;
    @XmlElement(name = "SPDefField")
    protected SPDefField spDefField;
    @XmlElement(name = "IntegrationInfo")
    protected IntegrationInfoType integrationInfo;

    /**
     * Gets the value of the custId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustId() {
        return custId;
    }

    /**
     * Sets the value of the custId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustId(String value) {
        this.custId = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(String value) {
        this.effDt = value;
    }

    /**
     * Информация о физическом лице.
     * 
     * @return
     *     possible object is
     *     {@link PersonInfoType }
     *     
     */
    public PersonInfoType getPersonInfo() {
        return personInfo;
    }

    /**
     * Sets the value of the personInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonInfoType }
     *     
     */
    public void setPersonInfo(PersonInfoType value) {
        this.personInfo = value;
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
     * Gets the value of the spDefField property.
     * 
     * @return
     *     possible object is
     *     {@link SPDefField }
     *     
     */
    public SPDefField getSPDefField() {
        return spDefField;
    }

    /**
     * Sets the value of the spDefField property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPDefField }
     *     
     */
    public void setSPDefField(SPDefField value) {
        this.spDefField = value;
    }

    /**
     * Gets the value of the integrationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IntegrationInfoType }
     *     
     */
    public IntegrationInfoType getIntegrationInfo() {
        return integrationInfo;
    }

    /**
     * Sets the value of the integrationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IntegrationInfoType }
     *     
     */
    public void setIntegrationInfo(IntegrationInfoType value) {
        this.integrationInfo = value;
    }

}
