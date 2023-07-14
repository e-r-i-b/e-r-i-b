
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ContactInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ContactInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="EmailAddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MessageDeliveryType" type="{}MessageDeliveryType_Type" minOccurs="0"/>
 *         &lt;element ref="{}ContactData" maxOccurs="6" minOccurs="0"/>
 *         &lt;element name="PostAddr" type="{}FullAddress_Type" maxOccurs="5" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ContactInfo_Type", propOrder = {
    "emailAddr",
    "messageDeliveryType",
    "contactDatas",
    "postAddrs"
})
@XmlRootElement(name = "ContactInfo")
public class ContactInfo {

    @XmlElement(name = "EmailAddr")
    protected String emailAddr;
    @XmlElement(name = "MessageDeliveryType")
    protected MessageDeliveryTypeType messageDeliveryType;
    @XmlElement(name = "ContactData")
    protected List<ContactData> contactDatas;
    @XmlElement(name = "PostAddr")
    protected List<FullAddressType> postAddrs;

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

    /**
     * Блок нетипизированных контактных данных Gets the value of the contactDatas property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the contactDatas property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContactDatas().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ContactData }
     * 
     * 
     */
    public List<ContactData> getContactDatas() {
        if (contactDatas == null) {
            contactDatas = new ArrayList<ContactData>();
        }
        return this.contactDatas;
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
     * {@link FullAddressType }
     * 
     * 
     */
    public List<FullAddressType> getPostAddrs() {
        if (postAddrs == null) {
            postAddrs = new ArrayList<FullAddressType>();
        }
        return this.postAddrs;
    }

}
