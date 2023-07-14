
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for IdInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="IdInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}RbTbBrchId"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "IdInfo_Type", propOrder = {
    "rbTbBrchId"
})
@XmlRootElement(name = "IdInfo")
public class IdInfo {

    @XmlElement(name = "RbTbBrchId", required = true)
    protected String rbTbBrchId;

    /**
     * Gets the value of the rbTbBrchId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRbTbBrchId() {
        return rbTbBrchId;
    }

    /**
     * Sets the value of the rbTbBrchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRbTbBrchId(String value) {
        this.rbTbBrchId = value;
    }

}
