//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.11 at 07:52:58 PM MSK 
//


package com.rssl.phizic.gate.bankroll.generated;


/**
 * �������� ����������� ������. ���� �� ���� source ����� ���� default="true".
 * Java content class for Source complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/bankrollServices.xsd line 43)
 * <p>
 * <pre>
 * &lt;complexType name="Source">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="alias" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="class-name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface SourceDescriptor {


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
     * Gets the value of the alias property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAlias();

    /**
     * Sets the value of the alias property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAlias(java.lang.String value);

}
