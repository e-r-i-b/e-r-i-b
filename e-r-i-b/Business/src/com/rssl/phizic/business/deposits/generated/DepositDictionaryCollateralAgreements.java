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
 *         &lt;element ref="{}collateralAgreementWithTariffPlan" minOccurs="0"/>
 *         &lt;element ref="{}collateralAgreementWithCapitalization" minOccurs="0"/>
 *         &lt;element ref="{}collateralAgreementWithMinimumBalance" minOccurs="0"/>
 *         &lt;element ref="{}collateralAgreementWithTariffPlan4Type_61" minOccurs="0"/>
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
    "collateralAgreementWithTariffPlan",
    "collateralAgreementWithCapitalization",
    "collateralAgreementWithMinimumBalance",
    "collateralAgreementWithTariffPlan4Type61"
})
@XmlRootElement(name = "collateralAgreements")
public class DepositDictionaryCollateralAgreements {

    protected DepositDictionaryCollateralAgreementWithTariffPlan collateralAgreementWithTariffPlan;
    protected DepositDictionaryCollateralAgreementWithCapitalization collateralAgreementWithCapitalization;
    protected DepositDictionaryCollateralAgreementWithMinimumBalance collateralAgreementWithMinimumBalance;
    @XmlElement(name = "collateralAgreementWithTariffPlan4Type_61")
    protected DepositDictionaryCollateralAgreementWithTariffPlan4Type61 collateralAgreementWithTariffPlan4Type61;

    /**
     * Gets the value of the collateralAgreementWithTariffPlan property.
     * 
     * @return
     *     possible object is
     *     {@link DepositDictionaryCollateralAgreementWithTariffPlan }
     *     
     */
    public DepositDictionaryCollateralAgreementWithTariffPlan getCollateralAgreementWithTariffPlan() {
        return collateralAgreementWithTariffPlan;
    }

    /**
     * Sets the value of the collateralAgreementWithTariffPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositDictionaryCollateralAgreementWithTariffPlan }
     *     
     */
    public void setCollateralAgreementWithTariffPlan(DepositDictionaryCollateralAgreementWithTariffPlan value) {
        this.collateralAgreementWithTariffPlan = value;
    }

    /**
     * Gets the value of the collateralAgreementWithCapitalization property.
     * 
     * @return
     *     possible object is
     *     {@link DepositDictionaryCollateralAgreementWithCapitalization }
     *     
     */
    public DepositDictionaryCollateralAgreementWithCapitalization getCollateralAgreementWithCapitalization() {
        return collateralAgreementWithCapitalization;
    }

    /**
     * Sets the value of the collateralAgreementWithCapitalization property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositDictionaryCollateralAgreementWithCapitalization }
     *     
     */
    public void setCollateralAgreementWithCapitalization(DepositDictionaryCollateralAgreementWithCapitalization value) {
        this.collateralAgreementWithCapitalization = value;
    }

    /**
     * Gets the value of the collateralAgreementWithMinimumBalance property.
     * 
     * @return
     *     possible object is
     *     {@link DepositDictionaryCollateralAgreementWithMinimumBalance }
     *     
     */
    public DepositDictionaryCollateralAgreementWithMinimumBalance getCollateralAgreementWithMinimumBalance() {
        return collateralAgreementWithMinimumBalance;
    }

    /**
     * Sets the value of the collateralAgreementWithMinimumBalance property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositDictionaryCollateralAgreementWithMinimumBalance }
     *     
     */
    public void setCollateralAgreementWithMinimumBalance(DepositDictionaryCollateralAgreementWithMinimumBalance value) {
        this.collateralAgreementWithMinimumBalance = value;
    }

    /**
     * Gets the value of the collateralAgreementWithTariffPlan4Type61 property.
     * 
     * @return
     *     possible object is
     *     {@link DepositDictionaryCollateralAgreementWithTariffPlan4Type61 }
     *     
     */
    public DepositDictionaryCollateralAgreementWithTariffPlan4Type61 getCollateralAgreementWithTariffPlan4Type61() {
        return collateralAgreementWithTariffPlan4Type61;
    }

    /**
     * Sets the value of the collateralAgreementWithTariffPlan4Type61 property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepositDictionaryCollateralAgreementWithTariffPlan4Type61 }
     *     
     */
    public void setCollateralAgreementWithTariffPlan4Type61(DepositDictionaryCollateralAgreementWithTariffPlan4Type61 value) {
        this.collateralAgreementWithTariffPlan4Type61 = value;
    }

}
