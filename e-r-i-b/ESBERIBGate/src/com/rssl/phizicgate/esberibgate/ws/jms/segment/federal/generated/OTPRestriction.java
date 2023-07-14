
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип для блока "Группа ограничений для одноразовых паролей (OTP)"
 * 
 * <p>Java class for OTPRestriction_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OTPRestriction_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OTPGet" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="OTPUse" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OTPRestriction_Type", propOrder = {
    "otpGet",
    "otpUse"
})
@XmlRootElement(name = "OTPRestriction")
public class OTPRestriction {

    @XmlElement(name = "OTPGet")
    protected boolean otpGet;
    @XmlElement(name = "OTPUse")
    protected Boolean otpUse;

    /**
     * Gets the value of the otpGet property.
     * 
     */
    public boolean isOTPGet() {
        return otpGet;
    }

    /**
     * Sets the value of the otpGet property.
     * 
     */
    public void setOTPGet(boolean value) {
        this.otpGet = value;
    }

    /**
     * Gets the value of the otpUse property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getOTPUse() {
        return otpUse;
    }

    /**
     * Sets the value of the otpUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setOTPUse(Boolean value) {
        this.otpUse = value;
    }

}
