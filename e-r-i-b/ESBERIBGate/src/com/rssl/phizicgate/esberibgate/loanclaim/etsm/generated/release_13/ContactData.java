
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactData_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactData_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ContactType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ContactNum" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PhoneOperName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactData_Type", propOrder = {
    "contactType",
    "contactNum",
    "phoneOperName"
})
@XmlRootElement(name = "ContactData")
public class ContactData {

    @XmlElement(name = "ContactType", required = true)
    protected String contactType;
    @XmlElement(name = "ContactNum", required = true)
    protected String contactNum;
    @XmlElement(name = "PhoneOperName")
    protected String phoneOperName;

    /**
     * Gets the value of the contactType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactType() {
        return contactType;
    }

    /**
     * Sets the value of the contactType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactType(String value) {
        this.contactType = value;
    }

    /**
     * Gets the value of the contactNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContactNum() {
        return contactNum;
    }

    /**
     * Sets the value of the contactNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContactNum(String value) {
        this.contactNum = value;
    }

    /**
     * Gets the value of the phoneOperName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneOperName() {
        return phoneOperName;
    }

    /**
     * Sets the value of the phoneOperName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneOperName(String value) {
        this.phoneOperName = value;
    }

}
