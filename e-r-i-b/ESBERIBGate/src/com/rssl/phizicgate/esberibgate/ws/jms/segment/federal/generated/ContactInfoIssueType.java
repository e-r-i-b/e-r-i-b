
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Контактная информация
 * 
 * <p>Java class for ContactInfoIssue_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfoIssue_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PhoneNum" type="{}PhoneNumber_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="EmailAddr" type="{}EMailAddr_Type" minOccurs="0"/>
 *         &lt;element name="URL" type="{}URL" minOccurs="0"/>
 *         &lt;element name="FullAddress" type="{}FullAddress_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MessageDeliveryType" type="{}MessageDeliveryType_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfoIssue_Type", propOrder = {
    "phoneNa",
    "emailAddr",
    "url",
    "fullAddresses",
    "messageDeliveryType"
})
public class ContactInfoIssueType {

    @XmlElement(name = "PhoneNum")
    protected List<String> phoneNa;
    @XmlElement(name = "EmailAddr")
    protected String emailAddr;
    @XmlElement(name = "URL")
    protected String url;
    @XmlElement(name = "FullAddress")
    protected List<FullAddressType> fullAddresses;
    @XmlElement(name = "MessageDeliveryType")
    protected MessageDeliveryTypeType messageDeliveryType;

    /**
     * Gets the value of the phoneNa property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phoneNa property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhoneNa().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getPhoneNa() {
        if (phoneNa == null) {
            phoneNa = new ArrayList<String>();
        }
        return this.phoneNa;
    }

    /**
     * Gets the value of the emailAddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAddr() {
        return emailAddr;
    }

    /**
     * Sets the value of the emailAddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmailAddr(String value) {
        this.emailAddr = value;
    }

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setURL(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the fullAddresses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the fullAddresses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFullAddresses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FullAddressType }
     * 
     * 
     */
    public List<FullAddressType> getFullAddresses() {
        if (fullAddresses == null) {
            fullAddresses = new ArrayList<FullAddressType>();
        }
        return this.fullAddresses;
    }

    /**
     * Gets the value of the messageDeliveryType property.
     * 
     * @return
     *     possible object is
     *     {@link MessageDeliveryTypeType }
     *     
     */
    public MessageDeliveryTypeType getMessageDeliveryType() {
        return messageDeliveryType;
    }

    /**
     * Sets the value of the messageDeliveryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageDeliveryTypeType }
     *     
     */
    public void setMessageDeliveryType(MessageDeliveryTypeType value) {
        this.messageDeliveryType = value;
    }

}
