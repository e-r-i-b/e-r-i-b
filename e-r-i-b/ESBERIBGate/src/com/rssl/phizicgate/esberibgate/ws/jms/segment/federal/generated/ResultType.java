
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERIBUID" type="{}UUID"/>
 *         &lt;element ref="{}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultType", propOrder = {
    "eribuid",
    "status"
})
public class ResultType {

    @XmlElement(name = "ERIBUID", required = true)
    protected String eribuid;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;

    /**
     * Gets the value of the eribuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getERIBUID() {
        return eribuid;
    }

    /**
     * Sets the value of the eribuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setERIBUID(String value) {
        this.eribuid = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

}
