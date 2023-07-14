
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Тип Информация о договоре (ответ)
 * 
 * <p>Java class for AgreemtInfoResponse_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AgreemtInfoResponse_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;element name="DepInfo" type="{}DepInfoResponse_Type"/>
 *         &lt;element name="IMAInfo" type="{}IMAInfoResponse_Type"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AgreemtInfoResponse_Type", propOrder = {
    "imaInfo",
    "depInfo"
})
public class AgreemtInfoResponseType {

    @XmlElement(name = "IMAInfo")
    protected IMAInfoResponseType imaInfo;
    @XmlElement(name = "DepInfo")
    protected DepInfoResponseType depInfo;

    /**
     * Gets the value of the imaInfo property.
     * 
     * @return
     *     possible object is
     *     {@link IMAInfoResponseType }
     *     
     */
    public IMAInfoResponseType getIMAInfo() {
        return imaInfo;
    }

    /**
     * Sets the value of the imaInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link IMAInfoResponseType }
     *     
     */
    public void setIMAInfo(IMAInfoResponseType value) {
        this.imaInfo = value;
    }

    /**
     * Gets the value of the depInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepInfoResponseType }
     *     
     */
    public DepInfoResponseType getDepInfo() {
        return depInfo;
    }

    /**
     * Sets the value of the depInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepInfoResponseType }
     *     
     */
    public void setDepInfo(DepInfoResponseType value) {
        this.depInfo = value;
    }

}
