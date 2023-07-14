
package com.rssl.phizic.synchronization.types;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpdateResourceProfilesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateResourceProfilesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientIdentity" type="{}IdentityType"/>
 *         &lt;element name="resource" type="{}ResourceIDType"/>
 *         &lt;element name="tb" type="{}String3Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateResourceProfilesType", propOrder = {
    "clientIdentity",
    "resource",
    "tb"
})
public class UpdateResourceProfilesType {

    @XmlElement(required = true)
    protected IdentityType clientIdentity;
    @XmlElement(required = true)
    protected ResourceIDType resource;
    protected String tb;

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
     * Gets the value of the resource property.
     * 
     * @return
     *     possible object is
     *     {@link ResourceIDType }
     *     
     */
    public ResourceIDType getResource() {
        return resource;
    }

    /**
     * Sets the value of the resource property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResourceIDType }
     *     
     */
    public void setResource(ResourceIDType value) {
        this.resource = value;
    }

    /**
     * Gets the value of the tb property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTb() {
        return tb;
    }

    /**
     * Sets the value of the tb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTb(String value) {
        this.tb = value;
    }

}
