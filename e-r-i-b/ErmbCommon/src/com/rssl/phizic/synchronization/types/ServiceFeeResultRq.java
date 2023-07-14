
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
@XmlType(name = "ServiceFeeResultRq", propOrder = {
    "rqVersion",
    "rqUID",
    "rqTime",
    "clientIdentity",
    "resource",
    "tb",
    "paymentStatus",
    "paymentID"
})
@XmlRootElement(name = "ServiceFeeResultRq")
public class ServiceFeeResultRq {

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
    @XmlElement(required = true)
    protected IdentityType clientIdentity;
    protected ResourceIDType resource;
    protected String tb;
    @XmlElement(required = true)
    protected String paymentStatus;
    protected String paymentID;

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

    public IdentityType getClientIdentity() {
        return clientIdentity;
    }

    public void setClientIdentity(IdentityType value) {
        this.clientIdentity = value;
    }

    public ResourceIDType getResource() {
        return resource;
    }

    public void setResource(ResourceIDType value) {
        this.resource = value;
    }

    public String getTb() {
        return tb;
    }

    public void setTb(String value) {
        this.tb = value;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String value) {
        this.paymentStatus = value;
    }

    public String getPaymentID() {
        return paymentID;
    }

    public void setPaymentID(String value) {
        this.paymentID = value;
    }

}
