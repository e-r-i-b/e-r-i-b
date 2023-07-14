
package com.rssl.phizic.test.webgate.rsa.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * Returns the results of device credential authentication
 * 
 * <p>Java class for DeviceResult_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceResult_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="deviceData" type="{}DeviceData_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceResult_Type", propOrder = {
    "deviceData"
})
public class DeviceResultType {

    protected DeviceDataType deviceData;

    /**
     * Gets the value of the deviceData property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceDataType }
     *     
     */
    public DeviceDataType getDeviceData() {
        return deviceData;
    }

    /**
     * Sets the value of the deviceData property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceDataType }
     *     
     */
    public void setDeviceData(DeviceDataType value) {
        this.deviceData = value;
    }

}
