
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Маркер ценной бумаги
 * 
 * <p>Java class for SecurityMarker_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SecurityMarker_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Remainder" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="MarkerDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SecurityMarker_Type", propOrder = {
    "remainder",
    "markerDescription"
})
public class SecurityMarkerType {

    @XmlElement(name = "Remainder")
    protected long remainder;
    @XmlElement(name = "MarkerDescription", required = true)
    protected String markerDescription;

    /**
     * Gets the value of the remainder property.
     * 
     */
    public long getRemainder() {
        return remainder;
    }

    /**
     * Sets the value of the remainder property.
     * 
     */
    public void setRemainder(long value) {
        this.remainder = value;
    }

    /**
     * Gets the value of the markerDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarkerDescription() {
        return markerDescription;
    }

    /**
     * Sets the value of the markerDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarkerDescription(String value) {
        this.markerDescription = value;
    }

}
