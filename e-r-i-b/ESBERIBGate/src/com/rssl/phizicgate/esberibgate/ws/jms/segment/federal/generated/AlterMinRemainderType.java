
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Элементы для изменения неснижаемого остатка
 * 
 * <p>Java class for AlterMinRemainder_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AlterMinRemainder_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="NewMinRemainder" type="{}Decimal"/>
 *         &lt;element name="NewInterestRate" type="{}Decimal"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AlterMinRemainder_Type", propOrder = {
    "newMinRemainder",
    "newInterestRate"
})
public class AlterMinRemainderType {

    @XmlElement(name = "NewMinRemainder", required = true)
    protected BigDecimal newMinRemainder;
    @XmlElement(name = "NewInterestRate", required = true)
    protected BigDecimal newInterestRate;

    /**
     * Gets the value of the newMinRemainder property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNewMinRemainder() {
        return newMinRemainder;
    }

    /**
     * Sets the value of the newMinRemainder property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNewMinRemainder(BigDecimal value) {
        this.newMinRemainder = value;
    }

    /**
     * Gets the value of the newInterestRate property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getNewInterestRate() {
        return newInterestRate;
    }

    /**
     * Sets the value of the newInterestRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setNewInterestRate(BigDecimal value) {
        this.newInterestRate = value;
    }

}
