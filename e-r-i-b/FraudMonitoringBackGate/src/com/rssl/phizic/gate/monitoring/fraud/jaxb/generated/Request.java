
package com.rssl.phizic.gate.monitoring.fraud.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This defines the contents of a Request
 * 
 * <p>Java class for Request_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Request_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="identificationData" type="{}IdentificationData_Type"/>
 *         &lt;element name="statusHeader" type="{}StatusHeader_Type"/>
 *         &lt;element name="deviceResult" type="{}DeviceResult_Type"/>
 *         &lt;element name="riskResult" type="{}RiskResult_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Request_Type", propOrder = {
    "identificationData",
    "statusHeader",
    "deviceResult",
    "riskResult"
})
@XmlRootElement(name = "Request")
public class Request {

    @XmlElement(required = true)
    protected IdentificationDataType identificationData;
    @XmlElement(required = true)
    protected StatusHeaderType statusHeader;
    @XmlElement(required = true)
    protected DeviceResultType deviceResult;
    @XmlElement(required = true)
    protected RiskResultType riskResult;

    /**
     * Gets the value of the identificationData property.
     * 
     * @return
     *     possible object is
     *     {@link IdentificationDataType }
     *     
     */
    public IdentificationDataType getIdentificationData() {
        return identificationData;
    }

    /**
     * Sets the value of the identificationData property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificationDataType }
     *     
     */
    public void setIdentificationData(IdentificationDataType value) {
        this.identificationData = value;
    }

    /**
     * Gets the value of the statusHeader property.
     * 
     * @return
     *     possible object is
     *     {@link StatusHeaderType }
     *     
     */
    public StatusHeaderType getStatusHeader() {
        return statusHeader;
    }

    /**
     * Sets the value of the statusHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusHeaderType }
     *     
     */
    public void setStatusHeader(StatusHeaderType value) {
        this.statusHeader = value;
    }

    /**
     * Gets the value of the deviceResult property.
     * 
     * @return
     *     possible object is
     *     {@link DeviceResultType }
     *     
     */
    public DeviceResultType getDeviceResult() {
        return deviceResult;
    }

    /**
     * Sets the value of the deviceResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceResultType }
     *     
     */
    public void setDeviceResult(DeviceResultType value) {
        this.deviceResult = value;
    }

    /**
     * Gets the value of the riskResult property.
     * 
     * @return
     *     possible object is
     *     {@link RiskResultType }
     *     
     */
    public RiskResultType getRiskResult() {
        return riskResult;
    }

    /**
     * Sets the value of the riskResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link RiskResultType }
     *     
     */
    public void setRiskResult(RiskResultType value) {
        this.riskResult = value;
    }

}
