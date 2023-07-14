
package com.rssl.phizicgate.mdm.integration.mdm.generated;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for LoanInfo_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LoanInfo_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{}ProdType" minOccurs="0"/>
 *         &lt;element ref="{}AgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}SupplAgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}LegalAgreemtNum" minOccurs="0"/>
 *         &lt;element ref="{}LegalName" minOccurs="0"/>
 *         &lt;element name="AcctId" type="{}AcctId_Identifier_Type" minOccurs="0"/>
 *         &lt;element name="StartDt" type="{}Date" minOccurs="0"/>
 *         &lt;element ref="{}ClosingDt" minOccurs="0"/>
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
@XmlType(name = "LoanInfo_Type", propOrder = {
    "prodType",
    "agreemtNum",
    "supplAgreemtNum",
    "legalAgreemtNum",
    "legalName",
    "acctId",
    "startDt",
    "closingDt",
    "custIds",
    "agreemtCustRoles"
})
@XmlRootElement(name = "LoanInfo")
public class LoanInfo {

    @XmlElement(name = "ProdType")
    protected String prodType;
    @XmlElement(name = "AgreemtNum")
    protected String agreemtNum;
    @XmlElement(name = "SupplAgreemtNum")
    protected String supplAgreemtNum;
    @XmlElement(name = "LegalAgreemtNum")
    protected String legalAgreemtNum;
    @XmlElement(name = "LegalName")
    protected String legalName;
    @XmlElement(name = "AcctId")
    protected String acctId;
    @XmlElement(name = "StartDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar startDt;
    @XmlElement(name = "ClosingDt", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    protected Calendar closingDt;
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
     * Номер договора
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAgreemtNum() {
        return agreemtNum;
    }

    /**
     * Sets the value of the agreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAgreemtNum(String value) {
        this.agreemtNum = value;
    }

    /**
     * Gets the value of the supplAgreemtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSupplAgreemtNum() {
        return supplAgreemtNum;
    }

    /**
     * Sets the value of the supplAgreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSupplAgreemtNum(String value) {
        this.supplAgreemtNum = value;
    }

    /**
     * Gets the value of the legalAgreemtNum property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalAgreemtNum() {
        return legalAgreemtNum;
    }

    /**
     * Sets the value of the legalAgreemtNum property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalAgreemtNum(String value) {
        this.legalAgreemtNum = value;
    }

    /**
     * Gets the value of the legalName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLegalName() {
        return legalName;
    }

    /**
     * Sets the value of the legalName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLegalName(String value) {
        this.legalName = value;
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
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(Calendar value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the closingDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getClosingDt() {
        return closingDt;
    }

    /**
     * Sets the value of the closingDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClosingDt(Calendar value) {
        this.closingDt = value;
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
