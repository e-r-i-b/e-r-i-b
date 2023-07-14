
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *   Запрос  на  первичеый  выпуск  карты
 * 
 * <p>Java class for IssueCardRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IssueCardRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="OperUID" type="{}OperUID_Type"/>
 *         &lt;element name="SPName" type="{}SPName_Type"/>
 *         &lt;element name="SystemId" type="{}SystemId_Type"/>
 *         &lt;element name="UserName" type="{}Identifier"/>
 *         &lt;element name="OperName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BankInfo" type="{}BankInfo_Type"/>
 *         &lt;element name="CardAcctId" type="{}CardAcctId_Type"/>
 *         &lt;element name="CardAcctInfo" type="{}CardAcctInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueCardRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "operUID",
    "spName",
    "systemId",
    "userName",
    "operName",
    "bankInfo",
    "cardAcctId",
    "cardAcctInfo"
})
@XmlRootElement(name = "IssueCardRq")
public class IssueCardRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "OperUID", required = true)
    protected String operUID;
    @XmlElement(name = "SPName", required = true)
    protected SPNameType spName;
    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "UserName", required = true)
    protected String userName;
    @XmlElement(name = "OperName", required = true)
    protected String operName;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "CardAcctId", required = true)
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "CardAcctInfo", required = true)
    protected CardAcctInfoType cardAcctInfo;

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
     * Gets the value of the userName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets the value of the userName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUserName(String value) {
        this.userName = value;
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
     * Gets the value of the cardAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CardAcctInfoType }
     *     
     */
    public CardAcctInfoType getCardAcctInfo() {
        return cardAcctInfo;
    }

    /**
     * Sets the value of the cardAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CardAcctInfoType }
     *     
     */
    public void setCardAcctInfo(CardAcctInfoType value) {
        this.cardAcctInfo = value;
    }

}
