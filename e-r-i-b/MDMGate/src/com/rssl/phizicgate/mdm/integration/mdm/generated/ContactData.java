
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for ContactData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ContactId" minOccurs="0"/>
 *         &lt;element ref="{}ContactPref" minOccurs="0"/>
 *         &lt;element ref="{}ContactType"/>
 *         &lt;element ref="{}ContactNum"/>
 *         &lt;element ref="{}PrefTimeStart" minOccurs="0"/>
 *         &lt;element ref="{}PrefTimeEnd" minOccurs="0"/>
 *         &lt;element ref="{}EffDt" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactData_Type", propOrder = {
    "contactId",
    "contactPref",
    "contactType",
    "contactNum",
    "prefTimeStart",
    "prefTimeEnd",
    "effDt"
})
@XmlRootElement(name = "ContactData")
public class ContactData {

    @XmlElement(name = "ContactId")
    protected String contactId;
    @XmlElement(name = "ContactPref")
    protected String contactPref;
    @XmlElement(name = "ContactType", required = true)
    protected String contactType;
    @XmlElement(name = "ContactNum", required = true)
    protected String contactNum;
    @XmlElement(name = "PrefTimeStart", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar prefTimeStart;
    @XmlElement(name = "PrefTimeEnd", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar prefTimeEnd;
    @XmlElement(name = "EffDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar effDt;

    /**
     * Gets the value of the contactId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactId() {
        return contactId;
    }

    /**
     * Sets the value of the contactId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactId(String value) {
        this.contactId = value;
    }

    /**
     * Gets the value of the contactPref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactPref() {
        return contactPref;
    }

    /**
     * Sets the value of the contactPref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactPref(String value) {
        this.contactPref = value;
    }

    /**
     * Gets the value of the contactType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * Sets the value of the contactType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactType(String value) {
        this.contactType = value;
    }

    /**
     * Gets the value of the contactNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactNum() {
        return contactNum;
    }

    /**
     * Sets the value of the contactNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactNum(String value) {
        this.contactNum = value;
    }

    /**
     * Gets the value of the prefTimeStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPrefTimeStart() {
        return prefTimeStart;
    }

    /**
     * Sets the value of the prefTimeStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefTimeStart(Calendar value) {
        this.prefTimeStart = value;
    }

    /**
     * Gets the value of the prefTimeEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getPrefTimeEnd() {
        return prefTimeEnd;
    }

    /**
     * Sets the value of the prefTimeEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrefTimeEnd(Calendar value) {
        this.prefTimeEnd = value;
    }

    /**
     * Gets the value of the effDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEffDt() {
        return effDt;
    }

    /**
     * Sets the value of the effDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEffDt(Calendar value) {
        this.effDt = value;
    }

}
