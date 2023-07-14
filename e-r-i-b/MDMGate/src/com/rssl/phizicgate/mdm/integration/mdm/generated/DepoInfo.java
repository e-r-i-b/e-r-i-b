
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DepoInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DepoInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProdType" minOccurs="0"/>
 *         &lt;element name="AcctId" type="{}AcctId_Identifier_Type" minOccurs="0"/>
 *         &lt;element ref="{}NotifAddress" minOccurs="0"/>
 *         &lt;element ref="{}CustId" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtCustRole" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepoInfo_Type", propOrder = {
    "prodType",
    "acctId",
    "notifAddress",
    "custIds",
    "agreemtCustRoles"
})
@XmlRootElement(name = "DepoInfo")
public class DepoInfo {

    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "NotifAddress")
    protected String notifAddress;
    @XmlElement(name = "CustId")
    protected List<CustId> custIds;
    @XmlElement(name = "AgreemtCustRole")
    protected List<AgreemtCustRole> agreemtCustRoles;

    /**
     * Gets the value of the prodType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProdType() {
        return prodType;
    }

    /**
     * Sets the value of the prodType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProdType(String value) {
        this.prodType = value;
    }

    /**
     * Gets the value of the acctId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctId() {
        return acctId;
    }

    /**
     * Sets the value of the acctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAcctId(String value) {
        this.acctId = value;
    }

    /**
     * Gets the value of the notifAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotifAddress() {
        return notifAddress;
    }

    /**
     * Sets the value of the notifAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotifAddress(String value) {
        this.notifAddress = value;
    }

    /**
     * Gets the value of the custIds property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the custIds property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCustIds().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CustId }
     * 
     * 
     */
    public List<CustId> getCustIds() {
        if (custIds == null) {
            custIds = new ArrayList<CustId>();
        }
        return this.custIds;
    }

    /**
     * Gets the value of the agreemtCustRoles property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the agreemtCustRoles property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAgreemtCustRoles().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AgreemtCustRole }
     * 
     * 
     */
    public List<AgreemtCustRole> getAgreemtCustRoles() {
        if (agreemtCustRoles == null) {
            agreemtCustRoles = new ArrayList<AgreemtCustRole>();
        }
        return this.agreemtCustRoles;
    }

}
