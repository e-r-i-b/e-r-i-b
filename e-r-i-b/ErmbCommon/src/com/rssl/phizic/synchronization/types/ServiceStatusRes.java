
package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ServiceStatusRes complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ServiceStatusRes">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rqVersion" type="{}VersionType"/>
 *         &lt;element name="rqUID" type="{}UIDType"/>
 *         &lt;element name="rqTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="phone" type="{}PhoneType"/>
 *         &lt;element name="service" type="{}ServiceType"/>
 *         &lt;element name="paymentPeriod" type="{}PaymentPeriodType" minOccurs="0"/>
 *         &lt;element name="correlationID" type="{}UIDType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceStatusRes", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "phone",
    "service",
    "paymentPeriod",
    "correlationID"
})
@XmlRootElement(name = "ServiceStatusRes")
public class ServiceStatusRes {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID rqUID;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    @XmlSchemaType(name = "dateTime")
    protected Calendar rqTime;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
    protected PhoneNumber phone;
    @XmlElement(required = true)
    protected ServiceType service;
    protected PaymentPeriodType paymentPeriod;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID correlationID;

    /**
     * Gets the value of the rqVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public VersionNumber getRqVersion() {
        return rqVersion;
    }

    /**
     * Sets the value of the rqVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqVersion(VersionNumber value) {
        this.rqVersion = value;
    }

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public UUID getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(UUID value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getRqTime() {
        return rqTime;
    }

    /**
     * Sets the value of the rqTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTime(Calendar value) {
        this.rqTime = value;
    }

    /**
     * Gets the value of the phone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public PhoneNumber getPhone() {
        return phone;
    }

    /**
     * Sets the value of the phone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(PhoneNumber value) {
        this.phone = value;
    }

    /**
     * Gets the value of the service property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceType }
     *     
     */
    public ServiceType getService() {
        return service;
    }

    /**
     * Sets the value of the service property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceType }
     *     
     */
    public void setService(ServiceType value) {
        this.service = value;
    }

    /**
     * Gets the value of the paymentPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link PaymentPeriodType }
     *     
     */
    public PaymentPeriodType getPaymentPeriod() {
        return paymentPeriod;
    }

    /**
     * Sets the value of the paymentPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentPeriodType }
     *     
     */
    public void setPaymentPeriod(PaymentPeriodType value) {
        this.paymentPeriod = value;
    }

    /**
     * Gets the value of the correlationID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public UUID getCorrelationID() {
        return correlationID;
    }

    /**
     * Sets the value of the correlationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrelationID(UUID value) {
        this.correlationID = value;
    }

}
