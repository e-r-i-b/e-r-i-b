
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SPDefField_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SPDefField_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}FieldName"/>
 *         &lt;element ref="{}FieldValue" minOccurs="0"/>
 *         &lt;element name="FieldDt" type="{}Date" minOccurs="0"/>
 *         &lt;element name="FieldNum" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FieldDt1" type="{}Date" minOccurs="0"/>
 *         &lt;element name="FieldData1" type="{}AgencyId_Type" minOccurs="0"/>
 *         &lt;element name="State" type="{}State_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SPDefField_Type", propOrder = {
    "fieldName",
    "fieldValue",
    "fieldDt",
    "fieldNum",
    "fieldDt1",
    "fieldData1",
    "state"
})
@XmlRootElement(name = "SPDefField")
public class SPDefField {

    @XmlElement(name = "FieldName", required = true)
    protected String fieldName;
    @XmlElement(name = "FieldValue")
    protected Boolean fieldValue;
    @XmlElement(name = "FieldDt")
    protected String fieldDt;
    @XmlElement(name = "FieldNum")
    protected String fieldNum;
    @XmlElement(name = "FieldDt1")
    protected String fieldDt1;
    @XmlElement(name = "FieldData1")
    protected String fieldData1;
    @XmlElement(name = "State")
    protected StateType state;

    /**
     * Название признака
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * Sets the value of the fieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldName(String value) {
        this.fieldName = value;
    }

    /**
     * Значение признака
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getFieldValue() {
        return fieldValue;
    }

    /**
     * Sets the value of the fieldValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFieldValue(Boolean value) {
        this.fieldValue = value;
    }

    /**
     * Gets the value of the fieldDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldDt() {
        return fieldDt;
    }

    /**
     * Sets the value of the fieldDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldDt(String value) {
        this.fieldDt = value;
    }

    /**
     * Gets the value of the fieldNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldNum() {
        return fieldNum;
    }

    /**
     * Sets the value of the fieldNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldNum(String value) {
        this.fieldNum = value;
    }

    /**
     * Gets the value of the fieldDt1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldDt1() {
        return fieldDt1;
    }

    /**
     * Sets the value of the fieldDt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldDt1(String value) {
        this.fieldDt1 = value;
    }

    /**
     * Gets the value of the fieldData1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFieldData1() {
        return fieldData1;
    }

    /**
     * Sets the value of the fieldData1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFieldData1(String value) {
        this.fieldData1 = value;
    }

    /**
     * Gets the value of the state property.
     * 
     * @return
     *     possible object is
     *     {@link StateType }
     *     
     */
    public StateType getState() {
        return state;
    }

    /**
     * Sets the value of the state property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateType }
     *     
     */
    public void setState(StateType value) {
        this.state = value;
    }

}
