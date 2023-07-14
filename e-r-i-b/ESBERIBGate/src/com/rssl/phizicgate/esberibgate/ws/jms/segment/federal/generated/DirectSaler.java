
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ÃÑÏÏ
 * 
 * <p>Java class for DirectSaler_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DirectSaler_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DirectSalerId" type="{}DirectSalerId_Type" minOccurs="0"/>
 *         &lt;element name="OrgName" type="{}OrgName_Type" minOccurs="0"/>
 *         &lt;element name="OrgRegion" type="{}OrgRegion_Type" minOccurs="0"/>
 *         &lt;element name="OrgPlace" type="{}OrgPlace_Type" minOccurs="0"/>
 *         &lt;element name="OrgAddress" type="{}OrgAddress_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DirectSaler_Type", propOrder = {
    "directSalerId",
    "orgName",
    "orgRegion",
    "orgPlace",
    "orgAddress"
})
@XmlRootElement(name = "DirectSaler")
public class DirectSaler {

    @XmlElement(name = "DirectSalerId")
    protected String directSalerId;
    @XmlElement(name = "OrgName")
    protected String orgName;
    @XmlElement(name = "OrgRegion")
    protected String orgRegion;
    @XmlElement(name = "OrgPlace")
    protected String orgPlace;
    @XmlElement(name = "OrgAddress")
    protected String orgAddress;

    /**
     * Gets the value of the directSalerId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirectSalerId() {
        return directSalerId;
    }

    /**
     * Sets the value of the directSalerId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirectSalerId(String value) {
        this.directSalerId = value;
    }

    /**
     * Gets the value of the orgName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgName() {
        return orgName;
    }

    /**
     * Sets the value of the orgName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgName(String value) {
        this.orgName = value;
    }

    /**
     * Gets the value of the orgRegion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgRegion() {
        return orgRegion;
    }

    /**
     * Sets the value of the orgRegion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgRegion(String value) {
        this.orgRegion = value;
    }

    /**
     * Gets the value of the orgPlace property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgPlace() {
        return orgPlace;
    }

    /**
     * Sets the value of the orgPlace property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgPlace(String value) {
        this.orgPlace = value;
    }

    /**
     * Gets the value of the orgAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgAddress() {
        return orgAddress;
    }

    /**
     * Sets the value of the orgAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgAddress(String value) {
        this.orgAddress = value;
    }

}
