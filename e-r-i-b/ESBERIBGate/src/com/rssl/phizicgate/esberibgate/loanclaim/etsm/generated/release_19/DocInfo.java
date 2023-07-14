
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_19;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Информация о переводе
 * 
 * <p>Java class for DocInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DocInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}DepAcctId"/>
 *         &lt;element name="Activity" type="{}String"/>
 *         &lt;element name="RecAcc" type="{}AcctIdType" minOccurs="0"/>
 *         &lt;element name="IsByCash" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocInfo_Type", propOrder = {
    "depAcctId",
    "activity",
    "recAcc",
    "isByCash"
})
@XmlRootElement(name = "DocInfo")
public class DocInfo {

    @XmlElement(name = "DepAcctId", required = true)
    protected DepAcctIdType depAcctId;
    @XmlElement(name = "Activity", required = true)
    protected String activity;
    @XmlElement(name = "RecAcc")
    protected String recAcc;
    @XmlElement(name = "IsByCash")
    protected Boolean isByCash;

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
     * Gets the value of the activity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Sets the value of the activity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivity(String value) {
        this.activity = value;
    }

    /**
     * Gets the value of the recAcc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecAcc() {
        return recAcc;
    }

    /**
     * Sets the value of the recAcc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecAcc(String value) {
        this.recAcc = value;
    }

    /**
     * Gets the value of the isByCash property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean getIsByCash() {
        return isByCash;
    }

    /**
     * Sets the value of the isByCash property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsByCash(Boolean value) {
        this.isByCash = value;
    }

}
