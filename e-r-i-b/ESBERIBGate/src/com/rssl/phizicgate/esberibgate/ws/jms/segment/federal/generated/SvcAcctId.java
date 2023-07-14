
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Данные длительного поручения
 * 
 * <p>Java class for SvcAcctId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SvcAcctId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}SystemId" minOccurs="0"/>
 *         &lt;element name="SvcAcctNum" type="{}Long"/>
 *         &lt;element ref="{}BankInfo"/>
 *         &lt;element name="SvcType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SvcAcctId_Type", propOrder = {
    "systemId",
    "svcAcctNum",
    "bankInfo",
    "svcType"
})
@XmlRootElement(name = "SvcAcctId")
public class SvcAcctId {

    @XmlElement(name = "SystemId")
    protected String systemId;
    @XmlElement(name = "SvcAcctNum")
    protected long svcAcctNum;
    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoType bankInfo;
    @XmlElement(name = "SvcType")
    protected boolean svcType;

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
     * Gets the value of the svcAcctNum property.
     * 
     */
    public long getSvcAcctNum() {
        return svcAcctNum;
    }

    /**
     * Sets the value of the svcAcctNum property.
     * 
     */
    public void setSvcAcctNum(long value) {
        this.svcAcctNum = value;
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
     * Gets the value of the svcType property.
     * 
     */
    public boolean isSvcType() {
        return svcType;
    }

    /**
     * Sets the value of the svcType property.
     * 
     */
    public void setSvcType(boolean value) {
        this.svcType = value;
    }

}
