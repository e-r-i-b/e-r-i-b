
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип данных запроса на отправку email c ЭД клиенту ФЛ
 * 
 * <p>Java class for GetPrivateOperationScanRq_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPrivateOperationScanRq_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime"/>
 *         &lt;element name="SPName" type="{}SystemId_Type"/>
 *         &lt;element name="BankInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}BankInfo_Type">
 *                 &lt;sequence>
 *                   &lt;element name="RbTbBrchId" type="{}RbTbBrchType"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="VSPOperationInfo" type="{}VSPOperation_Type"/>
 *         &lt;element name="ContactInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}ContactInfo_Type">
 *                 &lt;sequence>
 *                   &lt;element name="EmailAddr" type="{}EMailAddr_Type"/>
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
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GetPrivateOperationScanRq_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "bankInfo",
    "vspOperationInfo",
    "contactInfo"
})
@XmlRootElement(name = "GetPrivateOperationScanRq")
public class GetPrivateOperationScanRq {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm", required = true)
    protected String rqTm;
    @XmlElement(name = "SPName", required = true)
    protected String spName;
    @XmlElement(name = "BankInfo", required = true)
    protected GetPrivateOperationScanRq.BankInfo bankInfo;
    @XmlElement(name = "VSPOperationInfo", required = true)
    protected VSPOperationType vspOperationInfo;
    @XmlElement(name = "ContactInfo", required = true)
    protected GetPrivateOperationScanRq.ContactInfo contactInfo;

    /**
     * Gets the value of the rqUID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqUID() {
        return rqUID;
    }

    /**
     * Sets the value of the rqUID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqUID(String value) {
        this.rqUID = value;
    }

    /**
     * Gets the value of the rqTm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRqTm() {
        return rqTm;
    }

    /**
     * Sets the value of the rqTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRqTm(String value) {
        this.rqTm = value;
    }

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSPName(String value) {
        this.spName = value;
    }

    /**
     * Gets the value of the bankInfo property.
     * 
     * @return
     *     possible object is
     *     {@link GetPrivateOperationScanRq.BankInfo }
     *     
     */
    public GetPrivateOperationScanRq.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPrivateOperationScanRq.BankInfo }
     *     
     */
    public void setBankInfo(GetPrivateOperationScanRq.BankInfo value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the vspOperationInfo property.
     * 
     * @return
     *     possible object is
     *     {@link VSPOperationType }
     *     
     */
    public VSPOperationType getVSPOperationInfo() {
        return vspOperationInfo;
    }

    /**
     * Sets the value of the vspOperationInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link VSPOperationType }
     *     
     */
    public void setVSPOperationInfo(VSPOperationType value) {
        this.vspOperationInfo = value;
    }

    /**
     * Gets the value of the contactInfo property.
     * 
     * @return
     *     possible object is
     *     {@link GetPrivateOperationScanRq.ContactInfo }
     *     
     */
    public GetPrivateOperationScanRq.ContactInfo getContactInfo() {
        return contactInfo;
    }

    /**
     * Sets the value of the contactInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPrivateOperationScanRq.ContactInfo }
     *     
     */
    public void setContactInfo(GetPrivateOperationScanRq.ContactInfo value) {
        this.contactInfo = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{}BankInfo_Type">
     *       &lt;sequence>
     *         &lt;element name="RbTbBrchId" type="{}RbTbBrchType"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class BankInfo
        extends BankInfoType
    {


    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{}ContactInfo_Type">
     *       &lt;sequence>
     *         &lt;element name="EmailAddr" type="{}EMailAddr_Type"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class ContactInfo
        extends ContactInfoType
    {


    }

}
