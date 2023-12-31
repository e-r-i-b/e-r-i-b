//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.20 at 07:05:24 PM MSK 
//


package com.rssl.phizic.business.dictionaries.receivers.personal.generated;


/**
 * ����������. � ���������� ������ ���� ���� personId. ��� ����������� � EditPaymentReceiverAction, �������� � PersonalReceiversFieldsBuilder
 * Java content class for Receiver complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/receivers.xsd line 29)
 * <p>
 * <pre>
 * &lt;complexType name="Receiver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="field" type="{}Field" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="form-validators" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="form-validator" type="{}Form-validator" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="className" use="required" type="{}JavaClass" />
 *       &lt;attribute name="kind" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="listkind" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ReceiverDescriptor {


    /**
     * Gets the value of the FieldDescriptors property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the FieldDescriptors property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFieldDescriptors().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rssl.phizic.business.dictionaries.receivers.personal.generated.FieldDescriptor}
     * 
     */
    java.util.List getFieldDescriptors();

    /**
     * Gets the value of the kind property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getKind();

    /**
     * Sets the value of the kind property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setKind(java.lang.String value);

    /**
     * ���������� ����� ��� ����������
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor.FormValidatorsType}
     */
    com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor.FormValidatorsType getFormValidators();

    /**
     * ���������� ����� ��� ����������
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor.FormValidatorsType}
     */
    void setFormValidators(com.rssl.phizic.business.dictionaries.receivers.personal.generated.ReceiverDescriptor.FormValidatorsType value);

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
     * Gets the value of the listkind property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getListkind();

    /**
     * Sets the value of the listkind property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setListkind(java.lang.String value);


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/receivers.xsd line 45)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="form-validator" type="{}Form-validator" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface FormValidatorsType {


        /**
         * Gets the value of the FormValidator property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the FormValidator property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getFormValidator().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link com.rssl.phizic.business.dictionaries.receivers.personal.generated.FormValidatorDescriptor}
         * 
         */
        java.util.List getFormValidator();

    }

}
