//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.20 at 07:05:24 PM MSK 
//


package com.rssl.phizic.business.dictionaries.receivers.personal.generated;


/**
 * �������� ���� �� ������������ �������
 * Java content class for Format complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/receivers.xsd line 84)
 * <p>
 * <pre>
 * &lt;complexType name="Format">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="message" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="regexp" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface FormatDescriptor {


    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getMessage();

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setMessage(java.lang.String value);

    /**
     * Gets the value of the regexp property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getRegexp();

    /**
     * Sets the value of the regexp property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setRegexp(java.lang.String value);

}