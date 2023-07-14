
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepAcct_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepAcct_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DepAcctId"/>
 *         &lt;element ref="{}DepAcctStmtGen" minOccurs="0"/>
 *         &lt;element ref="{}DepAcctStmtRec" maxOccurs="unbounded" minOccurs="0"/>
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
@XmlType(name = "DepAcct_Type", propOrder = {
    "depAcctId",
    "depAcctStmtGen",
    "depAcctStmtRecs",
    "status"
})
@XmlRootElement(name = "DepAcct")
public class DepAcct {

    @XmlElement(name = "DepAcctId", required = true)
    protected DepAcctIdType depAcctId;
    @XmlElement(name = "DepAcctStmtGen")
    protected DepAcctStmtGen depAcctStmtGen;
    @XmlElement(name = "DepAcctStmtRec")
    protected List<DepAcctStmtRec> depAcctStmtRecs;
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
     * Gets the value of the depAcctStmtGen property.
     * 
     * @return
     *     possible object is
     *     {@link DepAcctStmtGen }
     *     
     */
    public DepAcctStmtGen getDepAcctStmtGen() {
        return depAcctStmtGen;
    }

    /**
     * Sets the value of the depAcctStmtGen property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepAcctStmtGen }
     *     
     */
    public void setDepAcctStmtGen(DepAcctStmtGen value) {
        this.depAcctStmtGen = value;
    }

    /**
     * Gets the value of the depAcctStmtRecs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the depAcctStmtRecs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDepAcctStmtRecs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepAcctStmtRec }
     * 
     * 
     */
    public List<DepAcctStmtRec> getDepAcctStmtRecs() {
        if (depAcctStmtRecs == null) {
            depAcctStmtRecs = new ArrayList<DepAcctStmtRec>();
        }
        return this.depAcctStmtRecs;
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
