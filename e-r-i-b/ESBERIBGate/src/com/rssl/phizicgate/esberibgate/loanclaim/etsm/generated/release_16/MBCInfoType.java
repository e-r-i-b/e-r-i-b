
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация об услуге мобильный банк
 * 
 * <p>Java class for MBCInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MBCInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ContractType" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="PhoneNum" type="{}PhoneNum_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MBCInfo_Type", propOrder = {
    "status",
    "contractType",
    "phoneNum"
})
public class MBCInfoType {

    @XmlElement(name = "Status")
    protected boolean status;
    @XmlElement(name = "ContractType")
    protected Long contractType;
    @XmlElement(name = "PhoneNum")
    protected PhoneNumType phoneNum;

    /**
     * Gets the value of the status property.
     * 
     */
    public boolean isStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(boolean value) {
        this.status = value;
    }

    /**
     * Gets the value of the contractType property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setContractType(Long value) {
        this.contractType = value;
    }

    /**
     * Gets the value of the phoneNum property.
     * 
     * @return
     *     possible object is
     *     {@link PhoneNumType }
     *     
     */
    public PhoneNumType getPhoneNum() {
        return phoneNum;
    }

    /**
     * Sets the value of the phoneNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link PhoneNumType }
     *     
     */
    public void setPhoneNum(PhoneNumType value) {
        this.phoneNum = value;
    }

}
