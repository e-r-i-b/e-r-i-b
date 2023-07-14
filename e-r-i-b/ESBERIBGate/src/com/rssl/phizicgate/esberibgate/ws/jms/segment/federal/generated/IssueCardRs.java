
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *   Ответ  на    запрос  первичный  выпуск  карты
 * 
 * <p>Java class for IssueCardRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IssueCardRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="SPName" type="{}SystemId_Type"/>
 *         &lt;element name="SystemId" type="{}SystemId_Type"/>
 *         &lt;element name="Status" type="{}Status_way_Type"/>
 *         &lt;element name="CardAcctId" type="{}CardAcctId_Type" minOccurs="0"/>
 *         &lt;element name="CardAcctInfo" type="{}CardAcctInfoType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IssueCardRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "systemId",
    "status",
    "cardAcctId",
    "cardAcctInfo"
})
@XmlRootElement(name = "IssueCardRs")
public class IssueCardRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "SPName", required = true)
    protected String spName;
    @XmlElement(name = "SystemId", required = true)
    protected String systemId;
    @XmlElement(name = "Status", required = true)
    protected StatusWayType status;
    @XmlElement(name = "CardAcctId")
    protected CardAcctIdType cardAcctId;
    @XmlElement(name = "CardAcctInfo")
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
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPName(String value) {
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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusWayType }
     *     
     */
    public StatusWayType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusWayType }
     *     
     */
    public void setStatus(StatusWayType value) {
        this.status = value;
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
