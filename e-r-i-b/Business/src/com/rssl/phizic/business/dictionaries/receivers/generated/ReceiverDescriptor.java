//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.18 at 10:39:31 AM MSK 
//


package com.rssl.phizic.business.dictionaries.receivers.generated;


/**
 * �������� ����������
 * Java content class for Receiver complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Programs/Sun/jwsdp-1.6/jaxb/bin/paymentReceivers.xsd line 45)
 * <p>
 * <pre>
 * &lt;complexType name="Receiver">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;sequence>
 *             &lt;element name="contact-code" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;/sequence>
 *           &lt;sequence>
 *             &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="bic" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="cor-account" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="inn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *             &lt;element name="kpp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="ground-expression" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
 *       &lt;attribute name="key" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="type" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface ReceiverDescriptor {


    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     *     {@link java.lang.String}
     */
    java.lang.String getAccount();

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     *     {@link java.lang.String}
     */
    void setAccount(java.lang.String value);

    /**
     * Gets the value of the contactCode property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getContactCode();

    /**
     * Sets the value of the contactCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setContactCode(java.lang.String value);

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getType();

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setType(java.lang.String value);

    /**
     * Gets the value of the key property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getKey();

    /**
     * Sets the value of the key property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setKey(java.lang.String value);

    /**
     * Gets the value of the groundExpression property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getGroundExpression();

    /**
     * Sets the value of the groundExpression property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setGroundExpression(java.lang.String value);

    /**
     * Gets the value of the bic property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getBic();

    /**
     * Sets the value of the bic property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setBic(java.lang.String value);

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
     * {@link com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor}
     * 
     */
    java.util.List getFieldDescriptors();

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
     * Gets the value of the corAccount property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCorAccount();

    /**
     * Sets the value of the corAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCorAccount(java.lang.String value);

    /**
     * Gets the value of the inn property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getInn();

    /**
     * Sets the value of the inn property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setInn(java.lang.String value);

    /**
     * Gets the value of the kpp property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getKpp();

    /**
     * Sets the value of the kpp property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setKpp(java.lang.String value);

    /**
     * ���������� �����
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType}
     */
    com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType getFormValidators();

    /**
     * ���������� �����
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType}
     */
    void setFormValidators(com.rssl.phizic.business.dictionaries.receivers.generated.ReceiverDescriptor.FormValidatorsType value);


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Programs/Sun/jwsdp-1.6/jaxb/bin/paymentReceivers.xsd line 76)
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
         * {@link com.rssl.phizic.business.dictionaries.receivers.generated.FormValidatorDescriptor}
         * 
         */
        java.util.List getFormValidator();

    }

}