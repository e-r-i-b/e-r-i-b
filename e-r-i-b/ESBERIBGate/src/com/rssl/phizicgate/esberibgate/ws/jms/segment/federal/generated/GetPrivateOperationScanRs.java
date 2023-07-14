
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип данных результата выполнения запроса на отправку email c ЭД клиенту ФЛ перевод
 * 
 * <p>Java class for GetPrivateOperationScanRs_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GetPrivateOperationScanRs_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RqUID" type="{}RqUID_Type"/>
 *         &lt;element name="RqTm" type="{}DateTime" minOccurs="0"/>
 *         &lt;element name="SPName" type="{}SystemId_Type" minOccurs="0"/>
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
 *         &lt;element name="Status">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{}Status_Type">
 *                 &lt;sequence>
 *                   &lt;element name="StatusCode" type="{}StatusCode_Type"/>
 *                   &lt;element name="StatusDesc" type="{}StatusDesc_Type" minOccurs="0"/>
 *                   &lt;element name="ServerStatusDesc" type="{}ServerStatusDesc_Type" minOccurs="0"/>
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
@XmlType(name = "GetPrivateOperationScanRs_Type", propOrder = {
    "rqUID",
    "rqTm",
    "spName",
    "bankInfo",
    "status"
})
@XmlRootElement(name = "GetPrivateOperationScanRs")
public class GetPrivateOperationScanRs {

    @XmlElement(name = "RqUID", required = true)
    protected String rqUID;
    @XmlElement(name = "RqTm")
    protected String rqTm;
    @XmlElement(name = "SPName")
    protected String spName;
    @XmlElement(name = "BankInfo", required = true)
    protected GetPrivateOperationScanRs.BankInfo bankInfo;
    @XmlElement(name = "Status", required = true)
    protected GetPrivateOperationScanRs.Status status;

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
     *     {@link GetPrivateOperationScanRs.BankInfo }
     *     
     */
    public GetPrivateOperationScanRs.BankInfo getBankInfo() {
        return bankInfo;
    }

    /**
     * Sets the value of the bankInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPrivateOperationScanRs.BankInfo }
     *     
     */
    public void setBankInfo(GetPrivateOperationScanRs.BankInfo value) {
        this.bankInfo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link GetPrivateOperationScanRs.Status }
     *     
     */
    public GetPrivateOperationScanRs.Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetPrivateOperationScanRs.Status }
     *     
     */
    public void setStatus(GetPrivateOperationScanRs.Status value) {
        this.status = value;
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
     *     &lt;restriction base="{}Status_Type">
     *       &lt;sequence>
     *         &lt;element name="StatusCode" type="{}StatusCode_Type"/>
     *         &lt;element name="StatusDesc" type="{}StatusDesc_Type" minOccurs="0"/>
     *         &lt;element name="ServerStatusDesc" type="{}ServerStatusDesc_Type" minOccurs="0"/>
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
    public static class Status
        extends StatusType
    {


    }

}
