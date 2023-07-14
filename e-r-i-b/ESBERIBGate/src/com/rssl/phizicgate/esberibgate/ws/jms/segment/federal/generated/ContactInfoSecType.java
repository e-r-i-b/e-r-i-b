
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Контактная информация МДО формат (проект сберсертификаты)
 * 
 * <p>Java class for ContactInfoSec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfoSec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PhoneNum" type="{}PhoneNumber_Type" minOccurs="0"/>
 *         &lt;element name="PostAddr" type="{}PostAddr_Type" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="PhoneType" type="{}PhoneType_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoSec_Type", propOrder = {
    "phoneNum",
    "postAddrs",
    "phoneType"
})
public class ContactInfoSecType {

    @XmlElement(name = "PhoneNum")
    protected String phoneNum;
    @XmlElement(name = "PostAddr")
    protected List<PostAddrType> postAddrs;
    @XmlElement(name = "PhoneType")
    protected PhoneTypeType phoneType;

    /**
     * Gets the value of the phoneNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the value of the phoneNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhoneNum(String value) {
        this.phoneNum = value;
    }

    /**
     * Gets the value of the postAddrs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postAddrs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostAddrs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostAddrType }
     * 
     * 
     */
    public List<PostAddrType> getPostAddrs() {
        if (postAddrs == null) {
            postAddrs = new ArrayList<PostAddrType>();
        }
        return this.postAddrs;
    }

    /**
     * Gets the value of the phoneType property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneTypeType }
     *     
     */
    public PhoneTypeType getPhoneType() {
        return phoneType;
    }

    /**
     * Sets the value of the phoneType property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneTypeType }
     *     
     */
    public void setPhoneType(PhoneTypeType value) {
        this.phoneType = value;
    }

}
