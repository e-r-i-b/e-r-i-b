
package com.rssl.phizic.rsa.integration.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This defines device information
 * 
 * <p>Java class for DeviceData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceTokenCookie" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="deviceTokenFSO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceData_Type", propOrder = {
    "deviceTokenCookie",
    "deviceTokenFSO"
})
public class DeviceDataType {

    @XmlElement(required = true)
    protected String deviceTokenCookie;
    @XmlElement(required = true)
    protected String deviceTokenFSO;

    /**
     * Gets the value of the deviceTokenCookie property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceTokenCookie() {
        return deviceTokenCookie;
    }

    /**
     * Sets the value of the deviceTokenCookie property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceTokenCookie(String value) {
        this.deviceTokenCookie = value;
    }

    /**
     * Gets the value of the deviceTokenFSO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceTokenFSO() {
        return deviceTokenFSO;
    }

    /**
     * Sets the value of the deviceTokenFSO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceTokenFSO(String value) {
        this.deviceTokenFSO = value;
    }

}
