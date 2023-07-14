package com.rssl.phizic.monitoring.fraud.messages.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * This reports the status of the call
 *
 * <p>Java class for StatusHeader_Type complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="StatusHeader_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="reasonDescription" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statusCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="reasonCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusHeader_Type", propOrder = {
    "reasonDescription",
    "statusCode",
    "reasonCode"
})
public class StatusHeaderType {

    protected String reasonDescription;
    protected Integer statusCode;
    protected Integer reasonCode;

    /**
     * Gets the value of the reasonDescription property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getReasonDescription() {
        return reasonDescription;
    }

    /**
     * Sets the value of the reasonDescription property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setReasonDescription(String value) {
        this.reasonDescription = value;
    }

    /**
     * Gets the value of the statusCode property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setStatusCode(Integer value) {
        this.statusCode = value;
    }

    /**
     * Gets the value of the reasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getReasonCode() {
        return reasonCode;
    }

    /**
     * Sets the value of the reasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setReasonCode(Integer value) {
        this.reasonCode = value;
    }

}
