
package com.rssl.phizic.test.webgate.rsa.jaxb.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * This defines the contents of a Response
 * 
 * <p>Java class for Response_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Response_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="statusHeader" type="{}StatusHeader_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response_Type", propOrder = {
    "statusHeader"
})
@XmlRootElement(name = "Response")
public class Response {

    protected StatusHeaderType statusHeader;

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

}
