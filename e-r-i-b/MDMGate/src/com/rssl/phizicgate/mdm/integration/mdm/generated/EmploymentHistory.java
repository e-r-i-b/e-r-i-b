
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateTimeAdapter;


/**
 * <p>Java class for EmploymentHistory_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EmploymentHistory_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}EmploymentStatus"/>
 *         &lt;element ref="{}OrgInfo" minOccurs="0"/>
 *         &lt;element ref="{}JobTitle" minOccurs="0"/>
 *         &lt;element ref="{}StartDt" minOccurs="0"/>
 *         &lt;element ref="{}EndDt" minOccurs="0"/>
 *         &lt;element ref="{}EffDt"/>
 *         &lt;element ref="{}PrimaryJob" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EmploymentHistory_Type", propOrder = {
    "employmentStatus",
    "orgInfo",
    "jobTitle",
    "startDt",
    "endDt",
    "effDt",
    "primaryJob"
})
@XmlRootElement(name = "EmploymentHistory")
public class EmploymentHistory {

    @XmlElement(name = "EmploymentStatus", required = true)
    protected String employmentStatus;
    @XmlElement(name = "OrgInfo")
    protected OrgInfo orgInfo;
    @XmlElement(name = "JobTitle")
    protected String jobTitle;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "EndDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar endDt;
    @XmlElement(name = "EffDt", required = true, type = String.class)
    @XmlJavaTypeAdapter(CalendarDateTimeAdapter.class)
    protected Calendar effDt;
    @XmlElement(name = "PrimaryJob")
    protected String primaryJob;

    /**
     * Gets the value of the employmentStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmploymentStatus() {
        return employmentStatus;
    }

    /**
     * Sets the value of the employmentStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmploymentStatus(String value) {
        this.employmentStatus = value;
    }

    /**
     * Gets the value of the orgInfo property.
     * 
     * @return
     *     possible object is
     *     {@link OrgInfo }
     *     
     */
    public OrgInfo getOrgInfo() {
        return orgInfo;
    }

    /**
     * Sets the value of the orgInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrgInfo }
     *     
     */
    public void setOrgInfo(OrgInfo value) {
        this.orgInfo = value;
    }

    /**
     * Gets the value of the jobTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getJobTitle() {
        return jobTitle;
    }

    /**
     * Sets the value of the jobTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(Calendar value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(Calendar value) {
        this.endDt = value;
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

    /**
     * Gets the value of the primaryJob property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrimaryJob() {
        return primaryJob;
    }

    /**
     * Sets the value of the primaryJob property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrimaryJob(String value) {
        this.primaryJob = value;
    }

}
