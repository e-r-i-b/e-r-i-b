//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated;


/**
 * Java content class for AllowOperationType complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/src/v1.18_newSVN/Settings/schemas/authenticationModes.xsd line 168)
 * <p>
 * <pre>
 * &lt;complexType name="AllowOperationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="service-key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="operation-class" type="{}JavaClass"/>
 *         &lt;element name="allow-if" type="{}JavaClass" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AllowOperationTypeDescriptor {


    /**
     * ����� �������� ��� ������
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getOperationClass();

    /**
     * ����� �������� ��� ������
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setOperationClass(java.lang.String value);

    /**
     * ����� ������� ���������� �������� ��� ������
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getAllowIf();

    /**
     * ����� ������� ���������� �������� ��� ������
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setAllowIf(java.lang.String value);

    /**
     * ��� �������
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getServiceKey();

    /**
     * ��� �������
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setServiceKey(java.lang.String value);

}