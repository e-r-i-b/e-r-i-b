//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.02.05 at 08:36:14 PM MSK 
//


package com.rssl.phizic.operations.passwordcards.generated;


/**
 * ����� ������
 * Java content class for Card complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Work/Java/SoftLab/PhizIC/Settings/schemas/passwordCards.xsd line 48)
 * <p>
 * <pre>
 * &lt;complexType name="Card">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="number" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="keys" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="issue-date" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface CardDescriptor {


    /**
     * Gets the value of the issueDate property.
     * 
     * @return
     *     possible object is
     *     {@link java.util.Calendar}
     */
    java.util.Calendar getIssueDate();

    /**
     * Sets the value of the issueDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.util.Calendar}
     */
    void setIssueDate(java.util.Calendar value);

    /**
     * Gets the value of the number property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getNumber();

    /**
     * Sets the value of the number property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setNumber(java.lang.String value);

    /**
     * Gets the value of the keys property.
     * 
     */
    long getKeys();

    /**
     * Sets the value of the keys property.
     * 
     */
    void setKeys(long value);

}