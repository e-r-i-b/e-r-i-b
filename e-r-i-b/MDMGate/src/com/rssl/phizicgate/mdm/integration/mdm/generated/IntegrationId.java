
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IntegrationId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IntegrationId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ISCode" minOccurs="0"/>
 *         &lt;element ref="{}ISCustId" minOccurs="0"/>
 *         &lt;element ref="{}IsActive" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IntegrationId_Type", propOrder = {
    "isCode",
    "isCustId",
    "isActive"
})
@XmlRootElement(name = "IntegrationId")
public class IntegrationId {

    @XmlElement(name = "ISCode")
    protected String isCode;
    @XmlElement(name = "ISCustId")
    protected String isCustId;
    @XmlElement(name = "IsActive")
    protected String isActive;

    /**
     * Gets the value of the isCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISCode() {
        return isCode;
    }

    /**
     * Sets the value of the isCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISCode(String value) {
        this.isCode = value;
    }

    /**
     * Gets the value of the isCustId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getISCustId() {
        return isCustId;
    }

    /**
     * Sets the value of the isCustId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setISCustId(String value) {
        this.isCustId = value;
    }

    /**
     * Gets the value of the isActive property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * Sets the value of the isActive property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsActive(String value) {
        this.isActive = value;
    }

}
