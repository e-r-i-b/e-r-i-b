
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Основание операции - описание договора (ДЕПО).
 * 
 * <p>Java class for TransferRcpInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransferRcpInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CorrDepositary" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrDepoAcctId" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrOwner" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CorrOwnerDetail" type="{}CorrOwnerDetail_Type" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="DeliveryType" type="{}DepoDeliveryType_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransferRcpInfo_Type", propOrder = {
    "corrDepositary",
    "corrDepoAcctId",
    "corrOwner",
    "corrOwnerDetail",
    "additionalInfo",
    "deliveryType"
})
public class TransferRcpInfoType {

    @XmlElement(name = "CorrDepositary")
    protected String corrDepositary;
    @XmlElement(name = "CorrDepoAcctId")
    protected String corrDepoAcctId;
    @XmlElement(name = "CorrOwner")
    protected String corrOwner;
    @XmlElement(name = "CorrOwnerDetail")
    protected CorrOwnerDetailType corrOwnerDetail;
    @XmlElement(name = "AdditionalInfo")
    protected String additionalInfo;
    @XmlElement(name = "DeliveryType")
    protected String deliveryType;

    /**
     * Gets the value of the corrDepositary property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrDepositary() {
        return corrDepositary;
    }

    /**
     * Sets the value of the corrDepositary property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrDepositary(String value) {
        this.corrDepositary = value;
    }

    /**
     * Gets the value of the corrDepoAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrDepoAcctId() {
        return corrDepoAcctId;
    }

    /**
     * Sets the value of the corrDepoAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrDepoAcctId(String value) {
        this.corrDepoAcctId = value;
    }

    /**
     * Gets the value of the corrOwner property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrOwner() {
        return corrOwner;
    }

    /**
     * Sets the value of the corrOwner property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrOwner(String value) {
        this.corrOwner = value;
    }

    /**
     * Gets the value of the corrOwnerDetail property.
     * 
     * @return
     *     possible object is
     *     {@link CorrOwnerDetailType }
     *     
     */
    public CorrOwnerDetailType getCorrOwnerDetail() {
        return corrOwnerDetail;
    }

    /**
     * Sets the value of the corrOwnerDetail property.
     * 
     * @param value
     *     allowed object is
     *     {@link CorrOwnerDetailType }
     *     
     */
    public void setCorrOwnerDetail(CorrOwnerDetailType value) {
        this.corrOwnerDetail = value;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdditionalInfo(String value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the deliveryType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeliveryType() {
        return deliveryType;
    }

    /**
     * Sets the value of the deliveryType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeliveryType(String value) {
        this.deliveryType = value;
    }

}
