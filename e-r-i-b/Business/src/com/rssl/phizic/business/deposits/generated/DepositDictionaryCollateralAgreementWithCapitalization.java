//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.10.21 at 06:30:15 PM MSD 
//


package com.rssl.phizic.business.deposits.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}collateralAgreement"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "collateralAgreement"
})
@XmlRootElement(name = "collateralAgreementWithCapitalization")
public class DepositDictionaryCollateralAgreementWithCapitalization {

    @XmlElement(required = true)
    protected DepositDictionaryCollateralAgreement collateralAgreement;

    /**
     * Gets the value of the collateralAgreement property.
     * 
     * @return
     *     possible object is
     *     {@link DepositDictionaryCollateralAgreement }
     *     
     */
    public DepositDictionaryCollateralAgreement getCollateralAgreement() {
        return collateralAgreement;
    }

    /**
     * Sets the value of the collateralAgreement property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositDictionaryCollateralAgreement }
     *     
     */
    public void setCollateralAgreement(DepositDictionaryCollateralAgreement value) {
        this.collateralAgreement = value;
    }

}