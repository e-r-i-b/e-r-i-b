
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Контактная информация
 * 
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
 *         &lt;element name="ReportOrderType" type="{}ReportOrderType_Type" minOccurs="0"/>
 *         &lt;element ref="{}ContactData" maxOccurs="6" minOccurs="0"/>
 *         &lt;element name="PostAddr" type="{}FullAddress_Type" maxOccurs="5" minOccurs="0"/>
 *         &lt;element name="ReportDeliveryType" type="{}ReportDeliveryType_Type" minOccurs="0"/>
 *         &lt;element name="ReportLangType" type="{}ReportLangType_Type" minOccurs="0"/>
 *         &lt;element name="ReportType" type="{}ReportType_Type" minOccurs="0"/>
 *         &lt;element name="FullAddress" type="{}FullAddress_IssueCard_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="PhoneNum" type="{}PhoneNum_Type" maxOccurs="unbounded" minOccurs="0"/>
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
    "reportOrderType",
    "contactDatas",
    "postAddrs",
    "reportDeliveryType",
    "reportLangType",
    "reportType",
    "fullAddresses",
    "phoneNa"
})
@XmlSeeAlso({
    com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.GetPrivateOperationScanRq.ContactInfo.class
})
public class ContactInfoType {

    @XmlElement(name = "EmailAddr")
    protected String emailAddr;
    @XmlElement(name = "MessageDeliveryType")
    protected MessageDeliveryTypeType messageDeliveryType;
    @XmlElement(name = "ReportOrderType")
    protected ReportOrderTypeType reportOrderType;
    @XmlElement(name = "ContactData")
    protected List<ContactData> contactDatas;
    @XmlElement(name = "PostAddr")
    protected List<FullAddressType> postAddrs;
    @XmlElement(name = "ReportDeliveryType")
    protected ReportDeliveryTypeType reportDeliveryType;
    @XmlElement(name = "ReportLangType")
    protected ReportLangTypeType reportLangType;
    @XmlElement(name = "ReportType")
    protected ReportTypeType reportType;
    @XmlElement(name = "FullAddress")
    protected List<FullAddressIssueCardType> fullAddresses;
    @XmlElement(name = "PhoneNum")
    protected List<PhoneNumType> phoneNa;

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
     * Gets the value of the reportOrderType property.
     * 
     * @return
     *     possible object is
     *     {@link ReportOrderTypeType }
     *     
     */
    public ReportOrderTypeType getReportOrderType() {
        return reportOrderType;
    }

    /**
     * Sets the value of the reportOrderType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportOrderTypeType }
     *     
     */
    public void setReportOrderType(ReportOrderTypeType value) {
        this.reportOrderType = value;
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

    /**
     * Gets the value of the reportDeliveryType property.
     * 
     * @return
     *     possible object is
     *     {@link ReportDeliveryTypeType }
     *     
     */
    public ReportDeliveryTypeType getReportDeliveryType() {
        return reportDeliveryType;
    }

    /**
     * Sets the value of the reportDeliveryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportDeliveryTypeType }
     *     
     */
    public void setReportDeliveryType(ReportDeliveryTypeType value) {
        this.reportDeliveryType = value;
    }

    /**
     * Gets the value of the reportLangType property.
     * 
     * @return
     *     possible object is
     *     {@link ReportLangTypeType }
     *     
     */
    public ReportLangTypeType getReportLangType() {
        return reportLangType;
    }

    /**
     * Sets the value of the reportLangType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportLangTypeType }
     *     
     */
    public void setReportLangType(ReportLangTypeType value) {
        this.reportLangType = value;
    }

    /**
     * Gets the value of the reportType property.
     * 
     * @return
     *     possible object is
     *     {@link ReportTypeType }
     *     
     */
    public ReportTypeType getReportType() {
        return reportType;
    }

    /**
     * Sets the value of the reportType property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportTypeType }
     *     
     */
    public void setReportType(ReportTypeType value) {
        this.reportType = value;
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
     * {@link FullAddressIssueCardType }
     * 
     * 
     */
    public List<FullAddressIssueCardType> getFullAddresses() {
        if (fullAddresses == null) {
            fullAddresses = new ArrayList<FullAddressIssueCardType>();
        }
        return this.fullAddresses;
    }

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
     * {@link PhoneNumType }
     * 
     * 
     */
    public List<PhoneNumType> getPhoneNa() {
        if (phoneNa == null) {
            phoneNa = new ArrayList<PhoneNumType>();
        }
        return this.phoneNa;
    }

}
