
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Инфромация о договоре
 * 
 * <p>Java class for AgreemtInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgreemtInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AgreemtType" type="{}String" minOccurs="0"/>
 *         &lt;choice>
 *           &lt;element ref="{}DepInfo"/>
 *           &lt;element ref="{}IMAInfo"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgreemtInfo_Type", propOrder = {
    "agreemtType",
    "imaInfo",
    "depInfo"
})
public class AgreemtInfoType {

    @XmlElement(name = "AgreemtType")
    protected String agreemtType;
    @XmlElement(name = "IMAInfo")
    protected IMAInfo imaInfo;
    @XmlElement(name = "DepInfo")
    protected DepInfo depInfo;

    /**
     * Gets the value of the agreemtType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtType() {
        return agreemtType;
    }

    /**
     * Sets the value of the agreemtType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtType(String value) {
        this.agreemtType = value;
    }

    /**
     * Информация об ОМС
     * 
     * @return
     *     possible object is
     *     {@link IMAInfo }
     *     
     */
    public IMAInfo getIMAInfo() {
        return imaInfo;
    }

    /**
     * Sets the value of the imaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAInfo }
     *     
     */
    public void setIMAInfo(IMAInfo value) {
        this.imaInfo = value;
    }

    /**
     * Информация о депозитном договоре
     * 
     * @return
     *     possible object is
     *     {@link DepInfo }
     *     
     */
    public DepInfo getDepInfo() {
        return depInfo;
    }

    /**
     * Sets the value of the depInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepInfo }
     *     
     */
    public void setDepInfo(DepInfo value) {
        this.depInfo = value;
    }

}
