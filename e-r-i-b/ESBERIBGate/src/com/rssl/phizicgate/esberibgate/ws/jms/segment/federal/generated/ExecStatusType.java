
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация об исполнении автоплатежа в АС AutoPay
 * 
 * <p>Java class for ExecStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ExecStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ExecPaymentDate" type="{}DateTime"/>
 *         &lt;element name="NonExecReasonCode" type="{}C" minOccurs="0"/>
 *         &lt;element name="NonExecReasonDesc" type="{}C" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ExecStatus_Type", propOrder = {
    "execPaymentDate",
    "nonExecReasonCode",
    "nonExecReasonDesc"
})
public class ExecStatusType {

    @XmlElement(name = "ExecPaymentDate", required = true)
    protected String execPaymentDate;
    @XmlElement(name = "NonExecReasonCode")
    protected String nonExecReasonCode;
    @XmlElement(name = "NonExecReasonDesc")
    protected String nonExecReasonDesc;

    /**
     * Gets the value of the execPaymentDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExecPaymentDate() {
        return execPaymentDate;
    }

    /**
     * Sets the value of the execPaymentDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExecPaymentDate(String value) {
        this.execPaymentDate = value;
    }

    /**
     * Gets the value of the nonExecReasonCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonExecReasonCode() {
        return nonExecReasonCode;
    }

    /**
     * Sets the value of the nonExecReasonCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonExecReasonCode(String value) {
        this.nonExecReasonCode = value;
    }

    /**
     * Gets the value of the nonExecReasonDesc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNonExecReasonDesc() {
        return nonExecReasonDesc;
    }

    /**
     * Sets the value of the nonExecReasonDesc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNonExecReasonDesc(String value) {
        this.nonExecReasonDesc = value;
    }

}
