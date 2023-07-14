
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информациооная запись по ценной бумаге
 * 
 * <p>Java class for SecuritiesRecShort_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecuritiesRecShort_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BlankInfo" type="{}BlankInfo_Type"/>
 *         &lt;element name="SecuritiesInfo" type="{}SecuritiesInfo_Type"/>
 *         &lt;element name="OnStorageInBank" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecuritiesRecShort_Type", propOrder = {
    "blankInfo",
    "securitiesInfo",
    "onStorageInBank"
})
public class SecuritiesRecShortType {

    @XmlElement(name = "BlankInfo", required = true)
    protected BlankInfoType blankInfo;
    @XmlElement(name = "SecuritiesInfo", required = true)
    protected SecuritiesInfoType securitiesInfo;
    @XmlElement(name = "OnStorageInBank")
    protected Boolean onStorageInBank;

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

    /**
     * Gets the value of the securitiesInfo property.
     * 
     * @return
     *     possible object is
     *     {@link SecuritiesInfoType }
     *     
     */
    public SecuritiesInfoType getSecuritiesInfo() {
        return securitiesInfo;
    }

    /**
     * Sets the value of the securitiesInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link SecuritiesInfoType }
     *     
     */
    public void setSecuritiesInfo(SecuritiesInfoType value) {
        this.securitiesInfo = value;
    }

    /**
     * Gets the value of the onStorageInBank property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getOnStorageInBank() {
        return onStorageInBank;
    }

    /**
     * Sets the value of the onStorageInBank property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOnStorageInBank(Boolean value) {
        this.onStorageInBank = value;
    }

}
