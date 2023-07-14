
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись о счете депо
 * 
 * <p>Java class for DepoAcctRes_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoAcctRes_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DepoAcctId"/>
 *         &lt;element ref="{}AcctBal"/>
 *         &lt;element name="AcctInfo" type="{}DepoAcctInfo_Type"/>
 *         &lt;element ref="{}CustRec"/>
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
@XmlType(name = "DepoAcctRes_Type", propOrder = {
    "depoAcctId",
    "acctBal",
    "acctInfo",
    "custRec",
    "status"
})
public class DepoAcctResType {

    @XmlElement(name = "DepoAcctId", required = true)
    protected DepoAcctId depoAcctId;
    @XmlElement(name = "AcctBal", required = true)
    protected AcctBal acctBal;
    @XmlElement(name = "AcctInfo", required = true)
    protected DepoAcctInfoType acctInfo;
    @XmlElement(name = "CustRec", required = true)
    protected CustRec custRec;
    @XmlElement(name = "Status", required = true)
    protected Status status;

    /**
     * Gets the value of the depoAcctId property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAcctId }
     *     
     */
    public DepoAcctId getDepoAcctId() {
        return depoAcctId;
    }

    /**
     * Sets the value of the depoAcctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAcctId }
     *     
     */
    public void setDepoAcctId(DepoAcctId value) {
        this.depoAcctId = value;
    }

    /**
     * Gets the value of the acctBal property.
     * 
     * @return
     *     possible object is
     *     {@link AcctBal }
     *     
     */
    public AcctBal getAcctBal() {
        return acctBal;
    }

    /**
     * Sets the value of the acctBal property.
     * 
     * @param value
     *     allowed object is
     *     {@link AcctBal }
     *     
     */
    public void setAcctBal(AcctBal value) {
        this.acctBal = value;
    }

    /**
     * Gets the value of the acctInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepoAcctInfoType }
     *     
     */
    public DepoAcctInfoType getAcctInfo() {
        return acctInfo;
    }

    /**
     * Sets the value of the acctInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoAcctInfoType }
     *     
     */
    public void setAcctInfo(DepoAcctInfoType value) {
        this.acctInfo = value;
    }

    /**
     * Gets the value of the custRec property.
     * 
     * @return
     *     possible object is
     *     {@link CustRec }
     *     
     */
    public CustRec getCustRec() {
        return custRec;
    }

    /**
     * Sets the value of the custRec property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustRec }
     *     
     */
    public void setCustRec(CustRec value) {
        this.custRec = value;
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
