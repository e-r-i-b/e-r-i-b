//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.11.06 at 04:05:33 PM MSK 
//


package com.rssl.phizic.business.test.mobile8.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ConfirmStage complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConfirmStage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="confirmType" type="{}ConfirmType"/>
 *         &lt;element name="confirmInfo" type="{}ConfirmInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConfirmStage", propOrder = {
    "confirmType",
    "confirmInfo"
})
public class ConfirmStageDescriptor {

    @XmlElement(required = true)
    protected ConfirmTypeDescriptor confirmType;
    protected ConfirmInfoDescriptor confirmInfo;

    /**
     * Gets the value of the confirmType property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmTypeDescriptor }
     *     
     */
    public ConfirmTypeDescriptor getConfirmType() {
        return confirmType;
    }

    /**
     * Sets the value of the confirmType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmTypeDescriptor }
     *     
     */
    public void setConfirmType(ConfirmTypeDescriptor value) {
        this.confirmType = value;
    }

    /**
     * Gets the value of the confirmInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ConfirmInfoDescriptor }
     *     
     */
    public ConfirmInfoDescriptor getConfirmInfo() {
        return confirmInfo;
    }

    /**
     * Sets the value of the confirmInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ConfirmInfoDescriptor }
     *     
     */
    public void setConfirmInfo(ConfirmInfoDescriptor value) {
        this.confirmInfo = value;
    }

}
