
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Статус счета
 * 
 * <p>Java class for BankAcctStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BankAcctStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}BankAcctStatusCode"/>
 *         &lt;element ref="{}StatusDesc" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankAcctStatus_Type", propOrder = {
    "bankAcctStatusCode",
    "statusDesc"
})
@XmlRootElement(name = "BankAcctStatus")
public class BankAcctStatus {

    @XmlElement(name = "BankAcctStatusCode", required = true)
    protected String bankAcctStatusCode;
    @XmlElement(name = "StatusDesc")
    protected String statusDesc;

    /**
     * Код статуса
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBankAcctStatusCode() {
        return bankAcctStatusCode;
    }

    /**
     * Sets the value of the bankAcctStatusCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBankAcctStatusCode(String value) {
        this.bankAcctStatusCode = value;
    }

    /**
     * Описание статуса
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * Sets the value of the statusDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatusDesc(String value) {
        this.statusDesc = value;
    }

}
