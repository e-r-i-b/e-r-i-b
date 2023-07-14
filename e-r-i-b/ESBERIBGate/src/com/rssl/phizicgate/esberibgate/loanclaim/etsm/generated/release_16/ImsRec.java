
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация по ОМС
 * 
 * <p>Java class for ImsRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImsRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ImsAcctId"/>
 *         &lt;element ref="{}ImsAcctInfo" minOccurs="0"/>
 *         &lt;element ref="{}Status" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImsRec_Type", propOrder = {
    "imsAcctId",
    "imsAcctInfo",
    "status"
})
@XmlRootElement(name = "ImsRec")
public class ImsRec {

    @XmlElement(name = "ImsAcctId", required = true)
    protected ImsAcctId imsAcctId;
    @XmlElement(name = "ImsAcctInfo")
    protected ImsAcctInfo imsAcctInfo;
    @XmlElement(name = "Status")
    protected Status status;

    /**
     * Gets the value of the imsAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link ImsAcctId }
     *     
     */
    public ImsAcctId getImsAcctId() {
        return imsAcctId;
    }

    /**
     * Sets the value of the imsAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImsAcctId }
     *     
     */
    public void setImsAcctId(ImsAcctId value) {
        this.imsAcctId = value;
    }

    /**
     * Gets the value of the imsAcctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ImsAcctInfo }
     *     
     */
    public ImsAcctInfo getImsAcctInfo() {
        return imsAcctInfo;
    }

    /**
     * Sets the value of the imsAcctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ImsAcctInfo }
     *     
     */
    public void setImsAcctInfo(ImsAcctInfo value) {
        this.imsAcctInfo = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link Status }
     *     
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link Status }
     *     
     */
    public void setStatus(Status value) {
        this.status = value;
    }

}
