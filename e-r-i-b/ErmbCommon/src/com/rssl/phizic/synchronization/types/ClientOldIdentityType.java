
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Устаревшие идентификационные данные клиента
 * 
 * <p>Java class for ClientOldIdentityType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClientOldIdentityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identity" type="{}IdentityType"/>
 *         &lt;element name="changed" type="{}DateTimeType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClientOldIdentityType", propOrder = {
    "identity",
    "changed"
})
public class ClientOldIdentityType {

    @XmlElement(required = true)
    protected IdentityType identity;
    @XmlElement(required = true)
    protected String changed;

    /**
     * Gets the value of the identity property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityType }
     *     
     */
    public IdentityType getIdentity() {
        return identity;
    }

    /**
     * Sets the value of the identity property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityType }
     *     
     */
    public void setIdentity(IdentityType value) {
        this.identity = value;
    }

    /**
     * Gets the value of the changed property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChanged() {
        return changed;
    }

    /**
     * Sets the value of the changed property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChanged(String value) {
        this.changed = value;
    }

}
