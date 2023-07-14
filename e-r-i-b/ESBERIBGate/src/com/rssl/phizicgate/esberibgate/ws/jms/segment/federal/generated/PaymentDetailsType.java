
package com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Платежные реквизиты
 * 
 * <p>Java class for PaymentDetails_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PaymentDetails_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BIC" type="{}BIC_Type" minOccurs="0"/>
 *         &lt;element name="CorrAcctNum" type="{}AcctId_NC_Type" minOccurs="0"/>
 *         &lt;element name="AcctNum" type="{}AcctId_NC_Type" minOccurs="0"/>
 *         &lt;element name="InterfilialAcctNum" type="{}AcctIdType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentDetails_Type", propOrder = {
    "bic",
    "corrAcctNum",
    "acctNum",
    "interfilialAcctNum"
})
public class PaymentDetailsType {

    @XmlElement(name = "BIC")
    protected String bic;
    @XmlElement(name = "CorrAcctNum")
    protected String corrAcctNum;
    @XmlElement(name = "AcctNum")
    protected String acctNum;
    @XmlElement(name = "InterfilialAcctNum")
    protected String interfilialAcctNum;

    /**
     * Gets the value of the bic property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBIC() {
        return bic;
    }

    /**
     * Sets the value of the bic property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBIC(String value) {
        this.bic = value;
    }

    /**
     * Gets the value of the corrAcctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCorrAcctNum() {
        return corrAcctNum;
    }

    /**
     * Sets the value of the corrAcctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCorrAcctNum(String value) {
        this.corrAcctNum = value;
    }

    /**
     * Gets the value of the acctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctNum() {
        return acctNum;
    }

    /**
     * Sets the value of the acctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctNum(String value) {
        this.acctNum = value;
    }

    /**
     * Gets the value of the interfilialAcctNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfilialAcctNum() {
        return interfilialAcctNum;
    }

    /**
     * Sets the value of the interfilialAcctNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfilialAcctNum(String value) {
        this.interfilialAcctNum = value;
    }

}
