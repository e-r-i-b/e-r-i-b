//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2011.03.11 at 03:31:17 PM MSK 
//


package com.rssl.phizic.service.generated;


/**
 * JMXBean
 * Java content class for JMXBean complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Projects/PhizIC_Sber/version1.18_Sphere/Settings/schemas/startServicies.xsd line 53)
 * <p>
 * <pre>
 * &lt;complexType name="JMXBean">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="className" use="required" type="{}JavaClass" />
 *       &lt;attribute name="description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="interfaceClassName" type="{}JavaClass" />
 *       &lt;attribute name="needRegister" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="readerClassName" type="{}JavaClass" />
 *       &lt;attribute name="readerInterfaceClassName" type="{}JavaClass" />
 *       &lt;attribute name="readerParam" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface JMXBeanDescriptor {


    /**
     * Gets the value of the readerInterfaceClassName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getReaderInterfaceClassName();

    /**
     * Sets the value of the readerInterfaceClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setReaderInterfaceClassName(java.lang.String value);

    /**
     * Gets the value of the interfaceClassName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getInterfaceClassName();

    /**
     * Sets the value of the interfaceClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setInterfaceClassName(java.lang.String value);

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getDescription();

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setDescription(java.lang.String value);

    /**
     * Gets the value of the needRegister property.
     * 
     */
    boolean isNeedRegister();

    /**
     * Sets the value of the needRegister property.
     * 
     */
    void setNeedRegister(boolean value);

    /**
     * Gets the value of the readerClassName property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getReaderClassName();

    /**
     * Sets the value of the readerClassName property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setReaderClassName(java.lang.String value);

    /**
     * Gets the value of the className property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getClassName();

    /**
     * Sets the value of the className property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setClassName(java.lang.String value);

    /**
     * Gets the value of the readerParam property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getReaderParam();

    /**
     * Sets the value of the readerParam property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setReaderParam(java.lang.String value);

}