
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Состояние потребителя
 * 
 * <p>Java class for CustStatus_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustStatus_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SPName" type="{}SPName_Type" minOccurs="0"/>
 *         &lt;element name="CustStatusCode" type="{}StatusCode_Type"/>
 *         &lt;element name="StatusDesc" type="{}StatusDesc_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustStatus_Type", propOrder = {
    "spName",
    "custStatusCode",
    "statusDesc"
})
public class CustStatusType {

    @XmlElement(name = "SPName")
    protected SPNameType spName;
    @XmlElement(name = "CustStatusCode")
    protected long custStatusCode;
    @XmlElement(name = "StatusDesc")
    protected String statusDesc;

    /**
     * Gets the value of the spName property.
     * 
     * @return
     *     possible object is
     *     {@link SPNameType }
     *     
     */
    public SPNameType getSPName() {
        return spName;
    }

    /**
     * Sets the value of the spName property.
     * 
     * @param value
     *     allowed object is
     *     {@link SPNameType }
     *     
     */
    public void setSPName(SPNameType value) {
        this.spName = value;
    }

    /**
     * Gets the value of the custStatusCode property.
     * 
     */
    public long getCustStatusCode() {
        return custStatusCode;
    }

    /**
     * Sets the value of the custStatusCode property.
     * 
     */
    public void setCustStatusCode(long value) {
        this.custStatusCode = value;
    }

    /**
     * Gets the value of the statusDesc property.
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
