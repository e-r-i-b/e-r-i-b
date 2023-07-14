
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.math.BigInteger;
import java.util.Calendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * Метод обработки информации (ДЕПО)
 * 
 * <p>Java class for DepoRecMethodr_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoRecMethodr_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Method" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *         &lt;element name="AgreementDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="AgreementNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoRecMethodr_Type", propOrder = {
    "method",
    "agreementDate",
    "agreementNumber"
})
public class DepoRecMethodrType {

    @XmlElement(name = "Method", required = true)
    protected BigInteger method;
    @XmlElement(name = "AgreementDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar agreementDate;
    @XmlElement(name = "AgreementNumber")
    protected String agreementNumber;

    /**
     * Gets the value of the method property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMethod() {
        return method;
    }

    /**
     * Sets the value of the method property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMethod(BigInteger value) {
        this.method = value;
    }

    /**
     * Gets the value of the agreementDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getAgreementDate() {
        return agreementDate;
    }

    /**
     * Sets the value of the agreementDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementDate(Calendar value) {
        this.agreementDate = value;
    }

    /**
     * Gets the value of the agreementNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreementNumber() {
        return agreementNumber;
    }

    /**
     * Sets the value of the agreementNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreementNumber(String value) {
        this.agreementNumber = value;
    }

}
