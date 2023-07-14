package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for UpdateProfilesRq complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateProfilesRq">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="rqVersion" type="{}VersionType"/>
 *         &lt;element name="rqUID" type="{}UIDType"/>
 *         &lt;element name="rqTime" type="{}DateTimeType"/>
 *         &lt;element name="updatedProfiles" type="{}UpdatedProfilesType" minOccurs="0"/>
 *         &lt;element name="deletedProfiles" type="{}DeleteProfilesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateProfilesRq", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "updatedProfiles",
    "deletedProfiles"
})
@XmlRootElement(name = "UpdateProfilesRq")
public class UpdateProfilesRq {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID rqUID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTime;
    protected UpdatedProfilesType updatedProfiles;
    protected DeleteProfilesType deletedProfiles;

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
     * Gets the value of the updatedProfiles property.
     * 
     * @return
     *     possible object is
     *     {@link UpdatedProfilesType }
     *     
     */
    public UpdatedProfilesType getUpdatedProfiles() {
        return updatedProfiles;
    }

    /**
     * Sets the value of the updatedProfiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpdatedProfilesType }
     *     
     */
    public void setUpdatedProfiles(UpdatedProfilesType value) {
        this.updatedProfiles = value;
    }

    /**
     * Gets the value of the deletedProfiles property.
     * 
     * @return
     *     possible object is
     *     {@link DeleteProfilesType }
     *     
     */
    public DeleteProfilesType getDeletedProfiles() {
        return deletedProfiles;
    }

    /**
     * Sets the value of the deletedProfiles property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeleteProfilesType }
     *     
     */
    public void setDeletedProfiles(DeleteProfilesType value) {
        this.deletedProfiles = value;
    }

}
