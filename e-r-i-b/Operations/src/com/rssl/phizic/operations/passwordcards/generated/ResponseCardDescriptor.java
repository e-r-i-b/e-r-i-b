//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2007.02.05 at 08:36:14 PM MSK 
//


package com.rssl.phizic.operations.passwordcards.generated;


/**
 * ����� ������
 * Java content class for ResponseCard complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Work/Java/SoftLab/PhizIC/Settings/schemas/passwordCards.xsd line 95)
 * <p>
 * <pre>
 * &lt;complexType name="ResponseCard">
 *   &lt;complexContent>
 *     &lt;extension base="{}Card">
 *       &lt;sequence>
 *         &lt;element name="password" type="{}Password" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ResponseCardDescriptor
    extends com.rssl.phizic.operations.passwordcards.generated.CardDescriptor
{


    /**
     * Gets the value of the Passwords property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the Passwords property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPasswords().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rssl.phizic.operations.passwordcards.generated.PasswordDescriptor}
     * 
     */
    java.util.List getPasswords();

}
