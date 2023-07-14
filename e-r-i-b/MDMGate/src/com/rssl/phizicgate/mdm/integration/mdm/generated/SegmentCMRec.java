
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SegmentCMRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SegmentCMRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ClientSegment" minOccurs="0"/>
 *         &lt;element ref="{}ClientSegmentType" minOccurs="0"/>
 *         &lt;element ref="{}ClientManagerId" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SegmentCMRec_Type", propOrder = {
    "clientSegment",
    "clientSegmentType",
    "clientManagerId"
})
@XmlRootElement(name = "SegmentCMRec")
public class SegmentCMRec {

    @XmlElement(name = "ClientSegment")
    protected String clientSegment;
    @XmlElement(name = "ClientSegmentType")
    protected String clientSegmentType;
    @XmlElement(name = "ClientManagerId")
    protected String clientManagerId;

    /**
     * Gets the value of the clientSegment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSegment() {
        return clientSegment;
    }

    /**
     * Sets the value of the clientSegment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSegment(String value) {
        this.clientSegment = value;
    }

    /**
     * Gets the value of the clientSegmentType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientSegmentType() {
        return clientSegmentType;
    }

    /**
     * Sets the value of the clientSegmentType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientSegmentType(String value) {
        this.clientSegmentType = value;
    }

    /**
     * Gets the value of the clientManagerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClientManagerId() {
        return clientManagerId;
    }

    /**
     * Sets the value of the clientManagerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClientManagerId(String value) {
        this.clientManagerId = value;
    }

}
