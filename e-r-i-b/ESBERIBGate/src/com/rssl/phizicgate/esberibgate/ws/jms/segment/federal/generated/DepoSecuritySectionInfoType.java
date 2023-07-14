
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о ценной бумаге (ДЕПО).
 * 
 * <p>Java class for DepoSecuritySectionInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoSecuritySectionInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SecurityMarker" type="{}SecurityMarker_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InsideCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Remainder" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="StorageMethod" type="{}DepoStorageMethod_Type"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoSecuritySectionInfo_Type", propOrder = {
    "securityMarkers",
    "insideCode",
    "remainder",
    "storageMethod"
})
public class DepoSecuritySectionInfoType {

    @XmlElement(name = "SecurityMarker")
    protected List<SecurityMarkerType> securityMarkers;
    @XmlElement(name = "InsideCode", required = true)
    protected String insideCode;
    @XmlElement(name = "Remainder")
    protected long remainder;
    @XmlElement(name = "StorageMethod", required = true)
    protected String storageMethod;

    /**
     * Gets the value of the securityMarkers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the securityMarkers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecurityMarkers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SecurityMarkerType }
     * 
     * 
     */
    public List<SecurityMarkerType> getSecurityMarkers() {
        if (securityMarkers == null) {
            securityMarkers = new ArrayList<SecurityMarkerType>();
        }
        return this.securityMarkers;
    }

    /**
     * Gets the value of the insideCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInsideCode() {
        return insideCode;
    }

    /**
     * Sets the value of the insideCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInsideCode(String value) {
        this.insideCode = value;
    }

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
     * Gets the value of the storageMethod property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStorageMethod() {
        return storageMethod;
    }

    /**
     * Sets the value of the storageMethod property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStorageMethod(String value) {
        this.storageMethod = value;
    }

}
