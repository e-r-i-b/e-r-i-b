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


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SMSRq", propOrder = {
    "rqVersion",
	"rqUID",
	"rqTime",
    "phone",
    "text",
    "eribSID"
})
@XmlRootElement(name = "SMSRq")
public class SMSRq{

    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(VersionNumberXmlAdapter.class)
    protected VersionNumber rqVersion;
	@XmlElement(required = true, type = String.class)
	@XmlJavaTypeAdapter(UUIDXmlAdapter.class)
	protected UUID rqUID;
	@XmlElement(required = true)
	@XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
	protected Calendar rqTime;
    @XmlElement(required = true, type = String.class)
    @XmlJavaTypeAdapter(MobileInternationalPhoneNumberXmlAdapter.class)
    protected PhoneNumber phone;
    @XmlElement(required = true)
    protected String text;
    @XmlElement(type = String.class)
    @XmlJavaTypeAdapter(UUIDXmlAdapter.class)
    protected UUID eribSID;

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

    public PhoneNumber getPhone() {
        return phone;
    }

    public void setPhone(PhoneNumber value) {
        this.phone = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String value) {
        this.text = value;
    }

    public UUID getEribSID() {
        return eribSID;
    }

    public void setEribSID(UUID value) {
        this.eribSID = value;
    }
}
