
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Блок данных - образование
 * 
 * <p>Java class for EducationData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="EducationData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{}EducationStatus_Type"/>
 *         &lt;element name="UnfinishedCourse" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="1"/>
 *               &lt;pattern value="\d"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EducationData_Type", propOrder = {
    "status",
    "unfinishedCourse"
})
public class EducationDataType {

    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "UnfinishedCourse")
    protected String unfinishedCourse;

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the unfinishedCourse property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnfinishedCourse() {
        return unfinishedCourse;
    }

    /**
     * Sets the value of the unfinishedCourse property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnfinishedCourse(String value) {
        this.unfinishedCourse = value;
    }

}
