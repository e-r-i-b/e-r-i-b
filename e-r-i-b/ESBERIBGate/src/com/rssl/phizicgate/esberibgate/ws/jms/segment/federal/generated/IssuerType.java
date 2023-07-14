
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ВСП, осуществляющий выдачу ЦБ
 * 
 * <p>Java class for Issuer_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Issuer_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BankInfo" type="{}BankInfoExt_Type"/>
 *         &lt;element name="BankOwnerInfo" type="{}Owner_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Issuer_Type", propOrder = {
    "bankInfo",
    "bankOwnerInfo"
})
public class IssuerType {

    @XmlElement(name = "BankInfo", required = true)
    protected BankInfoExtType bankInfo;
    @XmlElement(name = "BankOwnerInfo")
    protected OwnerType bankOwnerInfo;

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoExtType }
     *     
     */
    public BankInfoExtType getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoExtType }
     *     
     */
    public void setBankInfo(BankInfoExtType value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the bankOwnerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OwnerType }
     *     
     */
    public OwnerType getBankOwnerInfo() {
        return bankOwnerInfo;
    }

    /**
     * Sets the value of the bankOwnerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OwnerType }
     *     
     */
    public void setBankOwnerInfo(OwnerType value) {
        this.bankOwnerInfo = value;
    }

}
