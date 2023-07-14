
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SourceId_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SourceId_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceIdType">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="32"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="VSPInfo" type="{}BankInfoLeadZero_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SourceId_Type", propOrder = {
    "sourceIdType",
    "vspInfo"
})
public class SourceIdType {

    @XmlElement(name = "SourceIdType", required = true)
    protected String sourceIdType;
    @XmlElement(name = "VSPInfo")
    protected BankInfoLeadZeroType vspInfo;

    /**
     * Gets the value of the sourceIdType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceIdType() {
        return sourceIdType;
    }

    /**
     * Sets the value of the sourceIdType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceIdType(String value) {
        this.sourceIdType = value;
    }

    /**
     * Gets the value of the vspInfo property.
     * 
     * @return
     *     possible object is
     *     {@link BankInfoLeadZeroType }
     *     
     */
    public BankInfoLeadZeroType getVSPInfo() {
        return vspInfo;
    }

    /**
     * Sets the value of the vspInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankInfoLeadZeroType }
     *     
     */
    public void setVSPInfo(BankInfoLeadZeroType value) {
        this.vspInfo = value;
    }

}
