
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DocInfoStatRqDocumentsType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocInfoStatRqDocumentsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ERIBUID" type="{}UUID"/>
 *         &lt;element name="PaidBy" type="{}String50Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocInfoStatRqDocumentsType", propOrder = {
    "eribuid",
    "paidBy"
})
public class DocInfoStatRqDocumentsType {

    @XmlElement(name = "ERIBUID", required = true)
    protected String eribuid;
    @XmlElement(name = "PaidBy")
    protected String paidBy;

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
     * Gets the value of the paidBy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPaidBy() {
        return paidBy;
    }

    /**
     * Sets the value of the paidBy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPaidBy(String value) {
        this.paidBy = value;
    }

}
