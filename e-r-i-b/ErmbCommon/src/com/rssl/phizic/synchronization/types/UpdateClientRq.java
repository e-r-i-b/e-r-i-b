package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateClientRq", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "profile"
})
@XmlRootElement(name = "UpdateClientRq")
public class UpdateClientRq {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID rqUID;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTime;
    @XmlElement(required = true)
    protected UpdateClientProfilesType profile;

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

    public UUID getRqUID() {
        return rqUID;
    }

    public void setRqUID(UUID value) {
        this.rqUID = value;
    }

    public Calendar getRqTime() {
        return rqTime;
    }

    public void setRqTime(Calendar value) {
        this.rqTime = value;
    }

    public UpdateClientProfilesType getProfile() {
        return profile;
    }

    public void setProfile(UpdateClientProfilesType value) {
        this.profile = value;
    }

}
