//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.15 at 11:02:10 AM MSD
//


package com.rssl.common.forms.validators.passwords.generated;


/**
 * ��������� ��������� ������ �� ���������
 * Java content class for Password complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Sun/jwsdp-1.6/jaxb/bin/security-config.xsd line 51)
 * <p>
 * <pre>
 * &lt;complexType name="Password">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="password-minimal-length" type="{}PasswordMinimalLength"/>
 *         &lt;element name="allowed-charsets" type="{}CharsetGroup" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface PasswordDescriptor {


    /**
     * ������ ����������� ��������Gets the value of the AllowedCharsets property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AllowedCharsets property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedCharsets().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rssl.common.forms.validators.passwords.generated.CharsetGroup}
     * 
     */
    java.util.List getAllowedCharsets();

    /**
     * ����������� ����� ������
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.common.forms.validators.passwords.generated.PasswordMinimalLength}
     */
    com.rssl.common.forms.validators.passwords.generated.PasswordMinimalLength getPasswordMinimalLength();

    /**
     * ����������� ����� ������
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.common.forms.validators.passwords.generated.PasswordMinimalLength}
     */
    void setPasswordMinimalLength(com.rssl.common.forms.validators.passwords.generated.PasswordMinimalLength value);

}