//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v1.0.5-b16-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2008.12.11 at 07:52:58 PM MSK 
//


package com.rssl.phizic.gate.bankroll.generated;


/**
 * Java content class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/bankrollServices.xsd line 16)
 * <p>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="sources">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="source" type="{}Source" maxOccurs="unbounded"/>
 *                 &lt;/sequence>
 *                 &lt;attribute name="default" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="methods">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="method" type="{}Method" maxOccurs="unbounded"/>
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
public interface ServicesType {


    /**
     * �������� �������, ����������� ��� ��������� ���������� ���������� ��������.
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType}
     */
    com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType getSources();

    /**
     * �������� �������, ����������� ��� ��������� ���������� ���������� ��������.
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType}
     */
    void setSources(com.rssl.phizic.gate.bankroll.generated.ServicesType.SourcesType value);

    /**
     * �������� �������. ��������!!! � ������� ����������, ���� ������������ ���������, �������� list. Field ����������� ������.
     * 
     * @return
     *     possible object is
     *     {@link com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType}
     */
    com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType getMethods();

    /**
     * �������� �������. ��������!!! � ������� ����������, ���� ������������ ���������, �������� list. Field ����������� ������.
     * 
     * @param value
     *     allowed object is
     *     {@link com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType}
     */
    void setMethods(com.rssl.phizic.gate.bankroll.generated.ServicesType.MethodsType value);


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/bankrollServices.xsd line 33)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="method" type="{}Method" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface MethodsType {


        /**
         * Gets the value of the Method property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the Method property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMethod().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link com.rssl.phizic.gate.bankroll.generated.MethodDescriptor}
         * 
         */
        java.util.List getMethod();

    }


    /**
     * Java content class for anonymous complex type.
     * <p>The following schema fragment specifies the expected content contained within this java content object. (defined at file:/D:/projects/trunk.sber/Settings/schemas/bankrollServices.xsd line 22)
     * <p>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="source" type="{}Source" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *       &lt;attribute name="default" use="required" type="{http://www.w3.org/2001/XMLSchema}anySimpleType" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     */
    public interface SourcesType {


        /**
         * Gets the value of the default property.
         * 
         * @return
         *     possible object is
         *     {@link java.lang.String}
         */
        java.lang.String getDefault();

        /**
         * Sets the value of the default property.
         * 
         * @param value
         *     allowed object is
         *     {@link java.lang.String}
         */
        void setDefault(java.lang.String value);

        /**
         * Gets the value of the Source property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the Source property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSource().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link com.rssl.phizic.gate.bankroll.generated.SourceDescriptor}
         * 
         */
        java.util.List getSource();

    }

}
