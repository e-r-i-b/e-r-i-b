//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.07.17 at 02:39:21 PM MSD 
//


package com.rssl.phizic.business.test.mobile9.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ������ �����
 * 
 * <p>Java class for InitRurPayJurSB complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InitRurPayJurSB">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="operationUID" type="{}string"/>
 *         &lt;element name="fields">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="field" type="{}Field" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="availableFromResources" type="{}Field"/>
 *         &lt;element name="autoPaymentSupported" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InitRurPayJurSB", propOrder = {
    "operationUID",
    "fields",
    "availableFromResources",
    "autoPaymentSupported"
})
public class InitRurPayJurSBDescriptor {

    @XmlElement(required = true)
    protected String operationUID;
    @XmlElement(required = true)
    protected InitRurPayJurSBDescriptor.Fields fields;
    @XmlElement(required = true)
    protected FieldDescriptor availableFromResources;
    protected boolean autoPaymentSupported;

    /**
     * Gets the value of the operationUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperationUID() {
        return operationUID;
    }

    /**
     * Sets the value of the operationUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperationUID(String value) {
        this.operationUID = value;
    }

    /**
     * Gets the value of the fields property.
     * 
     * @return
     *     possible object is
     *     {@link InitRurPayJurSBDescriptor.Fields }
     *     
     */
    public InitRurPayJurSBDescriptor.Fields getFields() {
        return fields;
    }

    /**
     * Sets the value of the fields property.
     * 
     * @param value
     *     allowed object is
     *     {@link InitRurPayJurSBDescriptor.Fields }
     *     
     */
    public void setFields(InitRurPayJurSBDescriptor.Fields value) {
        this.fields = value;
    }

    /**
     * Gets the value of the availableFromResources property.
     * 
     * @return
     *     possible object is
     *     {@link FieldDescriptor }
     *     
     */
    public FieldDescriptor getAvailableFromResources() {
        return availableFromResources;
    }

    /**
     * Sets the value of the availableFromResources property.
     * 
     * @param value
     *     allowed object is
     *     {@link FieldDescriptor }
     *     
     */
    public void setAvailableFromResources(FieldDescriptor value) {
        this.availableFromResources = value;
    }

    /**
     * Gets the value of the autoPaymentSupported property.
     * 
     */
    public boolean isAutoPaymentSupported() {
        return autoPaymentSupported;
    }

    /**
     * Sets the value of the autoPaymentSupported property.
     * 
     */
    public void setAutoPaymentSupported(boolean value) {
        this.autoPaymentSupported = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="field" type="{}Field" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "field"
    })
    public static class Fields {

        protected List<FieldDescriptor> field;

        /**
         * Gets the value of the field property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the field property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getField().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link FieldDescriptor }
         * 
         * 
         */
        public List<FieldDescriptor> getField() {
            if (field == null) {
                field = new ArrayList<FieldDescriptor>();
            }
            return this.field;
        }

    }

}
