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
@XmlType(name = "UpdateResourceRq", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "profile"
})
@XmlRootElement(name = "UpdateResourceRq")
public class UpdateResourceRq {

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
    protected UpdateResourceProfilesType profile;

	public VersionNumber getRqVersion() {
        return rqVersion;
    }

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

    public UpdateResourceProfilesType getProfile() {
        return profile;
    }

    public void setProfile(UpdateResourceProfilesType value) {
        this.profile = value;
    }

}
