//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.11.18 at 10:39:31 AM MSK 
//


package com.rssl.phizic.business.dictionaries.receivers.generated;


/**
 * �������� ���� �����
 * Java content class for Field complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Programs/Sun/jwsdp-1.6/jaxb/bin/paymentReceivers.xsd line 86)
 * <p>
 * <pre>
 * &lt;complexType name="Field">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="validators" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="validator" type="{}Validator" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="formater" type="{}JavaClass" minOccurs="0"/>
 *         &lt;element name="parser" type="{}JavaClass" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="code" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="description" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="inital" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="length" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="mandatory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="message" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="name" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="signable" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="source" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *       &lt;attribute name="type" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface FieldDescriptor {


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
     * Gets the value of the parser property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getParser();

    /**
     * Sets the value of the parser property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setParser(java.lang.String value);

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
     * Gets the value of the inital property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getInital();

    /**
     * Sets the value of the inital property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setInital(java.lang.String value);

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getSource();

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setSource(java.lang.String value);

    /**
     * Gets the value of the signable property.
     * 
     */
    boolean isSignable();

    /**
     * Sets the value of the signable property.
     * 
     */
    void setSignable(boolean value);

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
     * Gets the value of the code property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getCode();

    /**
     * Sets the value of the code property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setCode(java.lang.String value);

    /**
     * Gets the value of the length property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getLength();

    /**
     * Sets the value of the length property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setLength(java.lang.String value);

    /**
     * Gets the value of the formater property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getFormater();

    /**
     * Sets the value of the formater property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setFormater(java.lang.String value);

    /**
     * Gets the value of the mandatory property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getMandatory();

    /**
     * Sets the value of the mandatory property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setMandatory(java.lang.String value);

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link java.lang.String}
     */
    java.lang.String getName();

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link java.lang.String}
     */
    void setName(java.lang.String value);

    /**
     * Gets the value of the validators property.
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType}
     */
    com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType getValidators();

    /**
     * Sets the value of the validators property.
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType}
     */
    void setValidators(com.rssl.phizic.business.dictionaries.receivers.generated.FieldDescriptor.ValidatorsType value);


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/C:/Programs/Sun/jwsdp-1.6/jaxb/bin/paymentReceivers.xsd line 92)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="validator" type="{}Validator" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface ValidatorsType {


        /**
         * Gets the value of the ValidatorDescriptor property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the ValidatorDescriptor property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getValidatorDescriptor().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link com.rssl.phizic.business.dictionaries.receivers.generated.ValidatorDescriptor}
         * 
         */
        java.util.List getValidatorDescriptor();

    }

}
