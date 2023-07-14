
package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Данные по услуге «Мобильный Банк»
 * 
 * <p>Java class for MobileBankServiceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MobileBankServiceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="registrationStatus" type="{}BooleanType"/>
 *         &lt;element name="serviceStatus" type="{}StringType" minOccurs="0"/>
 *         &lt;element name="activePhone" type="{}PhoneType" minOccurs="0"/>
 *         &lt;element name="informResources" type="{}InformResourcesType" minOccurs="0"/>
 *         &lt;element name="informNewResource" type="{}BooleanType"/>
 *         &lt;element name="informPeriod" type="{}InformPeriodType" minOccurs="0"/>
 *         &lt;element name="suppressAdvertising" type="{}BooleanType"/>
 *         &lt;element name="informDepositEnrollment" type="{}BooleanType"/>
 *         &lt;element name="transliterateSms" type="{}BooleanType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MobileBankServiceType", propOrder = {
    "registrationStatus",
    "serviceStatus",
    "activePhone",
    "informResources",
    "informNewResource",
    "informPeriod",
    "suppressAdvertising",
    "informDepositEnrollment",
    "transliterateSms"
})
public class MobileBankServiceType {

    protected boolean registrationStatus;
    protected String serviceStatus;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
    protected PhoneNumber activePhone;
    protected InformResourcesType informResources;
    protected boolean informNewResource;
    protected InformPeriodType informPeriod;
    protected boolean suppressAdvertising;
    protected boolean informDepositEnrollment;
    protected Boolean transliterateSms;

    /**
     * Gets the value of the registrationStatus property.
     * 
     */
    public boolean isRegistrationStatus() {
        return registrationStatus;
    }

    /**
     * Sets the value of the registrationStatus property.
     * 
     */
    public void setRegistrationStatus(boolean value) {
        this.registrationStatus = value;
    }

    /**
     * Gets the value of the serviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceStatus() {
        return serviceStatus;
    }

    /**
     * Sets the value of the serviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceStatus(String value) {
        this.serviceStatus = value;
    }

    /**
     * Gets the value of the activePhone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public PhoneNumber getActivePhone() {
        return activePhone;
    }

    /**
     * Sets the value of the activePhone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivePhone(PhoneNumber value) {
        this.activePhone = value;
    }

    /**
     * Gets the value of the informResources property.
     * 
     * @return
     *     possible object is
     *     {@link InformResourcesType }
     *     
     */
    public InformResourcesType getInformResources() {
        return informResources;
    }

    /**
     * Sets the value of the informResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformResourcesType }
     *     
     */
    public void setInformResources(InformResourcesType value) {
        this.informResources = value;
    }

    /**
     * Gets the value of the informNewResource property.
     * 
     */
    public boolean isInformNewResource() {
        return informNewResource;
    }

    /**
     * Sets the value of the informNewResource property.
     * 
     */
    public void setInformNewResource(boolean value) {
        this.informNewResource = value;
    }

    /**
     * Gets the value of the informPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link InformPeriodType }
     *     
     */
    public InformPeriodType getInformPeriod() {
        return informPeriod;
    }

    /**
     * Sets the value of the informPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link InformPeriodType }
     *     
     */
    public void setInformPeriod(InformPeriodType value) {
        this.informPeriod = value;
    }

    /**
     * Gets the value of the suppressAdvertising property.
     * 
     */
    public boolean isSuppressAdvertising() {
        return suppressAdvertising;
    }

    /**
     * Sets the value of the suppressAdvertising property.
     * 
     */
    public void setSuppressAdvertising(boolean value) {
        this.suppressAdvertising = value;
    }

    /**
     * Gets the value of the informDepositEnrollment property.
     * 
     */
    public boolean isInformDepositEnrollment() {
        return informDepositEnrollment;
    }

    /**
     * Sets the value of the informDepositEnrollment property.
     * 
     */
    public void setInformDepositEnrollment(boolean value) {
        this.informDepositEnrollment = value;
    }

    /**
     * Gets the value of the transliterateSms property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getTransliterateSms() {
        return transliterateSms;
    }

    /**
     * Sets the value of the transliterateSms property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTransliterateSms(Boolean value) {
        this.transliterateSms = value;
    }

}
