//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.02.15 at 09:41:08 AM MSK 
//


package com.rssl.common.forms.validators.passwords.generated;


/**
 * Java content class for ParameterGroup complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Sun/jwsdp-1.6/jaxb/bin/security-config.xsd line 115)
 * <p>
 * <pre>
 * &lt;complexType name="ParameterGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="parameter" type="{}Parameter" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ParameterGroup {


    /**
     * Gets the value of the Parameters property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Parameters property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameters().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rssl.common.forms.validators.passwords.generated.Parameter}
     * 
     */
    java.util.List getParameters();

}
