
package com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.rssl.phizic.utils.jaxb.CalendarDateAdapter;


/**
 * <p>Java class for CustRec_Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CustRec_Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustId" type="{}EribCustId_Type" minOccurs="0"/>
 *         &lt;element ref="{}CustInfo"/>
 *         &lt;element ref="{}ServiceInfo" minOccurs="0"/>
 *         &lt;element name="BnkAccRub" type="{}DepoBankAccount_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="BnkAccCur" type="{}DepoBankAccountCur_Type" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="AdditionalInfo" type="{}DepoBankAccountAdditionalInfo_Type" minOccurs="0"/>
 *         &lt;element name="StartDate" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="TaxIdFrom" type="{}TaxId_Type" minOccurs="0"/>
 *         &lt;element name="TarifPlanInfo" type="{}TarifPlanInfo_Type" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CustRec_Type", propOrder = {
    "custId",
    "custInfo",
    "serviceInfo",
    "bnkAccRubs",
    "bnkAccCurs",
    "additionalInfo",
    "startDate",
    "taxIdFrom",
    "tarifPlanInfo"
})
@XmlRootElement(name = "CustRec")
public class CustRec {

    @XmlElement(name = "CustId")
    protected String custId;
    @XmlElement(name = "CustInfo", required = true)
    protected CustInfo custInfo;
    @XmlElement(name = "ServiceInfo")
    protected ServiceInfo serviceInfo;
    @XmlElement(name = "BnkAccRub")
    protected List<DepoBankAccountType> bnkAccRubs;
    @XmlElement(name = "BnkAccCur")
    protected List<DepoBankAccountCurType> bnkAccCurs;
    @XmlElement(name = "AdditionalInfo")
    protected DepoBankAccountAdditionalInfoType additionalInfo;
    @XmlElement(name = "StartDate", type = String.class)
    @XmlJavaTypeAdapter(CalendarDateAdapter.class)
    @XmlSchemaType(name = "date")
    protected Calendar startDate;
    @XmlElement(name = "TaxIdFrom")
    protected String taxIdFrom;
    @XmlElement(name = "TarifPlanInfo")
    protected TarifPlanInfoType tarifPlanInfo;

    /**
     * Gets the value of the custId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustId() {
        return custId;
    }

    /**
     * Sets the value of the custId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustId(String value) {
        this.custId = value;
    }

    /**
     * Gets the value of the custInfo property.
     * 
     * @return
     *     possible object is
     *     {@link CustInfo }
     *     
     */
    public CustInfo getCustInfo() {
        return custInfo;
    }

    /**
     * Sets the value of the custInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustInfo }
     *     
     */
    public void setCustInfo(CustInfo value) {
        this.custInfo = value;
    }

    /**
     * Gets the value of the serviceInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceInfo }
     *     
     */
    public ServiceInfo getServiceInfo() {
        return serviceInfo;
    }

    /**
     * Sets the value of the serviceInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceInfo }
     *     
     */
    public void setServiceInfo(ServiceInfo value) {
        this.serviceInfo = value;
    }

    /**
     * Gets the value of the bnkAccRubs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bnkAccRubs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBnkAccRubs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoBankAccountType }
     * 
     * 
     */
    public List<DepoBankAccountType> getBnkAccRubs() {
        if (bnkAccRubs == null) {
            bnkAccRubs = new ArrayList<DepoBankAccountType>();
        }
        return this.bnkAccRubs;
    }

    /**
     * Gets the value of the bnkAccCurs property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bnkAccCurs property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBnkAccCurs().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DepoBankAccountCurType }
     * 
     * 
     */
    public List<DepoBankAccountCurType> getBnkAccCurs() {
        if (bnkAccCurs == null) {
            bnkAccCurs = new ArrayList<DepoBankAccountCurType>();
        }
        return this.bnkAccCurs;
    }

    /**
     * Gets the value of the additionalInfo property.
     * 
     * @return
     *     possible object is
     *     {@link DepoBankAccountAdditionalInfoType }
     *     
     */
    public DepoBankAccountAdditionalInfoType getAdditionalInfo() {
        return additionalInfo;
    }

    /**
     * Sets the value of the additionalInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link DepoBankAccountAdditionalInfoType }
     *     
     */
    public void setAdditionalInfo(DepoBankAccountAdditionalInfoType value) {
        this.additionalInfo = value;
    }

    /**
     * Gets the value of the startDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public Calendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDate(Calendar value) {
        this.startDate = value;
    }

    /**
     * Gets the value of the taxIdFrom property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxIdFrom() {
        return taxIdFrom;
    }

    /**
     * Sets the value of the taxIdFrom property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTaxIdFrom(String value) {
        this.taxIdFrom = value;
    }

    /**
     * Gets the value of the tarifPlanInfo property.
     * 
     * @return
     *     possible object is
     *     {@link TarifPlanInfoType }
     *     
     */
    public TarifPlanInfoType getTarifPlanInfo() {
        return tarifPlanInfo;
    }

    /**
     * Sets the value of the tarifPlanInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link TarifPlanInfoType }
     *     
     */
    public void setTarifPlanInfo(TarifPlanInfoType value) {
        this.tarifPlanInfo = value;
    }

}
