
package com.rssl.phizic.synchronization.types;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Профиль клиента на обновление сохранение
 * 
 * <p>Java class for ConfirmUpdatedProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmUpdatedProfileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="clientIdentity" type="{}IdentityType"/>
 *         &lt;element name="clientOldIdentity" type="{}IdentityType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="version" type="{}LongType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmUpdatedProfileType", propOrder = {
    "clientIdentity",
    "clientOldIdentities",
    "version"
})
public class ConfirmUpdatedProfileType {

    @XmlElement(required = true)
    protected IdentityType clientIdentity;
    @XmlElement(name = "clientOldIdentity")
    protected List<IdentityType> clientOldIdentities;
    protected long version;

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
     * Gets the value of the clientOldIdentities property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the clientOldIdentities property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getClientOldIdentities().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IdentityType }
     * 
     * 
     */
    public List<IdentityType> getClientOldIdentities() {
        if (clientOldIdentities == null) {
            clientOldIdentities = new ArrayList<IdentityType>();
        }
        return this.clientOldIdentities;
    }

    /**
     * Gets the value of the version property.
     * 
     */
    public long getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     */
    public void setVersion(long value) {
        this.version = value;
    }

}
