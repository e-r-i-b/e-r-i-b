//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.12.10 at 01:19:03 PM MSK 
//


package com.rssl.phizic.auth.modes.generated;


/**
 * ��������� struts action ��� ������
 * Java content class for AuthenticationStage complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/src/v1.18_newSVN/Settings/schemas/authenticationModes.xsd line 72)
 * <p>
 * <pre>
 * &lt;complexType name="AuthenticationStage">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="key" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="allow-action" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
 *         &lt;element name="allow-operation" type="{}AllowOperationType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="demand-if" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="class" type="{}JavaClass"/>
 *                   &lt;element name="parameter" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 */
public interface AuthenticationStageDescriptor {


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
     * ��������� struts action ��� ������Gets the value of the AllowedActions property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AllowedActions property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowedActions().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link java.lang.String}
     * 
     */
    java.util.List getAllowedActions();

    /**
     * ��������� ������ ��� �������
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType}
     */
    com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType getDemandIf();

    /**
     * ��������� ������ ��� �������
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType}
     */
    void setDemandIf(com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType value);

    /**
     * Gets the value of the AllowOperation property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the AllowOperation property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAllowOperation().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link com.rssl.phizic.auth.modes.generated.AllowOperationTypeDescriptor}
     * 
     */
    java.util.List getAllowOperation();


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/src/v1.18_newSVN/Settings/schemas/authenticationModes.xsd line 91)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="class" type="{}JavaClass"/>
     *         &lt;element name="parameter" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface DemandIfType {


        /**
         * Gets the value of the Parameters property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the Parameters property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getParameters().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link com.rssl.phizic.auth.modes.generated.AuthenticationStageDescriptor.DemandIfType.ParameterType}
         * 
         */
        java.util.List getParameters();

        /**
         * Gets the value of the clazz property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getClazz();

        /**
         * Sets the value of the clazz property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setClazz(java.lang.String value);


        /**
         * Java content class for anonymous complex type.
         * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/src/v1.18_newSVN/Settings/schemas/authenticationModes.xsd line 107)
         * <p>
         * <pre>
         * &lt;complexType>
         *   &lt;complexContent>
         *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *       &lt;sequence>
         *         &lt;element name="name" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         */
        public interface ParameterType {


            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link java.lang.String}
             */
            java.lang.String getValue();

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link java.lang.String}
             */
            void setValue(java.lang.String value);

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

        }

    }

}
