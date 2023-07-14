
package com.rssl.phizic.synchronization.types;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Профиль клиента на обновление сохранение
 * 
 * <p>Java class for UpdateProfileType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateProfileType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="version" type="{}LongType"/>
 *         &lt;element name="clientIdentity" type="{}IdentityType"/>
 *         &lt;element name="clientOldIdentity" type="{}ClientOldIdentityType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="clientCategory" type="{}IntegerType"/>
 *         &lt;element name="isUDBO" type="{}BooleanType"/>
 *         &lt;element name="clientPhones" type="{}ClientPhonesType"/>
 *         &lt;element name="clientResources" type="{}ClientResourcesType"/>
 *         &lt;element name="mobileBankService" type="{}MobileBankServiceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProfileType", propOrder = {
    "version",
    "clientIdentity",
    "clientOldIdentities",
    "clientCategory",
    "isUDBO",
    "clientPhones",
    "clientResources",
    "mobileBankService"
})
public class UpdateProfileType {

    protected long version;
    @XmlElement(required = true)
    protected IdentityType clientIdentity;
    @XmlElement(name = "clientOldIdentity")
    protected List<ClientOldIdentityType> clientOldIdentities;
    @XmlElement(required = true)
    protected BigInteger clientCategory;
    protected boolean isUDBO;
    @XmlElement(required = true)
    protected ClientPhonesType clientPhones;
    @XmlElement(required = true)
    protected ClientResourcesType clientResources;
    @XmlElement(required = true)
    protected MobileBankServiceType mobileBankService;

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
     * {@link ClientOldIdentityType }
     * 
     * 
     */
    public List<ClientOldIdentityType> getClientOldIdentities() {
        if (clientOldIdentities == null) {
            clientOldIdentities = new ArrayList<ClientOldIdentityType>();
        }
        return this.clientOldIdentities;
    }

    /**
     * Gets the value of the clientCategory property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getClientCategory() {
        return clientCategory;
    }

    /**
     * Sets the value of the clientCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setClientCategory(BigInteger value) {
        this.clientCategory = value;
    }

    /**
     * Gets the value of the isUDBO property.
     * 
     */
    public boolean isIsUDBO() {
        return isUDBO;
    }

    /**
     * Sets the value of the isUDBO property.
     * 
     */
    public void setIsUDBO(boolean value) {
        this.isUDBO = value;
    }

    /**
     * Gets the value of the clientPhones property.
     * 
     * @return
     *     possible object is
     *     {@link ClientPhonesType }
     *     
     */
    public ClientPhonesType getClientPhones() {
        return clientPhones;
    }

    /**
     * Sets the value of the clientPhones property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientPhonesType }
     *     
     */
    public void setClientPhones(ClientPhonesType value) {
        this.clientPhones = value;
    }

    /**
     * Gets the value of the clientResources property.
     * 
     * @return
     *     possible object is
     *     {@link ClientResourcesType }
     *     
     */
    public ClientResourcesType getClientResources() {
        return clientResources;
    }

    /**
     * Sets the value of the clientResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClientResourcesType }
     *     
     */
    public void setClientResources(ClientResourcesType value) {
        this.clientResources = value;
    }

    /**
     * Gets the value of the mobileBankService property.
     * 
     * @return
     *     possible object is
     *     {@link MobileBankServiceType }
     *     
     */
    public MobileBankServiceType getMobileBankService() {
        return mobileBankService;
    }

    /**
     * Sets the value of the mobileBankService property.
     * 
     * @param value
     *     allowed object is
     *     {@link MobileBankServiceType }
     *     
     */
    public void setMobileBankService(MobileBankServiceType value) {
        this.mobileBankService = value;
    }

}
