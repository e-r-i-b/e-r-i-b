
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Запись о счете депо
 * 
 * <p>Java class for DepoDeptRes_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoDeptRes_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DepoAcctId"/>
 *         &lt;element ref="{}AcctBal" minOccurs="0"/>
 *         &lt;element name="DepoAcctBalRec" type="{}DepoAcctBalRec_Type" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DepoDeptRes_Type", propOrder = {
    "depoAcctId",
    "acctBal",
    "depoAcctBalRecs",
    "status"
})
public class DepoDeptResType {

    @XmlElement(name = "DepoAcctId", required = true)
    protected DepoAcctId depoAcctId;
    @XmlElement(name = "AcctBal")
    protected AcctBal acctBal;
    @XmlElement(name = "DepoAcctBalRec")
    protected List<DepoAcctBalRecType> depoAcctBalRecs;
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
     * Gets the value of the depoAcctBalRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depoAcctBalRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepoAcctBalRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoAcctBalRecType }
     * 
     * 
     */
    public List<DepoAcctBalRecType> getDepoAcctBalRecs() {
        if (depoAcctBalRecs == null) {
            depoAcctBalRecs = new ArrayList<DepoAcctBalRecType>();
        }
        return this.depoAcctBalRecs;
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
