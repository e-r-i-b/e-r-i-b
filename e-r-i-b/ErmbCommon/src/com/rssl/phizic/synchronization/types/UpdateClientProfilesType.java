
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateClientProfilesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateClientProfilesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientIdentity" type="{}IdentityType"/>
 *         &lt;element name="clientOldIdentity" type="{}IdentityType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateClientProfilesType", propOrder = {
    "clientIdentity",
    "clientOldIdentity"
})
public class UpdateClientProfilesType {

    @XmlElement(required = true)
    protected IdentityType clientIdentity;
    @XmlElement(required = true)
    protected IdentityType clientOldIdentity;

    /**
     * Gets the value of the clientIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityType }
     *     
     */
    public IdentityType getClientIdentity() {
        return clientIdentity;
    }

    /**
     * Sets the value of the clientIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityType }
     *     
     */
    public void setClientIdentity(IdentityType value) {
        this.clientIdentity = value;
    }

    /**
     * Gets the value of the clientOldIdentity property.
     * 
     * @return
     *     possible object is
     *     {@link IdentityType }
     *     
     */
    public IdentityType getClientOldIdentity() {
        return clientOldIdentity;
    }

    /**
     * Sets the value of the clientOldIdentity property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentityType }
     *     
     */
    public void setClientOldIdentity(IdentityType value) {
        this.clientOldIdentity = value;
    }

}
