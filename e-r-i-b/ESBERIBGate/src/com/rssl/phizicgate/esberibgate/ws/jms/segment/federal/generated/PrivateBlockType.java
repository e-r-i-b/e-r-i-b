
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Дополнительная информация
 * 
 * <p>Java class for PrivateBlock_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PrivateBlock_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AddInfo1" type="{}AddInfo_Type" minOccurs="0"/>
 *         &lt;element name="AddInfo2" type="{}AddInfo_Type" minOccurs="0"/>
 *         &lt;element name="AddInfo3" type="{}AddInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PrivateBlock_Type", propOrder = {
    "addInfo1",
    "addInfo2",
    "addInfo3"
})
public class PrivateBlockType {

    @XmlElement(name = "AddInfo1")
    protected String addInfo1;
    @XmlElement(name = "AddInfo2")
    protected String addInfo2;
    @XmlElement(name = "AddInfo3")
    protected String addInfo3;

    /**
     * Gets the value of the addInfo1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddInfo1() {
        return addInfo1;
    }

    /**
     * Sets the value of the addInfo1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddInfo1(String value) {
        this.addInfo1 = value;
    }

    /**
     * Gets the value of the addInfo2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddInfo2() {
        return addInfo2;
    }

    /**
     * Sets the value of the addInfo2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddInfo2(String value) {
        this.addInfo2 = value;
    }

    /**
     * Gets the value of the addInfo3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddInfo3() {
        return addInfo3;
    }

    /**
     * Sets the value of the addInfo3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddInfo3(String value) {
        this.addInfo3 = value;
    }

}
