
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IFX_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IFX_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BaseSvcRq"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IFX_Type", propOrder = {
    "baseSvcRq"
})
@XmlRootElement(name = "IFX")
public class IFX {

    @XmlElement(name = "BaseSvcRq", required = true)
    protected BaseSvcRq baseSvcRq;

    /**
     * Gets the value of the baseSvcRq property.
     * 
     * @return
     *     possible object is
     *     {@link BaseSvcRq }
     *     
     */
    public BaseSvcRq getBaseSvcRq() {
        return baseSvcRq;
    }

    /**
     * Sets the value of the baseSvcRq property.
     * 
     * @param value
     *     allowed object is
     *     {@link BaseSvcRq }
     *     
     */
    public void setBaseSvcRq(BaseSvcRq value) {
        this.baseSvcRq = value;
    }

}
