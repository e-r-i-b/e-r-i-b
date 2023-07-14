
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация по счету
 * 
 * <p>Java class for DepoAccount_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAccount_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element ref="{}DepoAcctId"/>
 *         &lt;element name="DepoAccInfo" type="{}DepAcctInfo_Type" minOccurs="0"/>
 *         &lt;element ref="{}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoAccount_Type", propOrder = {
    "bankInfo",
    "depoAcctId",
    "depoAccInfo",
    "status"
})
@XmlRootElement(name = "DepoAccount")
public class DepoAccount {

    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "DepoAcctId", required = true)
    protected DepoAcctId depoAcctId;
    @XmlElement(name = "DepoAccInfo")
    protected DepAcctInfoType depoAccInfo;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;

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
     * Gets the value of the depoAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAcctId }
     *     
     */
    public DepoAcctId getDepoAcctId() {
        return depoAcctId;
    }

    /**
     * Sets the value of the depoAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAcctId }
     *     
     */
    public void setDepoAcctId(DepoAcctId value) {
        this.depoAcctId = value;
    }

    /**
     * Gets the value of the depoAccInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctInfoType }
     *     
     */
    public DepAcctInfoType getDepoAccInfo() {
        return depoAccInfo;
    }

    /**
     * Sets the value of the depoAccInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctInfoType }
     *     
     */
    public void setDepoAccInfo(DepAcctInfoType value) {
        this.depoAccInfo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}
