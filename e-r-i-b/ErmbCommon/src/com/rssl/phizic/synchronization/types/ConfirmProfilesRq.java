package com.rssl.phizic.synchronization.types;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;
import com.rssl.phizic.utils.jaxb.UUIDXmlAdapter;
import com.rssl.phizic.utils.jaxb.VersionNumberXmlAdapter;

import java.util.Calendar;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@PlainOldJavaObject
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmProfilesRq", propOrder = {
    "rqVersion",
    "rqTime",
    "rqUID",
    "updatedProfiles",
    "deletedProfiles"
})
@XmlRootElement(name = "ConfirmProfilesRq")
public class ConfirmProfilesRq {

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
    @XmlElement(required = true)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar rqTime;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID rqUID;
    protected ConfirmUpdatedProfilesType updatedProfiles;
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

    public void setRqVersion(VersionNumber value) {
        this.rqVersion = value;
    }

    public Calendar getRqTime() {
        return rqTime;
    }

    public void setRqTime(Calendar value) {
        this.rqTime = value;
    }

    public UUID getRqUID() {
        return rqUID;
    }

    public void setRqUID(UUID value) {
        this.rqUID = value;
    }

    public ConfirmUpdatedProfilesType getUpdatedProfiles() {
        return updatedProfiles;
    }

    public void setUpdatedProfiles(ConfirmUpdatedProfilesType value) {
        this.updatedProfiles = value;
    }

    public DeleteProfilesType getDeletedProfiles() {
        return deletedProfiles;
    }

    public void setDeletedProfiles(DeleteProfilesType value) {
        this.deletedProfiles = value;
    }

}
