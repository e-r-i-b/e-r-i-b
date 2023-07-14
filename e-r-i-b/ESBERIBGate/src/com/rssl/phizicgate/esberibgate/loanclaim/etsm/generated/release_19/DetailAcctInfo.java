
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Детальная информация по вкладу
 * 
 * <p>Java class for DetailAcctInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DetailAcctInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DepAcctId"/>
 *         &lt;element ref="{}DepAccInfo"/>
 *         &lt;element ref="{}Status"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailAcctInfo_Type", propOrder = {
    "depAcctId",
    "depAccInfo",
    "status"
})
@XmlRootElement(name = "DetailAcctInfo")
public class DetailAcctInfo {

    @XmlElement(name = "DepAcctId", required = true)
    protected DepAcctIdType depAcctId;
    @XmlElement(name = "DepAccInfo", required = true)
    protected DepAccInfo depAccInfo;
    @XmlElement(name = "Status", required = true)
    protected Status status;

    /**
     * Gets the value of the depAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctIdType }
     *     
     */
    public DepAcctIdType getDepAcctId() {
        return depAcctId;
    }

    /**
     * Sets the value of the depAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctIdType }
     *     
     */
    public void setDepAcctId(DepAcctIdType value) {
        this.depAcctId = value;
    }

    /**
     * Gets the value of the depAccInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepAccInfo }
     *     
     */
    public DepAccInfo getDepAccInfo() {
        return depAccInfo;
    }

    /**
     * Sets the value of the depAccInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAccInfo }
     *     
     */
    public void setDepAccInfo(DepAccInfo value) {
        this.depAccInfo = value;
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
