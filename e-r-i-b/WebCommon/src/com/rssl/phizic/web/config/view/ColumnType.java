//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.08.10 at 01:13:02 PM MSD 
//


package com.rssl.phizic.web.config.view;


/**
 * Java content class for ColumnType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/zzz/view/view-config.xsd line 46)
 * <p>
 * <pre>
 * &lt;complexType name="ColumnType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="id" type="{}IDType"/>
 *         &lt;element name="action" type="{}ActionType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ColumnType {


    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.web.config.view.ActionType}
     */
    com.rssl.phizic.web.config.view.ActionType getAction();

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.web.config.view.ActionType}
     */
    void setAction(com.rssl.phizic.web.config.view.ActionType value);

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getId();

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setId(java.lang.String value);

}
