package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.MobileInternationalPhoneNumberXmlAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ResetIMSIRq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResetIMSIRq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rqVersion" type="{}VersionType"/>
 *         &lt;element name="rqUID" type="{}UIDType"/>
 *         &lt;element name="rqTime" type="{}DateTimeType"/>
 *         &lt;element name="phone" type="{}PhoneType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResetIMSIRq", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "phones"
})
@XmlRootElement(name = "ResetIMSIRq")
public class ResetIMSIRq{

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID rqUID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTime;
    @XmlElement(name = "phone", required = true, type = String.class)
    @XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
    protected List<PhoneNumber> phones;

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
     * Gets the value of the phones property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phones property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhones().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<PhoneNumber> getPhones() {
        if (phones == null) {
            phones = new ArrayList<PhoneNumber>();
        }
        return this.phones;
    }

}
