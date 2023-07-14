
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Бланк ценной бумаги
 * 
 * <p>Java class for BlankInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BlankInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BlankType" type="{}Identifier"/>
 *         &lt;element name="SerialNum">
 *           &lt;simpleType>
 *             &lt;restriction base="{}C">
 *               &lt;maxLength value="255"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BlankInfo_Type", propOrder = {
    "blankType",
    "serialNum"
})
public class BlankInfoType {

    @XmlElement(name = "BlankType", required = true)
    protected String blankType;
    @XmlElement(name = "SerialNum", required = true)
    protected String serialNum;

    /**
     * Gets the value of the blankType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBlankType() {
        return blankType;
    }

    /**
     * Sets the value of the blankType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBlankType(String value) {
        this.blankType = value;
    }

    /**
     * Gets the value of the serialNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNum() {
        return serialNum;
    }

    /**
     * Sets the value of the serialNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNum(String value) {
        this.serialNum = value;
    }

}
